package android.example.xinxi.monkeytest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashTest/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".log";
    private static CrashHandler sInstance = new CrashHandler();
    private UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private static final String CrashTime = "发生时间";
    private static final String UpLoadTime = "上报时间";
    private static final String UserId = "用户 ID";
    private static final String AppName = "应用包名";
    private static final String AppVersion = "应用版本";
    private static final String UseTime = "使用时长";
    private static final String AppStatus = "前后台状态";
    private static final String DeviceModel = "设备机型";
    private static final String DeviceVersion = "系统版本";
    private static final String DeviceSERIAL = "设备号";
    private static final String DeviceRom = "ROM";
    private static final String CPU = "CPU架构";
    private static final String StackTrace = "堆栈";



    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     *
     * 这个方法的回调，该方法运行在发生crash的那个线程。如果UI线程crash，就运行在UI线程
     * 如果发生在子线程，就运行在子线程；因此不可以在这个方法中弹出toast或者dialog（因为这个线程的消息循环已经被破坏了）
     * 但是可以跳转到一个Activity，在这个Acitivty中弹dialog或者toast
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer();
            //这里可以通过网络上传异常信息到服务器（完成下面的方法uploadExceptionToServer），便于开发人员分析日志从而解决bug
        } catch (IOException e) {
            e.printStackTrace();
        }

        ex.printStackTrace();
        // 发生crash之后，需要将进程杀掉，因为此时程序不能继续往下运行，程序状态已不对
        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }

    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(current));
        String crashPath = PATH + FILE_NAME + time + FILE_NAME_SUFFIX;
        File file = new File(crashPath);
        Log.d(TAG,"写入崩溃文件地址:" + crashPath);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(CrashTime + ":" + time);
            dumpPhoneInfo(pw);
            pw.println(StackTrace + ":");
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //app包名
        pw.println(AppName + ":" + pi.packageName);
        //app版本号
        pw.println(AppVersion + ":" + pi.versionName + '_' + pi.versionCode);
        //android版本号
        pw.println(DeviceVersion + ":" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        //设备号
        pw.println(DeviceSERIAL + ":" + Build.SERIAL);
        //手机型号
        pw.println(DeviceModel + ":" + Build.MANUFACTURER);
        //cpu架构
        pw.println(CPU + ":" + Build.CPU_ABI);
    }

    private void uploadExceptionToServer() {
        //TODO Upload Exception Message To Your Web Server
    }


//    /**
//     * 崩溃文件扫描
//     */
//    private void crashScanner(){
//        FileTools fileTools = new FileTools();
//        List filelist = fileTools.getFiles(PATH);
//        try {
//            if (filelist.size() != 0) {
//                for (Object filepath : filelist) {
//                    JSONObject body = this.reslovefile(filepath.toString());
//                    if (body.length()!=0){
//                        if (service.reqBlockInfo(body).toString().contains("ok")) {
//                            fileTools.delFiles(filepath.toString());
//                            Log.d(TAG, String.format("上传日志:%s成功", filepath));
//                        } else {
//                            Log.d(TAG, String.format("上传日志:%s失败", filepath));
//                            continue;
//                        }
//                    }
//                }
//            } else {
//                Log.d(TAG, sdcardpath + "未找到文件!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



}

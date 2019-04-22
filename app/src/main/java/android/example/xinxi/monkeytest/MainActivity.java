package android.example.xinxi.monkeytest;

import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.ViewStateListener;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private UiautomatorThread uiautomatorThread;
    private Button startButton;
    private Button stopButton;
    private Context context;
    private static String TAG = "MonkeyTest";
    private static String packageName = "android.example.xinxi.monkeytest.test";
    private static String runClassName = "android.support.test.runner.AndroidJUnitRunner";
    public volatile boolean exit = false;
    public static String runtime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button)findViewById(R.id.startbutton);
        stopButton = (Button)findViewById(R.id.stopbutton);
        setFloatWindow();




    }


    /** * 点击按钮对应的方法
     *  @param v
     *
     *  */
    public void runMyUiautomator(View v){
        EditText editText1 =(EditText)findViewById(R.id.runTimeText);
        runtime = editText1.getText().toString();
        Log.i(TAG, runtime);
        Log.i(TAG, "runMyUiautomator!!!");
        uiautomatorThread= new UiautomatorThread(runtime);
        uiautomatorThread.start();
    }

    /** * 点击按钮对应的方法
     *  @param v
     *  */
    public void stopMyUiautomator(View v){

        try {
            uiautomatorThread.pauseThread();
            Log.i(TAG, "stopMyUiautomator!!!");
            //uiautomatorThread.interrupt();
            //uiautomatorThread.join();
            //Toast showToast = Toast.makeText(MainActivity.this, "停止Monkey任务", Toast.LENGTH_SHORT);
            //showToast.setGravity(Gravity.CENTER, 0, 0);
            //showToast.show();
        }catch (Exception e){
            Log.e(TAG, e.toString());
            Toast showToast = Toast.makeText(MainActivity.this, "Monkey任务不存在", Toast.LENGTH_SHORT);
            showToast.setGravity(Gravity.CENTER, 0, 0);
            showToast.show();
        }

        //if(! uiautomatorThread.isAlive()){
        //    Toast showToast = Toast.makeText(MainActivity.this, "MyUiautomator任务不存在!!!", Toast.LENGTH_SHORT);
        //    showToast.setGravity(Gravity.CENTER, 0, 0);
        //    showToast.show();
        //}else {
        //    Log.i(TAG, "stopMyUiautomator!!!");
        //    uiautomatorThread.stop();
        //}
    }

    /** * 设置悬浮框
     *  @param
     *  */
    public void setFloatWindow(){
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.play);
        FloatWindow
                .with(getApplicationContext())
                .setView(imageView)
                .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.2f)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.3f)
                //.setMoveType(MoveType.slide,100,-100)
                .setMoveStyle(500, new BounceInterpolator())
                //.setFilter(true, MainActivity.class, MainActivity.class) 不设置就是全局展示
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i(TAG, "stopMyUiautomator!!!");
                    uiautomatorThread.stop();
                    Toast showToast = Toast.makeText(MainActivity.this, "停止Monkey任务", Toast.LENGTH_SHORT);
                    showToast.setGravity(Gravity.CENTER, 0, 0);
                    showToast.show();
                }catch (Exception e){
                    Log.e(TAG, e.toString());
                    Toast showToast = Toast.makeText(MainActivity.this, "Monkey任务不存在", Toast.LENGTH_SHORT);
                    showToast.setGravity(Gravity.CENTER, 0, 0);
                    showToast.show();
                }
            }
        });

    }


    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };





    /** *
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     *
     * */
    class UiautomatorThread extends Thread {


        private final Object lock = new Object();
        private boolean pause = false;
        private String runtime;

        public UiautomatorThread(String runtime){
            this.runtime = runtime;

        }


        /**
         * 调用这个方法实现暂停线程
         */
        void pauseThread() {
            pause = true;
        }


        /**
         * 调用这个方法实现恢复线程的运行
         */
        void resumeThread() {
            pause = false;
            synchronized (lock) {
                lock.notifyAll();
            }
        }


        /**
         * 注意：这个方法只能在run方法里调用，不然会阻塞主线程，导致页面无响应
         */
        void onPause() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            super.run();
            String command = generateCommand("android.example.xinxi.monkeytest", "ExampleInstrumentedTest", "useAppContext");
            CMDUtils.CMD_Result rs= CMDUtils.runCMD(command,true,true);
            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }


        /**
         * 生成命令
         * @param pkgName uiautomator包名
         * @param clsName uiautomator类名
         * @param mtdName uiautomator方法名
         * @return
         */
        public  String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument -w -r -e runtime " + this.runtime +" -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e(TAG,command);
            return command;
        }


    }


    class CrashThread extends Thread {
        @Override
        public void run() {
            super.run();
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());

        }
    }
}

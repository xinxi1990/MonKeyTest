package android.example.xinxi.monkeytest;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MonkeyTest {

    private static String TAG= "MonkeyTest";
    private String mPackageName="com.luojilab.player";
    public static final String lanuchActivity = "com.luojilab.business.welcome.SplashActivity";
    public UiDevice mDevice;
    private int width,height, submitX_mim,
            submitX_max, submitY_mim,
            submitY_max, contentX_min,
            contentX_max, contentY_mim,
            contentY_max, special_point_x,
            special_point_y;

    private UiObject uiObject;
    private boolean isRunning = true;
    private int eventTabcount = 0;
    private int eventSwipecount = 0;
    private int eventBackcount = 0;
    private int eventHomecount = 0;
    private int eventSubmitcount = 0;
    private int eventXPATHcount = 0;
    private static long eventWaitTime = 500;
    private int TIMING = 2;



    @Test
    public void runCase() throws Exception {
        Map coordinateMap = calculateCoordinate();
        Log.i(TAG,"本次设定的运行时长【" + TIMING + "】分钟");
        long startTime = System.currentTimeMillis();
        try {
            while (isRunning) {
                switch (new MathRandom().PercentageRandom()) {
                    case 0: {
                        int x = (int) Math.ceil(Math.random() * (width - 1));
                        int y = (int) Math.ceil(Math.random() * (height - 1));
                        new MonkeyTapEvent(mDevice, x, y).injectEvent();
                        eventTabcount = eventTabcount+1;
                        Log.i(TAG,"---【 点击事件 】执行了："+eventTabcount+"次---");
                        break;
                    }
                    case 1: {
                        new MonkeySwipeEvent(mDevice).injectEvent_scrollDown();
                        eventSwipecount = eventSwipecount+1;
                        Log.i(TAG,"---【 滚动事件 】执行了："+eventSwipecount+"次---");
                        break;
                    }
                    case 2: {
                        int x = randomSubmitCoordinate(coordinateMap)[0];
                        int y = randomSubmitCoordinate(coordinateMap)[1];
                        new MonkeySubmitEvent(mDevice, x, y).injectEvent();
                        eventSubmitcount = eventSubmitcount+1;
                        Log.i(TAG,"---【 输入事件 】执行了："+eventSubmitcount+"次---");
                        break;
                    }
                    case 3: {
                        new MonkeyBackEvent(mDevice).injectEvent();
                        eventBackcount = eventBackcount+1;
                        Log.i(TAG,"---【 返回事件 】执行了："+eventBackcount+"次---");
                        break;
                    }
                }
                long endTime = System.currentTimeMillis();
                if((endTime - startTime) > (TIMING) * 60 * 1000) {
                    Log.i(TAG,"已运行" + (endTime - startTime)/60/1000 + "分钟，任务即将结束");
                    Log.i(TAG,"退出守护线程!");
                    isRunning = false;

                }else {
                    Thread.sleep(eventWaitTime);
                    long runTime = (endTime - startTime) / 1000;
                    String p = getRunProgress(TIMING* 60, (int)runTime);
                    Log.i(TAG,String.format("当前运行进度:%s",p));
                }
              }
            }catch (Exception E){
                Log.i(TAG,this.getClass().getName() + "-" + "Run异常:" + E);
            }
    }




    public void clickAllow() throws UiObjectNotFoundException, IOException {
        uiObject = mDevice.findObject(new UiSelector().text("允许"));
        if (uiObject.exists()){
            uiObject.click();
            Log.i(TAG, "点击【允许】");
        }
        if (uiObject.exists()){
            uiObject.click();
            Log.i(TAG, "点击【允许】");
        }

    }


    @Before
    public void beforeCase() throws UiObjectNotFoundException, IOException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            if(!mDevice.isScreenOn()){
                mDevice.wakeUp();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        startAPP(mPackageName);
        mDevice.waitForWindowUpdate(mPackageName, 5 * 1000);//等待app
        clickAllow();
    }

    @After
    public void atferCase(){
//        closeAPP(mDevice,mPackageName);
    }


    /**
     * 通过Intent启动app
     * @param sPackageName
     */
    private void startAPP(String sPackageName){
        Context mContext = InstrumentationRegistry.getContext();
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(sPackageName);
        mContext.startActivity(myIntent);
        Log.i(TAG, "app启动中...");
    }

    /**
     * 通过命令行关闭app
     * @param sPackageName
     */
//    private void closeAPP(UiDevice uiDevice,String sPackageName){
//        Log.i(TAG, "关闭app...");
//        try {
//            uiDevice.executeShellCommand("am force-stop "+sPackageName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /***
     * 通过命令行启动app
     * @param uiDevice
     * @param sPackageName
     * @param sLaunchActivity
     */
    private void startAPP(UiDevice uiDevice,String sPackageName, String sLaunchActivity){
        try {
            uiDevice.executeShellCommand("am start -n "+sPackageName+"/"+sLaunchActivity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 计算坐标
     */
    private Map calculateCoordinate() throws Exception {
        width= mDevice.getDisplayWidth();
        height= mDevice.getDisplayHeight();
        Log.i(TAG, String.format("屏幕尺寸:%s,%s",width,height));
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        submitX_max = width - 1;
        submitX_mim = width / 10;
        submitY_max = height - 1;
        submitY_mim = height / 10 * 9;
        contentX_max = width - width / 10;
        contentX_min = width / 10;
        contentY_max = height / 2 + height / 10;
        contentY_mim = height / 2 - height / 10;
        special_point_x = width / 2;
        special_point_y = (int) (height * 0.94);
        Map Coordinatemap = new HashMap();
        Coordinatemap.put("submitX_max",submitX_max);
        Coordinatemap.put("submitX_mim",submitX_mim);
        Coordinatemap.put("submitY_max",submitY_max);
        Coordinatemap.put("submitY_mim",submitY_mim);
        Coordinatemap.put("contentX_max",contentX_max);
        Coordinatemap.put("contentX_min",contentX_min);
        Coordinatemap.put("contentY_max",contentY_max);
        Coordinatemap.put("contentY_mim",contentY_mim);
        Coordinatemap.put("special_point_x",special_point_x);
        Coordinatemap.put("special_point_y",special_point_y);
        return Coordinatemap;
    }

    /**
     * 计算随机坐标
     */
    private int[] randomSubmitCoordinate(Map coordinateMap) throws Exception {
        try {
            Random random = new Random();
            submitX_max = (int) coordinateMap.get("submitX_max");
            submitX_mim = (int) coordinateMap.get("submitX_mim");
            submitY_max = (int) coordinateMap.get("submitY_max");
            submitY_mim = (int) coordinateMap.get("submitY_mim");
            int x = random.nextInt(submitX_max) % (submitX_max - submitX_mim + 1) + submitX_mim;
            int y = random.nextInt(submitY_max) % (submitY_max - submitY_mim + 1) + submitY_mim;
            int[] array = {x, y};
            return array;
        }catch (Exception E){
            Log.i(TAG,"计算随机坐标异常:" + E);
            int[] array = {0, 0};
            return array;
        }
    }


    /**
     * 计算运行进度,保留一位小数
     */
    public static String getRunProgress(int totalTime, int currentTime){
        try {
            float Progress = (float) currentTime / (float)totalTime;
            DecimalFormat decimalFormat= new DecimalFormat(".0");
            String p= decimalFormat.format(Progress * 100);
            return p ;
        }catch (Exception e) {
            Log.i(TAG,String.format("计算运行进度异常!%s", e));
            return "0";
        }
    }


}

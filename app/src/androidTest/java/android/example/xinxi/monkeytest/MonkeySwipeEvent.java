package android.example.xinxi.monkeytest;


import android.support.annotation.Dimension;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import java.util.ArrayList;


/**
 * @author xinxi
 * Monkey滑动类
 */

public class MonkeySwipeEvent extends MonkeyEvent {
    private Dimension dimension;
    private long sleepTime = 300;
    public static UiDevice uiDevice;
    private int MaxScroll = 1;
    private final String TAG = "MonkeyTest";
    private int width,height;


    public MonkeySwipeEvent(UiDevice mDevice) {
        super(MonkeyEvent.EVENT_TYPE_SWIPE);
        uiDevice = mDevice;
        width = uiDevice.getDisplayWidth();
        height =  uiDevice.getDisplayWidth();
    }

    @Override
    public int injectEvent() throws Exception {
        return 0;
    }

    /**
     * 向上滚动事件
     */
    public int injectEvent_scrollUp() throws Exception {
        try {
            //this.scrollUp(MaxScroll);
            UiScrollable uiScrollable = new UiScrollable(new UiSelector().scrollable(true));
            uiScrollable.scrollForward();
            Log.i(TAG,"向上滚动事件完成!");
            return MonkeyEvent.INJECT_SUCCESS;
        }catch (Exception E){
            Log.i(TAG,this.getClass().getName() + "-" + "向上滚动事件异常:" + E);
            return INJECT_FAIL;
        }
    }


    /**
     * 向下滚动事件
     */
    public int injectEvent_scrollDown() throws Exception {
        try {
            //this.scrollDown(MaxScroll);
            UiScrollable uiScrollable = new UiScrollable(new UiSelector().scrollable(true));
            uiScrollable.scrollBackward();
            Log.i(TAG,"向下滚动事件完成!");
            return MonkeyEvent.INJECT_SUCCESS;
        }catch (Exception E){
            Log.i(TAG,this.getClass().getName() + "-" + "向下滚动事件异常:" + E);
            return INJECT_FAIL;
        }
    }


    /**
     * 获取屏幕尺寸
     */
    private ArrayList getWindowSize(){
        width= uiDevice.getDisplayWidth();
        height= uiDevice.getDisplayHeight();
        ArrayList arrayList = new ArrayList();
        arrayList.add(width);
        arrayList.add(height);
        return arrayList;
    }

    /**
     * 向上滚动
     * @ looper 次数
     */
    public  void  scrollUp(int loop) throws InterruptedException {
        for (int i = 0; i < loop ; i++) {
            double startX = (width * 0.5);
            double startY = (height * 0.3);
            double endY = (height * 0.8);
            Log.i(TAG,startX + "," + startY+ "," + startX+ "," + endY);
            uiDevice.swipe((int)startX,(int)startY,(int)startX,(int)endY,1);
            Log.i(TAG,"向上滑动!");
            Thread.sleep(sleepTime);
        }
    }







    /**
     * 向上滚动
     * @ looper 次数
     */
    public  void  scrollDown(int loop) throws InterruptedException {
        for (int i = 0; i < loop ; i++) {
            int startX = (int) (width * 0.5);
            int startY = (int) (height * 0.8);
            int endY = (int) (height * 0.3);
            Log.i(TAG,startX + "," + startY+ "," + startX+ "," + endY);
            uiDevice.swipe(startX,startY,startX,endY,1);
            Log.i(TAG,"向下滑动!");
            Thread.sleep(sleepTime);
        }
    }



}

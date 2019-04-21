package android.example.xinxi.monkeytest;


import android.support.test.uiautomator.UiDevice;
import android.util.Log;


/**
 * @author xinxi
 * Monkey输入事件类
 */

public class MonkeySubmitEvent extends MonkeyEvent {

    private int x, y;
    public static UiDevice uiDevice;
    private final String TAG = "MonkeyTest";

    public MonkeySubmitEvent(UiDevice mDevice, int x, int y) {
        super(MonkeyEvent.EVENT_TYPE_SUBMIT);
        this.x = x;
        this.y = y;
        uiDevice = mDevice;
    }


    public int injectEvent() throws Exception {
        try {
            Log.i(TAG,"随机输入事件:点击->(" + x + ", " + y + ")");
            uiDevice.click(this.x,this.y);
            Log.i(TAG,"随机输入事件完成!");
            return MonkeyEvent.INJECT_SUCCESS;
        }catch (Exception E){
            Log.e(TAG,this.getClass().getName() + "-" + "随机输入事件异常:" + E);
            return INJECT_FAIL;
        }
    }

}

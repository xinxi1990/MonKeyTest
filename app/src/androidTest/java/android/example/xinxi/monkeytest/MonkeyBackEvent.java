package android.example.xinxi.monkeytest;

import android.support.test.uiautomator.UiDevice;
import android.util.Log;

public class MonkeyBackEvent extends MonkeyEvent {

    public static UiDevice uiDevice;
    private final String TAG = "MonkeyTest";

    public MonkeyBackEvent(UiDevice mDevice) {
        super(MonkeyEvent.EVENT_TYPE_BACK);
        uiDevice = mDevice;
    }

    @Override
    public int injectEvent() throws Exception {
        return 0;

    }

    public int injectBackEvent() throws Exception {
        try {
            String currentActivityName = uiDevice.getCurrentActivityName();
            Log.i(TAG,"当前页面:" + currentActivityName);
            if (! currentActivityName.contains("home")){
                uiDevice.pressBack();
                Log.i(TAG,"返回上一级页面");
            }
            return MonkeyEvent.INJECT_SUCCESS;
        }catch (Exception E){
            Log.e(TAG,this.getClass().getName() + "-" + "随机输入事件异常:" + E);
            return INJECT_FAIL;
        }


    }


}
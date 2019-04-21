package android.example.xinxi.monkeytest;

import android.support.test.uiautomator.UiDevice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MonkeyUntils {

    public String dumpPath = "data/data/android.example.xinxi.monkeyu2/dump.log";
    public static UiDevice uiDevice;

    public MonkeyUntils(UiDevice mDevice) {
        uiDevice = mDevice;
    }


    /**
     * 获取页面的xml数据结构
      */
    public void getViewHierarchy() throws IOException {
        File file = new File(dumpPath);
        FileOutputStream outputStream=new FileOutputStream(file,false);
        uiDevice.dumpWindowHierarchy(outputStream);
    }


}

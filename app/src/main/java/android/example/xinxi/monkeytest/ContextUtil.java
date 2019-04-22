package android.example.xinxi.monkeytest;

import android.app.Application;
import android.content.Context;
public class ContextUtil extends Application {

    private static String TAG = "MonkeyTest";
    private static ContextUtil instance;
    private static Context context;
    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        //CrashHandler crashHandler = CrashHandler.getInstance();
        //crashHandler.init(getApplicationContext());
    }



}

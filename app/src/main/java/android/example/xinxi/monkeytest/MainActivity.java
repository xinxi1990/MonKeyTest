package android.example.xinxi.monkeytest;

import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Context context;
    private static String TAG = "MonkeyTest";
    private static String packageName = "android.example.xinxi.monkeytest.test";
    private static String runClassName = "android.support.test.runner.AndroidJUnitRunner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button)findViewById(R.id.startbutton);

    }


//    private void addListenerOnStartButton() {
//        startButton = (Button)findViewById(R.id.startbutton);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: ");
//                Context context = ContextUtil.getInstance();
//                Log.d(TAG, context.toString());
//                ComponentName componentName = new ComponentName(packageName, runClassName);
//                context.startInstrumentation(componentName, null, null);
//            }
//
//        });
//    }


    /** * 点击按钮对应的方法 * @param v */
    public void runMyUiautomator(View v){
        Log.i(TAG, "runMyUiautomator: ");
        new UiautomatorThread().start();
    }


    /** * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行 */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            //String command = generateCommand("android.example.xinxi.monkeytest", "android.example.xinxi.monkeytest.MonkeyTest", "runCase");
            String command = generateCommand("android.example.xinxi.monkeytest", "android.example.xinxi.monkeytest.TestOne", "demo");
            CMDUtils.CMD_Result rs= CMDUtils.runCMD(command,true,true);
            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }

        /** * 生成命令 * @param pkgName 包名 * @param clsName 类名 * @param mtdName 方法名 * @return */
        public  String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument --user 0 -w -r -e debug false -e class "
                    + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e(TAG, command);
            return command;
        }
    }


}

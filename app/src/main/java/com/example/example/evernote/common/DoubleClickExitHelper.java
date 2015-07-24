package com.example.example.evernote.common;

import android.app.Activity;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.example.R;


/**
 * 双击退出
 */
public class DoubleClickExitHelper {
    private Activity context;
    private boolean firstKeyDown = true;
    private Handler handler;
    private Toast exitToast;

    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (exitToast != null) {
                exitToast.cancel();
            }
            firstKeyDown = true; //重置为还是第一次点击
        }
    };

    /**构造
     * @param context
     */
    public DoubleClickExitHelper(Activity context) {
        this.context = context;
        handler = new Handler();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }

        if (firstKeyDown) { //第一次点击
            if (exitToast == null) {
                exitToast = Toast.makeText(context, context.getString(R.string.double_back_exit_hint), Toast.LENGTH_SHORT);
            }
            exitToast.show();
            //
            handler.postDelayed(onBackTimeRunnable, 2000);
            firstKeyDown = false;
        } else {//第二次点击
            handler.removeCallbacks(onBackTimeRunnable);
            if (exitToast != null) {
                exitToast.cancel();
            }
            context.finish();
        }
        return true;
    }
}

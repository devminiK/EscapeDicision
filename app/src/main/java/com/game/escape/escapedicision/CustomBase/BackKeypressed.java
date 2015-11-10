package com.game.escape.escapedicision.CustomBase;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Mhwan on 2015. 11. 9..
 */
public class BackKeypressed {
    private long backKeyPressTime = 0;
    private Toast toast;
    private Activity activity;
    public BackKeypressed(Activity activity){
        this.activity = activity;
    }
    public void onBackPressed(){
        //2초이상 지났으면 마지막 시간을 현재 시간으로 갱신하고 토스트 메시지 실행
        if (System.currentTimeMillis() > backKeyPressTime + 2000) {
            backKeyPressTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        //2초이상 안지났으면 액티비티 종료
        else
            programShutdown();

    }
    private void programShutdown() {
        activity.moveTaskToBack(true);
        activity.finish();
        toast.cancel();
        //android.os.Process.killProcess(android.os.Process.myPid());
    }
    private void showGuide() {
        //Toast.LENGTH_SHORT = 2초
        toast = Toast.makeText(activity, "\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}

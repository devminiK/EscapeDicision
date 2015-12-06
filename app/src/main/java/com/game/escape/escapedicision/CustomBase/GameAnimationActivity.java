package com.game.escape.escapedicision.CustomBase;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.game.escape.escapedicision.Activity.MultiplayActivity;
import com.game.escape.escapedicision.Activity.SoloPlayActivity;
import com.game.escape.escapedicision.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Mhwan on 2015. 12. 7..
 */
public class GameAnimationActivity extends AppCompatActivity {
    private GifDrawable gifDrawable;
    private Handler handler;
    Random random = new Random(System.currentTimeMillis());
    private int selected_case_id;
    private int selected_name_id;
    private ArrayList<String> caseList;
    private ArrayList<String> nameList;
    private String[] predict_string;
    private int activityNum;
    ResultDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            activityNum = bundle.getInt(SoloPlayActivity.FORWARD_ACTIVITY_NUM);
            switch (activityNum){
                case 1:
                    caseList = (ArrayList<String>)bundle.getStringArrayList(SoloPlayActivity.FORWARD_CASELIST);
                    break;
                case 2:
                    caseList = (ArrayList<String>)bundle.getStringArrayList(SoloPlayActivity.FORWARD_CASELIST);
                    nameList = (ArrayList<String>)bundle.getStringArrayList(MultiplayActivity.FORWARD_NAMELIST);
                    if (MultiplayActivity.isPredict)
                        predict_string = bundle.getStringArray(MultiplayActivity.FORWARD_PREDICT);
                    break;
            }
        }
        initGifAnimation();
    }
    private void initGifAnimation(){
        GifImageView img = new GifImageView(this);
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.ladder_animation);
            gifDrawable.stop();
            gifDrawable.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.setImageDrawable(gifDrawable);
        setContentView(img);
        GifThread gifThread = new GifThread();
        handler = new Handler();
        gifDrawable.start();
        gifThread.start();
    }
    private void Animation_ended(){
        //랜덤값을 뽑고 뽑은거에 대한 값을 다이얼로그에 넣고, 결과예측이 되었다면 맞는지 비교를 하고 다이얼로그에 보여준다.
        dialog = new ResultDialog(this, favoriteListner());
        randomList();
        android.view.WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
    private View.OnClickListener favoriteListner(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //즐겨찾기가 눌렷을때 저장을하도록 만들것.
                Toast.makeText(getApplicationContext(), caseList.get(selected_case_id), Toast.LENGTH_SHORT).show();
            }
        };
    }
    private void randomList(){
        switch (activityNum){
            case 1:
                selected_case_id = random.nextInt(caseList.size());
                dialog.setResultMessage(caseList.get(selected_case_id));
                break;
            case 2:
                selected_case_id = random.nextInt(caseList.size());
                selected_name_id = random.nextInt(nameList.size());
                dialog.setResultMessage(caseList.get(selected_case_id) + " & " + nameList.get(selected_name_id));
                if (MultiplayActivity.isPredict)
                    dialog.setPredictMessage(predict_string[0].equals(caseList.get(selected_case_id)) && predict_string[1].equals(nameList.get(selected_name_id)));
                break;
        }
    }
    class GifThread extends Thread{
        private boolean isPlay = false;
        public GifThread(){
            this.isPlay = true;
        }
        @Override
        public void run() {
            while (isPlay) {
                //Log.d("thread", "running");
                if (gifDrawable.isAnimationCompleted()) {
                    gifDrawable.stop();
                    isPlay=false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isPlay)
                            //애니메이션이 끝남
                            Animation_ended();
                    }
                });
            }
        }
    }
}

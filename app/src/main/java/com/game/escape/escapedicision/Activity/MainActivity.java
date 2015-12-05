package com.game.escape.escapedicision.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.game.escape.escapedicision.CustomBase.BackKeypressed;
import com.game.escape.escapedicision.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BackKeypressed backpressed;
    private ImageButton soloplay, multiplay,favorite, memo, quit;
    //각 버튼에 인텐트 전달할 것.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backpressed = new BackKeypressed(this);
        initview();
    }
    private void initview(){
        soloplay = (ImageButton)findViewById(R.id.button_soloplay);
        multiplay = (ImageButton)findViewById(R.id.button_multiplay);
        favorite = (ImageButton)findViewById(R.id.button_favorite);
        memo = (ImageButton)findViewById(R.id.button_memo);
        quit = (ImageButton)findViewById(R.id.button_quit);
        soloplay.setOnClickListener(this);
        multiplay.setOnClickListener(this);
        favorite.setOnClickListener(this);
        memo.setOnClickListener(this);
        quit.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        backpressed.onBackPressed();
    }
    private void startnewActivity(Intent intent){
        //finish();
        startActivity(intent);
    }
    private void finishDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("정말로 게임을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_soloplay : startnewActivity(new Intent(this, SoloPlayActivity.class));
                break;
            case R.id.button_multiplay : startnewActivity(new Intent(this, MultiplayActivity.class));
                break;
            case R.id.button_favorite :
                startnewActivity(new Intent(this, FavoriteActivity.class));
                break;
            case R.id.button_memo : startnewActivity(new Intent(this, MemoActivity.class));
                break;
            case R.id.button_quit : finishDialog();
        }
    }
}

package com.game.escape.escapedicision.CustomBase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.escape.escapedicision.Activity.MainActivity;
import com.game.escape.escapedicision.R;

/**
 * Created by Mhwan on 2015. 12. 2..
 */
//혼자서 플레이에서 불러왔다면 결과예측메시지 실행x, 선택된 텍스트와 결과만 보여주면됨
//다함께라면 결과예측을 했을경우에는 결과예측과 선택된 텍스트만

public class ResultDialog extends Dialog {
    final Context context;
    final String baseResultMessage = "이(가) 선택되었습니다!";
    final String predictCMessage = "축하합니다! 결과 예측에 성공하셨습니다.";
    final String predictICMessage = "아쉽네요! 결과 예측에 실패하셨습니다.";
    private TextView predict_text, result_text;
    private ImageView random_game_image;
    private View.OnClickListener favorite_listner;
    public ResultDialog(Context context, View.OnClickListener favorite_listner) {
        super(context);
        this.context = context;
        this.favorite_listner = favorite_listner;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_result);
        //외부터치, 백버튼 막기
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView(){
        predict_text = (TextView)findViewById(R.id.predict_text);
        result_text = (TextView)findViewById(R.id.selected_result_text);
        random_game_image = (ImageView)findViewById(R.id.random_game_image);
        predict_text.setVisibility(View.GONE);

        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        findViewById(R.id.favorite_save_button).setOnClickListener(favorite_listner);
    }

    public void setPredictMessage(boolean isCorrected){
        //불러온 화면에서 결과예측을 했다면 불러올것
        if (isCorrected){
           predict_text.setText(predictCMessage);
           predict_text.setVisibility(View.VISIBLE);
        }
        else {
            predict_text.setText(predictICMessage);
            predict_text.setVisibility(View.VISIBLE);
        }
    }
    public void setResultMessage(String resultMessage){
        //항상불러와야함 결과 표시
        result_text.setText(resultMessage+baseResultMessage);
    }

    public void setGameImage(){
        //항상불러와야함 게임 이미지 표시
    }
}

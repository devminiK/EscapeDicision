package com.game.escape.escapedicision.CustomBase;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.escape.escapedicision.R;

/**
 * Created by Mhwan on 2015. 12. 2..
 */
//혼자서 플레이에서 불러왔다면 결과예측메시지 실행x, 선택된 텍스트와 결과만 보여주면됨
//다함께라면 결과예측을 했을경우에는 결과예측과 선택된 텍스트만

public class ResultDialog extends Dialog {
    final Context context;
    final String baseResultMessage = "이(가) 선택되었습니다!";
    private boolean isPredict_result = false;
    private TextView predict_text, result_text;
    private ImageView random_game_image;

    public ResultDialog(Context context) {
        super(context);
        this.context = context;
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

        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.favorite_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setPredictMessage(){
        //불러온 화면에서 결과예측을 했다면 불러올것
    }
    public void setResultMessage(){
        //항상불러와야함 결과 표시
    }

    public void setGameImage(){
        //항상불러와야함 게임 이미지 표시
    }
}

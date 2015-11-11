package com.game.escape.escapedicision.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.BaseActivity;
import com.game.escape.escapedicision.R;

public class SoloPlayActivity extends BaseActivity implements View.OnClickListener{
    private ListView case_list;
    private RelativeLayout add_case;
    private TextView num_case_textview;
    private Button start_button;
    private int num_case;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_play);
        add_case = (RelativeLayout)findViewById(R.id.add_case);
        add_case.setOnClickListener(this);
        num_case_textview = (TextView)findViewById(R.id.num_case);
        start_button = (Button)findViewById(R.id.game_start);
        start_button.setOnClickListener(this);
        case_list = (ListView)findViewById(R.id.case_list);


    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.solo_play_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_start:
                Toast.makeText(getApplicationContext(), "게임시작 버튼 누름", Toast.LENGTH_SHORT);
                break;
            case R.id.add_case:
                //경우의수 추가
                break;
        }


    }
}

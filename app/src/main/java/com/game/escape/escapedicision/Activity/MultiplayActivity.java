package com.game.escape.escapedicision.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.CustomBase.ItemMultiplayCase;
import com.game.escape.escapedicision.R;

import java.util.ArrayList;

public class MultiplayActivity extends BaseDrawerActivity implements View.OnClickListener{
    private ListView case_list;
    private RelativeLayout add_case;
    private TextView num_case_textview;
    private Button start_button;
    ArrayList<ItemMultiplayCase> caseArrayList;
    ArrayList<String> stringCaselist_1;
    ArrayList<String> stringCaselist_2;
    //어댑터 구현할 것.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_multiplay);
        initView();
        num_case_textview.setText("2");
        caseArrayList = new ArrayList<ItemMultiplayCase>();
    }

    private void initView(){
        add_case = (RelativeLayout)findViewById(R.id.add_case_double);
        add_case.setOnClickListener(this);
        num_case_textview = (TextView)findViewById(R.id.num_case_double);
        start_button = (Button)findViewById(R.id.game_start_double);
        start_button.setOnClickListener(this);
        case_list = (ListView)findViewById(R.id.case_double_list);
        //리스트뷰에서 포커스를 잃지 않도록 한다.
        case_list.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }
    @Override
    protected String getActionbarTitle() {
        return getString(R.string.multi_play_name);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            super.onBackPressed();
        else {
            if (!caseArrayList.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("입력한 경우의 수는 모두 사라집니다. 정말로 닫겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MultiplayActivity.this.finish();
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
            } else
                MultiplayActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_start_double :
                break;
            case R.id.add_case_double :
                break;
        }
    }
}

package com.game.escape.escapedicision.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.AdapterSoloplay;
import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.CustomBase.ItemSoloplayCase;
import com.game.escape.escapedicision.R;

import java.util.ArrayList;

public class SoloPlayActivity extends BaseDrawerActivity implements View.OnClickListener{
    private ListView case_list;
    private RelativeLayout add_case;
    private TextView num_case_textview;
    private Button start_button;
    ArrayList<ItemSoloplayCase> caseArrayList;
    AdapterSoloplay adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_solo_play);
        initFromOtherView(R.layout.activity_solo_play);
        initView();
        //처음에는 경우의 수가 아무것도 없음
        num_case_textview.setText("0");
        caseArrayList = new ArrayList<ItemSoloplayCase>();
        adapter = new AdapterSoloplay(this, R.layout.item_case_input, caseArrayList);
        case_list.setAdapter(adapter);
    }

    private void initView(){
        add_case = (RelativeLayout)findViewById(R.id.add_case);
        add_case.setOnClickListener(this);
        num_case_textview = (TextView)findViewById(R.id.num_case);
        start_button = (Button)findViewById(R.id.game_start);
        start_button.setOnClickListener(this);
        case_list = (ListView)findViewById(R.id.case_list);
        //리스트뷰에서 포커스를 잃지 않도록 한다.
        case_list.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }
    public void removeCase(View v){
        //키보드를 숨기고 뷰를 통해 지울 아이템을 갖고와서 지운다.
        ItemSoloplayCase removeitem = (ItemSoloplayCase)v.getTag();
        adapter.remove(removeitem);
        num_case_textview.setText(Integer.toString(caseArrayList.size()));
        adapter.changeOrderTag();
    }
    @Override
    protected String getActionbarTitle() {
        return getString(R.string.solo_play_name);
    }
    //뒤로가기가 눌렸을때 추가한 경우의 수가 있을경우에만 다이얼로그 띄우고 경우의수가 비었다면 그냥 종료
    @Override
    public void onBackPressed() {
        if (!caseArrayList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("입력한 경우의 수는 모두 사라집니다. 정말로 닫겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SoloPlayActivity.this.finish();
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
        else
            SoloPlayActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_start:
                Toast.makeText(getApplicationContext(), "게임시작 버튼 누름", Toast.LENGTH_SHORT);
                break;
            case R.id.add_case:
                //포커스가 되어있을경우 확인불가
                // 키보드를숨기고 어댑터에 추가하고 순서정력
                adapter.insert(new ItemSoloplayCase(""), 0);
                adapter.changeOrderTag();
                //경우의 수 글자는 어레이리스트의 사이즈로
                num_case_textview.setText(Integer.toString(caseArrayList.size()));
                adapter.notifyDataSetChanged();
                break;
        }
    }



}

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
    ArrayList<String> stringCaselist;
    AdapterSoloplay adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_solo_play);
        initFromOtherView(R.layout.activity_solo_play);
        initView();
        //처음에는 경우의 수가 2개부터
        num_case_textview.setText("2");
        caseArrayList = new ArrayList<ItemSoloplayCase>();
        adapter = new AdapterSoloplay(this, R.layout.item_case_input, caseArrayList);
        //처음에 경우의수가 두개부터 잇을경우. 태그는 거꾸로 만들어줌
        adapter.insert(new ItemSoloplayCase(2, ""), 0);
        adapter.insert(new ItemSoloplayCase(1, ""), 0);
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
    private boolean toCaseString(){
        //케이스에 있는 경우의 수를 하나씩 가져와서 스트링에 넣는다. 비어있을 경우 false를 반환하고 제대로 되었다면 add
        stringCaselist = new ArrayList<String>();
        for (int i = 0; i<caseArrayList.size(); i++){
            String temp = caseArrayList.get(i).getInput();
            //Log.d("input", temp);
            if (temp.equals("")){
                //Log.d("string", "data is null");
                return false;
            }
            else{
                //Log.d("string", "data is good");
                stringCaselist.add(temp);
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_start:
                //리스트가 없을경우, 리스트의 내용이 비었을경우
                if (!toCaseString()||caseArrayList.isEmpty())
                    Toast.makeText(getApplicationContext(), "경우의 수를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                //다이얼로그를 띄어 이대로 진행할거냐고 물어봄
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("게임시작")
                            .setMessage("입력한 경우의 수로 게임을 시작합니다")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                    //Toast.makeText(getApplicationContext(), "경우의 수가 제대로 됨", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_case:
                //에딧 텍스트 이외의 다른부분을 누를경우 키보드를 닫는다 (베이스드로워 내 setupUI)
                //포커스가 되어있을경우 포커스를 클리어 시키고 키보드를 닫는다(어댑터 내 getview)
                adapter.insert(new ItemSoloplayCase(""), 0);
                //경우의 수 순서 태그는 추가 버튼을 누를때마다 순서대로 갱신
                adapter.changeOrderTag();
                num_case_textview.setText(Integer.toString(caseArrayList.size()));
                adapter.notifyDataSetChanged();
                break;
        }
    }
}

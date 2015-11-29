package com.game.escape.escapedicision.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.AdapterMultiplay;
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
    AdapterMultiplay adapter;
    ArrayList<String> stringCaselist_1;
    ArrayList<String> stringCaselist_2;
    //어댑터 구현할 것.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_multiplay);
        initView();
        //처음 경우의수 2개로
        num_case_textview.setText("2");
        caseArrayList = new ArrayList<ItemMultiplayCase>();
        adapter = new AdapterMultiplay(this, R.layout.item_multi_case_input, caseArrayList);
        adapter.insert(new ItemMultiplayCase(2, "", ""), 0);
        adapter.insert(new ItemMultiplayCase(1, "", ""), 0);
        case_list.setAdapter(adapter);
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

    public void removeCase(View v){
        //키보드를 숨기고 뷰를 통해 지울 아이템을 갖고와서 지운다.
        ItemMultiplayCase removeitem = (ItemMultiplayCase)v.getTag();
        adapter.remove(removeitem);
        num_case_textview.setText(Integer.toString(caseArrayList.size()));
        adapter.changeOrderTag();
    }

    private boolean toCaseString(){
        //케이스에 있는 경우의 수를 하나씩 가져와서 스트링에 넣는다. 비어있을 경우 false를 반환하고 제대로 되었다면 add
        stringCaselist_1 = new ArrayList<String>();
        stringCaselist_2 = new ArrayList<String>();
        for (int i = 0; i<caseArrayList.size(); i++){
            String temp_1 = caseArrayList.get(i).getInput_1();
            String temp_2 = caseArrayList.get(i).getInput_2();
            Log.d("input", temp_1);
            Log.d("input_2", temp_2);
            if (temp_1.equals("")||temp_2.equals("")){
                Log.d("string", "data is null");
                return false;
            }
            else{
                Log.d("string", "data is good");
                stringCaselist_1.add(temp_1);
                stringCaselist_2.add(temp_2);
            }
        }
        return true;
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
                //리스트가 없을경우, 리스트의 내용이 비었을경우
                if (!toCaseString()||caseArrayList.isEmpty())
                    Toast.makeText(getApplicationContext(), "경우의 수를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    //다이얼로그를 띄어 이대로 진행할거냐고 물어봄
                else {
                    Log.d("size", Integer.toString(stringCaselist_1.size()));
                    Log.d("size", Integer.toString(stringCaselist_2.size()));
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
            case R.id.add_case_double :
                adapter.insert(new ItemMultiplayCase("", ""), 0);
                adapter.changeOrderTag();
                num_case_textview.setText(Integer.toString(caseArrayList.size()));
                adapter.notifyDataSetChanged();
                break;
        }
    }
}

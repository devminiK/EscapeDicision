package com.game.escape.escapedicision.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.AdapterMultiplay;
import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.CustomBase.GameAnimationActivity;
import com.game.escape.escapedicision.CustomBase.ItemMultiplayCase;
import com.game.escape.escapedicision.R;

import java.util.ArrayList;

public class MultiplayActivity extends BaseDrawerActivity implements View.OnClickListener {
    private ListView case_list;
    private RelativeLayout add_case;
    private TextView num_case_textview;
    private Button start_button, predict_button;
    private ArrayList<ItemMultiplayCase> caseArrayList;
    private AdapterMultiplay adapter;
    private Dialog predict_dialog;
    private ArrayList<String> stringCaselist_1;
    private ArrayList<String> stringCaselist_2;
    public static final String FORWARD_NAMELIST = "NAME_LIST";
    public static final String FORWARD_PREDICT = "PREDICT_LIST";
    //결과예측 문자열 예측안했다면 null, ispredict 결과예측을 안한상태
    //0 경우예측, 1 이름예측
    String[] predict_string_array;
    public static boolean isPredict = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_multiplay);
        initView();
        //처음 경우의수 2개로
        isPredict = false;
        num_case_textview.setText("2");
        caseArrayList = new ArrayList<ItemMultiplayCase>();
        adapter = new AdapterMultiplay(this, R.layout.item_multi_case_input, caseArrayList);
        adapter.insert(new ItemMultiplayCase(2, "", ""), 0);
        adapter.insert(new ItemMultiplayCase(1, "", ""), 0);
        case_list.setAdapter(adapter);
    }

    private void initView() {
        add_case = (RelativeLayout) findViewById(R.id.add_case_double);
        add_case.setOnClickListener(this);
        num_case_textview = (TextView) findViewById(R.id.num_case_double);
        start_button = (Button) findViewById(R.id.game_start_double);
        start_button.setOnClickListener(this);
        predict_button = (Button) findViewById(R.id.predict_result);
        predict_button.setOnClickListener(this);
        case_list = (ListView) findViewById(R.id.case_double_list);
        //리스트뷰에서 포커스를 잃지 않도록 한다.
        case_list.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    public void removeCase(View v) {
        //키보드를 숨기고 뷰를 통해 지울 아이템을 갖고와서 지운다.
        ItemMultiplayCase removeitem = (ItemMultiplayCase) v.getTag();
        adapter.remove(removeitem);
        num_case_textview.setText(Integer.toString(caseArrayList.size()));
        adapter.changeOrderTag();
    }

    private boolean toCaseString() {
        //케이스에 있는 경우의 수를 하나씩 가져와서 스트링에 넣는다. 비어있을 경우 false를 반환하고 제대로 되었다면 add
        stringCaselist_1 = new ArrayList<String>();
        stringCaselist_2 = new ArrayList<String>();
        for (int i = 0; i < caseArrayList.size(); i++) {
            String temp_1 = caseArrayList.get(i).getInput_1();
            String temp_2 = caseArrayList.get(i).getInput_2();
            Log.d("input", temp_1);
            Log.d("input_2", temp_2);
            if (temp_1.equals("") || temp_2.equals("")) {
                Log.d("string", "data is null");
                return false;
            } else {
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

    private AlertDialog createPredictDialog() {
        predict_string_array = new String[2];
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_predict_result, null);
        final AlertDialog.Builder buider = new AlertDialog.Builder(this, android.app.AlertDialog.THEME_HOLO_LIGHT);
        buider.setView(dialogView);

        //스피너 (선택된 텍스트 item_predict_spinner.xml, 드롭다운 iem_dropdown_spinner.xml
        Spinner case_spinner = (Spinner) dialogView.findViewById(R.id.case_spinner);
        Spinner name_spinner = (Spinner) dialogView.findViewById(R.id.name_spinner);
        ArrayAdapter case_adapter = new ArrayAdapter(this, R.layout.item_predict_spinner, stringCaselist_1);
        ArrayAdapter name_adpater = new ArrayAdapter(this, R.layout.item_predict_spinner, stringCaselist_2);
        case_adapter.setDropDownViewResource(R.layout.item_dropdown_spinner);
        name_adpater.setDropDownViewResource(R.layout.item_dropdown_spinner);
        case_spinner.setAdapter(case_adapter);
        name_spinner.setAdapter(name_adpater);

        //아이템이 선택될때마다 예측한 결과 스트링 갱신
        case_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("선택됨", String.valueOf(stringCaselist_1.get(position)));
                predict_string_array[0] = String.valueOf(stringCaselist_1.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("선택됨", String.valueOf(stringCaselist_2.get(position)));
                predict_string_array[1] = String.valueOf(stringCaselist_2.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialogView.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPredict = false;
                predict_string_array = null;
                predict_dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.okay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //확인버튼, 스피너에서 선택된 아이템을 받아오도록 할것
                isPredict = true;
                //Log.d("경우 선택", predict_string_array[0]);
                //Log.d("이름 선택 ", predict_string_array[1]);
                predict_dialog.dismiss();
            }
        });

        return buider.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_start_double:
                //리스트가 없을경우, 리스트의 내용이 비었을경우
                if (!toCaseString() || caseArrayList.isEmpty())
                    Toast.makeText(getApplicationContext(), "경우의 수를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    //다이얼로그를 띄어 이대로 진행할거냐고 물어봄
                else {
                    //Log.d("size", Integer.toString(stringCaselist_1.size()));
                    //Log.d("size", Integer.toString(stringCaselist_2.size()));
                    new AlertDialog.Builder(this)
                            .setTitle("게임시작")
                            .setMessage("입력한 경우의 수로 게임을 시작합니다")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MultiplayActivity.this, GameAnimationActivity.class);
                                    intent.putExtra(SoloPlayActivity.FORWARD_ACTIVITY_NUM, 2);
                                    intent.putStringArrayListExtra(SoloPlayActivity.FORWARD_CASELIST, stringCaselist_1);
                                    intent.putStringArrayListExtra(MultiplayActivity.FORWARD_NAMELIST, stringCaselist_2);
                                    if (isPredict)
                                        intent.putExtra(MultiplayActivity.FORWARD_PREDICT, predict_string_array);
                                    startActivity(intent);
                                    finish();
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
            case R.id.predict_result:
                if (toCaseString() && !caseArrayList.isEmpty()) {
                    predict_dialog = createPredictDialog();
                    predict_dialog.show();
                } else
                    Toast.makeText(getApplicationContext(), "경우의 수를 제대로 입력하고 실행해주세요.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_case_double:
                adapter.insert(new ItemMultiplayCase("", ""), 0);
                adapter.changeOrderTag();
                num_case_textview.setText(Integer.toString(caseArrayList.size()));
                adapter.notifyDataSetChanged();
                break;
        }
    }
}

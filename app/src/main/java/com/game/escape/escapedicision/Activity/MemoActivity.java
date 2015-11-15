package com.game.escape.escapedicision.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.R;

public class MemoActivity extends BaseDrawerActivity {
    //private ImageButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_memo);

        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메모작성으로 넘어가게 만들면됨
                startActivity(new Intent(getApplicationContext(), MemoWriteActivity.class));
            }
        });
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.memo_name);
    }
}

package com.game.escape.escapedicision.Activity;

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
                Snackbar.make(v, "메모액티비티 실행!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.memo_name);
    }
}

package com.game.escape.escapedicision.Activity;

import android.os.Bundle;

import com.game.escape.escapedicision.CustomBase.BaseActivity;
import com.game.escape.escapedicision.R;

public class MemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.memo_name);
    }
}

package com.game.escape.escapedicision.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.game.escape.escapedicision.CustomBase.BaseActivity;
import com.game.escape.escapedicision.R;

public class MemoWriteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_write);
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.write_memo_name);
    }
}

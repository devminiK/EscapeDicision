package com.game.escape.escapedicision.Activity;

import android.os.Bundle;

import com.game.escape.escapedicision.CustomBase.BaseActivity;
import com.game.escape.escapedicision.R;

public class SoloPlayActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_play);

    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.solo_play_name);
    }
}

package com.game.escape.escapedicision.Activity;

import android.os.Bundle;

import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.R;

public class MultiplayActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_multiplay);
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.multi_play_name);
    }
}

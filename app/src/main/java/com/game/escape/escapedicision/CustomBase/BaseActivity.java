package com.game.escape.escapedicision.CustomBase;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.game.escape.escapedicision.R;

/**
 * Created by Mhwan on 2015. 11. 9..
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar!=null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getActionbarTitle());
        }
        toolbar.setNavigationIcon(R.mipmap.ic_navidrawer);
    }
    protected abstract String getActionbarTitle();
}

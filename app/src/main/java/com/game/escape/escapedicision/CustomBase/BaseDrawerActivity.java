package com.game.escape.escapedicision.CustomBase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.game.escape.escapedicision.Activity.MemoActivity;
import com.game.escape.escapedicision.Activity.MultiplayActivity;
import com.game.escape.escapedicision.Activity.SoloPlayActivity;
import com.game.escape.escapedicision.R;

public abstract class BaseDrawerActivity extends AppCompatActivity {
    protected ActionBarDrawerToggle drawerToggle;
    protected DrawerLayout drawerLayout;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        viewInit();
    }

    private void viewInit() {
        findViewById(R.id.close_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        findViewById(R.id.button_soloplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(new Intent(getApplicationContext(), SoloPlayActivity.class));
            }
        });
        findViewById(R.id.button_multiplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(new Intent(getApplicationContext(), MultiplayActivity.class));
            }
        });
        findViewById(R.id.button_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startNewActivity(new Intent(getApplicationContext(), SoloPlayActivity.class));
            }
        });
        findViewById(R.id.button_memo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(new Intent(getApplicationContext(), MemoActivity.class));
            }
        });
        findViewById(R.id.button_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killApplication();
            }
        });
    }

    protected void initFromOtherView(int LayoutId) {
        //눌럿을때의 액티비티를 갖고와서 프레임레이아웃과 인플레이터를 시켜서 뷰에 보여준다.
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.drawer_frame);
        View view = LayoutInflater.from(this).inflate(LayoutId, frameLayout, false);
        frameLayout.addView(view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_widget);
        /*
        if (drawerLayout == null)
            Log.v("태그", "드로워레이아웃이 널임");*/
        if (toolbar != null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
                @Override
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            /*
            if (drawerToggle == null)
                Log.v("태그", "토글이 널임");*/
            drawerLayout.setDrawerListener(drawerToggle);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getActionbarTitle());
            toolbar.setNavigationIcon(R.mipmap.ic_navidrawer);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }

    protected void startNewActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    protected abstract String getActionbarTitle();

    private void killApplication() {
        //drawerLayout.closeDrawers();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("정말로 게임을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
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
}

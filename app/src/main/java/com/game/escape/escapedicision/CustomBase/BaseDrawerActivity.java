package com.game.escape.escapedicision.CustomBase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

    private void initNowLayout(int id){
        //현재 레이아웃 아이디를 받아 indicate가 보이도록 바꾼다
        switch (id){
            case R.layout.activity_solo_play :
                findViewById(R.id.indicate_solo).setVisibility(View.VISIBLE);
                findViewById(R.id.ic_solo_go).setVisibility(View.GONE);
                break;
            case R.layout.activity_multiplay :
                findViewById(R.id.indicate_multi).setVisibility(View.VISIBLE);
                findViewById(R.id.ic_multi_go).setVisibility(View.GONE);
                break;
            case R.layout.activity_memo :
                findViewById(R.id.indicate_memo).setVisibility(View.VISIBLE);
                findViewById(R.id.ic_memo_go).setVisibility(View.GONE);
                break;
        }
        //Log.d("id", Integer.toString(nowLayout_num));
    }
    protected void initFromOtherView(int LayoutId) {
        //눌럿을때의 액티비티를 갖고와서 프레임레이아웃과 인플레이터를 시켜서 뷰에 보여준다.
        initNowLayout(LayoutId);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.drawer_frame);
        View view = LayoutInflater.from(this).inflate(LayoutId, frameLayout, false);
        frameLayout.addView(view);
        //이 클래스를 상속받는 모든 액티비티의 상위 레이아웃에 parent id선언한다
        //최상위 레이아웃아이디를 넘겨주고 터치한 부분이 edittext가 아니면 키보드를 닫는다
        setupUI(findViewById(R.id.parent));
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
            try {
                getSupportActionBar().setTitle(getActionbarTitle());
            }
            catch (NullPointerException e){
                Log.e("Actionbar", "actionbar is null");
            }
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

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("영역선택", "완료");
                    hideKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    private void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

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

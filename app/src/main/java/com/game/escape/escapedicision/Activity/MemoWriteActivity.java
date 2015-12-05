package com.game.escape.escapedicision.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.BaseActivity;
import com.game.escape.escapedicision.CustomBase.NoteDBHelper;
import com.game.escape.escapedicision.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoWriteActivity extends BaseActivity {
    public static int numTitle = 1;
    public static String curDate = "";
    public static String curText = "";
    private EditText edit_title;
    private EditText edit_content;
    private TextView date_text;
    private Long mRowId;
    private Cursor note;
    private NoteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new NoteDBHelper(this);
        dbHelper.open();
        setContentView(R.layout.activity_memo_write);
        setupUI(findViewById(R.id.parent));
        edit_title = (EditText) findViewById(R.id.input_title);
        edit_content = (EditText) findViewById(R.id.memo_content);
        date_text = (TextView) findViewById(R.id.tag_date);

        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        curDate = formatter.format(curDateTime);
        date_text.setText(curDate);
        mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState.getSerializable(NoteDBHelper.KEY_ROWID);

        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null) ? extras.getLong(NoteDBHelper.KEY_ROWID) : null;
        }
        populateFields();

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_title.getText().toString().isEmpty() || edit_content.getText().toString().isEmpty())
                    finish();
                else {
                    saveState();
                    finish();
                }
            }
        });
    }

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //화면이 꺼질 경우 저장이 되게함
        Log.d("tag", "saved");
        saveState();
        outState.putSerializable(NoteDBHelper.KEY_ROWID, mRowId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String title = edit_title.getText().toString();
        String body = edit_content.getText().toString();
        if (!title.isEmpty()&&!body.isEmpty()) {
            if (mRowId == null) {
                long id = dbHelper.createNote(title, body, curDate);
                if (id > 0) {
                    mRowId = id;
                    Toast.makeText(getApplicationContext(), "메모를 성공적으로 저장하였습니다", Toast.LENGTH_SHORT).show();
                } else
                    Log.e("saveState", "failed to create note");
            } else {
                if (!dbHelper.updateNote(mRowId, title, body, curDate))
                    Log.e("saveState", "failed to update note");
                else
                    Toast.makeText(getApplicationContext(), "메모를 성공적으로 업데이트 하였습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료하시기 전에 메모를 저장하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveState();
                        MemoWriteActivity.this.finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        MemoWriteActivity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        //메모를 입력하지 않았면 그냥 액티비티를 종료하고 입력되었다면 다이얼로그를 띄운다
        if (edit_title.getText().toString().isEmpty() || edit_content.getText().toString().isEmpty())
            finish();
        else {
            saveDialog();
        }
    }

    @Override
    protected View.OnClickListener backClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메모를 입력하지 않았면 그냥 액티비티를 종료하고 입력되었다면 다이얼로그를 띄운다
                if (edit_title.getText().toString().isEmpty() || edit_content.getText().toString().isEmpty())
                    finish();
                else {
                    saveDialog();
                }
            }
        };
    }

    private void populateFields() {
        if (mRowId != null) {
            note = dbHelper.fetchNote(mRowId);
            startManagingCursor(note);
            edit_title.setText(note.getString(
                    note.getColumnIndexOrThrow(NoteDBHelper.KEY_TITLE)));
            edit_content.setText(note.getString(
                    note.getColumnIndexOrThrow(NoteDBHelper.KEY_BODY)));
            curText = note.getString(
                    note.getColumnIndexOrThrow(NoteDBHelper.KEY_BODY));
        }
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.write_memo_name);
    }
}

package com.game.escape.escapedicision.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.CustomBase.NoteDBHelper;
import com.game.escape.escapedicision.R;

public class MemoActivity extends BaseDrawerActivity {
    private ListView memo_list;
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private static final int DELETE_ID = Menu.FIRST;
    private int mNoteNumber = 1;
    private NoteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_memo);
        initView();
        memo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MemoWriteActivity.class);
                i.putExtra(NoteDBHelper.KEY_ROWID, id);
                startActivityForResult(i, ACTIVITY_EDIT);
            }
        });
        memo_list.setOnItemLongClickListener(longClickListener());
    }
    private void initView(){
        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메모작성으로 넘어가게 만들면됨
                startActivityForResult(new Intent(getApplicationContext(), MemoWriteActivity.class), ACTIVITY_CREATE);
            }
        });
        memo_list = (ListView)findViewById(R.id.memo_list);
        dbHelper = new NoteDBHelper(this);
        dbHelper.open();
        fillData();
        //registerForContextMenu(memo_list);
    }

    private AdapterView.OnItemLongClickListener longClickListener(){
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup = new PopupMenu(MemoActivity.this, view);
                getMenuInflater().inflate(R.menu.memo_context_menu, popup.getMenu());
                final int index_position = position;
                final long index_id = id;
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.memo_edit :
                                Intent i = new Intent(getApplicationContext(), MemoWriteActivity.class);
                                i.putExtra(NoteDBHelper.KEY_ROWID, index_id);
                                startActivityForResult(i, ACTIVITY_EDIT);
                                break;
                            case R.id.memo_delete :
                                dbHelper.deleteNote(index_id);
                                Toast.makeText(getApplicationContext(), "메모가 성공적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                fillData();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
                return true;
            }
        };
    }
    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor notesCursor = dbHelper.fetchAllNotes();
        startManagingCursor(notesCursor);

        String[] from = new String[] { NoteDBHelper.KEY_TITLE, NoteDBHelper.KEY_DATE};
        int[] to = new int[] {R.id.memo_title ,R.id.memo_date};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.item_memo_list, notesCursor, from, to);
        memo_list.setAdapter(notes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fillData();
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.memo_name);
    }
}

package com.game.escape.escapedicision.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.escape.escapedicision.CustomBase.BaseDrawerActivity;
import com.game.escape.escapedicision.CustomBase.FavoriteDBHelper;
import com.game.escape.escapedicision.CustomBase.ItemFavoriteList;
import com.game.escape.escapedicision.R;

import java.util.List;

/**
 * Created by Mhwan on 2015. 12. 5..
 */
public class FavoriteActivity extends BaseDrawerActivity {
    private ListView favorite_listview;
    private Context context = this;
    private List<ItemFavoriteList> favoriteLists;
    FavoriteDBHelper dbHelper;
    public static final String FORWARD_FTOCASE = "CASE_STRING";
    public static final String FORWARD_FTONAME = "NAME_STRING";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_favorite);
        favorite_listview = (ListView)findViewById(R.id.favorite_list);
        //리스트를 어떻게 불러올것인가
        dbHelper = new FavoriteDBHelper(this);
        favoriteLists = dbHelper.getFavList();
        favorite_listview.setAdapter(new AdapterFavorite());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            super.onBackPressed();
        else
            finish();
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.favorite_name);
    }

    public class AdapterFavorite extends BaseAdapter {
        LayoutInflater layoutInflater;
        public AdapterFavorite(){
            layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return favoriteLists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Viewholder holder;
            if (convertView == null)
                convertView = layoutInflater.inflate(R.layout.item_favorite_list, null);

            holder = new Viewholder();
            holder.text = (TextView)convertView.findViewById(R.id.text_favorite);
            holder.text.setText("경우의수\n"+favoriteLists.get(position).getText());
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터를 받아오고 데이터에 있는
                    ItemFavoriteList item = dbHelper.getFavItem(favoriteLists.get(position).getId());
                    Log.d("item", item.getText()+item.getActivity_num());
                    int activity_num = item.getActivity_num();
                    switch (activity_num){
                        case 1:
                            Intent intent = new Intent(FavoriteActivity.this, SoloPlayActivity.class);
                            intent.putExtra(FORWARD_FTOCASE, item.getText());
                            startActivity(intent);
                            break;
                        case 2:
                            String [] strings = item.getText().split(" / ");
                            Intent intent_1 = new Intent(FavoriteActivity.this, MultiplayActivity.class);
                            intent_1.putExtra(FORWARD_FTOCASE, strings[0]);
                            intent_1.putExtra(FORWARD_FTONAME, strings[1]);
                            startActivity(intent_1);
                            break;
                    }
                }
            });
            holder.remove = (ImageButton)convertView.findViewById(R.id.remove_favorite);
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.removeFav(favoriteLists.get(position).getId());
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "선택하신 즐겨찾기가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    favoriteLists = dbHelper.getFavList();
                    favorite_listview.setAdapter(new AdapterFavorite());
                }
            });

            return convertView;
        }
        class Viewholder {
            TextView text;
            ImageButton remove;
        }
    }
}

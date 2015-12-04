package com.game.escape.escapedicision.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromOtherView(R.layout.activity_favorite);
        favorite_listview = (ListView)findViewById(R.id.favorite_list);
        //리스트를 어떻게 불러올것인가
        //favoriteLists = dbHelper.getFavList();
        //favorite_listview.setAdapter(new AdapterFavorite());

        dbHelper = new FavoriteDBHelper(this);
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
                    //텍스트를 누르면 액티비티 시작
                }
            });
            holder.remove = (ImageButton)convertView.findViewById(R.id.remove_favorite);
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.removeFav(favoriteLists.get(position).getId());
                    notifyDataSetChanged();
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

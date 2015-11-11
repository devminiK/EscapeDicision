package com.game.escape.escapedicision.CustomBase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Mhwan on 2015. 11. 11..
 */
public class AdapterSoloplay extends BaseAdapter{
    private Context context;
    private List<ItemSoloplayCase> caselist;
    //순서, 입력, 지우기 버튼 리스트를 가져옴. 입력일때는 포커스에 중점. 지우기버튼일경우 리스트를 지우고 데이터 갱신. 추가할경우는 리스트를 어떻게 가져와서 추가시킬지
    public AdapterSoloplay(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

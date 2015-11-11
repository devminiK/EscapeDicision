package com.game.escape.escapedicision.CustomBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.escape.escapedicision.R;

import java.util.ArrayList;

/**
 * Created by Mhwan on 2015. 11. 11..
 */
public class AdapterSoloplay extends BaseAdapter {
    private Context context;
    private ArrayList<ItemSoloplayCase> caselist;
    private TextView num_case;
    //순서 갱신 오류, 해당 지운 에디트가 지워지지 않음
    public AdapterSoloplay(Context context, ArrayList<ItemSoloplayCase> caselist, TextView num_case) {
        this.context = context;
        this.caselist = caselist;
        this.num_case = num_case;
    }

    @Override
    public int getCount() {
        return caselist.size();
    }

    @Override
    public Object getItem(int position) {
        return caselist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private void reorder(){
        for (int i =0; i<caselist.size(); i++) {
            ItemSoloplayCase temp = caselist.get(i);
            temp.order.setText(Integer.toString(i + 1));
        }

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemSoloplayCase holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_case_input, parent, false);
            holder = new ItemSoloplayCase();
            holder.item = (RelativeLayout)convertView.findViewById(R.id.item_case);
            holder.order = (TextView) convertView.findViewById(R.id.order_num);
            holder.input = (EditText)convertView.findViewById(R.id.input_case);
            holder.clear = (ImageButton)convertView.findViewById(R.id.remove_case);
            //순서는 현재 크기만큼
            holder.order.setText(Integer.toString(getCount()));
            convertView.setTag(holder);
        }
        else
            holder = (ItemSoloplayCase)convertView.getTag();

        //클리어를 눌럿을때 제거하고 순서를 재정렬하고 갱신시킨다
        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caselist.remove(position);
                notifyDataSetChanged();
                //holder.item.setVisibility(View.GONE);
                num_case.setText(Integer.toString(caselist.size()));
                //reorder();
            }
        });
        return convertView;
    }
/*
    class Viewholder {
        private EditText editText;
        private TextView order;
        private ImageButton button_clear;
    }*/
}

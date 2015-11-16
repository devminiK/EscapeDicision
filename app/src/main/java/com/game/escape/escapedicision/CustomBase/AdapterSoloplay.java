package com.game.escape.escapedicision.CustomBase;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.escape.escapedicision.R;

import java.util.List;

/**
 * Created by Mhwan on 2015. 11. 11..
 */
public class AdapterSoloplay extends ArrayAdapter<ItemSoloplayCase> {
    private Context context;
    private List<ItemSoloplayCase> caselist;
    private int resource;
    //순서 갱신 오류, 해당 지운 에디트가 지워지지 않음
    public AdapterSoloplay(Context context, int resource, List<ItemSoloplayCase> caselist) {
        super(context, resource, caselist);
        this.resource = resource;
        this.context = context;
        this.caselist = caselist;
    }

    @Override
    public int getCount() {
        return caselist.size();
    }
    public void changeOrderTag(){
        for (int i = 0; i< caselist.size(); i++)
            caselist.get(i).setOrder(i+1);
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //나중에 메모리 누수를 막기위해 컨버트뷰가 널일경우에 다시 수정할것.
        View currentFocus = ((Activity)context).getCurrentFocus();
        if (currentFocus != null) {
            //포커스를 클리어시키고 키보드를 닫는다
            currentFocus.clearFocus();
            hideKeyboard(currentFocus);
        }
        Viewholder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_solo_case_input, parent, false);
        holder = new Viewholder();
        holder.itemSoloplayCase = caselist.get(position);
        holder.remove = (ImageButton) convertView.findViewById(R.id.remove_case);
        holder.remove.setTag(holder.itemSoloplayCase);

        holder.edittext = (TextView) convertView.findViewById(R.id.input_case);
        setTextChangeListener(holder);
        holder.order = (TextView) convertView.findViewById(R.id.order_num);

        convertView.setTag(holder);
        //순서는 현재 크기만큼
        holder.order.setText(Integer.toString(holder.itemSoloplayCase.getOrder()));
        holder.edittext.setText(holder.itemSoloplayCase.getInput());
        return convertView;
    }

    private void setTextChangeListener(final Viewholder holder) {
        holder.edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.itemSoloplayCase.setInput(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    class Viewholder {
        TextView order, edittext;
        ImageButton remove;
        ItemSoloplayCase itemSoloplayCase;
    }

    private void hideKeyboard(View v){
        InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

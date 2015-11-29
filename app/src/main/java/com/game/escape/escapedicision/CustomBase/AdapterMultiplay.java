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
 * Created by Mhwan on 2015. 11. 29..
 */
public class AdapterMultiplay extends ArrayAdapter<ItemMultiplayCase> {
    private Context context;
    private List<ItemMultiplayCase> list;
    private int resource;

    public AdapterMultiplay(Context context, int resource, List<ItemMultiplayCase> caselist) {
        super(context, resource, caselist);
        this.resource = resource;
        this.context = context;
        this.list = caselist;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    public void changeOrderTag(){
        for (int i = 0; i< list.size(); i++)
            list.get(i).setOrder(i+1);
        notifyDataSetChanged();
    }
    class Viewholder {
        TextView order, edittext_1, edittext_2;
        ImageButton remove;
        ItemMultiplayCase itemMultiplayCase;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentFocus = ((Activity)context).getCurrentFocus();
        if (currentFocus != null) {
            //포커스를 클리어시키고 키보드를 닫는다
            currentFocus.clearFocus();
            hideKeyboard(currentFocus);
        }
        Viewholder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_multi_case_input, parent, false);
        holder = new Viewholder();
        holder.itemMultiplayCase = list.get(position);
        holder.remove = (ImageButton) convertView.findViewById(R.id.remove);
        holder.remove.setTag(holder.itemMultiplayCase);

        holder.edittext_1 = (TextView) convertView.findViewById(R.id.input_case_1);
        holder.edittext_2 = (TextView) convertView.findViewById(R.id.input_case_2);
        setTextChangeListener(holder);
        holder.order = (TextView) convertView.findViewById(R.id.order_num);

        convertView.setTag(holder);
        //순서는 현재 크기만큼
        holder.order.setText(Integer.toString(holder.itemMultiplayCase.getOrder()));
        holder.edittext_1.setText(holder.itemMultiplayCase.getInput_1());
        holder.edittext_2.setText(holder.itemMultiplayCase.getInput_2());
        return convertView;
    }
    private void setTextChangeListener(final Viewholder holder) {
        holder.edittext_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.itemMultiplayCase.setInput_1(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.edittext_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.itemMultiplayCase.setInput_2(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void hideKeyboard(View v){
        InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

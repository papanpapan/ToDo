package com.example.amit.todoapps;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Amit on 7/18/2017.
 */

public class ProductAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public ProductAdapter( Context context,  int resource) {
        super(context, resource);
    }

    public void add( Product object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.show_product,parent,false);
            viewHolder.title=(TextView)convertView.findViewById(R.id.texttitle11);
            viewHolder.description=(TextView)convertView.findViewById(R.id.textdescription11);
            viewHolder.date1=(TextView)convertView.findViewById(R.id.textdate11);
            viewHolder.date2=(TextView)convertView.findViewById(R.id.textdate22);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Product product=(Product)getItem(position);
        viewHolder.title.setText(product.getTitle());
        viewHolder.description.setText(product.getDescription());
        viewHolder.date1.setText(product.getDate1());
        viewHolder.date2.setText(product.getDate2());

        return convertView;
    }
    static class ViewHolder{
        TextView title,description,date1,date2;
    }
}

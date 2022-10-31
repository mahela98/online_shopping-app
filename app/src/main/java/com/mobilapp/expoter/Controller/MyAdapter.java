package com.mobilapp.expoter.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilapp.expoter.R;
import com.mobilapp.expoter.models.ProductModel;

import java.util.ArrayList;

public class MyAdapter  extends BaseAdapter {

    Context context;
    ArrayList<ProductModel> arrayList;
    ProductModel product;

    public MyAdapter(Context context, ArrayList<ProductModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, null);
        TextView name = convertView.findViewById(R.id.titleText);
        TextView description = convertView.findViewById(R.id.supportText);
        TextView owner = convertView.findViewById(R.id.secondText);

        product = arrayList.get(position);

        name.setText(String.valueOf(product.getProductName()));
        owner.setText(String.valueOf(product.getOwnerName()));
        description.setText(product.getDescription());

        return convertView;

    }



}

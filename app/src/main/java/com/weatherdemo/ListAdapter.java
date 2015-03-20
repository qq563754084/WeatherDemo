package com.weatherdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weatherdemo.domain.Index;

import java.util.List;

/**
 * Created by Administrator on 2015/3/20.
 */
public class ListAdapter extends BaseAdapter {
    private List<Index> indexes;
    private Context context;
    ListAdapter(Context context, List<Index> indexes) {
        this.indexes = indexes;
        this.context=context;
    }

    @Override
    public int getCount() {
        return indexes.size();
    }

    @Override
    public Object getItem(int position) {
        return indexes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Index index = indexes.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_listview, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.zs = (TextView) convertView.findViewById(R.id.zs);
            holder.tipt = (TextView) convertView.findViewById(R.id.tipt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(index.getTitle());
        holder.zs.setText(index.getZs());
        holder.tipt.setText(index.getTipt());


        return convertView;
    }

    public class ViewHolder {

        private TextView title;
        private TextView zs;
        private TextView tipt;

    }
}


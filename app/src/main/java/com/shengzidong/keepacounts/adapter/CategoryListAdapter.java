package com.shengzidong.keepacounts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shengzidong.keepacounts.R;
import com.shengzidong.keepacounts.entity.Category;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31.
 */
public class CategoryListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Category> categories;

    public CategoryListAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        if (categories == null) {
            this.categories = new ArrayList<>();
        } else {
            this.categories = categories;
        }
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category_list, null);
            holder = new ViewHolder();
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.item_categorylist_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_categorylist_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(categories.get(position).getName());
        holder.ivIcon.setImageResource(categories.get(position).getIcon());

        return convertView;
    }

    class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
    }
}

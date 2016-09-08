package com.shengzidong.keepacounts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shengzidong.keepacounts.R;
import com.shengzidong.keepacounts.entity.Receipt;
import com.shengzidong.keepacounts.utils.MyDataBaseUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31.
 */
public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder> {

    Context context;
    ArrayList<Receipt> receipts;
    TextView headerView;

    public static int TYPE_HEADER = 0;
    public static int TYPE_NORMAL = 1;

    public ReceiptAdapter(ArrayList<Receipt> receipts, Context context) {
        this.receipts = receipts;
        this.context = context;
        notifyItemInserted(0);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        headerView = new TextView(context);
        headerView.setGravity(Gravity.CENTER);
        headerView.setWidth(parent.getWidth());
        headerView.setTextSize(30);
        headerView.setTextColor(Color.BLACK);

        float count=0;
        for (int i = 0; i < receipts.size(); i++) {
            count+=receipts.get(i).getMoney();
        }
        headerView.setText("今日:"+count);

        if (viewType == TYPE_HEADER) {
            return new MyViewHolder(headerView);
        }

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_receipt_list, parent, false));
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (headerView == null) {
            return TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(ReceiptAdapter.MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        holder.tvDate.setText(receipts.get(position).getDate());
        holder.tvMoney.setText(receipts.get(position).getMoney() + "");
        holder.tvCategory.setText(receipts.get(position).getCategory());
        holder.tvLocation.setText(receipts.get(position).getLocation());
        if (receipts.get(position).getLocation() == null) {
            holder.ivLocation.setVisibility(View.GONE);
        } else {
            holder.ivLocation.setVisibility(View.VISIBLE);
        }
        holder.tvLable.setText(receipts.get(position).getLabel());
        if (receipts.get(position).getLabel() == null) {
            holder.ivLable.setVisibility(View.GONE);
        } else {
            holder.ivLable.setVisibility(View.VISIBLE);
        }
        holder.tvMessage.setText(receipts.get(position).getMessage());
        if (receipts.get(position).getMessage() == null) {
            holder.ivMessage.setVisibility(View.GONE);
        } else {
            holder.ivMessage.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return receipts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvMoney;
        TextView tvCategory;
        TextView tvLocation;
        TextView tvLable;
        TextView tvMessage;

        ImageView ivLocation;
        ImageView ivLable;
        ImageView ivMessage;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvLable = (TextView) itemView.findViewById(R.id.tvLable);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);

            ivLocation = (ImageView) itemView.findViewById(R.id.ivLocation);
            ivLable = (ImageView) itemView.findViewById(R.id.ivLable);
            ivMessage = (ImageView) itemView.findViewById(R.id.ivMessage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition() != 0) {
                        MyDataBaseUtils db = new MyDataBaseUtils(context);
                        db.deleteFromDataBase(receipts.get(getPosition()));
                        receipts.remove(receipts.get(getPosition()));
                        notifyItemRemoved(getPosition());
                        notifyItemChanged(0);
                    }
                }
            });
        }
    }
}

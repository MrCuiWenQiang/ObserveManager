package com.zt.data.observe.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabProject;

import java.util.List;

import cn.faker.repaymodel.widget.view.BaseRecycleView;

public class MainProjectAdapter extends RecyclerView.Adapter<MainProjectAdapter.MainViewHolder> {

    private List<TabProject> datas;
    private BaseRecycleView.OnItemClickListener<TabProject> onItemClickListener;

    public void setDatas(List<TabProject> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(BaseRecycleView.OnItemClickListener<TabProject> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        final TabProject item = datas.get(position);
        holder.tv_name.setText(item.getName());
        if (TextUtils.isEmpty(item.getCreateDate())) {
            holder.tv_date.setText(item.getUpdateDate());
        } else {
            holder.tv_date.setText(item.getCreateDate());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,item,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class MainViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_date;

        public MainViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}

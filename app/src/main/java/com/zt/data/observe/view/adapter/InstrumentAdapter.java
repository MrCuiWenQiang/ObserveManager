package com.zt.data.observe.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;

import java.util.List;

import cn.faker.repaymodel.widget.view.BaseRecycleView;

public class InstrumentAdapter extends RecyclerView.Adapter<InstrumentAdapter.MainViewHolder> {

    private List<TabInstrument> datas;
    private BaseRecycleView.OnItemClickListener<TabInstrument> onItemClickListener;
    private BaseRecycleView.OnItemLongClickListener<TabInstrument> onItemLongClickListener;

    public void setDatas(List<TabInstrument> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(BaseRecycleView.OnItemLongClickListener<TabInstrument> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(BaseRecycleView.OnItemClickListener<TabInstrument> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ins, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        final TabInstrument item = datas.get(position);
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemClick(view,item,position);
                return true;
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

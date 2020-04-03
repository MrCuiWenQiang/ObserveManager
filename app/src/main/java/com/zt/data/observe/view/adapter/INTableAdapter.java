package com.zt.data.observe.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.orient.me.widget.rv.adapter.BaseAdapter;
import com.orient.me.widget.rv.adapter.TableAdapter;
import com.zt.data.observe.R;
import com.zt.data.observe.bean.TableIntCell;

import java.util.List;

import cn.faker.repaymodel.widget.view.BaseRecycleView;

//表格ad https://www.jianshu.com/p/144f1cfd7c4f
public class INTableAdapter extends TableAdapter<TableIntCell> {
    public INTableAdapter(List<TableIntCell> mDataList) {
        super(mDataList);
    }
    public void setdata(List<TableIntCell> mDataList){
        this.mDataList = mDataList;
    }
    private BaseRecycleView.OnItemClickListener<TableIntCell> onItemClickListener;



    public void setOnItemClickListener(BaseRecycleView.OnItemClickListener<TableIntCell> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemLayout(TableIntCell tableCell, int pos) {
        int type = tableCell.getType();
        if (type == 1) {
            return R.layout.item_tit;
        }
        return R.layout.item_row;
    }

    @Override
    public BaseAdapter.ViewHolder<TableIntCell> onCreateViewHolder(View root, int itemType) {
        switch (itemType) {
            case R.layout.item_row: {
                return new ContentHolder(root);
            }
            case R.layout.item_tit: {
                return new TieHolder(root);
            }
            default: return null;
        }
    }


    class ContentHolder extends BaseAdapter.ViewHolder<TableIntCell> {

        TextView mContent;

        public ContentHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_name);

        }

        @Override
        protected void onBind(final TableIntCell tableCell) {
            mContent.setText(tableCell.getValue());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view,tableCell,-1);
                }
            });
        }
    }

    class TieHolder extends BaseAdapter.ViewHolder<TableIntCell> {

        TextView mContent;

        public TieHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_name);
        }

        @Override
        protected void onBind(TableIntCell tableCell) {
            mContent.setText(tableCell.getName());
        }
    }
}

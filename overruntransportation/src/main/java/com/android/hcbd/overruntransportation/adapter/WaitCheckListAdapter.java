package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.TpsListInfo;
import com.android.hcbd.overruntransportation.ui.activity.ToCheckActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/23.
 */

public class WaitCheckListAdapter extends BaseAdapter {
    private Context context;
    private List<TpsListInfo.DataInfo> tpsListInfoList;
    public WaitCheckListAdapter(Context context, List<TpsListInfo.DataInfo> tpsListInfoList){
        this.context = context;
        this.tpsListInfoList = tpsListInfoList;
    }
    @Override
    public int getCount() {
        return tpsListInfoList == null ? 0 : tpsListInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return tpsListInfoList == null ? null : tpsListInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_wait_check_list_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_position.setText(tpsListInfoList.get(i).getCode());
        holder.tv_createTime.setText(tpsListInfoList.get(i).getCreateTime().replace("T"," "));
        holder.tv_no1.setText(tpsListInfoList.get(i).getCar().getNo1());
        holder.tv_driver_name.setText(tpsListInfoList.get(i).getDriver().getName());
        holder.tv_goods.setText(tpsListInfoList.get(i).getTpsGoods().getGoods());
        holder.tv_site_checkPlace.setText(tpsListInfoList.get(i).getSite().getCheckPlace());
        holder.tv_stateName.setText(tpsListInfoList.get(i).getStateName());
        holder.btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToCheckActivity.class);
                intent.putExtra("tpsListInfo",tpsListInfoList.get(i));
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_position)
        TextView tv_position;
        @BindView(R.id.tv_createTime)
        TextView tv_createTime;
        @BindView(R.id.tv_no1)
        TextView tv_no1;
        @BindView(R.id.tv_driver_name)
        TextView tv_driver_name;
        @BindView(R.id.tv_goods)
        TextView tv_goods;
        @BindView(R.id.tv_site_checkPlace)
        TextView tv_site_checkPlace;
        @BindView(R.id.tv_stateName)
        TextView tv_stateName;
        @BindView(R.id.btn_check)
        Button btn_check;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

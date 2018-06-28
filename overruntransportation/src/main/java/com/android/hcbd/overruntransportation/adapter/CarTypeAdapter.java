package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/17.
 */

public class CarTypeAdapter extends BaseAdapter {
    private Context context;
    private List<NewContentInfo.CarTypeEntity> carTypeEntityList = new ArrayList<>();
    private int index = 0;
    public CarTypeAdapter(Context context,List<NewContentInfo.CarTypeEntity> carTypeEntityList){
        this.context = context;
        this.carTypeEntityList = carTypeEntityList;
    }

    @Override
    public int getCount() {
        return carTypeEntityList == null ? 0 : carTypeEntityList.size();
    }

    @Override
    public Object getItem(int i) {
        return carTypeEntityList == null ? null : carTypeEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_axle_type_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvType.setText(carTypeEntityList.get(i).getType());
        Glide.with(context).load(MyApplication.getInstance().getBsaeUrl()+carTypeEntityList.get(i).getTypeImg()).into(holder.ivTypeImg);
        holder.tvAxisNum.setText("轴数："+carTypeEntityList.get(i).getAxisNum());
        holder.tvWheelNum.setText("轮数："+carTypeEntityList.get(i).getWheelNum());
        holder.tvLimit.setText("总质量限制："+carTypeEntityList.get(i).getLimit());
        holder.tvLength.setText("长度："+carTypeEntityList.get(i).getLength());
        holder.tvWidth.setText("宽度："+carTypeEntityList.get(i).getWidth());
        holder.tvHeight.setText("高度："+carTypeEntityList.get(i).getHeight());
        if(index == i)
            holder.cbSel.setChecked(true);
        else
            holder.cbSel.setChecked(false);
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.iv_typeImg)
        ImageView ivTypeImg;
        @BindView(R.id.tv_axisNum)
        TextView tvAxisNum;
        @BindView(R.id.tv_wheelNum)
        TextView tvWheelNum;
        @BindView(R.id.tv_limit)
        TextView tvLimit;
        @BindView(R.id.tv_length)
        TextView tvLength;
        @BindView(R.id.tv_width)
        TextView tvWidth;
        @BindView(R.id.tv_height)
        TextView tvHeight;
        @BindView(R.id.cb_sel)
        CheckBox cbSel;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

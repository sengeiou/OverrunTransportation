package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/15.
 */

public class HomeGridVIew1Adapter extends BaseAdapter {
    private Context context;
    private String[] names = new String[]{"录入","复核"};
    private int[] icons = new int[]{R.drawable.ic_home_add,R.drawable.ic_home_check};
    private int dots;
    public HomeGridVIew1Adapter(Context context){
        this.context = context;
    }

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }

    @Override
    public int getCount() {
        return names == null ? 0 : names.length;
    }

    @Override
    public Object getItem(int i) {
        return names == null ? null : names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_home_layout01,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        switch (i){
            case 0:
                holder.ivIcon.setBackgroundResource(R.drawable.image_input_bg);
                holder.ivIcon.setImageResource(icons[i]);
                break;
            case 1:
                holder.ivIcon.setBackgroundResource(R.drawable.image_check_bg);
                holder.ivIcon.setImageResource(icons[i]);
                break;
        }
        holder.tvName.setText(names[i]);
        return view;
    }

    class ViewHolder{
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

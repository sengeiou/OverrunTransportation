package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.LoginInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/15.
 */

public class HomeGridVIew2Adapter extends BaseAdapter {
    private Context context;
    private List<LoginInfo.MenuInfo> menuInfoList;
    //private String[] names = new String[]{"超限处罚","车辆信息","法律名称","法条信息","模版管理","默认问题","工作日安排"};
    private int[] icons = new int[]{R.drawable.ic_home_punish,R.drawable.ic_home_car_info,R.drawable.ic_home_legal_name,
            R.drawable.ic_home_bar_info,R.drawable.ic_home_template_management,R.drawable.ic_home_default_problem,R.drawable.ic_home_work_arr};
    public HomeGridVIew2Adapter(Context context,List<LoginInfo.MenuInfo> menuInfoList){
        this.context = context;
        this.menuInfoList = menuInfoList;
    }

    @Override
    public int getCount() {
        return menuInfoList == null ? 0 : menuInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return menuInfoList == null ? null : menuInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_home_layout02,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        switch (menuInfoList.get(i).getName()){
            case "超限处罚":
                holder.ivIcon.setBackgroundResource(R.drawable.image_red_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_punish);
                break;
            case "车型信息":
                holder.ivIcon.setBackgroundResource(R.drawable.image_green_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_car_info);
                break;
            case "法律名称":
                holder.ivIcon.setBackgroundResource(R.drawable.image_orange_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_legal_name);
                break;
            case "法条信息":
                holder.ivIcon.setBackgroundResource(R.drawable.image_blue_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_bar_info);
                break;
            case "模版管理":
                holder.ivIcon.setBackgroundResource(R.drawable.image_blue_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_template_management);
                break;
            case "默认问题":
                holder.ivIcon.setBackgroundResource(R.drawable.image_red_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_default_problem);
                break;
            case "工作日安排":
                holder.ivIcon.setBackgroundResource(R.drawable.image_green_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_work_arr);
                break;
            case "默认图片名称":
                holder.ivIcon.setBackgroundResource(R.drawable.image_orange_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_default_image);
                break;
            default:
                holder.ivIcon.setBackgroundResource(R.drawable.image_blue_bg);
                holder.ivIcon.setImageResource(R.drawable.ic_home_default_problem);
                break;
        }
        holder.tvName.setText(menuInfoList.get(i).getName());
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

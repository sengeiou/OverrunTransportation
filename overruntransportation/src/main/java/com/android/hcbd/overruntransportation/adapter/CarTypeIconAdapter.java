package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 14525 on 2017/3/31.
 */

public class CarTypeIconAdapter extends BaseAdapter {
    private Context context;
    private String[] names = {"载货汽车","载货汽车","载货汽车","载货汽车","中置轴挂车列车","中置轴挂车列车","中置轴挂车列车"
            ,"中置轴挂车列车","中置轴挂车列车","中置轴挂车列车","中置轴挂车列车","全挂汽车列车","全挂汽车列车","全挂汽车列车"
            ,"全挂汽车列车","铰接列车","铰接列车","铰接列车","铰接列车","铰接列车","铰接列车","铰接列车"};
    private int[] icons = {R.drawable.image_cargo_train_2axis,R.drawable.image_cargo_train_3axis_1,R.drawable.image_cargo_train_3axis_2
            ,R.drawable.image_cargo_train_4axis,R.drawable.image_center_train_3axis,R.drawable.image_center_train_4axis_1
            ,R.drawable.image_center_train_4axis_2,R.drawable.image_center_train_5axis_1,R.drawable.image_center_train_5axis_2
            ,R.drawable.image_center_train_6axis_1,R.drawable.image_center_train_6axis_2,R.drawable.image_full_train_4axis
            ,R.drawable.image_full_train_5axis_1,R.drawable.image_full_train_5axis_2,R.drawable.image_full_train_6axis
            ,R.drawable.image_hinge_train_3axis,R.drawable.image_hinge_train_4axis,R.drawable.image_hinge_train_5axis_1
            ,R.drawable.image_hinge_train_5axis_2,R.drawable.image_hinge_train_5axis_3,R.drawable.image_hinge_train_6axis_1
            ,R.drawable.image_hinge_train_6axis_2};
    private int[] axisNums = {2,3,3,4,3,4,4,5,5,6,6,4,5,5,6,3,4,5,5,5,6,6};
    private int index;
    public CarTypeIconAdapter(Context context){
        this.context = context;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIconRes(){
        return icons[getIndex()];
    }

    public String getCarType(){
        return names[getIndex()];
    }

    public int getaxisNums(){
        return axisNums[getIndex()];
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
            view = LayoutInflater.from(context).inflate(R.layout.item_car_type_pic_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_type.setText(names[i]);
        Glide.with(context).load(icons[i]).into(holder.iv_typeImg);
        holder.tv_axisNum.setText("轴数："+axisNums[i]);
        holder.tv_wheelNum.setText("轮数："+axisNums[i]*2);
        if(index == i)
            holder.cb_sel.setChecked(true);
        else
            holder.cb_sel.setChecked(false);
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.cb_sel)
        CheckBox cb_sel;
        @BindView(R.id.iv_typeImg)
        ImageView iv_typeImg;
        @BindView(R.id.tv_axisNum)
        TextView tv_axisNum;
        @BindView(R.id.tv_wheelNum)
        TextView tv_wheelNum;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

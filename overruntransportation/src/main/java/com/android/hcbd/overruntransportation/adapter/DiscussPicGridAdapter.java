package com.android.hcbd.overruntransportation.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.lzy.imagepicker.ImagePicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/23.
 */

public class DiscussPicGridAdapter extends BaseAdapter{
    private Context context;
    private List<OtherInfo.PicBean> list;
    public DiscussPicGridAdapter(Context context, List<OtherInfo.PicBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_other_pic_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        ImagePicker.getInstance().getImageLoader().displayImage((Activity) context, MyApplication.getInstance().getBsaeUrl()+list.get(i).getUrl(),holder.ivImage, 0, 0);
        holder.tvName.setText(list.get(i).getName());
        holder.ivDelete.setVisibility(View.GONE);
        return view;
    }

    class ViewHolder{
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
       }
    }


}

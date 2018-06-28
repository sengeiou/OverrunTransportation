package com.android.hcbd.overruntransportation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.constant.Constants;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.activity.OtherAddPicActivity;
import com.blankj.utilcode.utils.FileUtils;
import com.lzy.imagepicker.ImagePicker;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/22.
 */

public class OtherPicGridAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String,Object>> list;
    public OtherPicGridAdapter(Context context, List<Map<String,Object>> list){
        this.context = context;
        this.list = list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(list.get(position).get("type").toString());
    }

    @Override
    public int getViewTypeCount() {
        return list.size() == 1 ? 1 : 2;
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
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        int type = getItemViewType(i);
        if(view == null){
            switch (type){
                case Constants.NUMFIRST:
                    view = LayoutInflater.from(context).inflate(R.layout.item_other_pic_layout,null);
                    holder1 = new ViewHolder1(view);
                    view.setTag(holder1);
                    break;
                case Constants.NUMSECONd:
                    view = LayoutInflater.from(context).inflate(R.layout.item_other_pic_add_layout,null);
                    holder2 = new ViewHolder2(view);
                    view.setTag(holder2);
                    break;
            }
        }else{
            switch (type){
                case Constants.NUMFIRST:
                    holder1 = (ViewHolder1) view.getTag();
                    break;
                case Constants.NUMSECONd:
                    holder2 = (ViewHolder2) view.getTag();
                    break;
            }
        }

        switch (type){
            case Constants.NUMFIRST:
                if(((OtherInfo.PicBean)list.get(i).get("picInfo")).getId() == null){
                    ImagePicker.getInstance().getImageLoader().displayImage((Activity) context, MyApplication.getInstance().getBsaeUrl()+((OtherInfo.PicBean)list.get(i).get("picInfo")).getUrl(),holder1.ivImage, 0, 0);
                }else{
                    if(((OtherInfo.PicBean)list.get(i).get("picInfo")).getId() == -1)
                        ImagePicker.getInstance().getImageLoader().displayImage((Activity) context,((OtherInfo.PicBean)list.get(i).get("picInfo")).getUrl(),holder1.ivImage, 0, 0);
                    else{
                        if(FileUtils.isFileExists(((OtherInfo.PicBean)list.get(i).get("picInfo")).getUrl())){
                            ImagePicker.getInstance().getImageLoader().displayImage((Activity) context,((OtherInfo.PicBean)list.get(i).get("picInfo")).getUrl(),holder1.ivImage, 0, 0);
                        }else{
                            ImagePicker.getInstance().getImageLoader().displayImage((Activity) context,MyApplication.getInstance().getBsaeUrl()+((OtherInfo.PicBean)list.get(i).get("picInfo")).getUrl(),holder1.ivImage, 0, 0);
                        }
                    }
                }
                holder1.tvName.setText(((OtherInfo.PicBean)list.get(i).get("picInfo")).getName());
                if(list.size() == 2)
                    holder1.ivDelete.setVisibility(View.GONE);
                else
                    holder1.ivDelete.setVisibility(View.VISIBLE);
                holder1.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setEventId(MessageEvent.EVENT_OTHER_PHONE_DELETE_SUCCESS);
                        messageEvent.setObj(i);
                        EventBus.getDefault().post(messageEvent);
                    }
                });
                break;
            case Constants.NUMSECONd:
                holder2.ll_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, OtherAddPicActivity.class));
                    }
                });
                break;
        }
        return view;
    }

    class ViewHolder1{
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        public ViewHolder1(View view){
            ButterKnife.bind(this,view);
       }
    }

    class ViewHolder2{
        @BindView(R.id.ll_add)
        LinearLayout ll_add;
        public ViewHolder2(View view){
            ButterKnife.bind(this,view);
        }
    }


}

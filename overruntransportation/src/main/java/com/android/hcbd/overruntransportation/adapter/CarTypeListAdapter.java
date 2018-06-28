package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.CarTypeInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/29.
 */

public class CarTypeListAdapter extends BaseAdapter {
    private Context context;
    private List<CarTypeInfo.DataInfo> list;
    private int position;
    public CarTypeListAdapter(Context context,List<CarTypeInfo.DataInfo> list){
        this.context = context;
        this.list = list;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_car_type_list_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_type.setText(list.get(i).getType());
        Glide.with(context).load(MyApplication.getInstance().getBsaeUrl()+list.get(i).getTypeImg()).into(holder.iv_typeImg);
        holder.tv_axisNum.setText("轴数："+list.get(i).getAxisNum());
        holder.tv_wheelNum.setText("轮数："+list.get(i).getWheelNum());
        holder.tv_limit.setText("总质量限制："+list.get(i).getLimit());
        holder.tv_length.setText("长度："+list.get(i).getLength());
        holder.tv_width.setText("宽度："+list.get(i).getWidth());
        holder.tv_height.setText("高度："+list.get(i).getHeight());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[3].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "您当前登录的账户没有该权限！");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("删除提示");
                builder.setMessage("您确认删除该信息吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        final DialogUtils dialogUtils = new DialogUtils();
                        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl() + HttpUrlUtils.delete_car_type_url)
                                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                                .params("tpsCarType.id",list.get(i).getId())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onStart(Request<String, ? extends Request> request) {
                                        super.onStart(request);
                                        dialogUtils.showLoading(context);
                                    }

                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        String result = response.body();
                                        LogUtils.LogShow(result);
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(result);
                                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                                setPosition(i);
                                                ToastUtils.showShortToast(context,jsonObject.getString("data"));
                                                MessageEvent messageEvent = new MessageEvent(MessageEvent.EVENT_CAR_TYPE_LIST_DEL);
                                                EventBus.getDefault().post(messageEvent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            try {
                                                if(!TextUtils.isEmpty(jsonObject.getString("error"))){
                                                    ToastUtils.showShortToast(MyApplication.getInstance(),jsonObject.getString("error"));
                                                }
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        if(NetworkUtils.isAvailableByPing()){
                                            ToastUtils.showShortToast(MyApplication.getInstance(),"访问出错");
                                        }else{
                                            ToastUtils.showShortToast(MyApplication.getInstance(),"请检查网络是否连接");
                                        }
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                        dialogUtils.dismissLoading();
                                    }
                                });
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.iv_typeImg)
        ImageView iv_typeImg;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.tv_axisNum)
        TextView tv_axisNum;
        @BindView(R.id.tv_wheelNum)
        TextView tv_wheelNum;
        @BindView(R.id.tv_limit)
        TextView tv_limit;
        @BindView(R.id.tv_length)
        TextView tv_length;
        @BindView(R.id.tv_width)
        TextView tv_width;
        @BindView(R.id.tv_height)
        TextView tv_height;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

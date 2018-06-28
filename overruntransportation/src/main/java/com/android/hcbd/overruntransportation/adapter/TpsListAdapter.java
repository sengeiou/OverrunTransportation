package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.TpsListInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.activity.InputStep1Activity;
import com.android.hcbd.overruntransportation.ui.activity.OtherInfoActivity;
import com.android.hcbd.overruntransportation.ui.activity.WordListActivity;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/20.
 */

public class TpsListAdapter extends BaseAdapter {
    private Context context;
    List<TpsListInfo.DataInfo> tpsListInfoList = new ArrayList<>();
    public TpsListAdapter(Context context,List<TpsListInfo.DataInfo> tpsListInfoList){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_tps_list_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_code.setText(tpsListInfoList.get(i).getCode());
        holder.tv_createTime.setText(tpsListInfoList.get(i).getCreateTime().replace("T"," "));
        holder.tv_checkState.setText(tpsListInfoList.get(i).getCheckStateName());
        if(tpsListInfoList.get(i).getCheckStateName().equals("已复核")){
            holder.tv_checkState.setTextColor(0xFF000000);
        }else if(tpsListInfoList.get(i).getCheckStateName().equals("待复核")){
            holder.tv_checkState.setTextColor(0xFF11A9EE);
        }else if(tpsListInfoList.get(i).getCheckStateName().equals("驳回")){
            holder.tv_checkState.setTextColor(0xFFFF0000);
        }else{
            holder.tv_checkState.setTextColor(0xFFAAAAAA);
        }
        holder.tv_no1.setText(tpsListInfoList.get(i).getCar().getNo1());
        holder.tv_driver_name.setText(tpsListInfoList.get(i).getDriver().getName());
        holder.tv_goods.setText(tpsListInfoList.get(i).getTpsGoods().getGoods());
        holder.tv_site_checkPlace.setText(tpsListInfoList.get(i).getSite().getCheckPlace());
        holder.tv_stateName.setText(tpsListInfoList.get(i).getStateName());
        holder.btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tpsListInfoList.get(i).getCheckStateName().equals("已复核")){
                    ToastUtils.showLongToast(context,"已复核，无需再次提交。");
                }else if(tpsListInfoList.get(i).getCheckStateName().equals("待复核")){
                    ToastUtils.showLongToast(context,"已提交，无需再次提交。");
                }else{
                    final DialogUtils dialogUtils = new DialogUtils();

                    OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.submit_to_check_url)
                            .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                            .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                            .params("token",MyApplication.getInstance().getLoginInfo().getToken())
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
                                            ToastUtils.showLongToast(context,jsonObject.getString("data"));
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setEventId(MessageEvent.EVENT_TPS_EDIT_SUCCESS);
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
            }
        });
        holder.btn_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OtherInfoActivity.class);
                intent.putExtra("oid",tpsListInfoList.get(i).getId());
                context.startActivity(intent);
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[2].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "您当前登录的账户没有该权限！");
                    return;
                }
                MyApplication.getInstance().setEdit(true);
                Intent intent = new Intent(context, InputStep1Activity.class);
                intent.putExtra("oid", tpsListInfoList.get(i).getId());
                context.startActivity(intent);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[3].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "您当前登录的账户没有该权限！");
                    return;
                }

                final DialogUtils dialogUtils = new DialogUtils();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                        dialogInterface.dismiss();
                        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.delete_content_url)
                                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                                .params("oid",tpsListInfoList.get(i).getId())
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
                                                ToastUtils.showLongToast(context,jsonObject.getString("data"));
                                                MessageEvent messageEvent = new MessageEvent();
                                                messageEvent.setEventId(MessageEvent.EVENT_TPS_EDIT_SUCCESS);
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
        holder.btn_wenshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WordListActivity.class);
                intent.putExtra("oid",tpsListInfoList.get(i).getId());
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_code)
        TextView tv_code;
        @BindView(R.id.tv_createTime)
        TextView tv_createTime;
        @BindView(R.id.tv_checkState)
        TextView tv_checkState;
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
        @BindView(R.id.btn_submit)
        Button btn_submit;
        @BindView(R.id.btn_other)
        Button btn_other;
        @BindView(R.id.btn_edit)
        Button btn_edit;
        @BindView(R.id.btn_delete)
        Button btn_delete;
        @BindView(R.id.btn_wenshu)
        Button btn_wenshu;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

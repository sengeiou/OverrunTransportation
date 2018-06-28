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
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/22.
 */

public class OtherQuestionAdapter extends BaseAdapter {
    private List<OtherInfo.QuestionBean> list;
    private Context context;
    private String oid;
    public OtherQuestionAdapter(Context context,List<OtherInfo.QuestionBean> list){
        this.context = context;
        this.list = list;
    }

    public void setList(List<OtherInfo.QuestionBean> list) {
        this.list = list;
    }

    public void setOid(String oid) {
        this.oid = oid;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_other_question_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title1.setText("问题"+(i+1));
        holder.tv_title2.setText("答"+(i+1));
        holder.tv_question.setText(list.get(i).getQuestion());
        holder.tv_answer.setText(list.get(i).getAnswer());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(i);
            }
        });
        return view;
    }

    private void showDialog(final int position) {
        final String[] strs = MyApplication.getInstance().getPowerStr().split(",");
        if(strs[3].equals("0")) {
            ToastUtils.showShortToast(MyApplication.getInstance(), "您当前登录的账户没有该权限！");
            return;
        }
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
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                final DialogUtils dialogUtils = new DialogUtils();
                OkGo.<String>post(MyApplication.getInstance().getBsaeUrl() + HttpUrlUtils.save_other_question_url)
                        .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                        .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                        .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                        .params("flag","delete")
                        .params("seq",""+list.get(position).getSeq())
                        .params("ids",oid)
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
                                        Toast.makeText(MyApplication.getInstance(),"删除成功！",Toast.LENGTH_SHORT).show();
                                        MessageEvent messageEvent = new MessageEvent();
                                        messageEvent.setEventId(MessageEvent.EVENT_OTHER_QUESTION_ADD_SUCCESS);
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

    class ViewHolder{
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.tv_title1)
        TextView tv_title1;
        @BindView(R.id.tv_question)
        TextView tv_question;
        @BindView(R.id.tv_title2)
        TextView tv_title2;
        @BindView(R.id.tv_answer)
        TextView tv_answer;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

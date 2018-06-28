package com.android.hcbd.overruntransportation.adapter;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
import com.android.hcbd.overruntransportation.entity.TemplateManageInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.IntentUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.NetWorkUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.android.hcbd.overruntransportation.util.IntentUtils.getWordFileIntent;

/**
 * Created by guocheng on 2017/3/29.
 */

public class WordListAdapter extends BaseAdapter {
    private Context context;
    private List<TemplateManageInfo.DataInfo> list;
    private int position;
    private int oid;
    public WordListAdapter(Context context, List<TemplateManageInfo.DataInfo> list){
        this.context = context;
        this.list = list;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOid(int oid) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_word_list_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_name.setText((i+1)+"、"+list.get(i).getUploadFileName());
        holder.tv_operNames.setText("上传人："+list.get(i).getOperNames());
        holder.tv_time.setText("上传时间："+list.get(i).getCreateTime().replace("T"," "));

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[3].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "您当前登录的账户没有该权限！");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除提示");
                builder.setMessage("您确认删除该信息吗？");
                builder.setCancelable(false);
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
                        final DialogUtils dialogUtils = new DialogUtils();
                        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.delete_word_list_url)
                                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                                .params("tpsWord.id",list.get(i).getId())
                                .params("tpsWord.type","Oper")
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
                                                MessageEvent messageEvent = new MessageEvent(MessageEvent.EVENT_WORD_LIST_DEL);
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
        holder.iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetWorkUtils.getAPNType(context) != 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("您当前处于移动网络是否下载？");
                    builder.setCancelable(false);
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
                            realDownload();
                        }
                    });
                    builder.create().show();
                }else{
                    realDownload();
                }
            }
            private void realDownload() {
                if (!AndPermission.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ToastUtils.showShortToast(context,"存储权限被禁止，无法进行下载");
                    return;
                }

                OkGo.<File>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.download_word_url)
                        .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                        .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                        .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                        .params("params",list.get(i).getUrl())
                        .params("contentType",list.get(i).getUploadContentType())
                        .params("fileName",list.get(i).getUploadFileName())
                        .execute(new FileCallback(MyApplication.getInstance().getSDPath()+"/tps/download/",list.get(i).getUploadFileName()) {
                            @Override
                            public void onStart(Request<File, ? extends Request> request) {
                                super.onStart(request);
                                ToastUtils.showShortToast(context,"正在下载文书...");
                            }

                            @Override
                            public void onSuccess(Response<File> response) {
                                ToastUtils.showShortToast(context,"文书下载完成。");
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("文书下载完成");
                                builder.setMessage("是否立即打开查看？");
                                builder.setCancelable(false);
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
                                        Intent intent = IntentUtils.getWordFileIntent(context,MyApplication.getInstance().getSDPath()+"/tps/download/"+list.get(i).getUploadFileName());
                                        context.startActivity(intent);
                                    }
                                });
                                builder.create().show();

                                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                                mBuilder.setContentTitle("下载完成,点击打开。");//设置通知栏标题
                                mBuilder.setContentText(list.get(i).getUploadFileName()); //设置通知栏显示内容
                                PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, getWordFileIntent(context,MyApplication.getInstance().getSDPath()+"/tps/download/"+list.get(i).getUploadFileName()) , Notification.FLAG_AUTO_CANCEL);
                                mBuilder.setContentIntent(pendingIntent); //设置通知栏点击意图
                                mBuilder.setTicker("下载完成,点击打开。"); //通知首次出现在通知栏，带上升动画效果的
                                mBuilder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                                mBuilder.setPriority(Notification.PRIORITY_DEFAULT); //设置该通知优先级
                                mBuilder.setAutoCancel(true);//设置这个标志当用户单击面板就可以让通知将自动取消
                                mBuilder.setOngoing(false);//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                                mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                                mBuilder.setSmallIcon(R.drawable.app_icon);//设置通知小ICON
                                mNotificationManager.notify(list.get(i).getId(), mBuilder.build());
                            }

                            @Override
                            public void downloadProgress(Progress progress) {
                                super.downloadProgress(progress);
                            }

                            @Override
                            public void onError(Response<File> response) {
                                super.onError(response);
                                if(NetworkUtils.isAvailableByPing()){
                                    ToastUtils.showShortToast(MyApplication.getInstance(),"下载出错");
                                }else{
                                    ToastUtils.showShortToast(MyApplication.getInstance(),"请检查网络是否连接");
                                }
                            }
                        });

            }
        });
        holder.iv_download_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetWorkUtils.getAPNType(context) != 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("您当前处于移动网络是否下载？");
                    builder.setCancelable(false);
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
                            realDownload();
                        }
                    });
                    builder.create().show();
                }else{
                    realDownload();
                }
            }

            private void realDownload() {
                if (!AndPermission.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ToastUtils.showShortToast(context,"存储权限被禁止，无法进行下载");
                    return;
                }
                OkGo.<File>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.generate_word_url)
                        .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                        .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                        .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                        .params("oid",oid)
                        .params("tpsWord.id",list.get(i).getId())
                        .params("contentType ",list.get(i).getUploadContentType())
                        .execute(new FileCallback(MyApplication.getInstance().getSDPath()+"/tps/download/",list.get(i).getUploadFileName()) {

                            @Override
                            public void onStart(Request<File, ? extends Request> request) {
                                super.onStart(request);
                                ToastUtils.showShortToast(context,"正在生成文书...");
                            }

                            @Override
                            public void onSuccess(Response<File> response) {
                                File file = response.body();
                                String str = FileUtils.readFile2String(file,null);
                                if(str.indexOf("请设置集体讨论信息") != -1){
                                    ToastUtils.showShortToast(MyApplication.getInstance(),"请先设置集体讨论信息！");
                                    return;
                                }
                                ToastUtils.showShortToast(context,"文书生成完成。");
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("文书生成完成");
                                builder.setMessage("是否立即打开查看？");
                                builder.setCancelable(false);
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
                                        Intent intent = IntentUtils.getWordFileIntent(context,MyApplication.getInstance().getSDPath()+"/tps/download/"+list.get(i).getUploadFileName());
                                        context.startActivity(intent);
                                    }
                                });
                                builder.create().show();

                                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                                mBuilder.setContentTitle("下载完成,点击打开。");//设置通知栏标题
                                mBuilder.setContentText(list.get(i).getUploadFileName()); //设置通知栏显示内容
                                PendingIntent pendingIntent= PendingIntent.getActivity(context, 1,IntentUtils.getWordFileIntent(context,MyApplication.getInstance().getSDPath()+"/tps/download/"+list.get(i).getUploadFileName()) , Notification.FLAG_AUTO_CANCEL);
                                mBuilder.setContentIntent(pendingIntent); //设置通知栏点击意图
                                mBuilder.setTicker("下载完成,点击打开。"); //通知首次出现在通知栏，带上升动画效果的
                                mBuilder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                                mBuilder.setPriority(Notification.PRIORITY_DEFAULT); //设置该通知优先级
                                mBuilder.setAutoCancel(true);//设置这个标志当用户单击面板就可以让通知将自动取消
                                mBuilder.setOngoing(false);//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                                mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                                mBuilder.setSmallIcon(R.drawable.app_icon);//设置通知小ICON
                                mNotificationManager.notify(list.get(i).getId(),mBuilder.build());
                            }

                            @Override
                            public void downloadProgress(Progress progress) {
                                super.downloadProgress(progress);
                            }

                            @Override
                            public void onError(Response<File> response) {
                                super.onError(response);
                                if(NetworkUtils.isAvailableByPing()){
                                    ToastUtils.showShortToast(MyApplication.getInstance(),"下载出错");
                                }else{
                                    ToastUtils.showShortToast(MyApplication.getInstance(),"请检查网络是否连接");
                                }
                            }
                        });

            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.iv_download_word)
        ImageView iv_download_word;
        @BindView(R.id.iv_download)
        ImageView iv_download;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.tv_operNames)
        TextView tv_operNames;
        @BindView(R.id.tv_time)
        TextView tv_time;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

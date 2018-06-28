package com.android.hcbd.overruntransportation.ui.fragment;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.OverLoadDataAdapter;
import com.android.hcbd.overruntransportation.entity.OverLoadDataInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.activity.OverloadDataAdd1Activity;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.IntentUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.NetWorkUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 信息统计表
 */
public class InfoStatisticsFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.iv_start_time)
    ImageView iv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.iv_end_time)
    ImageView iv_end_time;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.iv_export)
    ImageView iv_export;

    private DialogUtils dialogUtils = new DialogUtils();
    private int currentPage = 1;
    private List<OverLoadDataInfo.DataInfo> dataInfoList = new ArrayList<>();
    private OverLoadDataAdapter adapter;


    public InfoStatisticsFragment() {
        // Required empty public constructor
    }

    public static InfoStatisticsFragment newInstance(String param1, String param2) {
        InfoStatisticsFragment fragment = new InfoStatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_statistics, container, false);
        ButterKnife.bind(this,view);

        setupRefresh();
        dialogUtils.showLoading(getActivity());
        initHttpData();
        initListener();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_OVERLOAD_DATA_LIST_ADD:
                currentPage = 1;
                initHttpData();
                break;
            case MessageEvent.EVENT_OVERLOAD_DATA_LIST_EDIT:
                OverLoadDataInfo.DataInfo dataInfo = (OverLoadDataInfo.DataInfo) event.getObj();
                dataInfoList.set(adapter.getPosition(),dataInfo);
                adapter.notifyDataSetChanged();
                break;
            case MessageEvent.EVENT_OVERLOAD_DATA_LIST_DEL:
                dataInfoList.remove(adapter.getPosition());
                adapter.notifyDataSetChanged();
                if(dataInfoList.size()==0){
                    listView.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.overload_data_list_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("tpsData.beginTime", TextUtils.isEmpty(tv_start_time.getText().toString()) ? "" : tv_start_time.getText().toString())
                .params("tpsData.endTime",TextUtils.isEmpty(tv_end_time.getText().toString()) ? "" : tv_end_time.getText().toString())
                .params("currentPage",currentPage)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        if(1 == currentPage)
                            dataInfoList.clear();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            JSONArray array = new JSONArray(jsonObject.getString("data"));
                            if(array.length()==0){
                                refreshLayout.finishRefreshing();
                                refreshLayout.finishLoadmore();
                                currentPage = currentPage - 1;
                                if(dataInfoList.size()>0){
                                    ll_empty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                }else{
                                    ll_empty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                                return;
                            }
                            ll_empty.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            Gson gson = new Gson();
                            for(int i=0;i<array.length();i++){
                                OverLoadDataInfo.DataInfo dataInfo = gson.fromJson(array.getString(i),OverLoadDataInfo.DataInfo.class);
                                dataInfoList.add(dataInfo);
                            }

                            if(1 == currentPage){
                                adapter = new OverLoadDataAdapter(getActivity(),dataInfoList);
                                listView.setAdapter(adapter);
                                refreshLayout.finishRefreshing();
                            }else{
                                adapter.notifyDataSetChanged();
                                refreshLayout.finishLoadmore();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
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

    private void setupRefresh() {
        SinaRefreshView headerView = new SinaRefreshView(getActivity());
        refreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getActivity());
        refreshLayout.setBottomView(loadingView);
        refreshLayout.setAutoLoadMore(true);
    }

    private void initListener() {
        iv_edit.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        iv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        iv_end_time.setOnClickListener(this);
        iv_export.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                currentPage = 1;
                initHttpData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                currentPage++;
                initHttpData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[2].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                    return;
                }
                MyApplication.getInstance().setEdit(true);
                Intent intent = new Intent(getActivity(),OverloadDataAdd1Activity.class);
                intent.putExtra("data", dataInfoList.get(i));
                startActivity(intent);
                adapter.setPosition(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_edit:
                if(!TextUtils.isEmpty(MyApplication.getInstance().getPowerStr())){
                    String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                    if(strs[0].equals("0")) {
                        ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                        return;
                    }
                    MyApplication.getInstance().setEdit(false);
                    startActivity(new Intent(getActivity(),OverloadDataAdd1Activity.class));
                }
                break;
            case R.id.iv_search:
                dialogUtils.showLoading(getActivity());
                currentPage = 1;
                initHttpData();
                break;
            case R.id.tv_start_time:
                showDialogYearMonthDay(tv_start_time);
                break;
            case R.id.iv_start_time:
                showDialogYearMonthDay(tv_start_time);
                break;
            case R.id.tv_end_time:
                showDialogYearMonthDay(tv_end_time);
                break;
            case R.id.iv_end_time:
                showDialogYearMonthDay(tv_end_time);
                break;
            case R.id.iv_export:
                if(NetWorkUtils.getAPNType(getActivity()) != 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("下载提示");
                    builder.setMessage("当前处于移动网络是否下载？");
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
                break;
        }
    }

    private void realDownload() {
        if (!AndPermission.hasPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtils.showShortToast(getActivity(),"存储权限被禁止，无法进行下载");
            return;
        }
        OkGo.<File>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.export_overload_data_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("tpsData.beginTime",tv_start_time.getText().toString())
                .params("tpsData.endTime",tv_end_time.getText().toString())
                .execute(new FileCallback(MyApplication.getInstance().getSDPath()+"/tps/download/","治超信息汇总表.xlsx") {
                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        ToastUtils.showShortToast(getActivity(),"正在导出治超数据信息统计表...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                        ToastUtils.showShortToast(getActivity(),"治超数据信息统计表导出完成。");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("治超数据信息统计表导出完成");
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
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = IntentUtils.getExcelFileIntent(getActivity(),MyApplication.getInstance().getSDPath()+"/tps/download/"+"治超信息汇总表.xlsx");
                                startActivity(intent);
                            }
                        });
                        builder.create().show();
                        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
                        mBuilder.setContentTitle("下载完成,点击打开。");//设置通知栏标题
                        mBuilder.setContentText("治超信息汇总表.xlsx"); //设置通知栏显示内容
                        PendingIntent pendingIntent= PendingIntent.getActivity(getActivity(), 1, IntentUtils.getExcelFileIntent(getActivity(),MyApplication.getInstance().getSDPath()+"/tps/download/"+"治超信息汇总表.xlsx") , Notification.FLAG_AUTO_CANCEL);
                        mBuilder.setContentIntent(pendingIntent); //设置通知栏点击意图
                        mBuilder.setTicker("下载完成,点击打开。"); //通知首次出现在通知栏，带上升动画效果的
                        mBuilder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        mBuilder.setPriority(Notification.PRIORITY_DEFAULT); //设置该通知优先级
                        mBuilder.setAutoCancel(true);//设置这个标志当用户单击面板就可以让通知将自动取消
                        mBuilder.setOngoing(false);//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                        mBuilder.setSmallIcon(R.drawable.app_icon);//设置通知小ICON
                        mNotificationManager.notify(1, mBuilder.build());
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<File> response) {
                        super.onError(response);
                        if(NetworkUtils.isAvailableByPing()){
                            ToastUtils.showShortToast(MyApplication.getInstance(),"下载失败");
                        }else{
                            ToastUtils.showShortToast(MyApplication.getInstance(),"请检查网络是否连接");
                        }
                    }
                });

    }


    private void showDialogYearMonthDay(final TextView tv){
        new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                tv.setText(format.format(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFEFEFEF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .isCenterLabel(false)
                .isDialog(true)//是否显示为对话框样式
                .build().show();
    }

}

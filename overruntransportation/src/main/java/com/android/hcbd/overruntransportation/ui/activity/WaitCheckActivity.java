package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.WaitCheckListAdapter;
import com.android.hcbd.overruntransportation.entity.TpsListInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaitCheckActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private DialogUtils dialogUtils = new DialogUtils();
    private int currentPage = 1;
    private List<TpsListInfo.DataInfo> dataInfoList = new ArrayList<>();
    private WaitCheckListAdapter adapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_check);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        setupRefresh();
        dialogUtils.showLoading(this);
        initHttpData();
        initListener();

    }

    private void setupRefresh() {
        SinaRefreshView headerView = new SinaRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(this);
        refreshLayout.setBottomView(loadingView);
        refreshLayout.setAutoLoadMore(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_TPS_CHECK_SAVE_SUCCESS:
                initHttpData();
                break;
        }
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
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
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.check_list_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("punish.checkState","0")
                .params("currentPage",currentPage)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
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
                                TpsListInfo.DataInfo tpsListInfo = gson.fromJson(array.getString(i),TpsListInfo.DataInfo.class);
                                dataInfoList.add(tpsListInfo);
                            }

                            if(1 == currentPage){
                                adapter = new WaitCheckListAdapter(WaitCheckActivity.this,dataInfoList);
                                listView.setAdapter(adapter);
                                refreshLayout.finishRefreshing();
                            }else{
                                adapter.notifyDataSetChanged();
                                refreshLayout.finishLoadmore();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }
}

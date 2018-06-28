package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.WorkDayAdapter;
import com.android.hcbd.overruntransportation.entity.WorkDayInfo;
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
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

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

public class WorkDayListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private WorkDayAdapter adapter;
    private DialogUtils dialogUtils = new DialogUtils();
    private int currentPage = 1;
    private List<WorkDayInfo.DataInfo> dataInfoList = new ArrayList<>();

    private String searchStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_day_list);
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

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                currentPage = 1;
                initHttpData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                currentPage++;
                initHttpData();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[2].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                    return;
                }
                Intent intent = new Intent(WorkDayListActivity.this,WorkDayAddActivity.class);
                intent.putExtra("type","edit");
                intent.putExtra("data", dataInfoList.get(i));
                startActivity(intent);
                adapter.setPosition(i);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_WORK_DAY_LIST_EDIT:
                WorkDayInfo.DataInfo dataInfo = (WorkDayInfo.DataInfo) event.getObj();
                dataInfoList.set(adapter.getPosition(),dataInfo);
                adapter.notifyDataSetChanged();
                break;
            case MessageEvent.EVENT_WORK_DAY_LIST_ADD:
                currentPage = 1;
                initHttpData();
                break;
            case MessageEvent.EVENT_WORK_DAY_LIST_DEL:
                dataInfoList.remove(adapter.getPosition());
                adapter.notifyDataSetChanged();
                if(dataInfoList.size()==0){
                    mListView.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.work_day_list_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("currentPage",currentPage)
                .params("tpsWorkDay.name",TextUtils.isEmpty(searchStr)?"":searchStr)
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
                                    mListView.setVisibility(View.VISIBLE);
                                }else{
                                    ll_empty.setVisibility(View.VISIBLE);
                                    mListView.setVisibility(View.GONE);
                                }
                                return;
                            }

                            ll_empty.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                            Gson gson = new Gson();
                            for(int i=0;i<array.length();i++){
                                WorkDayInfo.DataInfo dataInfo = gson.fromJson(array.getString(i),WorkDayInfo.DataInfo.class);
                                dataInfoList.add(dataInfo);
                            }

                            if(1 == currentPage){
                                adapter = new WorkDayAdapter(WorkDayListActivity.this,dataInfoList);
                                mListView.setAdapter(adapter);
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
            case R.id.iv_edit:
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[0].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                    return;
                }
                Intent intent = new Intent(this,WorkDayAddActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
                break;
            case R.id.iv_search:
                SearchFragment searchFragment = SearchFragment.newInstance(searchStr,null);
                searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
                    @Override
                    public void OnSearchClick(String keyword) {
                        searchStr = keyword ;
                        dialogUtils.showLoading(WorkDayListActivity.this);
                        currentPage = 1;
                        initHttpData();
                    }
                });
                searchFragment.show(getSupportFragmentManager(),SearchFragment.TAG);
                break;
        }
    }
}

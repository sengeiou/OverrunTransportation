package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.MyViewPagerAdapter;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.fragment.DiscussedFragment;
import com.android.hcbd.overruntransportation.ui.fragment.QuestionFragment;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
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

public class OtherInfoActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPager;
    private int oid ;
    private DialogUtils dialogUtils = new DialogUtils();
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        oid = getIntent().getIntExtra("oid",0);
        initHttpData();
        initListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_OTHER_QUESTION_ADD_SUCCESS:
                flag = true;
                initHttpData();
                break;
        }
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(0 == tab.getPosition())
                    ivSave.setVisibility(View.VISIBLE);
                else
                    ivSave.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_other_content_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",String.valueOf(oid))
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(OtherInfoActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        dealJsonData(result);
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

    private void dealJsonData(final String response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Gson gson = new Gson();
                    OtherInfo otherInfo = new OtherInfo();
                    otherInfo.setTotal(jsonObject.getString("total"));
                    otherInfo.setEditFlag(jsonObject.getString("editFlag"));

                    JSONArray questionArray = new JSONArray(jsonObject.getString("questionList"));
                    List<OtherInfo.QuestionBean> questionBeanList = new ArrayList<>();
                    for(int i=0;i<questionArray.length();i++){
                        OtherInfo.QuestionBean questionBean = gson.fromJson(questionArray.getString(i),OtherInfo.QuestionBean.class);
                        questionBeanList.add(questionBean);
                    }
                    otherInfo.setQuestionList(questionBeanList);

                    JSONArray picArray = new JSONArray(jsonObject.getString("picList"));
                    List<OtherInfo.PicBean> picBeanList = new ArrayList<>();
                    for(int i=0;i<picArray.length();i++){
                        OtherInfo.PicBean picBean = gson.fromJson(picArray.getString(i),OtherInfo.PicBean.class);
                        picBeanList.add(picBean);
                    }
                    otherInfo.setPicList(picBeanList);

                    JSONArray rowsArray = new JSONArray(jsonObject.getString("rows"));
                    List<OtherInfo.RowsBean> rowsBeanList = new ArrayList<>();
                    for(int i=0;i<rowsArray.length();i++){
                        OtherInfo.RowsBean rowsBean = gson.fromJson(rowsArray.getString(i),OtherInfo.RowsBean.class);
                        rowsBeanList.add(rowsBean);
                    }
                    otherInfo.setRows(rowsBeanList);

                    OtherInfo.DiscussBean discussBean = gson.fromJson(jsonObject.getString("discussBean"),OtherInfo.DiscussBean.class);
                    otherInfo.setDiscussBean(discussBean);
                    MyApplication.getInstance().setOtherInfo(otherInfo);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(flag){
                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.setEventId(MessageEvent.EVENT_OTHER_UPDATE_SUCCESS);
                                EventBus.getDefault().post(messageEvent);
                            }else{
                                setupTabLayout();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    final JSONObject finalJsonObject = jsonObject;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(!TextUtils.isEmpty(finalJsonObject.getString("error"))){
                                    ToastUtils.showShortToast(MyApplication.getInstance(), finalJsonObject.getString("error"));
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void setupTabLayout() {
        //ViewPager关联适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DiscussedFragment.newInstance(String.valueOf(oid),null),"集体讨论列表");
        adapter.addFragment(QuestionFragment.newInstance(String.valueOf(oid),null),"问题列表");
        viewPager.setAdapter(adapter);
        //ViewPager和TabLayout关联
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确认修改吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                        if(strs[2].equals("0")) {
                            ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                            return;
                        }
                        dialogInterface.dismiss();
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setEventId(MessageEvent.EVENT_OTHER_DISCUSSED_SAVE_SUCCESS);
                        EventBus.getDefault().post(messageEvent);
                    }
                });
                builder.create().show();
                break;
        }
    }
}

package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.MyViewPagerAdapter;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.entity.TpsListInfo;
import com.android.hcbd.overruntransportation.ui.fragment.DiscussInfoFragment;
import com.android.hcbd.overruntransportation.ui.fragment.QuestionInfoFragment;
import com.android.hcbd.overruntransportation.ui.fragment.TpsInfo2Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TpsInfoActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPager;
    private TpsListInfo.DataInfo tpsListInfo;
    private DialogUtils dialogUtils = new DialogUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tps_info);
        ButterKnife.bind(this);

        tpsListInfo = (TpsListInfo.DataInfo) getIntent().getSerializableExtra("data");
        initHttpData();
        iv_back.setOnClickListener(this);
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_other_content_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",String.valueOf(tpsListInfo.getId()))
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(TpsInfoActivity.this);
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
                            dialogUtils.dismissLoading();
                            setupTabLayout();
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
        adapter.addFragment(TpsInfo2Fragment.newInstance(tpsListInfo,null),"超限处罚详情");
        adapter.addFragment(DiscussInfoFragment.newInstance(String.valueOf(tpsListInfo.getId()),null),"集体讨论列表");
        adapter.addFragment(QuestionInfoFragment.newInstance(String.valueOf(tpsListInfo.getId()),null),"问题列表");
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
        }
    }
}

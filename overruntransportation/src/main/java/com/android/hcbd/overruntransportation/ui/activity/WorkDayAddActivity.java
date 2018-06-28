package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.WorkDayInfo;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkDayAddActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_name)
    ImageView iv_name;
    @BindView(R.id.cb_enable)
    CheckBox cb_enable;
    @BindView(R.id.cb_frozen)
    CheckBox cb_frozen;
    @BindView(R.id.cb_close)
    CheckBox cb_close;
    private String type;
    private WorkDayInfo.DataInfo dataInfo;
    private DialogUtils dialogUtils = new DialogUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_day_add);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");
        dataInfo = (WorkDayInfo.DataInfo) getIntent().getSerializableExtra("data");
        initData();
        initListener();
    }

    private void initData() {
        cb_enable.setChecked(true);
        cb_frozen.setChecked(false);
        cb_close.setChecked(false);
        if(type.equals("add")) {
            tv_title.setText("工作日安排录入");
        }else{
            tv_title.setText("工作日安排修改");
            tv_name.setText(dataInfo.getName());
            if(dataInfo.getState().equals("1")){
                cb_enable.setChecked(true);
                cb_frozen.setChecked(false);
                cb_close.setChecked(false);
            }else if(dataInfo.getState().equals("2")){
                cb_enable.setChecked(false);
                cb_frozen.setChecked(true);
                cb_close.setChecked(false);
            }else{
                cb_enable.setChecked(false);
                cb_frozen.setChecked(false);
                cb_close.setChecked(true);
            }
        }

    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_save.setOnClickListener(this);
        iv_name.setOnClickListener(this);
        cb_enable.setOnClickListener(this);
        cb_frozen.setOnClickListener(this);
        cb_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                if(TextUtils.isEmpty(tv_name.getText().toString())){
                    ToastUtils.showShortToast(this,"请选择工作日时间！");
                    return;
                }
                savaDate();
                break;
            case R.id.iv_name:
                showDialogYearMonthDay(tv_name);
                break;
            case R.id.cb_enable:
                cb_enable.setChecked(true);
                cb_frozen.setChecked(false);
                cb_close.setChecked(false);
                break;
            case R.id.cb_frozen:
                cb_enable.setChecked(false);
                cb_frozen.setChecked(true);
                cb_close.setChecked(false);
                break;
            case R.id.cb_close:
                cb_enable.setChecked(false);
                cb_frozen.setChecked(false);
                cb_close.setChecked(true);
                break;
        }
    }

    private void savaDate() {
        String state = "";
        if(cb_enable.isChecked()){
            state = "1";
        }else if(cb_frozen.isChecked()){
            state = "2";
        }else{
            state = "3";
        }
        final String finalState = state;
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.add_edit_work_day_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", dataInfo == null ? "" : String.valueOf(dataInfo.getId()))
                .params("tpsWorkDay.code",dataInfo == null ? "" : dataInfo.getCode())
                .params("tpsWorkDay.name",tv_name.getText().toString())
                .params("tpsWorkDay.state",state)
                .params("tpsWorkDay.operNames",MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .params("tpsWorkDay.id",dataInfo == null ? "":String.valueOf(dataInfo.getId()))
                .params("tpsWorkDay.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(WorkDayAddActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                if(type.equals("edit")){
                                    dataInfo.setName(tv_name.getText().toString());
                                    dataInfo.setState(finalState);
                                    switch (finalState){
                                        case "1":
                                            dataInfo.setStateContent("启用");
                                            break;
                                        case "2":
                                            dataInfo.setStateContent("冻结");
                                            break;
                                        case "3":
                                            dataInfo.setStateContent("关闭");
                                            break;
                                    }
                                    dataInfo.setOperNames(MyApplication.getInstance().getLoginInfo().getUserInfo().getNames());
                                }
                                if(type.equals("add"))
                                    ToastUtils.showShortToast(WorkDayAddActivity.this,"添加成功！");
                                else
                                    ToastUtils.showShortToast(WorkDayAddActivity.this,"修改成功！");
                                MessageEvent messageEvent = new MessageEvent();

                                if(type.equals("edit")){
                                    messageEvent.setEventId(MessageEvent.EVENT_WORK_DAY_LIST_EDIT);
                                    messageEvent.setObj(dataInfo);
                                }else{
                                    messageEvent.setEventId(MessageEvent.EVENT_WORK_DAY_LIST_ADD);
                                }
                                EventBus.getDefault().post(messageEvent);
                                finishActivity();
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

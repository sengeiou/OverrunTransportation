package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.OverLoadDataInfo;
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

public class OverloadDataAdd2Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_d11)
    EditText et_d11;
    @BindView(R.id.et_a3)
    EditText et_a3;
    @BindView(R.id.et_a4)
    EditText et_a4;
    @BindView(R.id.et_a5)
    EditText et_a5;
    @BindView(R.id.et_d12)
    EditText et_d12;
    @BindView(R.id.et_d13)
    EditText et_d13;
    @BindView(R.id.et_d14)
    EditText et_d14;
    @BindView(R.id.et_d15)
    EditText et_d15;
    @BindView(R.id.et_d16)
    EditText et_d16;
    @BindView(R.id.et_d17)
    EditText et_d17;
    @BindView(R.id.tv_dataTime)
    TextView tv_dataTime;
    @BindView(R.id.iv_dataTime)
    ImageView iv_dataTime;
    @BindView(R.id.btn_last)
    Button btn_last;
    @BindView(R.id.btn_complete)
    Button btn_complete;
    private OverLoadDataInfo.DataInfo dataInfo;
    private DialogUtils dialogUtils = new DialogUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overload_data_add_2);
        ButterKnife.bind(this);

        dataInfo = (OverLoadDataInfo.DataInfo) getIntent().getSerializableExtra("data");
        if(MyApplication.getInstance().isEdit()) {
            tv_title.setText("治超数据信息统计表编辑");
        }else{
            tv_title.setText("治超数据信息统计表录入");
        }
        initEditData();
        iv_back.setOnClickListener(this);
        iv_dataTime.setOnClickListener(this);
        btn_last.setOnClickListener(this);
        btn_complete.setOnClickListener(this);

    }

    private void initEditData() {
        if(MyApplication.getInstance().isEdit()){
            et_d11.setText(String.valueOf(dataInfo.getD11()));
            et_a3.setText(String.valueOf(dataInfo.getA3()));
            et_a4.setText(String.valueOf(dataInfo.getA4()));
            et_a5.setText(String.valueOf(dataInfo.getA5()));
            et_d12.setText(String.valueOf(dataInfo.getD12()));
            et_d13.setText(String.valueOf(dataInfo.getD13()));
            et_d14.setText(String.valueOf(dataInfo.getD14()));
            et_d15.setText(String.valueOf(dataInfo.getD15()));
            et_d16.setText(String.valueOf(dataInfo.getD16()));
            et_d17.setText(String.valueOf(dataInfo.getD17()));
            tv_dataTime.setText(dataInfo.getDataTime());
        }else{
            et_d11.setText(dataInfo.getD11()==0?"":String.valueOf(dataInfo.getD11()));
            et_a3.setText(dataInfo.getA3()==0.0?"":String.valueOf(dataInfo.getA3()));
            et_a4.setText(dataInfo.getA4()==0.0?"":String.valueOf(dataInfo.getA4()));
            et_a5.setText(dataInfo.getA5()==0.0?"":String.valueOf(dataInfo.getA5()));
            et_d12.setText(dataInfo.getD12()==0?"":String.valueOf(dataInfo.getD12()));
            et_d13.setText(dataInfo.getD13()==0?"":String.valueOf(dataInfo.getD13()));
            et_d14.setText(dataInfo.getD14()==0?"":String.valueOf(dataInfo.getD14()));
            et_d15.setText(dataInfo.getD15()==0?"":String.valueOf(dataInfo.getD15()));
            et_d16.setText(dataInfo.getD16()==0?"":String.valueOf(dataInfo.getD16()));
            et_d17.setText(dataInfo.getD17()==0?"":String.valueOf(dataInfo.getD17()));
            tv_dataTime.setText(TextUtils.isEmpty(dataInfo.getDataTime())?"":dataInfo.getDataTime());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_last:
                complete();
                Intent i= new Intent();
                i.putExtra("dataInfo",dataInfo);
                setResult(0x11,i);
                finishActivity();
                break;
            case R.id.btn_complete:
                if(TextUtils.isEmpty(tv_dataTime.getText().toString())){
                    ToastUtils.showShortToast(this,"请选择日期！");
                    return;
                }
                complete();
                completeHttp();
                break;
            case R.id.iv_dataTime:
                showDialogYearMonthDay(tv_dataTime);
                break;
        }

    }
    private void complete(){
        if(dataInfo == null)
            dataInfo = new OverLoadDataInfo.DataInfo();
        dataInfo.setD11(TextUtils.isEmpty(et_d11.getEditableText().toString())?0:Integer.parseInt(et_d11.getEditableText().toString()));
        dataInfo.setA3(TextUtils.isEmpty(et_a3.getEditableText().toString())?0:Double.parseDouble(et_a3.getEditableText().toString()));
        dataInfo.setA4(TextUtils.isEmpty(et_a4.getEditableText().toString())?0:Double.parseDouble(et_a4.getEditableText().toString()));
        dataInfo.setA5(TextUtils.isEmpty(et_a5.getEditableText().toString())?0:Double.parseDouble(et_a5.getEditableText().toString()));
        dataInfo.setD12(TextUtils.isEmpty(et_d12.getEditableText().toString())?0:Integer.parseInt(et_d12.getEditableText().toString()));
        dataInfo.setD13(TextUtils.isEmpty(et_d13.getEditableText().toString())?0:Integer.parseInt(et_d13.getEditableText().toString()));
        dataInfo.setD14(TextUtils.isEmpty(et_d14.getEditableText().toString())?0:Integer.parseInt(et_d14.getEditableText().toString()));
        dataInfo.setD15(TextUtils.isEmpty(et_d15.getEditableText().toString())?0:Integer.parseInt(et_d15.getEditableText().toString()));
        dataInfo.setD16(TextUtils.isEmpty(et_d16.getEditableText().toString())?0:Integer.parseInt(et_d16.getEditableText().toString()));
        dataInfo.setD17(TextUtils.isEmpty(et_d17.getEditableText().toString())?0:Integer.parseInt(et_d17.getEditableText().toString()));
        dataInfo.setDataTime(tv_dataTime.getText().toString());
    }

    private void completeHttp() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_overload_data_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", String.valueOf(MyApplication.getInstance().isEdit() ? dataInfo.getId() : ""))
                .params("tpsData.id", String.valueOf(MyApplication.getInstance().isEdit() ? dataInfo.getId() : ""))
                .params("tpsData.code", dataInfo == null ? "" : dataInfo.getCode())
                .params("tpsData.d1", dataInfo.getD1())
                .params("tpsData.d2", dataInfo.getD2())
                .params("tpsData.d3", dataInfo.getD3())
                .params("tpsData.d4", dataInfo.getD4())
                .params("tpsData.d5", dataInfo.getD5())
                .params("tpsData.d6", dataInfo.getD6())
                .params("tpsData.a1", dataInfo.getA1())
                .params("tpsData.a2", dataInfo.getA2())
                .params("tpsData.d7", dataInfo.getD7())
                .params("tpsData.d8", dataInfo.getD8())
                .params("tpsData.d9", dataInfo.getD9())
                .params("tpsData.d10", dataInfo.getD10())
                .params("tpsData.d11", dataInfo.getD11())
                .params("tpsData.a3", dataInfo.getA3())
                .params("tpsData.a4", dataInfo.getA4())
                .params("tpsData.a5", dataInfo.getA5())
                .params("tpsData.d12", dataInfo.getD12())
                .params("tpsData.d13", dataInfo.getD13())
                .params("tpsData.d14", dataInfo.getD14())
                .params("tpsData.d15", dataInfo.getD15())
                .params("tpsData.d16", dataInfo.getD16())
                .params("tpsData.d17", dataInfo.getD17())
                .params("tpsData.dataTime", dataInfo.getDataTime())
                .params("tpsData.state", MyApplication.getInstance().isEdit() ? dataInfo.getState() : "1")
                .params("tpsData.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("tpsData.operNames", MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(OverloadDataAdd2Activity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                ToastUtils.showShortToast(MyApplication.getInstance(),jsonObject.getString("data"));
                                MessageEvent messageEvent = new MessageEvent();
                                if(MyApplication.getInstance().isEdit()){
                                    messageEvent.setEventId(MessageEvent.EVENT_OVERLOAD_DATA_LIST_EDIT);
                                    messageEvent.setObj(dataInfo);
                                }else{
                                    messageEvent.setEventId(MessageEvent.EVENT_OVERLOAD_DATA_LIST_ADD);
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

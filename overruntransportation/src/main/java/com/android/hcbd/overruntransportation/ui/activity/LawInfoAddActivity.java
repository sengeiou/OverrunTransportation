package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.LawInfoInfo;
import com.android.hcbd.overruntransportation.entity.LegalNameInfo;
import com.android.hcbd.overruntransportation.entity.TemplateManageInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.android.hcbd.overruntransportation.widget.CustomSpinner;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LawInfoAddActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.spinner_word_name)
    CustomSpinner spinner_word_name;
    @BindView(R.id.spinner_legal_name)
    Spinner spinner_legal_name;
    @BindView(R.id.et_n1)
    EditText et_n1;
    @BindView(R.id.et_n2)
    EditText et_n2;
    @BindView(R.id.et_n3)
    EditText et_n3;
    @BindView(R.id.et_seq)
    EditText et_seq;
    @BindView(R.id.et_suffix)
    EditText et_suffix;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    private DialogUtils dialogUtils = new DialogUtils();
    private String type;
    private int oid;
    private LawInfoInfo.DataInfo dataInfo;
    private List<TemplateManageInfo.DataInfo> wordList = new ArrayList<>();
    private List<LegalNameInfo.DataInfo> lawNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_info_add);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");
        if(type.equals("edit")){
            tv_title.setText("法条信息修改");
            oid = getIntent().getIntExtra("oid",0);
        }else{
            tv_title.setText("法条信息录入");
        }
        initHttpData();

        initListener();
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.toedit_law_info_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",type.equals("add") ? "":String.valueOf(oid))
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(LawInfoAddActivity.this);
                        ll_main.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        realJsonData(result);
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
                        ll_main.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void realJsonData(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    Gson gson = new Gson();
                    if(type.equals("edit")){
                        dataInfo = gson.fromJson(jsonObject.getString("data"), LawInfoInfo.DataInfo.class);
                    }

                    JSONArray array1 = new JSONArray(jsonObject.getString("wordList"));
                    for(int i=0;i<array1.length();i++){
                        TemplateManageInfo.DataInfo dataInfo1 = gson.fromJson(array1.getString(i), TemplateManageInfo.DataInfo.class);
                        wordList.add(dataInfo1);
                    }

                    JSONArray array2 = new JSONArray(jsonObject.getString("lawNameList"));
                    for(int i=0;i<array2.length();i++){
                        LegalNameInfo.DataInfo dataInfo1 = gson.fromJson(array2.getString(i), LegalNameInfo.DataInfo.class);
                        lawNameList.add(dataInfo1);
                    }

                    final String[] list1 = new String[wordList.size()];
                    final List<String> list2 = new ArrayList<String>();
                    for(int i=0;i<wordList.size();i++){
                        list1[i] = wordList.get(i).getUploadFileName();
                    }
                    for(int i=0;i<lawNameList.size();i++){
                        list2.add(lawNameList.get(i).getName());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinner_word_name.initializeStringValues(list1,"请选择word模版");
                            spinner_legal_name.setAdapter(new ArrayAdapter<String>(LawInfoAddActivity.this,android.R.layout.simple_spinner_dropdown_item,list2));

                            if(type.equals("add"))
                                return;
                            for(int i=0;i<list1.length;i++){
                                if(dataInfo.getWordName().equals(list1[i])){
                                    spinner_word_name.setSelection(i+1);
                                    break;
                                }
                            }
                            for(int i=0;i<list2.size();i++){
                                if(dataInfo.getName().equals(list2.get(i))){
                                    spinner_legal_name.setSelection(i);
                                    break;
                                }
                            }

                            et_n1.setText(dataInfo.getN1());
                            et_n2.setText(dataInfo.getN2());
                            et_n3.setText(dataInfo.getN3());
                            et_seq.setText(""+dataInfo.getSeq());
                            et_suffix.setText(dataInfo.getSuffix());
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

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                if(spinner_word_name.getSelectedItemPosition() == 0){
                    ToastUtils.showShortToast(this,"请选择word模版！");
                    return;
                }
                saveData();
                break;
        }

    }

    private void saveData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_law_info_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",oid)
                .params("tpsLaw.code",dataInfo == null ? "" : dataInfo.getCode())
                .params("tpsLaw.name",(String)spinner_legal_name.getSelectedItem())
                .params("tpsLaw.state",dataInfo == null ? "1" : dataInfo.getState())
                .params("tpsLaw.operNames",MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .params("tpsLaw.id",dataInfo == null ? "" : String.valueOf(dataInfo.getId()))
                .params("tpsLaw.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("tpsLaw.n1",et_n1.getEditableText().toString())
                .params("tpsLaw.n2",et_n2.getEditableText().toString())
                .params("tpsLaw.n3",et_n3.getEditableText().toString())
                .params("tpsLaw.seq",et_seq.getEditableText().toString())
                .params("tpsLaw.suffix",et_suffix.getEditableText().toString())
                .params("tpsLaw.tpsWord.id",wordList.get(spinner_word_name.getSelectedItemPosition()-1).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(LawInfoAddActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if (!TextUtils.isEmpty(jsonObject.getString("data"))){
                                MessageEvent messageEvent = new MessageEvent();
                                if(type.equals("add")) {
                                    ToastUtils.showShortToast(LawInfoAddActivity.this, "录入成功");
                                    messageEvent.setEventId(MessageEvent.EVENT_LAW_INFO_LIST_ADD);
                                }else{
                                    ToastUtils.showShortToast(LawInfoAddActivity.this,"修改成功");
                                    dataInfo.setName((String)spinner_legal_name.getSelectedItem());
                                    dataInfo.setWordName((String)spinner_word_name.getSelectedItem());
                                    dataInfo.setN1(et_n1.getEditableText().toString());
                                    dataInfo.setN2(et_n2.getEditableText().toString());
                                    dataInfo.setN3(et_n3.getEditableText().toString());
                                    dataInfo.setSeq(Integer.parseInt(et_seq.getEditableText().toString()));
                                    dataInfo.setSuffix(et_suffix.getEditableText().toString());
                                    messageEvent.setEventId(MessageEvent.EVENT_LAW_INFO_LIST_EDIT);
                                    messageEvent.setObj(dataInfo);

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

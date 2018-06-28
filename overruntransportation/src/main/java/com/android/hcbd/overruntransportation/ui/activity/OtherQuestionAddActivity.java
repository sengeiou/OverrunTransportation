package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
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

public class OtherQuestionAddActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.et_question)
    EditText et_question;
    @BindView(R.id.et_answer)
    EditText et_answer;
    private String oid;
    private DialogUtils dialogUtils = new DialogUtils();
    private String type;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_question_add);
        ButterKnife.bind(this);

        oid = getIntent().getStringExtra("oid");
        type = getIntent().getStringExtra("type");
        index = getIntent().getIntExtra("position",0);
        if(type.equals("edit")){
            et_question.setText(MyApplication.getInstance().getOtherInfo().getQuestionList().get(index).getQuestion());
            et_answer.setText(MyApplication.getInstance().getOtherInfo().getQuestionList().get(index).getAnswer());
        }
        initListener();

    }

    private void initListener() {
        tv_cancel.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                finishActivity();
                break;
            case R.id.tv_ok:
                if(TextUtils.isEmpty(et_question.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入问题！");
                    return;
                }
                if(TextUtils.isEmpty(et_answer.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入回答！");
                    return;
                }
                realHttpData();
                break;
        }
    }

    private void realHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.save_other_question_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("flag",type.equals("edit")?"update":"add")
                .params("code",et_question.getEditableText().toString())
                .params("name",et_answer.getEditableText().toString())
                .params("seq",type.equals("edit")?""+MyApplication.getInstance().getOtherInfo().getQuestionList().get(index).getSeq():""+MyApplication.getInstance().getOtherInfo().getQuestionList().size())
                .params("ids",oid)
                .params("id",type.equals("edit")?""+MyApplication.getInstance().getOtherInfo().getQuestionList().get(index).getId():"")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(OtherQuestionAddActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                dialogUtils.dismissLoading();
                                if(type.equals("edit"))
                                    Toast.makeText(OtherQuestionAddActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(OtherQuestionAddActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.setEventId(MessageEvent.EVENT_OTHER_QUESTION_ADD_SUCCESS);
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

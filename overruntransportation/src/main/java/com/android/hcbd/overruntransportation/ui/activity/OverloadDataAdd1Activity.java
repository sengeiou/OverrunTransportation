package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.OverLoadDataInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverloadDataAdd1Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.et_d1)
    EditText et_d1;
    @BindView(R.id.et_d2)
    EditText et_d2;
    @BindView(R.id.et_d3)
    EditText et_d3;
    @BindView(R.id.et_d4)
    EditText et_d4;
    @BindView(R.id.et_d5)
    EditText et_d5;
    @BindView(R.id.et_a1)
    EditText et_a1;
    @BindView(R.id.et_d6)
    EditText et_d6;
    @BindView(R.id.et_a2)
    EditText et_a2;
    @BindView(R.id.et_d7)
    EditText et_d7;
    @BindView(R.id.et_d8)
    EditText et_d8;
    @BindView(R.id.et_d9)
    EditText et_d9;
    @BindView(R.id.et_d10)
    EditText et_d10;
    @BindView(R.id.btn_next)
    Button btn_next;
    private OverLoadDataInfo.DataInfo dataInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overload_data_add_1);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if(MyApplication.getInstance().isEdit()) {
            dataInfo = (OverLoadDataInfo.DataInfo) getIntent().getSerializableExtra("data");
            tv_title.setText("治超数据信息统计表编辑");
            initEditData();
        }else{
            tv_title.setText("治超数据信息统计表录入");
        }
        iv_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_OVERLOAD_DATA_LIST_ADD:
            case MessageEvent.EVENT_OVERLOAD_DATA_LIST_EDIT:
                finishActivity();
                break;
        }
    }

    private void initEditData() {
        if(dataInfo == null)
            return;
        et_d1.setText(String.valueOf(dataInfo.getD1()));
        et_d2.setText(String.valueOf(dataInfo.getD2()));
        et_d3.setText(String.valueOf(dataInfo.getD3()));
        et_d4.setText(String.valueOf(dataInfo.getD4()));
        et_d5.setText(String.valueOf(dataInfo.getD5()));
        et_a1.setText(String.valueOf(dataInfo.getA1()));
        et_d6.setText(String.valueOf(dataInfo.getD6()));
        et_a2.setText(String.valueOf(dataInfo.getA2()));
        et_d7.setText(String.valueOf(dataInfo.getD7()));
        et_d8.setText(String.valueOf(dataInfo.getD8()));
        et_d9.setText(String.valueOf(dataInfo.getD9()));
        et_d10.setText(String.valueOf(dataInfo.getD10()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_next:
                if(dataInfo == null)
                    dataInfo = new OverLoadDataInfo.DataInfo();
                dataInfo.setD1(TextUtils.isEmpty(et_d1.getEditableText().toString())?0:Integer.parseInt(et_d1.getEditableText().toString()));
                dataInfo.setD2(TextUtils.isEmpty(et_d2.getEditableText().toString())?0:Integer.parseInt(et_d2.getEditableText().toString()));
                dataInfo.setD3(TextUtils.isEmpty(et_d3.getEditableText().toString())?0:Integer.parseInt(et_d3.getEditableText().toString()));
                dataInfo.setD4(TextUtils.isEmpty(et_d4.getEditableText().toString())?0:Integer.parseInt(et_d4.getEditableText().toString()));
                dataInfo.setD5(TextUtils.isEmpty(et_d5.getEditableText().toString())?0:Integer.parseInt(et_d5.getEditableText().toString()));
                dataInfo.setA1(TextUtils.isEmpty(et_a1.getEditableText().toString())?0:Double.parseDouble(et_a1.getEditableText().toString()));
                dataInfo.setD6(TextUtils.isEmpty(et_d6.getEditableText().toString())?0:Integer.parseInt(et_d6.getEditableText().toString()));
                dataInfo.setA2(TextUtils.isEmpty(et_a2.getEditableText().toString())?0:Double.parseDouble(et_a2.getEditableText().toString()));
                dataInfo.setD7(TextUtils.isEmpty(et_d7.getEditableText().toString())?0:Integer.parseInt(et_d7.getEditableText().toString()));
                dataInfo.setD8(TextUtils.isEmpty(et_d8.getEditableText().toString())?0:Integer.parseInt(et_d8.getEditableText().toString()));
                dataInfo.setD9(TextUtils.isEmpty(et_d9.getEditableText().toString())?0:Integer.parseInt(et_d9.getEditableText().toString()));
                dataInfo.setD10(TextUtils.isEmpty(et_d10.getEditableText().toString())?0:Integer.parseInt(et_d10.getEditableText().toString()));
                Intent intent = new Intent(this,OverloadDataAdd2Activity.class);
                intent.putExtra("data", dataInfo);
                startActivityForResult(intent,0x12);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0x11){
            if(requestCode == 0x12){
                dataInfo = (OverLoadDataInfo.DataInfo) data.getSerializableExtra("dataInfo");
                System.out.println("data = "+dataInfo.getDataTime());
            }
        }
    }
}

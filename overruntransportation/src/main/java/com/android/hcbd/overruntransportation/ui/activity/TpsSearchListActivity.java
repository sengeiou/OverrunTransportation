package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.TpsSearchInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TpsSearchListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.tv_check_state)
    TextView tv_check_state;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    @BindView(R.id.iv_check_state)
    ImageView iv_check_state;
    @BindView(R.id.iv_start_time)
    ImageView iv_start_time;
    @BindView(R.id.iv_end_time)
    ImageView iv_end_time;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    private DialogUtils dialogUtils = new DialogUtils();
    private TpsSearchInfo tpsSearchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tps_search_list);
        ButterKnife.bind(this);

        tpsSearchInfo = (TpsSearchInfo) getIntent().getSerializableExtra("data");

        if(tpsSearchInfo != null){
            et_code.setText(tpsSearchInfo.getCode());
            et_name.setText(tpsSearchInfo.getGoodsName());
            tv_check_state.setText(tpsSearchInfo.getCheckStateName());
            tv_start_time.setText(tpsSearchInfo.getBeginTime());
            tv_end_time.setText(tpsSearchInfo.getEndTime());
        }

        iv_back.setOnClickListener(this);
        tv_check_state.setOnClickListener(this);
        iv_check_state.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        iv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        iv_end_time.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.tv_check_state:
                showDialog();
                break;
            case R.id.iv_check_state:
                showDialog();
                break;
            case R.id.tv_start_time:
                showAllTimePickerDialog(tv_start_time);
                break;
            case R.id.iv_start_time:
                showAllTimePickerDialog(tv_start_time);
                break;
            case R.id.tv_end_time:
                showAllTimePickerDialog(tv_end_time);
                break;
            case R.id.iv_end_time:
                showAllTimePickerDialog(tv_end_time);
                break;
            case R.id.btn_ok:
                if(tpsSearchInfo == null)
                    tpsSearchInfo = new TpsSearchInfo();
                tpsSearchInfo.setCode(et_code.getEditableText().toString());
                tpsSearchInfo.setGoodsName(et_name.getEditableText().toString());
                tpsSearchInfo.setCheckStateName(tv_check_state.getText().toString());
                if(tpsSearchInfo.getCheckStateName().equals("未提交")){
                    tpsSearchInfo.setCheckState("0");
                }else if(tpsSearchInfo.getCheckStateName().equals("待复核")){
                    tpsSearchInfo.setCheckState("2");
                }else if(tpsSearchInfo.getCheckStateName().equals("已复核")){
                    tpsSearchInfo.setCheckState("1");
                }else if(tpsSearchInfo.getCheckStateName().equals("驳回")){
                    tpsSearchInfo.setCheckState("4");
                }else{
                    tpsSearchInfo.setCheckState("");
                }
                tpsSearchInfo.setBeginTime(tv_start_time.getText().toString());
                tpsSearchInfo.setEndTime(tv_end_time.getText().toString());
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setEventId(MessageEvent.EVENT_TPS_SEARCH);
                messageEvent.setObj(tpsSearchInfo);
                EventBus.getDefault().post(messageEvent);
                finishActivity();
                break;
        }
    }

    private void showDialog() {
        //未提交：0 、 已复核：1 、 待复核：2 、 驳回：4
        final String[] names = {"未提交","已复核","待复核","驳回","全部状态"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        //builder.setTitle("请选择"); //设置标题
        builder.setItems(names,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                tv_check_state.setText(names[which]);
            }
        });
        builder.create().show();
    }

}

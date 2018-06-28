package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.InputInfo;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.android.hcbd.overruntransportation.widget.CustomSpinner;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputStep3Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.btn_last)
    Button btnLast;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.cb_yes_closure)
    CheckBox cbYesClosure;
    @BindView(R.id.cb_no_closure)
    CheckBox cbNoClosure;
    @BindView(R.id.cb_yes_organize)
    CheckBox cbYesOrgenize;
    @BindView(R.id.cb_no_organize)
    CheckBox cbNoOrganize;
    @BindView(R.id.spinner_o1_id)
    CustomSpinner spinner_o1_id;
    @BindView(R.id.spinner_o2_id)
    CustomSpinner spinner_o2_id;
    @BindView(R.id.spinner_k1_id)
    CustomSpinner spinner_k1_id;
    @BindView(R.id.spinner_k2_id)
    CustomSpinner spinner_k2_id;

    @BindView(R.id.et_source)
    EditText et_source;
    @BindView(R.id.iv_source)
    ImageView iv_source;

    @BindView(R.id.tv_tpsCase_endTime1)
    TextView tv_tpsCase_endTime1;
    @BindView(R.id.iv_tpsCase_endTime1)
    ImageView iv_tpsCase_endTime1;
    @BindView(R.id.tv_tpsCase_beginTime3)
    TextView tv_tpsCase_beginTime3;
    @BindView(R.id.iv_tpsCase_beginTime3)
    ImageView iv_tpsCase_beginTime3;
    @BindView(R.id.tv_tpsCase_endTime3)
    TextView tv_tpsCase_endTime3;
    @BindView(R.id.iv_tpsCase_endTime3)
    ImageView iv_tpsCase_endTime3;
    @BindView(R.id.tv_tpsCase_beginTime4)
    TextView tv_tpsCase_beginTime4;
    @BindView(R.id.iv_tpsCase_beginTime4)
    ImageView iv_tpsCase_beginTime4;
    @BindView(R.id.tv_tpsCase_endTime4)
    TextView tv_tpsCase_endTime4;
    @BindView(R.id.iv_tpsCase_endTime4)
    ImageView iv_tpsCase_endTime4;

    @BindView(R.id.iv_car_type_select)
    ImageView iv_car_type_select;
    @BindView(R.id.iv_proof_select)
    ImageView iv_proof_select;

    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.et_car_weight)
    EditText et_car_weight;
    @BindView(R.id.et_width)
    EditText et_width;
    @BindView(R.id.et_length)
    EditText et_length;
    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.tv_proof)
    TextView tv_proof;
    @BindView(R.id.et_amt)
    EditText et_amt;
    @BindView(R.id.tv_duty_time)
    TextView tv_duty_time;
    @BindView(R.id.iv_duty_time)
    ImageView iv_duty_time;

    private InputInfo inputInfo;
    private DialogUtils dialogUtils = new DialogUtils();
    private NewContentInfo.CarTypeEntity carTypeEntity;
    private int tmpLimitAmt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_step3);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        inputInfo = (InputInfo) getIntent().getSerializableExtra("input");

        if(MyApplication.getInstance().isEdit()) {
            tvTitle.setText("超限处罚编辑");
        }else{
            tvTitle.setText("超限处罚录入");
        }
        tvName.setText("执法人员："+ MyApplication.getInstance().getLoginInfo().getUserInfo().getName());
        String[] arrays = MyApplication.getInstance().getLoginInfo().getUserInfo().getUnit().split("-");
        if(arrays.length == 2){
            switch (Integer.parseInt(arrays[0])){
                case 1:
                    tvPost.setText("职务："+"勘验员");
                    break;
                case 2:
                    tvPost.setText("职务："+"大队长");
                    break;
                case 3:
                    tvPost.setText("职务："+"支队长");
                    break;
                case 4:
                    tvPost.setText("职务："+"副支队长");
                    break;
                case 5:
                    tvPost.setText("职务："+"路政员");
                    break;
                default:
                    tvPost.setText("职务："+"勘验员");
                    break;
            }
        }
        cbYesClosure.setChecked(false);
        cbNoClosure.setChecked(true);
        cbYesOrgenize.setChecked(true);
        cbNoOrganize.setChecked(false);
        initListener();
        initSpinnerAdapter();
        toEdit();

    }

    private void toEdit() {
        if (MyApplication.getInstance().isEdit()){
            NewContentInfo.Data tpsInputInfo = MyApplication.getInstance().getNewContentInfo().getData();
            tv_tpsCase_endTime1.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_endTime1())?tpsInputInfo.getTpsCase().getEndTime1():inputInfo.getPunish_tpsCase_endTime1());
            tv_tpsCase_beginTime3.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_beginTime3())?tpsInputInfo.getTpsCase().getBeginTime3():inputInfo.getPunish_tpsCase_beginTime3());
            tv_tpsCase_endTime3.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_endTime3())?tpsInputInfo.getTpsCase().getEndTime3():inputInfo.getPunish_tpsCase_endTime3());
            tv_tpsCase_beginTime4.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_beginTime4())?tpsInputInfo.getTpsCase().getBeginTime4():inputInfo.getPunish_tpsCase_beginTime4());
            tv_tpsCase_endTime4.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_endTime4())?tpsInputInfo.getTpsCase().getEndTime4():inputInfo.getPunish_tpsCase_endTime4());

            for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                if((TextUtils.isEmpty(inputInfo.getPunish_o1_id())?tpsInputInfo.getO1().getId():Integer.parseInt(inputInfo.getPunish_o1_id())) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                    spinner_o1_id.setSelection(i+1);
                    break;
                }
            }
            for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                if((TextUtils.isEmpty(inputInfo.getPunish_o2_id())?tpsInputInfo.getO2().getId():Integer.parseInt(inputInfo.getPunish_o2_id())) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                    spinner_o2_id.setSelection(i+1);
                    break;
                }
            }
            for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                if((TextUtils.isEmpty(inputInfo.getPunish_k1_id())?tpsInputInfo.getK1().getId():Integer.parseInt(inputInfo.getPunish_k1_id())) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                    spinner_k1_id.setSelection(i+1);
                    break;
                }
            }
            for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                if((TextUtils.isEmpty(inputInfo.getPunish_k2_id())?tpsInputInfo.getK2().getId():Integer.parseInt(inputInfo.getPunish_k2_id())) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                    spinner_k2_id.setSelection(i+1);
                    break;
                }
            }

            if(!TextUtils.isEmpty(inputInfo.getPunish_carType_remark())){
                String[] a = inputInfo.getPunish_carType_remark().split("-");
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getCarTypeList().size();i++){
                    if(MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i).getCode().equals(a[0])){
                        carTypeEntity = MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i);
                    }
                }
            }else{
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getCarTypeList().size();i++){
                    if(MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i).getCode().equals(tpsInputInfo.getCarType().getCode())){
                        carTypeEntity = MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i);
                    }
                }
            }

            tv_car_type.setText(TextUtils.isEmpty(inputInfo.getPunish_carType_remark())?tpsInputInfo.getCarType().getCode()+"-"+tpsInputInfo.getCarType().getType():inputInfo.getPunish_carType_remark());

            et_car_weight.setText(TextUtils.isEmpty(inputInfo.getPunish_weight())?""+tpsInputInfo.getWeight():inputInfo.getPunish_weight());
            et_length.setText(TextUtils.isEmpty(inputInfo.getPunish_length())?""+tpsInputInfo.getLength():inputInfo.getPunish_length());
            et_width.setText(TextUtils.isEmpty(inputInfo.getPunish_width())?""+tpsInputInfo.getWidth():inputInfo.getPunish_width());
            et_height.setText(TextUtils.isEmpty(inputInfo.getPunish_height())?""+tpsInputInfo.getHeight():inputInfo.getPunish_height());

            et_source.setText(TextUtils.isEmpty(inputInfo.getPunish_source())?tpsInputInfo.getSource():inputInfo.getPunish_source());
            tv_duty_time.setText(TextUtils.isEmpty(inputInfo.getPunish_dutyTime())?tpsInputInfo.getDutyTime():inputInfo.getPunish_dutyTime());
            et_amt.setText(TextUtils.isEmpty(inputInfo.getPunish_amt())?""+tpsInputInfo.getAmt():inputInfo.getPunish_amt());

            tv_proof.setText(TextUtils.isEmpty(inputInfo.getPunish_proof())?(String)tpsInputInfo.getProof():inputInfo.getPunish_proof());

            if(TextUtils.isEmpty(inputInfo.getPunish_state())?tpsInputInfo.getStateName().equals("已结案"):inputInfo.getPunish_state().equals("1")){
                cbYesClosure.setChecked(true);
                cbNoClosure.setChecked(false);
            }else{
                cbYesClosure.setChecked(false);
                cbNoClosure.setChecked(true);
            }

            if(TextUtils.isEmpty(inputInfo.getPunish_isCompany())?tpsInputInfo.getIsCompany().equals("是"):inputInfo.getPunish_isCompany().equals("是")){
                cbYesOrgenize.setChecked(true);
                cbNoOrganize.setChecked(false);
            }else{
                cbYesOrgenize.setChecked(false);
                cbNoOrganize.setChecked(true);
            }

        }else{
            tv_tpsCase_endTime1.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_endTime1())?"":inputInfo.getPunish_tpsCase_endTime1());
            tv_tpsCase_beginTime3.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_beginTime3())?"":inputInfo.getPunish_tpsCase_beginTime3());
            tv_tpsCase_endTime3.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_endTime3())?"":inputInfo.getPunish_tpsCase_endTime3());
            tv_tpsCase_beginTime4.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_beginTime4())?"":inputInfo.getPunish_tpsCase_beginTime4());
            tv_tpsCase_endTime4.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsCase_endTime4())?"":inputInfo.getPunish_tpsCase_endTime4());
            if(!TextUtils.isEmpty(inputInfo.getPunish_o1_id())){
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                    if(Integer.parseInt(inputInfo.getPunish_o1_id()) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                        spinner_o1_id.setSelection(i+1);
                        break;
                    }
                }
            }

            if(!TextUtils.isEmpty(inputInfo.getPunish_o2_id())){
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                    if(Integer.parseInt(inputInfo.getPunish_o2_id()) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                        spinner_o2_id.setSelection(i+1);
                        break;
                    }
                }
            }

            if(!TextUtils.isEmpty(inputInfo.getPunish_k1_id())){
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                    if(Integer.parseInt(inputInfo.getPunish_k1_id()) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                        spinner_k1_id.setSelection(i+1);
                        break;
                    }
                }
            }

            if(!TextUtils.isEmpty(inputInfo.getPunish_k2_id())){
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
                    if(Integer.parseInt(inputInfo.getPunish_k2_id()) == MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getId()){
                        spinner_k2_id.setSelection(i+1);
                        break;
                    }
                }
            }

            if(!TextUtils.isEmpty(inputInfo.getPunish_carType_remark())){
                String[] a = inputInfo.getPunish_carType_remark().split("-");
                for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getCarTypeList().size();i++){
                    if(MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i).getCode().equals(a[0])){
                        carTypeEntity = MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i);
                    }
                }
            }
            tv_car_type.setText(TextUtils.isEmpty(inputInfo.getPunish_carType_remark())?"":inputInfo.getPunish_carType_remark());

            et_car_weight.setText(TextUtils.isEmpty(inputInfo.getPunish_weight())?"":inputInfo.getPunish_weight());
            et_length.setText(TextUtils.isEmpty(inputInfo.getPunish_length())?"":inputInfo.getPunish_length());
            et_width.setText(TextUtils.isEmpty(inputInfo.getPunish_width())?"":inputInfo.getPunish_width());
            et_height.setText(TextUtils.isEmpty(inputInfo.getPunish_height())?"":inputInfo.getPunish_height());

            et_source.setText(TextUtils.isEmpty(inputInfo.getPunish_source())?"":inputInfo.getPunish_source());
            tv_duty_time.setText(TextUtils.isEmpty(inputInfo.getPunish_dutyTime())?"":inputInfo.getPunish_dutyTime());
            et_amt.setText(TextUtils.isEmpty(inputInfo.getPunish_amt())?"":inputInfo.getPunish_amt());
            tv_proof.setText(TextUtils.isEmpty(inputInfo.getPunish_proof())?"":inputInfo.getPunish_proof());
            if(!TextUtils.isEmpty(inputInfo.getPunish_state())){
                if(inputInfo.getPunish_state().equals("1")){
                    cbYesClosure.setChecked(true);
                    cbNoClosure.setChecked(false);
                }else{
                    cbYesClosure.setChecked(false);
                    cbNoClosure.setChecked(true);
                }
            }

            if(!TextUtils.isEmpty(inputInfo.getPunish_isCompany())){
                if(inputInfo.getPunish_isCompany().equals("是")){
                    cbYesOrgenize.setChecked(true);
                    cbNoOrganize.setChecked(false);
                }else{
                    cbYesOrgenize.setChecked(false);
                    cbNoOrganize.setChecked(true);
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_CAR_TYPE:
                carTypeEntity = (NewContentInfo.CarTypeEntity) event.getObj();
                tv_car_type.setText(carTypeEntity.getCode()+"-"+carTypeEntity.getType());
                calLimit();
                calPunishAmt();
                break;
            case MessageEvent.EVENT_EVIDENCE_MATERIAL:
                List<NewContentInfo.ProofEntity> proofEntityList = (List<NewContentInfo.ProofEntity>) event.getObj();
                String str = "";
                for(int i=0;i<proofEntityList.size();i++){
                    str+=","+proofEntityList.get(i).getCode()+"_"+proofEntityList.get(i).getName()+"_"+proofEntityList.get(i).getIds();
                }
                tv_proof.setText(str.replaceFirst(",",""));
                break;
        }
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
        cbYesClosure.setOnClickListener(this);
        cbNoClosure.setOnClickListener(this);
        cbYesOrgenize.setOnClickListener(this);
        cbNoOrganize.setOnClickListener(this);
        tv_tpsCase_endTime1.setOnClickListener(this);
        iv_tpsCase_endTime1.setOnClickListener(this);
        tv_tpsCase_beginTime3.setOnClickListener(this);
        iv_tpsCase_beginTime3.setOnClickListener(this);
        tv_tpsCase_endTime3.setOnClickListener(this);
        iv_tpsCase_endTime3.setOnClickListener(this);
        tv_tpsCase_beginTime4.setOnClickListener(this);
        iv_tpsCase_beginTime4.setOnClickListener(this);
        tv_tpsCase_endTime4.setOnClickListener(this);
        iv_tpsCase_endTime4.setOnClickListener(this);
        tv_car_type.setOnClickListener(this);
        iv_car_type_select.setOnClickListener(this);
        tv_proof.setOnClickListener(this);
        iv_proof_select.setOnClickListener(this);
        tv_duty_time.setOnClickListener(this);
        iv_duty_time.setOnClickListener(this);
        iv_source.setOnClickListener(this);
        et_car_weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    calLimit();
                }
            }
        });
        et_length.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    calPunishAmt();
                }
            }
        });
        et_width.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    calPunishAmt();
                }
            }
        });
        et_height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    calPunishAmt();
                }
            }
        });
    }

    private void calLimit(){
        if(TextUtils.isEmpty(et_car_weight.getEditableText().toString()))
            return;
        if(carTypeEntity != null){
            int tmp = (int)Double.parseDouble(et_car_weight.getEditableText().toString());
            if(tmp > 0){
                int min = tmp - (int)carTypeEntity.getLimit();
                min = min * 500 >= 30000 ? 30000 : min * 500;
                min = min < 0 ? 0 : min;
                tmpLimitAmt = min;
                et_amt.setText(""+min);
            }
        }
    }

    private boolean calPunishAmt(){
        if(carTypeEntity == null)
            return false;
        int level = 0;
        boolean flag = true;
        if(TextUtils.isEmpty(et_car_weight.getEditableText().toString())){
            et_car_weight.setText(""+0);
        }
        if(TextUtils.isEmpty(et_length.getEditableText().toString())){
            et_length.setText(""+0);
        }
        if(TextUtils.isEmpty(et_width.getEditableText().toString())){
            et_width.setText(""+0);
        }
        if(TextUtils.isEmpty(et_height.getEditableText().toString())){
            et_height.setText(""+0);
        }
        //length
        if(flag && !TextUtils.isEmpty(et_length.getEditableText().toString())){
            int tmp = (int)Double.parseDouble(et_length.getEditableText().toString());
            if(tmp > 60){
                ToastUtils.showLongToast(InputStep3Activity.this,"输入的长度不能超过60米!");
                flag = false;
            }
            if(flag){
                if (tmp > 0 && tmp <= 20)
                    level = level < 1 ? 1 : level;
                if (tmp > 20 && tmp <= 28)
                    level = level < 2 ? 2 : level;
                if (tmp > 28)
                    level = level < 3 ? 3 : level;
            }
        }
        //width
        if(flag && !TextUtils.isEmpty(et_width.getEditableText().toString())){
            int tmp = (int)Double.parseDouble(et_width.getEditableText().toString());
            if(tmp > 8){
                ToastUtils.showLongToast(InputStep3Activity.this,"输入的宽度不能超过8米!");
                flag = false;
            }
            if (flag && tmp > 0) {
                if (tmp > 0 && tmp <= 3)
                    level = level < 1 ? 1 : level;
                if (tmp > 3 && tmp <= 3.75)
                    level = level < 2 ? 2 : level;
                if (tmp > 3.75)
                    level = level < 3 ? 3 : level;
            }
        }
        //height
        if(flag && !TextUtils.isEmpty(et_height.getEditableText().toString())){
            int tmp = (int)Double.parseDouble(et_height.getEditableText().toString());
            if(tmp > 6){
                ToastUtils.showLongToast(InputStep3Activity.this,"输入的高度不能超过6米!");
                flag = false;
            }
            if (flag) {
                if (tmp > 4 && tmp <= 4.2)
                    level = level < 1 ? 1 : level;
                if (tmp > 4.2 && tmp <= 4.5)
                    level = level < 2 ? 2 : level;
                if (tmp > 4.5)
                    level = level < 3 ? 3 : level;
            }
        }

        int totalAmt = TextUtils.isEmpty(et_amt.getEditableText().toString())? 0:(int)Double.parseDouble(et_amt.getEditableText().toString());
        if(level == 0){
            totalAmt = tmpLimitAmt;
            et_amt.setText(""+totalAmt);
        }
        if (level == 1) {
            totalAmt = tmpLimitAmt + 200;
            et_amt.setText(""+totalAmt);
        }
        int min = totalAmt - tmpLimitAmt;
        if(flag && min < 0){
            ToastUtils.showLongToast(this,"超重金额:" + tmpLimitAmt + ",输入的罚金" + min + ",不能小于0元");
            flag = false;
        }
        if (flag && level == 2) {
            if (flag && min < 200) {
                ToastUtils.showLongToast(this,"超重金额:" + tmpLimitAmt + ",输入的罚金" + min + ",不能小于200元");
                flag = false;
            }
            if (flag && min > 1000) {
                ToastUtils.showLongToast(this,"超重金额:" + tmpLimitAmt + ",输入的罚金" + min + ",不能大于1000元");
                flag = false;
            }
        }
        if (flag && level == 3) {
            if (flag && min < 1000) {
                ToastUtils.showLongToast(this,"超重金额:" + tmpLimitAmt + ",输入的罚金" + min + ",不能小于1000元");
                flag = false;
            }
            if (flag && min > 3000) {
                ToastUtils.showLongToast(this,"超重金额:" + tmpLimitAmt + ",输入的罚金" + min + ",不能大于3000元");
                flag = false;
            }
        }
        if (flag && totalAmt > 30000) {
            flag = false;
            ToastUtils.showLongToast(this,"处罚金额不能超过30000");
        }
        return flag;
    }

    private void initSpinnerAdapter() {
        String[] operNames = new String[MyApplication.getInstance().getNewContentInfo().getOperList().size()];
        for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getOperList().size();i++){
            operNames[i] = MyApplication.getInstance().getNewContentInfo().getOperList().get(i).getNames();
        }

        spinner_o1_id.initializeStringValues(operNames,"请选择执法人员1");
        spinner_o2_id.initializeStringValues(operNames,"请选择执法人员2");
        spinner_k1_id.initializeStringValues(operNames,"请选择记录人");
        spinner_k2_id.initializeStringValues(operNames,"请选择机构联系人");

        //spinner_o1_id .setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, operNames));
        //spinner_o2_id .setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, operNames));
        //spinner_k1_id .setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, operNames));
        //spinner_k2_id .setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, operNames));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_last:
                complete();
                Intent i = new Intent();
                i.putExtra("intput_data",inputInfo);
                setResult(0x2,i);
                finishActivity();
                break;
            case R.id.btn_complete:
                if(TextUtils.isEmpty(tv_tpsCase_endTime1.getText().toString())){
                    ToastUtils.showShortToast(this,"请选择立案时间！");
                    return;
                }
                if(TextUtils.isEmpty(tv_tpsCase_beginTime4.getText().toString())){
                    ToastUtils.showShortToast(this,"请选择询问时间！");
                    return;
                }
                if(spinner_o1_id.getSelectedItemPosition() == 0){
                    ToastUtils.showShortToast(this,"请选择执法人员1！");
                    return;
                }
                if(spinner_o2_id.getSelectedItemPosition() == 0){
                    ToastUtils.showShortToast(this,"请选择执法人员2！");
                    return;
                }
                if(spinner_k1_id.getSelectedItemPosition() == 0){
                    ToastUtils.showShortToast(this,"请选择记录人！");
                    return;
                }
                if(TextUtils.isEmpty(et_source.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入或选择案件来源！");
                    et_source.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(tv_car_type.getText().toString())){
                    ToastUtils.showShortToast(this,"请选择车轴类型！");
                    return;
                }
                if(TextUtils.isEmpty(tv_proof.getText().toString())){
                    ToastUtils.showShortToast(this,"请选择证据材料！");
                    return;
                }
                if(spinner_k2_id.getSelectedItemPosition() == 0){
                    ToastUtils.showShortToast(this,"请选择机构联系人！");
                    return;
                }
                if(calPunishAmt()){
                    complete();
                    completeHttp();
                }

                break;
            case R.id.cb_yes_closure:
                cbYesClosure.setChecked(true);
                cbNoClosure.setChecked(false);
                break;
            case R.id.cb_no_closure:
                cbYesClosure.setChecked(false);
                cbNoClosure.setChecked(true);
                break;
            case R.id.cb_yes_organize:
                cbYesOrgenize.setChecked(true);
                cbNoOrganize.setChecked(false);
                break;
            case R.id.cb_no_organize:
                cbYesOrgenize.setChecked(false);
                cbNoOrganize.setChecked(true);
                break;
            case R.id.tv_tpsCase_endTime1:
                showTimePickerDialog();
                break;
            case R.id.iv_tpsCase_endTime1:
                showTimePickerDialog();
                break;
            case R.id.tv_tpsCase_beginTime3:
                showAllTimePickerDialog(tv_tpsCase_beginTime3);
                break;
            case R.id.iv_tpsCase_beginTime3:
                showAllTimePickerDialog(tv_tpsCase_beginTime3);
                break;
            case R.id.tv_tpsCase_endTime3:
                showAllTimePickerDialog(tv_tpsCase_endTime3);
                break;
            case R.id.iv_tpsCase_endTime3:
                showAllTimePickerDialog(tv_tpsCase_endTime3);
                break;
            case R.id.tv_tpsCase_beginTime4:
                showTime4Dialog();
                break;
            case R.id.iv_tpsCase_beginTime4:
                showTime4Dialog();
                break;
            case R.id.tv_tpsCase_endTime4:
                showAllTimePickerDialog(tv_tpsCase_endTime4);
                break;
            case R.id.iv_tpsCase_endTime4:
                showAllTimePickerDialog(tv_tpsCase_endTime4);
                break;
            case R.id.tv_car_type:
                toSetCarType();
                break;
            case R.id.iv_car_type_select:
                toSetCarType();
                break;
            case R.id.tv_proof:
                startActivity(new Intent(this,EvidenceMaterialsListActivity.class));
                break;
            case R.id.iv_proof_select:
                startActivity(new Intent(this,EvidenceMaterialsListActivity.class));
                break;
            case R.id.tv_duty_time:
                showDialogYearMonthDay(tv_duty_time);
                break;
            case R.id.iv_duty_time:
                showDialogYearMonthDay(tv_duty_time);
                break;
            case R.id.iv_source:
                showDialog(et_source,MyApplication.getInstance().getNewContentInfo().getSourceList());
                break;
        }
    }

    private void toSetCarType() {
        Intent intent = new Intent(this,AxleTypeActivity.class);
        intent.putExtra("id",carTypeEntity == null ? 0:carTypeEntity.getId());
        startActivity(intent);
    }

    private void showDialog(final EditText et, final String[] items) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        //builder.setTitle("请选择"); //设置标题
        builder.setItems(items,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                et.setText(items[which]);
            }
        });
        builder.create().show();
    }

    private void complete() {
        inputInfo.setPunish_tpsCase_endTime1(tv_tpsCase_endTime1.getText().toString());
        inputInfo.setPunish_tpsCase_beginTime3(tv_tpsCase_beginTime3.getText().toString());
        inputInfo.setPunish_tpsCase_endTime3(tv_tpsCase_endTime3.getText().toString());
        inputInfo.setPunish_tpsCase_beginTime4(tv_tpsCase_beginTime4.getText().toString());
        inputInfo.setPunish_tpsCase_endTime4(tv_tpsCase_endTime4.getText().toString());
        inputInfo.setPunish_o1_id(spinner_o1_id.getSelectedItemPosition() == 0 ? "":""+MyApplication.getInstance().getNewContentInfo().getOperList().get(spinner_o1_id.getSelectedItemPosition()-1).getId());
        inputInfo.setPunish_o2_id(spinner_o2_id.getSelectedItemPosition() == 0 ? "":""+MyApplication.getInstance().getNewContentInfo().getOperList().get(spinner_o2_id.getSelectedItemPosition()-1).getId());
        inputInfo.setPunish_k1_id(spinner_k1_id.getSelectedItemPosition() == 0 ? "":""+MyApplication.getInstance().getNewContentInfo().getOperList().get(spinner_k1_id.getSelectedItemPosition()-1).getId());
        inputInfo.setPunish_k2_id(spinner_k2_id.getSelectedItemPosition() == 0 ? "":""+MyApplication.getInstance().getNewContentInfo().getOperList().get(spinner_k2_id.getSelectedItemPosition()-1).getId());
        inputInfo.setPunish_carType_remark(tv_car_type.getText().toString());
        inputInfo.setPunish_weight(et_car_weight.getEditableText().toString());
        inputInfo.setPunish_length(et_length.getEditableText().toString());
        inputInfo.setPunish_width(et_width.getEditableText().toString());
        inputInfo.setPunish_height(et_height.getEditableText().toString());
        inputInfo.setPunish_source(et_source.getEditableText().toString());
        inputInfo.setPunish_dutyTime(tv_duty_time.getText().toString());
        inputInfo.setPunish_amt(et_amt.getEditableText().toString());
        inputInfo.setPunish_proof(tv_proof.getText().toString());

        if(cbYesClosure.isChecked())
            inputInfo.setPunish_state("1");
        else
            inputInfo.setPunish_state("2");
        if(cbYesOrgenize.isChecked())
            inputInfo.setPunish_isCompany("是");
        else
            inputInfo.setPunish_isCompany("否");
    }

    private void completeHttp() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.save_content_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())

                .params("punish.site.name",inputInfo.getPunish_site_name())
                .params("punish.site.checkPlace",inputInfo.getPunish_site_checkPlace())
                .params("punish.site.shipDirection",inputInfo.getPunish_site_shipDirection())
                .params("punish.driver.name",inputInfo.getPunish_driver_name())
                .params("punish.driver.ids",inputInfo.getPunish_driver_ids())
                .params("punish.driver.sex",inputInfo.getPunish_driver_sex())
                .params("punish.driver.phone",inputInfo.getPunish_driver_phone())
                .params("punish.driver.addr",inputInfo.getPunish_driver_addr())
                .params("punish.driver.driverAddr",inputInfo.getPunish_driver_driverAddr())
                .params("punish.driver.no1",inputInfo.getPunish_driver_no1())
                .params("punish.driver.certId",inputInfo.getPunish_driver_certId())
                .params("punish.driver.post",inputInfo.getPunish_driver_post())
                .params("punish.driver.caseRel",inputInfo.getPunish_driver_caseRel())
                .params("punish.driver.job",inputInfo.getPunish_driver_job())
                .params("punish.tpsGoods.name",inputInfo.getPunish_tpsGoods_name())
                .params("punish.tpsGoods.phone",inputInfo.getPunish_tpsGoods_phone())
                .params("punish.tpsGoods.goods",inputInfo.getPunish_tpsGoods_goods())
                .params("punish.tpsGoods.startPlace",inputInfo.getPunish_tpsGoods_startPlace())
                .params("punish.tpsGoods.endPlace",inputInfo.getPunish_tpsGoods_endPlace())
                .params("punish.isUninstall",inputInfo.getPunish_isUninstall())
                .params("punish.overrunNo",inputInfo.getPunish_overrunNo())
                .params("punish.tpsCase.endTime1",inputInfo.getPunish_tpsCase_endTime1())
                .params("punish.tpsCase.beginTime3",inputInfo.getPunish_tpsCase_beginTime3())
                .params("punish.tpsCase.endTime3",inputInfo.getPunish_tpsCase_endTime3())
                .params("punish.tpsCase.beginTime4",inputInfo.getPunish_tpsCase_beginTime4())
                .params("punish.tpsCase.endTime4",inputInfo.getPunish_tpsCase_endTime4())
                .params("punish.car.no1",inputInfo.getPunish_car_no1())
                .params("punish.car.no2",inputInfo.getPunish_car_no2())
                .params("punish.car.no3",inputInfo.getPunish_car_no3())
                .params("punish.car.owner",inputInfo.getPunish_car_owner())
                .params("punish.car.addr",inputInfo.getPunish_car_addr())
                .params("punish.car.phone",inputInfo.getPunish_car_phone())
                .params("punish.car.lawOper",inputInfo.getPunish_car_lawOper())
                .params("punish.car.lawName",inputInfo.getPunish_car_lawName())
                .params("punish.car.lawPhone",inputInfo.getPunish_car_lawPhone())
                .params("punish.car.lawAddr",inputInfo.getPunish_car_lawAddr())
                .params("punish.car.lawPost",inputInfo.getPunish_car_lawPost())
                .params("punish.o1.id",String.valueOf(inputInfo.getPunish_o1_id()))
                .params("punish.o2.id",String.valueOf(inputInfo.getPunish_o2_id()))
                .params("punish.k1.id",String.valueOf(inputInfo.getPunish_k1_id()))
                .params("punish.k2.id",String.valueOf(inputInfo.getPunish_k2_id()))
                .params("punish.carType.remark",inputInfo.getPunish_carType_remark())
                .params("punish.weight",String.valueOf(inputInfo.getPunish_weight()))
                .params("punish.length",String.valueOf(inputInfo.getPunish_length()))
                .params("punish.width",String.valueOf(inputInfo.getPunish_width()))
                .params("punish.height",String.valueOf(inputInfo.getPunish_height()))
                .params("punish.source",inputInfo.getPunish_source())
                .params("punish.amt",String.valueOf(inputInfo.getPunish_amt()))
                .params("punish.isCompany",inputInfo.getPunish_isCompany())
                .params("punish.dutyTime",inputInfo.getPunish_dutyTime())
                .params("punish.state",inputInfo.getPunish_state())
                .params("punish.proof",inputInfo.getPunish_proof())

                .params("oid", String.valueOf(MyApplication.getInstance().isEdit() ? MyApplication.getInstance().getNewContentInfo().getDataBase().getId() : ""))
                .params("punish.checkState",MyApplication.getInstance().isEdit() ? MyApplication.getInstance().getNewContentInfo().getDataBase().getState() : "0")
                .params("punish.code",MyApplication.getInstance().isEdit() ? MyApplication.getInstance().getNewContentInfo().getDataBase().getCode() : "")
                .params("punish.operNames",MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .params("punish.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(InputStep3Activity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                if(MyApplication.getInstance().isEdit()){
                                    Toast.makeText(InputStep3Activity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setEventId(MessageEvent.EVENT_TPS_EDIT_SUCCESS);
                                    EventBus.getDefault().post(messageEvent);
                                }else{
                                    Toast.makeText(InputStep3Activity.this,"录入成功！",Toast.LENGTH_SHORT).show();
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setEventId(MessageEvent.EVENT_TPS_ADD_SUCCESS);
                                    EventBus.getDefault().post(messageEvent);
                                }
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

    private void showTimePickerDialog(){
        new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                tv_tpsCase_endTime1.setText(format.format(date));
                tv_tpsCase_beginTime3.setText(format.format(date));
                Date afterDate = new Date(date .getTime() + 300000);
                tv_tpsCase_endTime3.setText(format.format(afterDate));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFEFEFEF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .isCenterLabel(false)
                .isDialog(false)//是否显示为对话框样式
                .build().show();
    }

    private void showTime4Dialog() {
        new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                tv_tpsCase_beginTime4.setText(format.format(date));
                Date afterDate = new Date(date .getTime() + 300000);
                tv_tpsCase_endTime4.setText(format.format(afterDate));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFEFEFEF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .isCenterLabel(false)
                .isDialog(false)//是否显示为对话框样式
                .build().show();
    }

}

package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.InputInfo;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;



public class InputStep2Activity extends BaseActivity implements View.OnClickListener{

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
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_car_addr)
    EditText et_car_addr;
    @BindView(R.id.et_car_phone)
    EditText et_car_phone;
    @BindView(R.id.et_tpsGoods_goods)
    EditText et_tpsGoods_goods;
    @BindView(R.id.iv_tpsGoods_goods)
    ImageView iv_tpsGoods_goods;
    @BindView(R.id.et_site_checkPlace)
    EditText et_site_checkPlace;
    @BindView(R.id.iv_site_checkPlace)
    ImageView iv_site_checkPlace;
    @BindView(R.id.et_car_lawOper)
    EditText et_car_lawOper;
    @BindView(R.id.et_car_lawPost)
    EditText et_car_lawPost;
    @BindView(R.id.et_car_lawName)
    EditText et_car_lawName;
    @BindView(R.id.et_car_lawPhone)
    EditText et_car_lawPhone;
    @BindView(R.id.et_car_lawAddr)
    EditText et_car_lawAddr;
    @BindView(R.id.et_tpsGoods_name)
    EditText et_tpsGoods_name;
    @BindView(R.id.et_tpsGoods_startPlace)
    EditText et_tpsGoods_startPlace;
    @BindView(R.id.et_tpsGoods_endPlace)
    EditText et_tpsGoods_endPlace;
    @BindView(R.id.et_overrunNo)
    EditText et_overrunNo;

    private InputInfo inputInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_step2);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        inputInfo = (InputInfo) getIntent().getSerializableExtra("input");
        if(MyApplication.getInstance().isEdit())
            tvTitle.setText("超限处罚编辑");
        else
            tvTitle.setText("超限处罚录入");
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
        ivBack.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        iv_tpsGoods_goods.setOnClickListener(this);
        iv_site_checkPlace.setOnClickListener(this);
        //iv_site_shipDirection.setOnClickListener(this);
        toEdit();
    }

    private void toEdit() {
        if (MyApplication.getInstance().isEdit()){
            NewContentInfo.Data tpsInputInfo = MyApplication.getInstance().getNewContentInfo().getData();
            et_car_addr.setText(TextUtils.isEmpty(inputInfo.getPunish_car_addr()) ? tpsInputInfo.getCar().getAddr():inputInfo.getPunish_car_addr());
            et_car_phone.setText(TextUtils.isEmpty(inputInfo.getPunish_car_phone())?tpsInputInfo.getCar().getPhone():inputInfo.getPunish_car_phone());
            et_car_lawOper.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawOper())?tpsInputInfo.getCar().getLawOper():inputInfo.getPunish_car_lawOper());
            et_car_lawName.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawName())?tpsInputInfo.getCar().getLawName():inputInfo.getPunish_car_lawName());
            et_car_lawPhone.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawPhone())?tpsInputInfo.getCar().getLawPhone():inputInfo.getPunish_car_lawPhone());
            et_car_lawAddr.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawAddr())?tpsInputInfo.getCar().getLawAddr():inputInfo.getPunish_car_lawAddr());
            et_car_lawPost.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawPost())?tpsInputInfo.getCar().getLawPost():inputInfo.getPunish_car_lawPost());

            et_tpsGoods_name.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_name())?tpsInputInfo.getTpsGoods().getName():inputInfo.getPunish_tpsGoods_name());
            //et_tpsGoods_phone.setText(tpsInputInfo.getTpsGoods().getPhone());
            et_tpsGoods_startPlace.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_startPlace())?tpsInputInfo.getTpsGoods().getStartPlace():inputInfo.getPunish_tpsGoods_startPlace());
            et_tpsGoods_endPlace.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_endPlace())?tpsInputInfo.getTpsGoods().getEndPlace():inputInfo.getPunish_tpsGoods_endPlace());
            et_overrunNo.setText(TextUtils.isEmpty(inputInfo.getPunish_overrunNo())?tpsInputInfo.getOverrunNo():inputInfo.getPunish_overrunNo());

            et_tpsGoods_goods.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_goods())?tpsInputInfo.getTpsGoods().getGoods():inputInfo.getPunish_tpsGoods_goods());
            et_site_checkPlace.setText(TextUtils.isEmpty(inputInfo.getPunish_site_checkPlace())?tpsInputInfo.getSite().getCheckPlace():inputInfo.getPunish_site_checkPlace());
            //et_site_shipDirection.setText(tpsInputInfo.getSite().getShipDirection());
        }else{
            et_car_addr.setText(TextUtils.isEmpty(inputInfo.getPunish_car_addr()) ? "":inputInfo.getPunish_car_addr());
            et_car_phone.setText(TextUtils.isEmpty(inputInfo.getPunish_car_phone())?"":inputInfo.getPunish_car_phone());
            et_car_lawOper.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawOper())?"/":inputInfo.getPunish_car_lawOper());
            et_car_lawName.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawName())?"/":inputInfo.getPunish_car_lawName());
            et_car_lawPhone.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawPhone())?"/":inputInfo.getPunish_car_lawPhone());
            et_car_lawAddr.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawAddr())?"/":inputInfo.getPunish_car_lawAddr());
            et_car_lawPost.setText(TextUtils.isEmpty(inputInfo.getPunish_car_lawPost())?"总经理":inputInfo.getPunish_car_lawPost());

            et_tpsGoods_name.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_name())?"":inputInfo.getPunish_tpsGoods_name());
            et_tpsGoods_startPlace.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_startPlace())?"":inputInfo.getPunish_tpsGoods_startPlace());
            et_tpsGoods_endPlace.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_endPlace())?"":inputInfo.getPunish_tpsGoods_endPlace());
            et_overrunNo.setText(TextUtils.isEmpty(inputInfo.getPunish_overrunNo())?"":inputInfo.getPunish_overrunNo());

            et_tpsGoods_goods.setText(TextUtils.isEmpty(inputInfo.getPunish_tpsGoods_goods())?"":inputInfo.getPunish_tpsGoods_goods());
            et_site_checkPlace.setText(TextUtils.isEmpty(inputInfo.getPunish_site_checkPlace())?"":inputInfo.getPunish_site_checkPlace());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_TPS_ADD_SUCCESS:
                finish();
                break;
            case MessageEvent.EVENT_TPS_EDIT_SUCCESS:
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_last:
                next();
                Intent i = new Intent();
                i.putExtra("intput_data",inputInfo);
                setResult(0x1,i);
                finishActivity();
                break;
            case R.id.btn_next:
                if(TextUtils.isEmpty(et_tpsGoods_goods.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入货物名称！");
                    et_tpsGoods_goods.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(et_site_checkPlace.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入检查地点！");
                    et_site_checkPlace.requestFocus();
                    return;
                }
                next();
                Intent intent = new Intent(this,InputStep3Activity.class);
                intent.putExtra("input",inputInfo);
                startActivityForResult(intent,0x3);
                break;
            case R.id.iv_tpsGoods_goods:
                showDialog(et_tpsGoods_goods,MyApplication.getInstance().getNewContentInfo().getTpsGoodsGoodsList());
                break;
            case R.id.iv_site_checkPlace:
                showDialog(et_site_checkPlace,MyApplication.getInstance().getNewContentInfo().getSiteCheckPlaceList());
                break;
            /*case R.id.iv_site_shipDirection:
                showDialog(et_site_shipDirection,MyApplication.app.getNewContentInfo().getSiteShipDirectionList());
                break;*/

        }
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

    private void next() {
        inputInfo.setPunish_car_addr(et_car_addr.getEditableText().toString());
        inputInfo.setPunish_car_phone(et_car_phone.getEditableText().toString());
        inputInfo.setPunish_car_lawOper(et_car_lawOper.getEditableText().toString());
        inputInfo.setPunish_car_lawPost(et_car_lawPost.getEditableText().toString());
        inputInfo.setPunish_car_lawName(et_car_lawName.getEditableText().toString());
        inputInfo.setPunish_car_lawPhone(et_car_lawPhone.getEditableText().toString());
        inputInfo.setPunish_car_lawAddr(et_car_lawAddr.getEditableText().toString());
        inputInfo.setPunish_tpsGoods_name(et_tpsGoods_name.getEditableText().toString());
        inputInfo.setPunish_tpsGoods_goods(et_tpsGoods_goods.getEditableText().toString());
        //inputInfo.setPunish_tpsGoods_phone(et_tpsGoods_phone.getEditableText().toString());
        inputInfo.setPunish_tpsGoods_startPlace(et_tpsGoods_startPlace.getEditableText().toString());
        inputInfo.setPunish_tpsGoods_endPlace(et_tpsGoods_endPlace.getEditableText().toString());
        inputInfo.setPunish_overrunNo(et_overrunNo.getEditableText().toString());
        inputInfo.setPunish_isUninstall("否");

        inputInfo.setPunish_site_name("SS");
        inputInfo.setPunish_site_checkPlace(et_site_checkPlace.getEditableText().toString());
        //inputInfo.setPunish_site_shipDirection(et_site_shipDirection.getEditableText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0x2){
            if(requestCode == 0x3){
                inputInfo = (InputInfo) data.getSerializableExtra("intput_data");
            }
        }
    }
}

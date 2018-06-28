package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.constant.Constants;
import com.android.hcbd.overruntransportation.entity.InputInfo;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.nanchen.compresshelper.CompressHelper;
import com.qq.youtu.Youtu;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputStep1Activity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_driver_name)
    EditText et_driver_name;
    @BindView(R.id.iv_driver_name)
    ImageView iv_driver_name;
    @BindView(R.id.cb_boy)
    CheckBox cbBoy;
    @BindView(R.id.cb_girl)
    CheckBox cbGirl;
    @BindView(R.id.et_driver_ids)
    EditText et_driver_ids;
    @BindView(R.id.et_driver_phone)
    EditText et_driver_phone;
    @BindView(R.id.et_driver_addr)
    EditText et_driver_addr;
    @BindView(R.id.et_driver_driverAddr)
    EditText et_driver_driverAddr;
    @BindView(R.id.et_driver_no1)
    EditText et_driver_no1;
    @BindView(R.id.et_driver_certId)
    EditText et_driver_certId;
    @BindView(R.id.et_driver_post)
    EditText et_driver_post;
    @BindView(R.id.et_driver_caseRel)
    EditText et_driver_caseRel;
    @BindView(R.id.et_driver_job)
    EditText et_driver_job;
    @BindView(R.id.et_car_no1)
    EditText et_car_no1;
    @BindView(R.id.et_car_no2)
    EditText et_car_no2;
    @BindView(R.id.et_car_no3)
    EditText et_car_no3;
    @BindView(R.id.et_car_owner)
    EditText et_car_owner;

    @BindView(R.id.iv_id_card_short)
    ImageView iv_id_card_short;
    @BindView(R.id.iv_vehicle_lincense_short)
    ImageView iv_vehicle_lincense_short;

    private static final int REQUEST_CODE_SELECT = 101;
    private static final int REQUEST_EASYPR = 102;
    private int oid ;
    private DialogUtils dialogUtils = new DialogUtils();
    private InputInfo inputInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_step1);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
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
        oid = getIntent().getIntExtra("oid",0);
        cbBoy.setChecked(true);
        cbGirl.setChecked(false);
        ll_main.setVisibility(View.GONE);
        initListener();
        if(MyApplication.getInstance().isEdit()){
            tvTitle.setText("超限处罚编辑");
            initHttpEdit();
        }else{
            tvTitle.setText("超限处罚录入");
            initHttpAdd();
        }


    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        cbBoy.setOnClickListener(this);
        cbGirl.setOnClickListener(this);
        iv_driver_name.setOnClickListener(this);
        iv_id_card_short.setOnClickListener(this);
        iv_vehicle_lincense_short.setOnClickListener(this);
        et_driver_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    httpDriverInfo();
                }
            }
        });
        et_driver_ids.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    httpDriverInfo();
                }
            }
        });
    }

    private void initHttpEdit() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_content_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",oid)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(InputStep1Activity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
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

    private void initHttpAdd() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.content_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(InputStep1Activity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(" result = "+result);
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
                    NewContentInfo newContentInfo = new NewContentInfo();
                    Gson gson = new Gson();

                    JSONArray siteNameArray = new JSONArray(jsonObject.getString("siteNameList"));
                    String[] siteNames = new String[siteNameArray.length()];
                    for(int i=0;i<siteNameArray.length();i++){
                        siteNames[i] = siteNameArray.getString(i);
                    }
                    newContentInfo.setSiteNameList(siteNames);

                    JSONArray siteCheckPlaceArray = new JSONArray(jsonObject.getString("siteCheckPlaceList"));
                    String[] siteCheckPlaces = new String[siteCheckPlaceArray.length()];
                    for(int i=0;i<siteCheckPlaceArray.length();i++){
                        siteCheckPlaces[i] = siteCheckPlaceArray.getString(i);
                    }
                    newContentInfo.setSiteCheckPlaceList(siteCheckPlaces);

                    JSONArray siteShipDirectionArray = new JSONArray(jsonObject.getString("siteShipDirectionList"));
                    String[] siteShipDirections = new String[siteShipDirectionArray.length()];
                    for(int i=0;i<siteShipDirectionArray.length();i++){
                        siteShipDirections[i] = siteShipDirectionArray.getString(i);
                    }
                    newContentInfo.setSiteShipDirectionList(siteShipDirections);

                    JSONArray tpsGoodsGoodArray = new JSONArray(jsonObject.getString("tpsGoodsGoodsList"));
                    String[] tpsGoodsGoods = new String[tpsGoodsGoodArray.length()];
                    for(int i=0;i<tpsGoodsGoodArray.length();i++){
                        tpsGoodsGoods[i] = tpsGoodsGoodArray.getString(i);
                    }
                    newContentInfo.setTpsGoodsGoodsList(tpsGoodsGoods);

                    JSONArray operArray = new JSONArray(jsonObject.getString("operList"));
                    List<NewContentInfo.OperEntity> operEntityList = new ArrayList<NewContentInfo.OperEntity>();
                    for(int i=0;i<operArray.length();i++){
                        NewContentInfo.OperEntity operEntity = gson.fromJson(operArray.getString(i),NewContentInfo.OperEntity.class);
                        operEntityList.add(operEntity);
                    }
                    newContentInfo.setOperList(operEntityList);

                    JSONArray carTypeArray = new JSONArray(jsonObject.getString("carTypeList"));
                    List<NewContentInfo.CarTypeEntity> carTypeList = new ArrayList<NewContentInfo.CarTypeEntity>();
                    for(int i=0;i<carTypeArray.length();i++){
                        NewContentInfo.CarTypeEntity carTypeEntity = gson.fromJson(carTypeArray.getString(i),NewContentInfo.CarTypeEntity.class);
                        carTypeList.add(carTypeEntity);
                    }
                    newContentInfo.setCarTypeList(carTypeList);

                    JSONArray sourceArray = new JSONArray(jsonObject.getString("sourceList"));
                    String[] sources = new String[sourceArray.length()];
                    for(int i=0;i<sourceArray.length();i++){
                        sources[i] = sourceArray.getString(i);
                    }
                    newContentInfo.setSourceList(sources);

                    JSONArray proofArray = new JSONArray(jsonObject.getString("proofList"));
                    List<NewContentInfo.ProofEntity> prrofList = new ArrayList<NewContentInfo.ProofEntity>();
                    for(int i=0;i<proofArray.length();i++){
                        NewContentInfo.ProofEntity proofEntity = gson.fromJson(proofArray.getString(i),NewContentInfo.ProofEntity.class);
                        prrofList.add(proofEntity);
                    }
                    newContentInfo.setProofList(prrofList);

                    JSONArray driverNameArray = new JSONArray(jsonObject.getString("driverNameList"));
                    String[] driverNames = new String[driverNameArray.length()];
                    for(int i=0;i<driverNameArray.length();i++){
                        driverNames[i] = driverNameArray.getString(i);
                    }
                    newContentInfo.setDriverNameList(driverNames);

                    if(MyApplication.getInstance().isEdit()){
                        LogUtils.LogShow(jsonObject.getString("data"));
                        NewContentInfo.Data datainfo = gson.fromJson(jsonObject.getString("data"),NewContentInfo.Data.class);
                        newContentInfo.setData(datainfo);

                        NewContentInfo.DataBase dataBase = gson.fromJson(jsonObject.getString("dataBase"),NewContentInfo.DataBase.class);
                        newContentInfo.setDataBase(dataBase);
                    }else{
                        newContentInfo.setData(null);
                        newContentInfo.setDataBase(null);
                    }

                    MyApplication.getInstance().setNewContentInfo(newContentInfo);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll_main.setVisibility(View.VISIBLE);
                            if(MyApplication.getInstance().isEdit()){
                                NewContentInfo.Data inputInfo = MyApplication.getInstance().getNewContentInfo().getData();
                                et_driver_name.setText(inputInfo.getDriver().getName());
                                if(inputInfo.getDriver().getSex().equals("男")) {
                                    cbBoy.setChecked(true);
                                    cbGirl.setChecked(false);
                                }else {
                                    cbBoy.setChecked(false);
                                    cbGirl.setChecked(true);
                                }
                                et_driver_ids.setText(inputInfo.getDriver().getIds());
                                et_driver_phone.setText(inputInfo.getDriver().getPhone());
                                et_driver_addr.setText(inputInfo.getDriver().getAddr());
                                et_driver_driverAddr.setText(inputInfo.getDriver().getDriverAddr());
                                et_driver_no1.setText(inputInfo.getDriver().getNo1());
                                et_driver_certId.setText(inputInfo.getDriver().getCertId());
                                et_driver_post.setText(inputInfo.getDriver().getPost());
                                et_driver_caseRel.setText(inputInfo.getDriver().getCaseRel());
                                et_driver_job.setText(inputInfo.getDriver().getJob());

                                et_car_no1.setText(inputInfo.getCar().getNo1());
                                et_car_no2.setText(inputInfo.getCar().getNo2());
                                et_car_no3.setText(inputInfo.getCar().getNo3());
                                et_car_owner.setText(inputInfo.getCar().getOwner());

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_next:
                if(TextUtils.isEmpty(et_driver_name.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入司机姓名！");
                    et_driver_name.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(et_driver_ids.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入身份证号！");
                    et_driver_ids.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(et_car_no1.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入车辆号码！");
                    et_car_no1.requestFocus();
                    return;
                }
                next();
                break;
            case R.id.cb_boy:
                cbBoy.setChecked(true);
                cbGirl.setChecked(false);
                break;
            case R.id.cb_girl:
                cbBoy.setChecked(false);
                cbGirl.setChecked(true);
                break;
            case R.id.iv_driver_name:
                showDialog();
                break;
            case R.id.iv_id_card_short:
                cameraWriteTask();
                break;
            case R.id.iv_vehicle_lincense_short:
                cameraTask();
                break;
        }
    }

    private void showDialog() {
        final String[] driverNameList = MyApplication.getInstance().getNewContentInfo().getDriverNameList();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        //builder.setTitle("请选择"); //设置标题
        builder.setItems(driverNameList,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                et_driver_name.setText(driverNameList[which]);
                httpDriverInfo();
            }
        });
        builder.create().show();
    }

    private void next() {
        if(inputInfo == null)
            inputInfo = new InputInfo();
        inputInfo.setPunish_driver_name(et_driver_name.getEditableText().toString());
        inputInfo.setPunish_driver_ids(et_driver_ids.getEditableText().toString());
        if(cbBoy.isChecked())
            inputInfo.setPunish_driver_sex("男");
        else
            inputInfo.setPunish_driver_sex("女");
        inputInfo.setPunish_driver_phone(et_driver_phone.getEditableText().toString());
        inputInfo.setPunish_driver_addr(et_driver_addr.getEditableText().toString());
        inputInfo.setPunish_driver_driverAddr(et_driver_driverAddr.getEditableText().toString());
        inputInfo.setPunish_driver_no1(et_driver_no1.getEditableText().toString());
        inputInfo.setPunish_driver_certId(et_driver_certId.getEditableText().toString());
        inputInfo.setPunish_driver_post(et_driver_post.getEditableText().toString());
        inputInfo.setPunish_driver_caseRel(et_driver_caseRel.getEditableText().toString());
        inputInfo.setPunish_driver_job(et_driver_job.getEditableText().toString());
        inputInfo.setPunish_car_no1(et_car_no1.getEditableText().toString());
        inputInfo.setPunish_car_no2(et_car_no2.getEditableText().toString());
        inputInfo.setPunish_car_no3(et_car_no3.getEditableText().toString());
        inputInfo.setPunish_car_owner(et_car_owner.getEditableText().toString());

        Intent intent = new Intent(this,InputStep2Activity.class);
        intent.putExtra("input", inputInfo);
        startActivityForResult(intent,0x2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null ){
                    httpData(images.get(0).path);
                }
            }else if(data != null && requestCode == REQUEST_EASYPR){
                String plate = data.getStringExtra("plate");
                et_car_no1.setText(plate);
            }
        }else if(resultCode == 0x1){
            if(requestCode == 0x2){
                inputInfo = (InputInfo) data.getSerializableExtra("intput_data");
            }
        }

    }

    private void httpData(final String path) {
        dialogUtils.showLoading(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = CompressHelper.getDefault(InputStep1Activity.this).compressToBitmap(FileUtils.getFileByPath(path));
                try {
                    JSONObject jsonObject = Youtu.getInstance().IdcardOcr(bitmap,0);
                    LogUtils.LogShow(jsonObject.toString());
                    if(jsonObject.getString("errormsg").equals("OK")){
                        final String ids = jsonObject.getString("id");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialogUtils.dismissLoading();
                                ToastUtils.showShortToast(MyApplication.getInstance(),"证件识别成功!");
                                et_driver_ids.setText(ids);
                                httpDriverInfo();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialogUtils.dismissLoading();
                                ToastUtils.showShortToast(MyApplication.getInstance(),"证件识别失败!");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void httpDriverInfo() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.driver_info_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("name",et_driver_name.getEditableText().toString())
                .params("ids",et_driver_ids.getEditableText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                et_driver_name.setText(jsonObject.getString("driverName"));
                                JSONObject object = new JSONObject(jsonObject.getString("data"));
                                if(object.getString("sex").equals("男")){
                                    cbBoy.setChecked(true);
                                    cbGirl.setChecked(false);
                                }else{
                                    cbBoy.setChecked(false);
                                    cbGirl.setChecked(true);
                                }
                                et_driver_phone.setText(object.getString("phone"));
                                et_driver_addr.setText(object.getString("addr"));
                                et_driver_driverAddr.setText(object.getString("driverAddr"));
                                et_driver_ids.setText(object.getString("ids"));
                                et_driver_no1.setText(object.getString("no1"));
                                et_driver_certId.setText(object.getString("certId"));
                                et_driver_post.setText(object.getString("post"));
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

    public void cameraWriteTask() {
        AndPermission.with(this)
                .requestCode(Constants.REQUEST_CODE_PERMISSION_MULTI)
                .permission(Permission.CAMERA,Permission.STORAGE)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(InputStep1Activity.this, rationale).show();
                    }
                })
                .start();
    }

    public void cameraTask() {
        AndPermission.with(this)
                .requestCode(Constants.REQUEST_CODE_PERMISSION_SINGLE)
                .permission(Permission.CAMERA)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(InputStep1Activity.this, rationale).show();
                    }
                })
                .start();
    }

    @PermissionYes(Constants.REQUEST_CODE_PERMISSION_SINGLE)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        //successfully
        LogUtils.LogShow("successfully");
        Intent intent = new Intent(this, EasyPRActivity.class);
        startActivityForResult(intent,REQUEST_EASYPR);
    }

    @PermissionNo(Constants.REQUEST_CODE_PERMISSION_SINGLE)
    private void getMultiNo(@NonNull List<String> deniedPermissions) {
        //failure
        LogUtils.LogShow("failure");
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, Constants.REQUEST_CODE_SETTING).show();
            // 第二种：用自定义的提示语。
            /*AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                .setTitle("权限申请失败")
                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                .setPositiveButton("好，去设置")
                .show();*/
        }
    }

    @PermissionYes(Constants.REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiYes1(@NonNull List<String> grantedPermissions) {
        //successfully
        LogUtils.LogShow("successfully");
        Intent intent = new Intent(InputStep1Activity.this,ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @PermissionNo(Constants.REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiNo1(@NonNull List<String> deniedPermissions) {
        //failure
        LogUtils.LogShow("failure");
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, Constants.REQUEST_CODE_SETTING).show();
            // 第二种：用自定义的提示语。
            /*AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                .setTitle("权限申请失败")
                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                .setPositiveButton("好，去设置")
                .show();*/
        }
    }


}

package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.CarTypeInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.ImageUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarTypeAddActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_PERMISSION = 102;
    private static final int REQUEST_CODE_SETTING = 301;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.et_type)
    EditText et_type;
    @BindView(R.id.et_axisNum)
    EditText et_axisNum;
    @BindView(R.id.et_wheelNum)
    EditText et_wheelNum;
    @BindView(R.id.et_limit)
    EditText et_limit;
    @BindView(R.id.et_length)
    EditText et_length;
    @BindView(R.id.et_width)
    EditText et_width;
    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.rl_add)
    RelativeLayout rl_add;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.ll_text)
    LinearLayout ll_text;
    private String type;
    private CarTypeInfo.DataInfo dataInfo;
    private DialogUtils dialogUtils = new DialogUtils();
    private String iconPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_add);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");
        if(type.equals("add")){
            tv_title.setText("车型信息录入");
        }else{
            tv_title.setText("车型信息修改");
            dataInfo = (CarTypeInfo.DataInfo) getIntent().getSerializableExtra("data");
            et_type.setText(dataInfo.getType());
            et_axisNum.setText(dataInfo.getAxisNum());
            et_wheelNum.setText(dataInfo.getWheelNum());
            et_limit.setText(String.valueOf(dataInfo.getLimit()));
            et_length.setText(String.valueOf(dataInfo.getLength()));
            et_width.setText(String.valueOf(dataInfo.getWidth()));
            et_height.setText(String.valueOf(dataInfo.getHeight()));
            Glide.with(this).load(MyApplication.getInstance().getBsaeUrl()+dataInfo.getTypeImg())//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.drawable.ic_default_image)           //设置错误图片
                    .placeholder(R.drawable.ic_default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(iv_pic);
            ll_text.setVisibility(View.GONE);
        }

        initListener();
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_save.setOnClickListener(this);
        rl_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                if(TextUtils.isEmpty(iconPath)){
                    ToastUtils.showShortToast(this,"请选择车型图标");
                    return;
                }
                if(TextUtils.isEmpty(et_type.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入车型");
                    return;
                }
                if(TextUtils.isEmpty(et_axisNum.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入轴数");
                    return;
                }
                if(TextUtils.isEmpty(et_wheelNum.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入轮数");
                    return;
                }
                if(TextUtils.isEmpty(et_limit.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入总质量限值");
                    return;
                }
                if(TextUtils.isEmpty(et_length.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入长度");
                    return;
                }
                if(TextUtils.isEmpty(et_width.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入宽度");
                    return;
                }
                if(TextUtils.isEmpty(et_height.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入高度");
                    return;
                }
                saveDate();
                break;
            case R.id.rl_add:
                writeTask();
                break;
        }

    }

    private void saveDate() {
        List<File> fileList = new ArrayList<>();
        if(!TextUtils.isEmpty(iconPath)){
            fileList.add(FileUtils.getFileByPath(iconPath));
        }
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_car_type_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",dataInfo == null ? "" : String.valueOf(dataInfo.getId()))
                .params("tpsCarType.code",dataInfo == null ? "" : dataInfo.getCode())
                .params("tpsCarType.state",dataInfo == null ? "1" : dataInfo.getState())
                .params("tpsCarType.operNames",MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .params("tpsCarType.id",dataInfo == null ? "1" : String.valueOf(dataInfo.getId()))
                .params("tpsCarType.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("tpsCarType.type",et_type.getEditableText().toString())
                .params("tpsCarType.axisNum",et_axisNum.getEditableText().toString())
                .params("tpsCarType.wheelNum",et_wheelNum.getEditableText().toString())
                .params("tpsCarType.limit",et_limit.getEditableText().toString())
                .params("tpsCarType.width",et_width.getEditableText().toString())
                .params("tpsCarType.length",et_length.getEditableText().toString())
                .params("tpsCarType.height",et_height.getEditableText().toString())
                .params("tpsCarType.typeImg",dataInfo == null ? "":dataInfo.getTypeImg())
                .addFileParams("tpsCarType.upload",fileList )
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(CarTypeAddActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                ToastUtils.showShortToast(CarTypeAddActivity.this,jsonObject.getString("data"));
                                MessageEvent messageEvent = new MessageEvent();
                                if(type.equals("add")){
                                    messageEvent.setEventId(MessageEvent.EVENT_CAR_TYPE_LIST_ADD);
                                }else{
                                    messageEvent.setEventId(MessageEvent.EVENT_CAR_TYPE_LIST_EDIT);
                                }
                                EventBus.getDefault().post(messageEvent);
                                finishActivity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                if(!TextUtils.isEmpty(jsonObject.getString("error"))){
                                    ToastUtils.showShortToast(CarTypeAddActivity.this,jsonObject.getString("error"));
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

    public void writeTask() {
// 申请多个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Permission.STORAGE)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(CarTypeAddActivity.this, rationale).show();
                    }
                })
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1 == resultCode){
            iconPath = data.getStringExtra("path");
            System.out.println("path = "+iconPath);
            String name = data.getStringExtra("name");
            int axisNum = data.getIntExtra("axisNum",0);
            iv_pic.setImageBitmap(ImageUtils.getBitmap(iconPath));
            et_type.setText(name);
            et_axisNum.setText(""+axisNum);
            et_wheelNum.setText(""+axisNum*2);
            ll_text.setVisibility(View.GONE);
        }
        if(requestCode == REQUEST_CODE_SETTING){
            LogUtils.LogShow("The user came back from the settings");
        }
    }

    @PermissionYes(REQUEST_CODE_PERMISSION)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        //successfully
        LogUtils.LogShow("successfully");
        Intent intent = new Intent(this,CarTypeIconListActivity.class);
        startActivityForResult(intent,1);
    }

    @PermissionNo(REQUEST_CODE_PERMISSION)
    private void getMultiNo(@NonNull List<String> deniedPermissions) {
        //failure
        LogUtils.LogShow("failure");
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(CarTypeAddActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(CarTypeAddActivity.this, REQUEST_CODE_SETTING).show();
            // 第二种：用自定义的提示语。
            /*AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                .setTitle("权限申请失败")
                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                .setPositiveButton("好，去设置")
                .show();*/
        }
    }

}

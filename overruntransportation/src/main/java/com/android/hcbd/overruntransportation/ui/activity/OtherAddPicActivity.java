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
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.constant.Constants;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherAddPicActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.rl_add)
    RelativeLayout rl_add;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.ll_text)
    LinearLayout ll_text;
    private OtherInfo.PicBean picBean;

    private ImageItem imageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_add_pic);
        ButterKnife.bind(this);

        picBean = (OtherInfo.PicBean) getIntent().getSerializableExtra("data");

        if(picBean!= null){
            et_name.setText(picBean.getName());
            ll_text.setVisibility(View.GONE);
            if(picBean.getId() == -1)
                Glide.with(this).load(picBean.getUrl()).error(R.drawable.ic_default_image).into(iv_image);
            else
                Glide.with(this).load(MyApplication.getInstance().getBsaeUrl()+picBean.getUrl()).error(R.drawable.ic_default_image).into(iv_image);
        }
        initListener();
    }

    private void initListener() {
        tv_cancel.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        rl_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                finishActivity();
                break;
            case R.id.tv_ok:
                if(picBean != null){
                    if(TextUtils.isEmpty(et_name.getEditableText().toString())){
                        Toast.makeText(this,"请先添加照片及文字",Toast.LENGTH_LONG).show();
                    }else{
                        if(imageItem != null){
                            picBean.setUrl(imageItem.path);
                        }
                        picBean.setName(et_name.getEditableText().toString());
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setEventId(MessageEvent.EVENT_OTHER_PHONE_EDIT_SUCCESS);
                        messageEvent.setObj(picBean);
                        EventBus.getDefault().post(messageEvent);
                        finishActivity();
                    }
                }else{
                    if(imageItem == null || TextUtils.isEmpty(et_name.getEditableText().toString())){
                        Toast.makeText(this,"请先添加照片及文字",Toast.LENGTH_LONG).show();
                    }else{
                        picBean = new OtherInfo.PicBean();
                        picBean.setId((long) -1);
                        picBean.setUrl(imageItem.path);
                        picBean.setName(et_name.getEditableText().toString());
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setEventId(MessageEvent.EVENT_OTHER_PHONE_ADD_SUCCESS);
                        messageEvent.setObj(picBean);
                        EventBus.getDefault().post(messageEvent);
                        finishActivity();
                    }
                }

                break;
            case R.id.rl_add:
                cameraWriteTask();
                break;
        }
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
                        AndPermission.rationaleDialog(OtherAddPicActivity.this, rationale).show();
                    }
                })
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == Constants.REQUEST_CODE_SELECT) {
                ll_text.setVisibility(View.GONE);
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    imageItem = images.get(0);
                    Glide.with(this).load(imageItem.path).error(R.drawable.ic_default_image).into(iv_image);
                }
            }
        }else {
            //Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    @PermissionYes(Constants.REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        //successfully
        LogUtils.LogShow("successfully");
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT);
    }

    @PermissionNo(Constants.REQUEST_CODE_PERMISSION_MULTI)
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


}

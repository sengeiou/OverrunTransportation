package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.constant.Constants;
import com.android.hcbd.overruntransportation.entity.TemplateManageInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordAddActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rl_add)
    RelativeLayout rl_add;
    private DialogUtils dialogUtils = new DialogUtils();
    private String filePath;
    private String type;
    private TemplateManageInfo.DataInfo dataInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_add);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");

        if(type.equals("add")){
            tv_title.setText("生成文书录入");
        }else{
            dataInfo = (TemplateManageInfo.DataInfo) getIntent().getSerializableExtra("data");
            tv_title.setText("生成文书修改");
            tv_name.setText(dataInfo.getUploadFileName());
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
                if(TextUtils.isEmpty(filePath))
                    ToastUtils.showLongToast(this,"请先选择文件！");
                else
                    saveData();
                break;
            case R.id.rl_add:
                writeTask();
                break;
        }
    }

    private void saveData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.save_word_list_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token",MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid",dataInfo == null ? "" : String.valueOf(dataInfo.getId()))
                .params("tpsWord.id",dataInfo == null ? "" : String.valueOf(dataInfo.getId()))
                .params("tpsWord.upload",FileUtils.getFileByPath(filePath))
                .params("tpsWord.orgCode",MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("tpsWord.code",dataInfo == null ? "" : dataInfo.getCode())
                .params("tpsWord.type","Oper")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(WordAddActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            MessageEvent messageEvent = new MessageEvent();
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                ToastUtils.showShortToast(WordAddActivity.this,jsonObject.getString("data"));
                                if(type.equals("add")){
                                    messageEvent.setEventId(MessageEvent.EVENT_WORD_LIST_ADD);
                                }else{
                                    messageEvent.setEventId(MessageEvent.EVENT_WORD_LIST_EDIT);
                                    dataInfo.setUploadFileName(FileUtils.getFileName(filePath));
                                    messageEvent.setObj(dataInfo);
                                }
                            }
                            EventBus.getDefault().post(messageEvent);
                            finishActivity();
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

    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.docx$"))
                .withHiddenFiles(true)
                .withTitle("请选择一个word文件")
                .start();
    }

    public void writeTask() {
        AndPermission.with(this)
                .requestCode(Constants.REQUEST_CODE_PERMISSION_SINGLE)
                .permission(Permission.STORAGE)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(WordAddActivity.this, rationale).show();
                    }
                })
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if(!TextUtils.isEmpty(filePath))
                tv_name.setText(FileUtils.getFileName(filePath));
        }
    }

    @PermissionYes(Constants.REQUEST_CODE_PERMISSION_SINGLE)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        //successfully
        LogUtils.LogShow("successfully");
        openFilePicker();
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

}

package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.LoginInfo;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LocationUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.SharedPreferencesUtil;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pgyersdk.update.PgyUpdateManager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_PERMISSION_MULTI = 101;
    private static final int REQUEST_CODE_SETTING = 300;

    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private DialogUtils dialogUtils = new DialogUtils();
    private LocationUtils mLocationUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        PgyUpdateManager.register(this, getPackageName()+".provider");

        String user = SharedPreferencesUtil.get(this, "username_info");
        etUser.setText(user);
        etUser.setSelection(user.length());
        requestMultiPermission();
        btnOk.setOnClickListener(this);
        mLocationUtils = new LocationUtils();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationUtils.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationUtils.stop();
        PgyUpdateManager.unregister();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                final String user = etUser.getEditableText().toString();
                String password = etPassword.getEditableText().toString();
                if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
                    OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.login_url)
                            .tag(this)
                            .retryCount(0)
                            .params("userName",user)
                            .params("userPwd",password)
                            .params("orgCode","027")
                            .params("addr",MyApplication.getInstance().getCityInfo() == null ? "未知地点" : (TextUtils.isEmpty(MyApplication.getInstance().getCityInfo().getAddr()) ? "未知地点" : MyApplication.getInstance().getCityInfo().getAddr()))
                            .execute(new StringCallback() {
                                @Override
                                public void onStart(Request<String, ? extends Request> request) {
                                    super.onStart(request);
                                    dialogUtils.showLoading(LoginActivity.this);
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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("提示");
                                        builder.setMessage("连接服务器失败，是否需要修改服务器地址");
                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                Intent intent = new Intent(LoginActivity.this,IpAddressActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                        builder.create().show();
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
                }else{
                    Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void realJsonData(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    if(!TextUtils.isEmpty(jsonObject.getString("token"))){
                        Gson gson = new Gson();
                        LoginInfo loginInfo = new LoginInfo();

                        JSONArray permitArray = new JSONArray(jsonObject.getString("permitList"));
                        String[] permits = new String[permitArray.length()];
                        for(int i=0;i<permitArray.length();i++){
                            permits[i] = permitArray.getString(i);
                        }
                        loginInfo.setPermitList(permits);
                        loginInfo.setLastAddr(jsonObject.getString("lastAddr"));
                        loginInfo.setToken(jsonObject.getString("token"));
                        loginInfo.setLoginCount(jsonObject.getString("loginCount"));
                        LoginInfo.UserInfo userInfo = gson.fromJson(jsonObject.getString("data"), LoginInfo.UserInfo.class);
                        loginInfo.setUserInfo(userInfo);

                        JSONArray menuArray = new JSONArray(jsonObject.getString("menuList"));
                        List<LoginInfo.MenuInfo> menuInfoList = new ArrayList<LoginInfo.MenuInfo>();
                        for(int i=0;i<menuArray.length();i++){
                            LoginInfo.MenuInfo menuInfo = gson.fromJson(menuArray.getString(i),LoginInfo.MenuInfo.class);
                            menuInfoList.add(menuInfo);
                        }
                        loginInfo.setMenuList(menuInfoList);
                        MyApplication.getInstance().setLoginInfo(loginInfo);

                        SharedPreferencesUtil.save(LoginActivity.this, "username_info", etUser.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finishActivity();
                            }
                        });

                    }
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


    private void requestMultiPermission() {
        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_MULTI)
                .permission(Permission.LOCATION, Permission.STORAGE)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(LoginActivity.this, rationale).show();
                    }
                })
                .start();
    }

    @PermissionYes(REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        //successfully
        LogUtils.LogShow("successfully");
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiNo(@NonNull List<String> deniedPermissions) {
        //failure
        LogUtils.LogShow("failure");
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(LoginActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(LoginActivity.this, REQUEST_CODE_SETTING).show();
            // 第二种：用自定义的提示语。
            /*AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                .setTitle("权限申请失败")
                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                .setPositiveButton("好，去设置")
                .show();*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                LogUtils.LogShow("The user came back from the settings");
                break;
            }
        }
    }



}

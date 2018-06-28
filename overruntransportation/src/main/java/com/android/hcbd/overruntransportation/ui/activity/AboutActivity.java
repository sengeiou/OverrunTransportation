package com.android.hcbd.overruntransportation.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.AppUtils;
import com.lzy.imagepicker.util.ProviderUtil;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.btn_check)
    Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        tvVersion.setText("版本："+AppUtils.getAppVersionName(this));
    }

    @OnClick({R.id.iv_back, R.id.btn_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_check:
                PgyUpdateManager.register(this, ProviderUtil.getFileProviderName(this), new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        ToastUtils.showShortToast(MyApplication.getInstance(),"已经是最新版本");
                    }

                    @Override
                    public void onUpdateAvailable(String s) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(s);
                        new AlertDialog.Builder(AboutActivity.this)
                                .setTitle("更新提示")
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {
                                        dialog.dismiss();
                                        startDownloadTask(AboutActivity.this,appBean.getDownloadURL());
                                    }
                                }).show();
                    }
                });
                break;
        }
    }
}

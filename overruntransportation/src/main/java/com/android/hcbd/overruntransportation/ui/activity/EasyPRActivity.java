package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.hcbd.overruntransportation.R;
import com.fosung.libeasypr.util.FlashLightUtils;
import com.fosung.libeasypr.view.EasyPRPreSurfaceView;
import com.fosung.libeasypr.view.EasyPRPreView;
import com.lzy.imagepicker.ImagePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EasyPRActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.preSurfaceView)
    EasyPRPreView easyPRPreView;
    @BindView(R.id.btnShutter)
    Button btnCamera;
    @BindView(R.id.iv_ligth)
    ImageView iv_ligth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_pr);
        ButterKnife.bind(this);

        iv_ligth.setSelected(false);

        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (easyPRPreView != null) {
            easyPRPreView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (easyPRPreView != null) {
            easyPRPreView.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (easyPRPreView != null) {
            easyPRPreView.onDestroy();
        }
    }

    private void initListener() {
        btnCamera.setOnClickListener(this);
        iv_ligth.setOnClickListener(this);
        easyPRPreView.setRecognizedListener(new EasyPRPreSurfaceView.OnRecognizedListener() {
            @Override
            public void onRecognized(String s) {
                if (s == null || s.equals("0")) {
                    Toast.makeText(EasyPRActivity.this, "换个姿势试试!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("plate",s);
                    setResult(ImagePicker.RESULT_CODE_ITEMS, intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShutter:
                easyPRPreView.recognize();
                break;
            case R.id.iv_ligth:
                if(iv_ligth.isSelected()){
                    iv_ligth.setSelected(false);
                    FlashLightUtils.isLightEnable(false);
                }else{
                    iv_ligth.setSelected(true);
                    FlashLightUtils.isLightEnable(true);
                }

                break;
        }
    }


}

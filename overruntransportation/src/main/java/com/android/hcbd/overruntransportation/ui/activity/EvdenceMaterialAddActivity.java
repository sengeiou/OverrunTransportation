package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvdenceMaterialAddActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_spec)
    EditText et_spec;
    @BindView(R.id.et_num)
    EditText et_num;
    private NewContentInfo.ProofEntity proofEntity;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evdence_material_add);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");
        if(type.equals("edit")){
            proofEntity = (NewContentInfo.ProofEntity) getIntent().getSerializableExtra("data");
            et_name.setText(proofEntity.getCode());
            et_spec.setText(proofEntity.getName());
            et_num.setText(proofEntity.getIds());
        }
        if(proofEntity == null){
            proofEntity = new NewContentInfo.ProofEntity();
        }
        iv_back.setOnClickListener(this);
        iv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                if(TextUtils.isEmpty(et_name.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入证据名称！");
                    return;
                }
                if(TextUtils.isEmpty(et_spec.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入规格！");
                    return;
                }
                if(TextUtils.isEmpty(et_num.getEditableText().toString())){
                    ToastUtils.showShortToast(this,"请输入数量！");
                    return;
                }
                proofEntity.setCode(et_name.getEditableText().toString());
                proofEntity.setName(et_spec.getEditableText().toString());
                proofEntity.setIds(et_num.getEditableText().toString());
                MessageEvent messageEvent = new MessageEvent();
                if(type.equals("add")){
                    messageEvent.setEventId(MessageEvent.EVENT_EM_LIST_ADD);
                }else{
                    messageEvent.setEventId(MessageEvent.EVENT_EM_LIST_EDIT);
                }
                messageEvent.setObj(proofEntity);
                EventBus.getDefault().post(messageEvent);
                finishActivity();
                break;
        }
    }
}

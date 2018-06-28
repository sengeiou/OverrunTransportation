package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.EvdenceMaterialsAdapter;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 证据材料列表
 */
public class EvidenceMaterialsListActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.cb_select)
    CheckBox cb_select;
    private EvdenceMaterialsAdapter adapter;
    private List<NewContentInfo.ProofEntity> proofEntityList = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence_materials_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        proofEntityList.addAll(MyApplication.getInstance().getNewContentInfo().getProofList());
        adapter = new EvdenceMaterialsAdapter(this,proofEntityList);
        mListView.setAdapter(adapter);
        initListener();

        int selNum = 0;
        for(NewContentInfo.ProofEntity proofEntity:proofEntityList){
            if (proofEntity.isChecked())
                selNum++;
        }
        if(selNum == proofEntityList.size())
            cb_select.setChecked(true);
        else
            cb_select.setChecked(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_EM_LIST_ADD:
                NewContentInfo.ProofEntity proofEntity = (NewContentInfo.ProofEntity) event.getObj();
                proofEntityList.add(proofEntity);
                adapter.notifyDataSetChanged();
                break;
            case MessageEvent.EVENT_EM_LIST_EDIT:
                NewContentInfo.ProofEntity proofEntity1 = (NewContentInfo.ProofEntity) event.getObj();
                proofEntityList.set(adapter.getPoisition(),proofEntity1);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        //ivSave.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewContentInfo.ProofEntity proofEntity = proofEntityList.get(i);
                if (proofEntity.isChecked()) {
                    //取消选中
                    proofEntity.setChecked(false);
                } else {
                    //选中
                    proofEntity.setChecked(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for(NewContentInfo.ProofEntity proofEntity:proofEntityList){
                        proofEntity.setChecked(true);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    for(NewContentInfo.ProofEntity proofEntity:proofEntityList){
                        proofEntity.setChecked(false);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                getProofList();
                break;
            case R.id.iv_edit:
                Intent intent = new Intent(this,EvdenceMaterialAddActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getProofList();
    }

    private void getProofList() {
        List<NewContentInfo.ProofEntity> proofLists = new ArrayList<>();
        for(int i=0;i<proofEntityList.size();i++){
            if(proofEntityList.get(i).isChecked()){
                proofLists.add(proofEntityList.get(i));
            }
        }
        if(proofLists.size() > 0){
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setEventId(MessageEvent.EVENT_EVIDENCE_MATERIAL);
            messageEvent.setObj(proofLists);
            EventBus.getDefault().post(messageEvent);
        }
        finishActivity();
    }
}

package com.android.hcbd.overruntransportation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.CarTypeAdapter;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 车轴类型列表
 */
public class AxleTypeActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    private CarTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axle_type);
        ButterKnife.bind(this);

        ivBack.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        int id = getIntent().getIntExtra("id",0);

        if(MyApplication.getInstance().getNewContentInfo().getCarTypeList().size() > 0){
            mListView.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
            adapter = new CarTypeAdapter(this, MyApplication.getInstance().getNewContentInfo().getCarTypeList());
            mListView.setAdapter(adapter);

            for(int i=0;i<MyApplication.getInstance().getNewContentInfo().getCarTypeList().size();i++){
                if(id == MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(i).getId()){
                    adapter.setIndex(i);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }else{
            mListView.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
        }

        initListener();
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setIndex(i);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setEventId(MessageEvent.EVENT_CAR_TYPE);
                NewContentInfo.CarTypeEntity carTypeEntity = MyApplication.getInstance().getNewContentInfo().getCarTypeList().get(adapter.getIndex());
                messageEvent.setObj(carTypeEntity);
                EventBus.getDefault().post(messageEvent);
                finishActivity();
                break;
        }
    }
}

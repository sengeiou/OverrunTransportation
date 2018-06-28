package com.android.hcbd.overruntransportation.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.CarTypeIconAdapter;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypeIconListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.listView)
    ListView listView;
    private CarTypeIconAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_pic_list);
        ButterKnife.bind(this);

        adapter = new CarTypeIconAdapter(this);
        listView.setAdapter(adapter);
        initListener();
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_save.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                saveData();
                break;
        }
    }

    private void saveData() {
        Bitmap bitmap = ImageUtils.getBitmap(getResources(),adapter.getIconRes());
        String path = getSDPath()+"/tps/cache/ic_car.png";
        boolean isSave = ImageUtils.save(bitmap,path,Bitmap.CompressFormat.PNG,true);
        if(isSave) {
            Intent intent = new Intent();
            intent.putExtra("path",path);
            intent.putExtra("name",adapter.getCarType());
            intent.putExtra("axisNum",adapter.getaxisNums());
            setResult(1,intent);
            finish();
        }else{
            ToastUtils.showShortToast(this,"未知的错误！");
        }
    }

}

package com.android.hcbd.overruntransportation.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.OtherPicGridAdapter;
import com.android.hcbd.overruntransportation.constant.Constants;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.activity.OtherAddPicActivity;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.android.hcbd.overruntransportation.widget.NoScrollGridView;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.nanchen.compresshelper.CompressHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 集体讨论列表
 */
public class DiscussedFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static final int REQUEST_CODE_SELECT = 101;

    @BindView(R.id.tv_beginTime)
    TextView tv_beginTime;
    @BindView(R.id.iv_beginTime)
    ImageView iv_beginTime;
    @BindView(R.id.tv_endTime)
    TextView tv_endTime;
    @BindView(R.id.iv_endTime)
    ImageView iv_endTime;
    @BindView(R.id.tv_n1)
    TextView tv_n1;
    @BindView(R.id.iv_n1)
    ImageView iv_n1;
    @BindView(R.id.tv_n2)
    TextView tv_n2;
    @BindView(R.id.iv_n2)
    ImageView iv_n2;
    @BindView(R.id.tv_n3)
    TextView tv_n3;
    @BindView(R.id.iv_n3)
    ImageView iv_n3;
    @BindView(R.id.tv_n4)
    TextView tv_n4;
    @BindView(R.id.iv_n4)
    ImageView iv_n4;
    @BindView(R.id.tv_n5)
    TextView tv_n5;
    @BindView(R.id.iv_n5)
    ImageView iv_n5;
    @BindView(R.id.gridView)
    NoScrollGridView gridView;

    private OtherInfo otherInfo;
    private List<Map<String,Object>> picList = new ArrayList<>();
    private OtherPicGridAdapter adapter;
    private List<OtherInfo.PicBean> picBeanList;
    private ArrayList<ImageItem> imageItemList = new ArrayList<>();
    private DialogUtils dialogUtils = new DialogUtils();
    private List<OtherInfo.PicBean> delPicList = new ArrayList<>();
    private OtherInfo.PicBean picBeans;

    public DiscussedFragment() {
        // Required empty public constructor
    }

    public static DiscussedFragment newInstance(String param1, String param2) {
        DiscussedFragment fragment = new DiscussedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussed, container, false);
        ButterKnife.bind(this,view);

        initListener();
        initData();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_OTHER_PHONE_ADD_SUCCESS:
                OtherInfo.PicBean picBean = (OtherInfo.PicBean) event.getObj();

                ImageItem item = new ImageItem();
                item.path = picBean.getUrl();
                imageItemList.add(item);

                picList.remove((picList.size()-1));
                Map<String,Object> map = null;
                map = new HashMap<>();
                map.put("picInfo",picBean);
                map.put("type", Constants.NUMFIRST);
                picList.add(map);

                map = new HashMap<>();
                map.put("picInfo",null);
                map.put("type", Constants.NUMSECONd);
                picList.add(map);
                gridView.setAdapter(adapter);
                break;
            case MessageEvent.EVENT_OTHER_PHONE_EDIT_SUCCESS:
                OtherInfo.PicBean picBean2 = (OtherInfo.PicBean) event.getObj();
                for(int i=0;i<picList.size()-1;i++){
                    if(picBean2.getId().longValue() == ((OtherInfo.PicBean)picList.get(i).get("picInfo")).getId().longValue()){
                        System.out.println("ssssssss = "+picBean2.getName());
                        Map<String,Object> map2 = new HashMap<>();
                        map2.put("picInfo",picBean2);
                        map2.put("type", Constants.NUMFIRST);
                        picList.set(i,map2);
                    }
                }
                gridView.setAdapter(adapter);
                break;
            case MessageEvent.EVENT_OTHER_PHONE_DELETE_SUCCESS:
                int position = (int) event.getObj();

                for(int i=0;i<picBeanList.size();i++){
                    if(((OtherInfo.PicBean)picList.get(position).get("picInfo")).getId() == picBeanList.get(i).getId()){
                        delPicList.add(picBeanList.get(i));
                    }
                }

                imageItemList.remove(position);
                picList.remove(position);
                gridView.setAdapter(adapter);
                break;
            case MessageEvent.EVENT_OTHER_DISCUSSED_SAVE_SUCCESS:
                discussedSave();
                break;
        }
    }

    private void discussedSave() {
        List<File> fileList = new ArrayList<>();
        List<String> fileFlag = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for(int i=0;i<(picList.size()-1);i++){
            if(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getId() == null){
                fileFlag.add("0");
                if(TextUtils.isEmpty(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl()))
                    urls.add("");
                else
                    urls.add(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl());
                ids.add("");
                names.add(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getName());
            }else{
                if(-1 == ((OtherInfo.PicBean)picList.get(i).get("picInfo")).getId()){
                    File file = FileUtils.getFileByPath(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl());
                    File newFile = CompressHelper.getDefault(getActivity()).compressToFile(file);
                    fileList.add(newFile);
                    fileFlag.add("1");
                    urls.add("");
                    ids.add("");
                    names.add(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getName());
                }else{
                    if(FileUtils.isFileExists(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl())){
                        File file = FileUtils.getFileByPath(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl());
                        File newFile = CompressHelper.getDefault(getActivity()).compressToFile(file);
                        fileList.add(newFile);
                        fileFlag.add("1");
                        urls.add("");
                        ids.add(""+((OtherInfo.PicBean)picList.get(i).get("picInfo")).getId());
                        names.add(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getName());
                    }else{
                        fileFlag.add("0");
                        if(TextUtils.isEmpty(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl()))
                            urls.add("");
                        else
                            urls.add(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getUrl());
                        ids.add(""+((OtherInfo.PicBean)picList.get(i).get("picInfo")).getId());
                        names.add(((OtherInfo.PicBean)picList.get(i).get("picInfo")).getName());
                    }
                }
            }
        }

        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.edit_other_discussed_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("discuss.beginTime",tv_beginTime.getText().toString())
                .params("discuss.endTime",tv_endTime.getText().toString())
                .params("discuss.n1",tv_n1.getText().toString())
                .params("discuss.n2",tv_n2.getText().toString())
                .params("discuss.n3",tv_n3.getText().toString())
                .params("discuss.n4",tv_n4.getText().toString())
                .params("discuss.n5",tv_n5.getText().toString())
                .params("discuss.id",otherInfo.getDiscussBean() == null ? "" : ""+otherInfo.getDiscussBean().getId())
                .params("discuss.punish.id",mParam1)
                .params("oid",mParam1)
                .params("editFlag","更新")
                .addFileParams("upload",fileList)
                .addUrlParams("fileFlag",fileFlag)
                .addUrlParams("urls",urls)
                .addUrlParams("ids",ids)
                .addUrlParams("names",names)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(getActivity());
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                ToastUtils.showLongToast(getActivity(),jsonObject.getString("data"));
                            }
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

    private void initListener() {
        tv_beginTime.setOnClickListener(this);
        iv_beginTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        iv_endTime.setOnClickListener(this);
        tv_n1.setOnClickListener(this);
        iv_n1.setOnClickListener(this);
        tv_n2.setOnClickListener(this);
        iv_n2.setOnClickListener(this);
        tv_n3.setOnClickListener(this);
        iv_n3.setOnClickListener(this);
        tv_n4.setOnClickListener(this);
        iv_n4.setOnClickListener(this);
        tv_n5.setOnClickListener(this);
        iv_n5.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //打开预览
                /*Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItemList );
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, i);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);*/
                //打开选择图片
                picBeans = (OtherInfo.PicBean)picList.get(i).get("picInfo");
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), OtherAddPicActivity.class);
                intent.putExtra("data", (OtherInfo.PicBean)picList.get(i).get("picInfo"));
                startActivity(intent);
                return true;
            }
        });
    }

    private void initData() {
        otherInfo = MyApplication.getInstance().getOtherInfo();
        if(otherInfo.getDiscussBean() != null){
            tv_beginTime.setText(otherInfo.getDiscussBean().getBeginTime());
            tv_endTime.setText(otherInfo.getDiscussBean().getEndTime());
            tv_n1.setText(otherInfo.getDiscussBean().getN1());
            tv_n2.setText(otherInfo.getDiscussBean().getN2());
            tv_n3.setText(otherInfo.getDiscussBean().getN3());
            tv_n4.setText(otherInfo.getDiscussBean().getN4());
            tv_n5.setText(otherInfo.getDiscussBean().getN5());
        }

        picBeanList = otherInfo.getPicList();
        for(int i=0;i<picBeanList.size();i++){
            ImageItem imageItem = new ImageItem();
            imageItem.path = MyApplication.getInstance().getBsaeUrl()+picBeanList.get(i).getUrl();
            imageItemList.add(imageItem);
        }

        Map<String,Object> map = null;
        for(int i=0;i<picBeanList.size();i++){
            map = new HashMap<>();
            map.put("picInfo",picBeanList.get(i));
            map.put("type", Constants.NUMFIRST);
            picList.add(map);
        }
        map = new HashMap<>();
        map.put("picInfo",null);
        map.put("type", Constants.NUMSECONd);
        picList.add(map);

        adapter = new OtherPicGridAdapter(getActivity(),picList);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_beginTime:
                showAllTimePickerDialog(tv_beginTime);
                break;
            case R.id.iv_beginTime:
                showAllTimePickerDialog(tv_beginTime);
                break;
            case R.id.tv_endTime:
                showAllTimePickerDialog(tv_endTime);
                break;
            case R.id.iv_endTime:
                showAllTimePickerDialog(tv_endTime);
                break;
            case R.id.tv_n1:
                showDialog(tv_n1,1);
                break;
            case R.id.iv_n1:
                showDialog(tv_n1,1);
                break;
            case R.id.tv_n2:
                showDialog(tv_n2,1);
                break;
            case R.id.iv_n2:
                showDialog(tv_n2,1);
                break;
            case R.id.tv_n3:
                showDialog(tv_n3,1);
                break;
            case R.id.iv_n3:
                showDialog(tv_n3,1);
                break;
            case R.id.tv_n4:
                showDialog(tv_n4,0);
                break;
            case R.id.iv_n4:
                showDialog(tv_n4,0);
                break;
            case R.id.tv_n5:
                showDialog(tv_n5,1);
                break;
            case R.id.iv_n5:
                showDialog(tv_n5,1);
                break;
        }
    }



    private void showAllTimePickerDialog(final TextView tv){
        new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                tv.setText(format.format(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFEFEFEF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .isCenterLabel(false)
                .isDialog(false)//是否显示为对话框样式
                .build().show();
    }

    private void showDialog(final TextView tv,int flag){
        List<OtherInfo.RowsBean> rowsBeanList = otherInfo.getRows();
        final String[] names = new String[rowsBeanList.size()];
        for(int i=0;i<rowsBeanList.size();i++){
            if(1 == flag)
                names[i] = rowsBeanList.get(i).getCode().trim();
            else
                names[i] = rowsBeanList.get(i).getNames().trim();
        }
        String[] n1s = tv.getText().toString().trim().split(",");
        final boolean selected[] = new boolean[rowsBeanList.size()];
        for(int i=0;i<n1s.length;i++){
            for(int j = 0;j<names.length;j++){
                if(names[j].trim().equals(n1s[i].trim())){
                    selected[j] = true;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择信息");
        builder.setMultiChoiceItems(names, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });
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
                String str = "";
                for (int j=0;j<selected.length;j++){
                    if(selected[j]){
                        str += ", "+names[j];
                    }
                }
                tv.setText(str.replaceFirst(", ",""));
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if(data != null && requestCode == REQUEST_CODE_SELECT){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    if(picBeans != null){
                        System.out.println("path = "+images.get(0).path);
                        picBeans.setUrl(images.get(0).path);
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setEventId(MessageEvent.EVENT_OTHER_PHONE_EDIT_SUCCESS);
                        messageEvent.setObj(picBeans);
                        EventBus.getDefault().post(messageEvent);
                    }
                    //Glide.with(this).load(imageItem.path).error(R.mipmap.default_image).into(iv_image);
                }
            }
            //预览图片返回
            /*if (data != null && requestCode == REQUEST_CODE_PREVIEW) {

            }*/
        }
    }


}

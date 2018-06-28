package com.android.hcbd.overruntransportation.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.TpsListInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.util.DialogUtils;
import com.android.hcbd.overruntransportation.util.HttpUrlUtils;
import com.android.hcbd.overruntransportation.util.LogUtils;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 超限处罚详情
 */
public class TpsInfoFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TpsListInfo.DataInfo tpsListInfo;
    private String mParam2;

    @BindView(R.id.tv_car_noTitle)
    TextView tv_car_noTitle;
    @BindView(R.id.rl_site_info)
    RelativeLayout rl_site_info;
    @BindView(R.id.rl_drive_info)
    RelativeLayout rl_drive_info;
    @BindView(R.id.rl_cargo_info)
    RelativeLayout rl_cargo_info;
    @BindView(R.id.rl_car_info)
    RelativeLayout rl_car_info;
    @BindView(R.id.rl_law_info)
    RelativeLayout rl_law_info;
    @BindView(R.id.iv_site_info)
    ImageView iv_site_info;
    @BindView(R.id.iv_drive_info)
    ImageView iv_drive_info;
    @BindView(R.id.iv_cargo_info)
    ImageView iv_cargo_info;
    @BindView(R.id.iv_car_info)
    ImageView iv_car_info;
    @BindView(R.id.iv_law_info)
    ImageView iv_law_info;
    @BindView(R.id.ll_site_info)
    LinearLayout ll_site_info;
    @BindView(R.id.ll_drive_info)
    LinearLayout ll_drive_info;
    @BindView(R.id.ll_cargo_info)
    LinearLayout ll_cargo_info;
    @BindView(R.id.ll_car_info)
    LinearLayout ll_car_info;
    @BindView(R.id.ll_law_info)
    LinearLayout ll_law_info;
    @BindView(R.id.cb_adopt)
    CheckBox cb_adopt;
    @BindView(R.id.cb_reject)
    CheckBox cb_reject;
    @BindView(R.id.et_reject_Reason)
    EditText et_reject_Reason;

    @BindView(R.id.tv_site_checkPlace)
    TextView tv_site_checkPlace;
    @BindView(R.id.tv_driver_name)
    TextView tv_driver_name;
    @BindView(R.id.tv_driver_ids)
    TextView tv_driver_ids;
    @BindView(R.id.tv_driver_sex)
    TextView tv_driver_sex;
    @BindView(R.id.tv_driver_phone)
    TextView tv_driver_phone;
    @BindView(R.id.tv_driver_addr)
    TextView tv_driver_addr;
    @BindView(R.id.tv_driver_driverAddr)
    TextView tv_driver_driverAddr;
    @BindView(R.id.tv_driver_no1)
    TextView tv_driver_no1;
    @BindView(R.id.tv_driver_certId)
    TextView tv_driver_certId;
    @BindView(R.id.tv_driver_post)
    TextView tv_driver_post;
    @BindView(R.id.tv_driver_caseRel)
    TextView tv_driver_caseRel;
    @BindView(R.id.tv_driver_job)
    TextView tv_driver_job;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_goods_goods)
    TextView tv_goods_goods;
    @BindView(R.id.tv_goods_startPlace)
    TextView tv_goods_startPlace;
    @BindView(R.id.tv_goods_endPlace)
    TextView tv_goods_endPlace;
    @BindView(R.id.tv_overrunNo)
    TextView tv_overrunNo;
    @BindView(R.id.tv_car_no1)
    TextView tv_car_no1;
    @BindView(R.id.tv_car_no2)
    TextView tv_car_no2;
    @BindView(R.id.tv_car_no3)
    TextView tv_car_no3;
    @BindView(R.id.tv_car_owner)
    TextView tv_car_owner;
    @BindView(R.id.tv_car_addr)
    TextView tv_car_addr;
    @BindView(R.id.tv_car_phone)
    TextView tv_car_phone;
    @BindView(R.id.tv_car_lawOper)
    TextView tv_car_lawOper;
    @BindView(R.id.tv_car_lawName)
    TextView tv_car_lawName;
    @BindView(R.id.tv_car_lawPhone)
    TextView tv_car_lawPhone;
    @BindView(R.id.tv_car_lawAddr)
    TextView tv_car_lawAddr;
    @BindView(R.id.tv_car_lawPost)
    TextView tv_car_lawPost;
    @BindView(R.id.tv_o1_id)
    TextView tv_o1_id;
    @BindView(R.id.tv_o2_id)
    TextView tv_o2_id;
    @BindView(R.id.tv_k1_id)
    TextView tv_k1_id;
    @BindView(R.id.tv_k2_id)
    TextView tv_k2_id;
    @BindView(R.id.tv_carType_remark)
    TextView tv_carType_remark;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_length)
    TextView tv_length;
    @BindView(R.id.tv_width)
    TextView tv_width;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_source)
    TextView tv_source;
    @BindView(R.id.tv_amt)
    TextView tv_amt;
    @BindView(R.id.tv_isCompany)
    TextView tv_isCompany;
    @BindView(R.id.tv_stateName)
    TextView tv_stateName;
    @BindView(R.id.tv_proof)
    TextView tv_proof;
    @BindView(R.id.tv_duty_time)
    TextView tv_duty_time;

    private DialogUtils dialogUtils = new DialogUtils();

    public TpsInfoFragment() {
        // Required empty public constructor
    }

    public static TpsInfoFragment newInstance(TpsListInfo.DataInfo param1, String param2) {
        TpsInfoFragment fragment = new TpsInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tpsListInfo = (TpsListInfo.DataInfo) getArguments().getSerializable(ARG_PARAM1);
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
        View view = inflater.inflate(R.layout.fragment_tps_info, container, false);
        ButterKnife.bind(this,view);

        initData();
        initListener();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_TPS_CHECK_SAVE:
                saveCheck();
                break;
        }
    }

    private void saveCheck() {
        String state = "";
        if(cb_adopt.isChecked()) {
            state = "1";
        }else{
            state = "4";
            if(TextUtils.isEmpty(et_reject_Reason.getEditableText().toString())){
                Toast.makeText(getActivity(),"请输入驳回理由！",Toast.LENGTH_LONG).show();
                return;
            }
        }
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.save_tps_check_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("punish.checkState", state)
                .params("punish.reason", et_reject_Reason.getEditableText().toString())
                .params("oid",String.valueOf(tpsListInfo.getId()))
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialogUtils.showLoading(getActivity());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                ToastUtils.showLongToast(getActivity(),jsonObject.getString("data"));
                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.setEventId(MessageEvent.EVENT_TPS_CHECK_SAVE_SUCCESS);
                                EventBus.getDefault().post(messageEvent);
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
                    public void onError(com.lzy.okgo.model.Response<String> response) {
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
        rl_site_info.setOnClickListener(this);
        rl_drive_info.setOnClickListener(this);
        rl_cargo_info.setOnClickListener(this);
        rl_car_info.setOnClickListener(this);
        rl_law_info.setOnClickListener(this);
        cb_adopt.setOnClickListener(this);
        cb_reject.setOnClickListener(this);
    }

    private void initData() {
        ll_site_info.setVisibility(View.VISIBLE);
        ll_drive_info.setVisibility(View.VISIBLE);
        ll_cargo_info.setVisibility(View.VISIBLE);
        ll_car_info.setVisibility(View.VISIBLE);
        ll_law_info.setVisibility(View.VISIBLE);
        cb_adopt.setChecked(true);
        iv_site_info.setSelected(true);
        iv_drive_info.setSelected(true);
        iv_cargo_info.setSelected(true);
        iv_car_info.setSelected(true);
        iv_law_info.setSelected(true);
        tv_car_noTitle.setText(tpsListInfo.getCode());
        tv_site_checkPlace.setText(tpsListInfo.getSite().getCheckPlace());
        tv_driver_name.setText(tpsListInfo.getDriver().getName());
        tv_driver_ids.setText(tpsListInfo.getDriver().getIds());
        tv_driver_sex.setText(tpsListInfo.getDriver().getSex());
        tv_driver_phone.setText(tpsListInfo.getDriver().getPhone());
        tv_driver_addr.setText(tpsListInfo.getDriver().getAddr());
        tv_driver_driverAddr.setText(tpsListInfo.getDriver().getDriverAddr());
        tv_driver_no1.setText(tpsListInfo.getDriver().getNo1());
        tv_driver_certId.setText(tpsListInfo.getDriver().getCertId());
        tv_driver_post.setText(tpsListInfo.getDriver().getPost());
        tv_driver_caseRel.setText(tpsListInfo.getDriver().getCaseRel());
        tv_driver_job.setText(tpsListInfo.getDriver().getJob());
        tv_goods_name.setText(tpsListInfo.getTpsGoods().getName());
        tv_goods_goods.setText(tpsListInfo.getTpsGoods().getGoods());
        tv_goods_startPlace.setText(tpsListInfo.getTpsGoods().getStartPlace());
        tv_goods_endPlace.setText(tpsListInfo.getTpsGoods().getEndPlace());
        tv_overrunNo.setText(tpsListInfo.getOverrunNo());
        tv_car_no1.setText(tpsListInfo.getCar().getNo1());
        tv_car_no2.setText(tpsListInfo.getCar().getNo2());
        tv_car_no3.setText(tpsListInfo.getCar().getNo3());
        tv_car_owner.setText(tpsListInfo.getCar().getOwner());
        tv_car_addr.setText(tpsListInfo.getCar().getAddr());
        tv_car_phone.setText(tpsListInfo.getCar().getPhone());
        tv_car_lawOper.setText(tpsListInfo.getCar().getLawOper());
        tv_car_lawName.setText(tpsListInfo.getCar().getLawName());
        tv_car_lawPhone.setText(tpsListInfo.getCar().getLawPhone());
        tv_car_lawAddr.setText(tpsListInfo.getCar().getLawAddr());
        tv_car_lawPost.setText(tpsListInfo.getCar().getLawPost());
        tv_o1_id.setText(tpsListInfo.getO1().getName());
        tv_o2_id.setText(tpsListInfo.getO2().getName());
        tv_k1_id.setText(tpsListInfo.getK1().getName());
        tv_k2_id.setText(tpsListInfo.getK2().getName());
        tv_carType_remark.setText(tpsListInfo.getCarType().getType());
        tv_weight.setText(tpsListInfo.getWeight()+"KG");
        tv_length.setText(tpsListInfo.getLength()+"M");
        tv_width.setText(tpsListInfo.getWidth()+"M");
        tv_height.setText(tpsListInfo.getHeight()+"M");
        tv_source.setText(tpsListInfo.getSource());
        tv_amt.setText(tpsListInfo.getAmt()+"元");
        tv_isCompany.setText(tpsListInfo.getIsCompany());
        tv_stateName.setText(tpsListInfo.getStateName());
        tv_proof.setText(tpsListInfo.getProofName());
        tv_duty_time.setText(tpsListInfo.getDutyTime());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_site_info:
                if(iv_site_info.isSelected()) {
                    iv_site_info.setSelected(false);
                    ll_site_info.setVisibility(View.GONE);
                }else{
                    iv_site_info.setSelected(true);
                    ll_site_info.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_drive_info:
                if(iv_drive_info.isSelected()){
                    iv_drive_info.setSelected(false);
                    ll_drive_info.setVisibility(View.GONE);
                }else{
                    iv_drive_info.setSelected(true);
                    ll_drive_info.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_cargo_info:
                if(iv_cargo_info.isSelected()){
                    iv_cargo_info.setSelected(false);
                    ll_cargo_info.setVisibility(View.GONE);
                }else{
                    iv_cargo_info.setSelected(true);
                    ll_cargo_info.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_car_info:
                if(iv_car_info.isSelected()){
                    iv_car_info.setSelected(false);
                    ll_car_info.setVisibility(View.GONE);
                }else{
                    iv_car_info.setSelected(true);
                    ll_car_info.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_law_info:
                if(iv_law_info.isSelected()){
                    iv_law_info.setSelected(false);
                    ll_law_info.setVisibility(View.GONE);
                }else{
                    iv_law_info.setSelected(true);
                    ll_law_info.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cb_adopt:
                cb_adopt.setChecked(true);
                cb_reject.setChecked(false);
                break;
            case R.id.cb_reject:
                cb_adopt.setChecked(false);
                cb_reject.setChecked(true);
                break;
        }
    }
}

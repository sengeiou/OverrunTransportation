package com.android.hcbd.overruntransportation.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.LoginInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.activity.AboutActivity;
import com.android.hcbd.overruntransportation.ui.activity.EditPwdActivity;
import com.android.hcbd.overruntransportation.ui.activity.FeedbackActivity;
import com.android.hcbd.overruntransportation.ui.activity.IpAddressActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 设置
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    @BindView(R.id.tv_userName)
    TextView tv_userName;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_banNo)
    TextView tv_banNo;
    @BindView(R.id.tv_credit)
    TextView tv_credit;
    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_birthday)
    TextView tv_birthday;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_mobilephone)
    TextView tv_mobilephone;
    @BindView(R.id.tv_notice)
    TextView tv_notice;
    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.rl_update_password)
    RelativeLayout rl_update_password;
    @BindView(R.id.tv_sign_out)
    TextView tv_sign_out;
    @BindView(R.id.rl_about)
    RelativeLayout rl_about;
    @BindView(R.id.rl_feedback)
    RelativeLayout rl_feedback;
    @BindView(R.id.rl_ip_address)
    RelativeLayout rl_ip_address;

    Unbinder unbinder;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();
        initListener();
        return view;
    }

    private void initListener() {
        rl_update_password.setOnClickListener(this);
        tv_sign_out.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_ip_address.setOnClickListener(this);
    }

    private void initData() {
        LoginInfo.UserInfo userInfo = MyApplication.getInstance().getLoginInfo().getUserInfo();
        tv_userName.setText(userInfo.getUserName());
        tv_name.setText(userInfo.getName());
        tv_banNo.setText(userInfo.getNo());
        String[] arrays = userInfo.getUnit().split("-");
        if (arrays.length == 2) {
            tv_credit.setText(arrays[1]);
            switch (Integer.parseInt(arrays[0])) {
                case 1:
                    tv_post.setText("勘验员");
                    break;
                case 2:
                    tv_post.setText("大队长");
                    break;
                case 3:
                    tv_post.setText("支队长");
                    break;
                case 4:
                    tv_post.setText("副支队长");
                    break;
                case 5:
                    tv_post.setText("路政员");
                    break;
                default:
                    tv_post.setText("勘验员");
                    break;
            }
        }
        tv_email.setText(userInfo.getEmail());
        tv_birthday.setText(userInfo.getBirthday());
        if (userInfo.getSex().equals("1"))
            tv_sex.setText("男");
        else
            tv_sex.setText("女");
        tv_phone.setText(userInfo.getPhone());
        tv_mobilephone.setText(userInfo.getMobilephone());
        if (userInfo.getNotice().equals("1"))
            tv_notice.setText("是");
        else
            tv_notice.setText("否");
        if (userInfo.getState().equals("1")) {
            tv_state.setText("启用");
        } else if (userInfo.getState().equals("2")) {
            tv_state.setText("冻结");
        } else {
            tv_state.setText("关闭");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_update_password:
                startActivity(new Intent(getActivity(), EditPwdActivity.class));
                break;
            case R.id.tv_sign_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("退出提示");
                builder.setMessage("您确认退出登录吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setEventId(MessageEvent.EVENT_LOGINOUT);
                        EventBus.getDefault().post(messageEvent);
                    }
                });
                builder.create().show();
                break;
            case R.id.rl_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.rl_ip_address:
                startActivity(new Intent(getActivity(), IpAddressActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

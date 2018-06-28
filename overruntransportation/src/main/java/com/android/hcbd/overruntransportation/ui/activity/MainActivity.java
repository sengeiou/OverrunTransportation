package com.android.hcbd.overruntransportation.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.fragment.HomeFragment;
import com.android.hcbd.overruntransportation.ui.fragment.InfoStatisticsFragment;
import com.android.hcbd.overruntransportation.ui.fragment.SettingFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity{

    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private InfoStatisticsFragment infoStatisticsFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        showLoginDialog();
        rbHome.setChecked(true);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        initListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_LOGINOUT:
                startActivity(new Intent(this,LoginActivity.class));
                finishActivity();
                break;
        }
    }

    private void showLoginDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setContentView(R.layout.dialog_login_success_layout);
        LinearLayout llMain = (LinearLayout) dialog.findViewById(R.id.main);
        TextView tvName = (TextView) dialog.findViewById(R.id.tv_name);
        TextView tvPost = (TextView) dialog.findViewById(R.id.tv_post);
        TextView tvAddress = (TextView) dialog.findViewById(R.id.tv_address);
        TextView tvLastAddress = (TextView) dialog.findViewById(R.id.tv_last_address);
        TextView tvLoginNum = (TextView) dialog.findViewById(R.id.tv_login_num);
        LinearLayout ll_last_address = (LinearLayout) dialog.findViewById(R.id.ll_last_address);
        dialog.setCancelable(true);
        tvName.setText("执法人员："+MyApplication.getInstance().getLoginInfo().getUserInfo().getName());
        String[] arrays = MyApplication.getInstance().getLoginInfo().getUserInfo().getUnit().split("-");
        if(arrays.length == 2){
            switch (Integer.parseInt(arrays[0])){
                case 1:
                    tvPost.setText("职务："+"勘验员");
                    break;
                case 2:
                    tvPost.setText("职务："+"大队长");
                    break;
                case 3:
                    tvPost.setText("职务："+"支队长");
                    break;
                case 4:
                    tvPost.setText("职务："+"副支队长");
                    break;
                case 5:
                    tvPost.setText("职务："+"路政员");
                    break;
                default:
                    tvPost.setText("职务："+"勘验员");
                    break;
            }
        }
        tvAddress.setText("本次登录地点："+(MyApplication.getInstance().getCityInfo() == null ? "未知地点" : (TextUtils.isEmpty(MyApplication.getInstance().getCityInfo().getAddr()) ? "未知地点" : MyApplication.getInstance().getCityInfo().getAddr())));
        if(MyApplication.getInstance().getLoginInfo().getLoginCount().equals("1")) {
            ll_last_address.setVisibility(View.GONE);
            tvLoginNum.setText("首次登录");
        }else{
            ll_last_address.setVisibility(View.VISIBLE);
            tvLastAddress.setText("上次登录地点："+MyApplication.getInstance().getLoginInfo().getLastAddr());
            tvLoginNum.setText("第"+MyApplication.getInstance().getLoginInfo().getLoginCount()+"次登录");
        }
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void initListener() {
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home:
                        setTabSelection(0);
                        break;
                    case R.id.rb_info:
                        for(int m=0;m<MyApplication.getInstance().getLoginInfo().getMenuList().size();m++){
                            if(MyApplication.getInstance().getLoginInfo().getMenuList().get(m).getName().equals("治超数据信息")){
                                MyApplication.getInstance().setPowerStr(MyApplication.getInstance().getLoginInfo().getMenuList().get(m).getCode());
                                break;
                            }
                        }
                        setTabSelection(1);
                        break;
                    case R.id.rb_setting:
                        setTabSelection(2);
                        break;
                }
            }
        });
    }

    private void setTabSelection(int i) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (i){
            case 0:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fl_content,homeFragment);
                }else{
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case 1:
                if(infoStatisticsFragment == null){
                    infoStatisticsFragment = new InfoStatisticsFragment();
                    fragmentTransaction.add(R.id.fl_content,infoStatisticsFragment);
                }else{
                    fragmentTransaction.show(infoStatisticsFragment);
                }
                break;
            case 2:
                if(settingFragment == null){
                    settingFragment = new SettingFragment();
                    fragmentTransaction.add(R.id.fl_content,settingFragment);
                }else{
                    fragmentTransaction.show(settingFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if(homeFragment != null) fragmentTransaction.hide(homeFragment);
        if(infoStatisticsFragment != null) fragmentTransaction.hide(infoStatisticsFragment);
        if(settingFragment != null) fragmentTransaction.hide(settingFragment);
    }

    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        }else{//退出程序
            this.finish();
            System.exit(0);
        }
    }

}

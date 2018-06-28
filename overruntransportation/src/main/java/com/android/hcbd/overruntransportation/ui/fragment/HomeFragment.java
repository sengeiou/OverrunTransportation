package com.android.hcbd.overruntransportation.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.HomeGridVIew1Adapter;
import com.android.hcbd.overruntransportation.adapter.HomeGridVIew2Adapter;
import com.android.hcbd.overruntransportation.entity.LoginInfo;
import com.android.hcbd.overruntransportation.ui.activity.CarTypeListActivity;
import com.android.hcbd.overruntransportation.ui.activity.DefaultPicNameActivity;
import com.android.hcbd.overruntransportation.ui.activity.DefaultProblemActivity;
import com.android.hcbd.overruntransportation.ui.activity.InputStep1Activity;
import com.android.hcbd.overruntransportation.ui.activity.LawInfoListActivity;
import com.android.hcbd.overruntransportation.ui.activity.LegalNameListActivity;
import com.android.hcbd.overruntransportation.ui.activity.TemplateManageActivity;
import com.android.hcbd.overruntransportation.ui.activity.TpsListActivity;
import com.android.hcbd.overruntransportation.ui.activity.WaitCheckActivity;
import com.android.hcbd.overruntransportation.ui.activity.WorkDayListActivity;
import com.android.hcbd.overruntransportation.util.ToastUtils;
import com.android.hcbd.overruntransportation.widget.NoScrollGridView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 首页
 */
public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.gridView1)
    NoScrollGridView gridView1;
    @BindView(R.id.gridView2)
    NoScrollGridView gridView2;

    private List<Integer> bannerList= new ArrayList<>();
    private List<LoginInfo.MenuInfo> menuInfoList  = new ArrayList<>();
    private HomeGridVIew1Adapter homeGridVIew1Adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        initData();
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerViewHolder();
            }
        },bannerList).setPageIndicator(new int[]{R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        homeGridVIew1Adapter = new HomeGridVIew1Adapter(getActivity());
        gridView1.setAdapter(homeGridVIew1Adapter);

        menuInfoList.addAll(MyApplication.getInstance().getLoginInfo().getMenuList());
        for(int i = 0;i<menuInfoList.size();i++){
            if(menuInfoList.get(i).getName().equals("治超数据信息")){
                menuInfoList.remove(i);
            }else if(menuInfoList.get(i).getName().equals("时间计算")){
                menuInfoList.remove(i);
            }
        }
        HomeGridVIew2Adapter homeGridVIew2Adapter = new HomeGridVIew2Adapter(getActivity(), menuInfoList);
        gridView2.setAdapter(homeGridVIew2Adapter);
        initListener();

        return view;
    }


    private void initListener() {
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        MyApplication.getInstance().setEdit(false);
                        String[] strs = MyApplication.getInstance().getLoginInfo().getMenuList().get(0).getCode().split(",");
                        if(strs[0].equals("1"))
                            startActivity(new Intent(getActivity(), InputStep1Activity.class));
                        else
                            ToastUtils.showShortToast(getActivity(),"当前登录的账户没有该权限！");
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), WaitCheckActivity.class));
                        break;
                }
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyApplication.getInstance().setPowerStr(MyApplication.getInstance().getLoginInfo().getMenuList().get(i).getCode());
                switch (menuInfoList.get(i).getName()){
                    case "超限处罚":
                        startActivity(new Intent(getActivity(), TpsListActivity.class));
                        break;
                    case "工作日安排":
                        startActivity(new Intent(getActivity(), WorkDayListActivity.class));
                        break;
                    case "默认问题":
                        startActivity(new Intent(getActivity(), DefaultProblemActivity.class));
                        break;
                    case "法律名称":
                        startActivity(new Intent(getActivity(), LegalNameListActivity.class));
                        break;
                    case "法条信息":
                        startActivity(new Intent(getActivity(), LawInfoListActivity.class));
                        break;
                    case "车型信息":
                        startActivity(new Intent(getActivity(), CarTypeListActivity.class));
                        break;
                    case "模版管理":
                        startActivity(new Intent(getActivity(), TemplateManageActivity.class));
                        break;
                    case "默认图片名称":
                        startActivity(new Intent(getActivity(), DefaultPicNameActivity.class));
                        break;
                }
            }
        });
    }

    private void initData() {
        bannerList.add(R.drawable.image_banner_01);
        bannerList.add(R.drawable.image_banner_02);
        bannerList.add(R.drawable.image_banner_03);

        tvName.setText("执法人员："+ MyApplication.getInstance().getLoginInfo().getUserInfo().getName());
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
    }

    class BannerViewHolder implements Holder<Integer> {

        private ImageView mBannerImageView;

        @Override
        public View createView(Context context) {
            mBannerImageView = new ImageView(getActivity());
            mBannerImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return mBannerImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            Glide.with(getActivity()).load(data).into(mBannerImageView);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}

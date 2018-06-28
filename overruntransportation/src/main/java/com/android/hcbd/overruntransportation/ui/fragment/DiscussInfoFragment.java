package com.android.hcbd.overruntransportation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.DiscussPicGridAdapter;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * 集体讨论详情
 */
public class DiscussInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_n1)
    TextView tv_n1;
    @BindView(R.id.tv_n2)
    TextView tv_n2;
    @BindView(R.id.tv_n3)
    TextView tv_n3;
    @BindView(R.id.tv_n4)
    TextView tv_n4;
    @BindView(R.id.tv_n5)
    TextView tv_n5;
    @BindView(R.id.gridView)
    GridView gridView;

    private OtherInfo otherInfo;
    private ArrayList<ImageItem> imageItemList = new ArrayList<>();

    public DiscussInfoFragment() {
        // Required empty public constructor
    }

    public static DiscussInfoFragment newInstance(String param1, String param2) {
        DiscussInfoFragment fragment = new DiscussInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_discuss_info, container, false);
        ButterKnife.bind(this,view);

        initData();
        initLintener();
        return view;
    }

    private void initLintener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //打开预览
                Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItemList );
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, i);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
            }
        });
    }

    private void initData() {
        otherInfo = MyApplication.getInstance().getOtherInfo();
        if(otherInfo.getDiscussBean() == null)
            return;
        tv_time.setText(otherInfo.getDiscussBean().getBeginTime()+" 至 "+otherInfo.getDiscussBean().getEndTime());
        tv_n1.setText(otherInfo.getDiscussBean().getN1());
        tv_n2.setText(otherInfo.getDiscussBean().getN2());
        tv_n3.setText(otherInfo.getDiscussBean().getN3());
        tv_n4.setText(otherInfo.getDiscussBean().getN4());
        tv_n5.setText(otherInfo.getDiscussBean().getN5());

        for(int i=0;i<otherInfo.getPicList().size();i++){
            ImageItem imageItem = new ImageItem();
            imageItem.path = MyApplication.getInstance().getBsaeUrl()+otherInfo.getPicList().get(i).getUrl();
            imageItemList.add(imageItem);
        }

        DiscussPicGridAdapter adapter = new DiscussPicGridAdapter(getActivity(),otherInfo.getPicList());
        gridView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {

            }
        }
    }
}

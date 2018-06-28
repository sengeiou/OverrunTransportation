package com.android.hcbd.overruntransportation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.OtherQuestionAdapter;
import com.android.hcbd.overruntransportation.entity.OtherInfo;
import com.android.hcbd.overruntransportation.event.MessageEvent;
import com.android.hcbd.overruntransportation.ui.activity.OtherQuestionAddActivity;
import com.android.hcbd.overruntransportation.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 问题列表
 */
public class QuestionFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    private List<OtherInfo.QuestionBean> questionBeanList ;
    private OtherQuestionAdapter adapter;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this,view);
        btnAdd.setOnClickListener(this);

        questionBeanList = MyApplication.getInstance().getOtherInfo().getQuestionList();
        adapter = new OtherQuestionAdapter(getActivity(),questionBeanList);
        adapter.setOid(mParam1);
        listView.setAdapter(adapter);
        initListener();
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[2].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                    return;
                }
                Intent intent = new Intent(getActivity(),OtherQuestionAddActivity.class);
                intent.putExtra("position",i);
                intent.putExtra("type","edit");
                intent.putExtra("oid",mParam1);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_OTHER_UPDATE_SUCCESS:
                questionBeanList = MyApplication.getInstance().getOtherInfo().getQuestionList();
                adapter.setList(questionBeanList);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                String[] strs = MyApplication.getInstance().getPowerStr().split(",");
                if(strs[0].equals("0")) {
                    ToastUtils.showShortToast(MyApplication.getInstance(), "当前登录的账户没有该权限！");
                    return;
                }
                Intent intent = new Intent(getActivity(), OtherQuestionAddActivity.class);
                intent.putExtra("oid",mParam1);
                intent.putExtra("type","add");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

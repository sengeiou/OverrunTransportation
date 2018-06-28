package com.android.hcbd.overruntransportation.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.hcbd.overruntransportation.MyApplication;
import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.adapter.QuestionInfoListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 问题列表详情
 */
public class QuestionInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.listView)
    ListView listView;

    public QuestionInfoFragment() {
        // Required empty public constructor
    }

    public static QuestionInfoFragment newInstance(String param1, String param2) {
        QuestionInfoFragment fragment = new QuestionInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_question_info, container, false);
        ButterKnife.bind(this,view);

        QuestionInfoListAdapter adapter = new QuestionInfoListAdapter(getActivity(), MyApplication.getInstance().getOtherInfo().getQuestionList());
        listView.setAdapter(adapter);

        return view;
    }


}

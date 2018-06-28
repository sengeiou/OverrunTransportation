package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.OtherInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/23.
 */

public class QuestionInfoListAdapter extends BaseAdapter {
    private Context context;
    private List<OtherInfo.QuestionBean> list;
    public QuestionInfoListAdapter(Context context,List<OtherInfo.QuestionBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_question_info_list_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_question.setText("问"+(i+1)+"："+list.get(i).getQuestion());
        holder.tv_answer.setText("答"+(i+1)+"："+list.get(i).getAnswer());
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_question)
        TextView tv_question;
        @BindView(R.id.tv_answer)
        TextView tv_answer;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

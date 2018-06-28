package com.android.hcbd.overruntransportation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.overruntransportation.R;
import com.android.hcbd.overruntransportation.entity.NewContentInfo;
import com.android.hcbd.overruntransportation.ui.activity.EvdenceMaterialAddActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/3/17.
 */

public class EvdenceMaterialsAdapter extends BaseAdapter{
    private Context context;
    private List<NewContentInfo.ProofEntity> proofEntityList;
    private int poisition;
    public EvdenceMaterialsAdapter(Context context,List<NewContentInfo.ProofEntity> proofEntityList){
        this.context = context;
        this.proofEntityList = proofEntityList;
    }

    public int getPoisition() {
        return poisition;
    }

    public void setPoisition(int poisition) {
        this.poisition = poisition;
    }

    @Override
    public int getCount() {
        return proofEntityList == null ? 0 : proofEntityList.size();
    }

    @Override
    public Object getItem(int i) {
        return proofEntityList == null ? null : proofEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_evidence_materials_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvName.setText(proofEntityList.get(i).getCode());
        holder.tvSpec.setText("规格："+proofEntityList.get(i).getName());
        holder.tvNum.setText("数量："+proofEntityList.get(i).getIds());
        holder.cbSelect.setChecked(proofEntityList.get(i).isChecked());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EvdenceMaterialAddActivity.class);
                intent.putExtra("type","edit");
                intent.putExtra("data",proofEntityList.get(i));
                context.startActivity(intent);
                setPoisition(i);
            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_spec)
        TextView tvSpec;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.cb_select)
        CheckBox cbSelect;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}

package com.fahmuidrug.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fahmuidrug.R;
import com.fahmuidrug.model.DrugRemindVo;

import java.util.ArrayList;
import java.util.List;

public class DrugRemindAdapter extends ArrayAdapter<DrugRemindVo> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private List<DrugRemindVo> mItems;


    private static class ViewHolder {
        TextView Show_name;
        TextView Show_use;
    }

    public DrugRemindAdapter(Context context, int resource, ArrayList<DrugRemindVo> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getDrugname();
        String use = getItem(position).getDruguse();

        DrugRemindVo All = new DrugRemindVo(name,use);
        final View result;
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.Show_name = (TextView) convertView.findViewById(R.id.show_drug_name);
            holder.Show_use = (TextView) convertView.findViewById(R.id.show_drug_use);

            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.Show_name.setText(All.getDrugname());
        holder.Show_use.setText("ต้องทานยาจำนวน "+All.getDruguse()+" เม็ด");


        return convertView;
    }

    public void setItems(List<DrugRemindVo> mItems) {
        this.mItems = mItems;
    }
}
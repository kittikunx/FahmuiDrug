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
import com.fahmuidrug.model.LabVo;

import java.util.ArrayList;
import java.util.Objects;

public class LabAdapter extends ArrayAdapter<LabVo> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;


    private static class ViewHolder {
        TextView txtLab;
        TextView txtDate;
    }

    public LabAdapter(Context context, int resource, ArrayList<LabVo> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String lab = Objects.requireNonNull(getItem(position)).getLab();
        String date = Objects.requireNonNull(getItem(position)).getDate();

        LabVo newObj = new LabVo(date,lab,null);
        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();

            holder.txtLab = (TextView) convertView.findViewById(R.id.lab);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);

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

        holder.txtLab.setText(newObj.getLab());
        holder.txtDate.setText(newObj.getDate());


        return convertView;
    }
}

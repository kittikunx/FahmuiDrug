package com.fahmuidrug.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fahmuidrug.R;
import com.fahmuidrug.model.DrugVo;
import com.fahmuidrug.view.DrugView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DrugAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<DrugVo> listDrug = null;
    private ArrayList<DrugVo> arraylist;

    public DrugAdapter(Context context, List<DrugVo> listDrug) {
        mContext = context;
        this.listDrug = listDrug;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<DrugVo>();
        this.arraylist.addAll(listDrug);
    }

    public class ViewHolder {
        TextView name;
        ImageView image;
    }

    @Override
    public int getCount() {
        return listDrug.size();
    }

    @Override
    public DrugVo getItem(int position) {
        return listDrug.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fragment_drug, null);

            holder.name = (TextView) view.findViewById(R.id.name);
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(listDrug.get(position).getName());
        Glide.with(mContext).load(listDrug.get(position).getImage()).into(holder.image);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, DrugView.class);
                // Pass all data rank
                intent.putExtra("name",(listDrug.get(position).getName()));
                intent.putExtra("howTo",(listDrug.get(position).getHowTo()));
                intent.putExtra("level",(listDrug.get(position).getLevel()));
                intent.putExtra("properties",(listDrug.get(position).getProperties()));
                intent.putExtra("adr",(listDrug.get(position).getAdr()));
                intent.putExtra("contra",(listDrug.get(position).getContra()));
                intent.putExtra("url",(listDrug.get(position).getUrl()));
                intent.putExtra("image",(listDrug.get(position).getImage()));
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listDrug.clear();
        if (charText.length() == 0) {
            listDrug.addAll(arraylist);
        }
        else
        {
            for (DrugVo wp : arraylist)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listDrug.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

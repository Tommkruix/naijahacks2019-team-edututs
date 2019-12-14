package com.codebrainz.campuschat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tommkruix on 12/11/2019.
 */

public class Bg_ListViewAdapter_TourSchool extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<Bg_Model_TourSchool> modellist;
    ArrayList<Bg_Model_TourSchool> arrayList;

    //  constuctor


    public Bg_ListViewAdapter_TourSchool(Context context, List<Bg_Model_TourSchool> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Bg_Model_TourSchool>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv, mDescTv, mTypeTv;
        ImageView mIconTv;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder  = new ViewHolder();
            view = inflater.inflate(R.layout.content_bg_designrow_tour_school, null);

            //locate the views in content_bg_u_designrow_online_test
            holder.mTitleTv = view.findViewById(R.id.mainTitle);
            holder.mDescTv= view.findViewById(R.id.mainDesc);
            holder.mTypeTv = view.findViewById(R.id.mainType);
            holder.mIconTv = view.findViewById(R.id.mainIcon);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        // set the results into textViews and ImageView
        holder.mTitleTv.setText(modellist.get(position).getTitle());
        holder.mDescTv.setText(modellist.get(position).getDesc());
        holder.mTypeTv.setText(modellist.get(position).getType());
        holder.mIconTv.setImageResource(modellist.get(position).getIcon());

        // listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modellist.get(position).getTitle().equals("Unilorin")){
                    Intent intent = new Intent(mContext, TourSchool_Unilorin.class);
                   // intent.putExtra("actionBarTitle", "Unilorin Online Test");
                   // intent.putExtra("actionBarDetails", "Unilorin Online Test Details here...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("The Federal Polytechnic Ede")){
                   // Intent intent = new Intent(mContext, UnilorinQuestion.class);
                  //  mContext.startActivity(intent);
                }
               /* if (modellist.get(position).getTitle().equals("Uniabuja")){
                    Intent intent = new Intent(mContext, UnilorinQuestion.class);
                    intent.putExtra("actionBarTitle", "Uniabuja Online Test");
                    intent.putExtra("actionBarDetails", "Uniabuja Online Test Details here...");
                    mContext.startActivity(intent);
                }*/

            }
        });

        return view;
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }else{
            for (Bg_Model_TourSchool model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}


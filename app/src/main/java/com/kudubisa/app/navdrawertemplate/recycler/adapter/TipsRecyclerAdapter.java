package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.Tips;
import com.kudubisa.app.navdrawertemplate.remote.Common;

import java.util.List;

/**
 * Created by asus on 8/10/18.
 */

public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipsRecyclerAdapter.ViewHolder> {
    private List<Tips> tipsList;
    private Context context;
    private Common common;

    public TipsRecyclerAdapter(List<Tips> tipsList, Context context) {
        this.tipsList = tipsList;
        this.context = context;
        this.common = new Common();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tips_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tips tips = tipsList.get(position);
        holder.title.setText(tips.getTitle());
        holder.body.setText(tips.getBody());
        if (tips.getPicture().equals("null")) {
            holder.picture.setImageDrawable(context.getDrawable(R.drawable.a));
        } else {
            String picUrl = common.getFullUrl(tips.getPicture());
            Glide.with(context).load(picUrl).into(holder.picture);
        }
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView body;
        ImageView picture;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tips_title);
            body = (TextView) itemView.findViewById(R.id.tips_body);
            picture = (ImageView) itemView.findViewById(R.id.tips_image);
        }
    }
}

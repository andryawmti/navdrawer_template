package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.TipsDetailActivity;
import com.kudubisa.app.navdrawertemplate.model.Tips;
import com.kudubisa.app.navdrawertemplate.remote.Common;

import java.util.List;

/**
 * Created by asus on 8/10/18.
 */

public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipsRecyclerAdapter.ViewHolder> {
    private List<Tips> tipsList;
    private Context context;

    public TipsRecyclerAdapter(List<Tips> tipsList, Context context) {
        this.tipsList = tipsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tips_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tips tips = tipsList.get(position);
        holder.title.setText(tips.getTitle());
        holder.body.setText(tips.getBody());

        if (tips.getPicture().equals("null")) {
            holder.picture.setImageDrawable(context.getDrawable(R.drawable.a));
        } else {
            String picUrl = Common.getFullUrl(tips.getPicture());
            Glide.with(context).load(picUrl).into(holder.picture);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TipsDetailActivity.class);
                intent.putExtra("id", tips.getId());
                intent.putExtra("title", tips.getTitle());
                intent.putExtra("content", tips.getBody());
                intent.putExtra("author_id", tips.getAuthorId());
                intent.putExtra("author_name", tips.getAuthorName());
                intent.putExtra("photo", tips.getPicture());
                intent.putExtra("created_at", tips.getCreatedAt());
                intent.putExtra("updated_at", tips.getUpdatedAt());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView body;
        ImageView picture;
        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tips_title);
            body = itemView.findViewById(R.id.tips_body);
            picture = itemView.findViewById(R.id.tips_image);
        }
    }
}

package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.MenuDetailActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.Ingredient;
import com.kudubisa.app.navdrawertemplate.model.Menu;
import com.kudubisa.app.navdrawertemplate.remote.Common;

import java.util.List;

/**
 * Created by asus on 7/1/18.
 */

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {
    private List<Menu> menuList;
    private Context context;
    private Common common;

    public MenuRecyclerAdapter(List<Menu> menuList) {
        this.menuList = menuList;
        common = new Common();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Menu menu = menuList.get(position);
        if (menu.getPicture() != "null") {
            String pictureUrl = common.getFullUrl(menu.getPicture());
            Glide.with(context).load(pictureUrl).into(holder.avatar);
        } else {
            holder.avatar.setImageDrawable(context.getDrawable(R.drawable.vegan_tofu_tacos));
        }
        holder.title.setText(menu.getTitle());
        holder.desc.setText(menu.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuDetailActivity.class);
                intent.putExtra("title", menu.getTitle());
                intent.putExtra("desc", menu.getDescription());
                intent.putExtra("picture", menu.getPicture());
                intent.putExtra("cooking", menu.getCookingInstruction());
                intent.putExtra("calorie", menu.getCalorie());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView title, desc;
        private ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.list_image);
            title = (TextView) itemView.findViewById(R.id.list_title);
            desc = (TextView) itemView.findViewById(R.id.list_desc);
        }
    }
}

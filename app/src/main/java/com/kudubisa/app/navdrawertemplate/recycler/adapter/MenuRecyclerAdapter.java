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

    public MenuRecyclerAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_list, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView title, desc, calorie;
        private ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.list_image);
            title = itemView.findViewById(R.id.list_title);
            desc = itemView.findViewById(R.id.list_desc);
            calorie = itemView.findViewById(R.id.list_calorie);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Menu menu = menuList.get(position);
        if (!menu.getPhoto().equals("null")) {
            String pictureUrl = Common.getFullUrl(menu.getPhoto());
            Glide.with(context).load(pictureUrl).into(holder.avatar);
        } else {
            holder.avatar.setImageDrawable(context.getDrawable(R.drawable.vegan_tofu_tacos));
        }
        holder.title.setText(menu.getName());
        holder.desc.setText(menu.getDescription());
        holder.calorie.setText(menu.getCalorie() + " KKal");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuDetailActivity.class);
                intent.putExtra("menu_id", menu.getId());
                intent.putExtra("added_by", menu.getAddedBy());
                intent.putExtra("name", menu.getName());
                intent.putExtra("photo", menu.getPhoto());
                intent.putExtra("description", menu.getDescription());
                intent.putExtra("calorie", menu.getCalorie());
                intent.putExtra("carbohydrate", menu.getCarbohydrate());
                intent.putExtra("protein", menu.getProtein());
                intent.putExtra("fat", menu.getFat());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}

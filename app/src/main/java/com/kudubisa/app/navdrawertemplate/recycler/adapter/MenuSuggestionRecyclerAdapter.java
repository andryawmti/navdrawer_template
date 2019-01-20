package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.MenuDetailActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.Menu;
import com.kudubisa.app.navdrawertemplate.remote.Common;

import java.util.List;

/**
 * Created by asus on 8/18/18.
 */

public class MenuSuggestionRecyclerAdapter extends RecyclerView.Adapter<MenuSuggestionRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Menu> menuList;

    public MenuSuggestionRecyclerAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_suggestion_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Menu menu = menuList.get(position);
        if (!menu.getPhoto().equals("null")) {
            String pictureUrl = Common.getFullUrl(menu.getPhoto());
            Glide.with(context).load(pictureUrl).into(holder.menuPicture);
        } else {
            holder.menuPicture.setImageDrawable(context.getDrawable(R.drawable.vegan_tofu_tacos));
        }

        holder.tvMenuSuggestionTitle.setText(menu.getName());
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

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView menuPicture;
        TextView tvMenuSuggestion, tvMenuSuggestionTitle;

        ViewHolder(View itemView) {
            super(itemView);
            menuPicture = itemView.findViewById(R.id.menuSuggestionPicture);
            tvMenuSuggestion = itemView.findViewById(R.id.tvMenuSuggestion);
            tvMenuSuggestionTitle = itemView.findViewById(R.id.tvMenuSuggestionTitle);
        }
    }

}

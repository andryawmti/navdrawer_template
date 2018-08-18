package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kudubisa.app.navdrawertemplate.R;

/**
 * Created by asus on 8/18/18.
 */

public class MenuSuggestionRecyclerAdapter extends RecyclerView.Adapter<MenuSuggestionRecyclerAdapter.ViewHolder> {
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_suggestion_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView menuPicture;
        TextView tvMenuSuggestion, tvMenuSuggestionTitle;

        ViewHolder(View itemView) {
            super(itemView);
            menuPicture = (ImageView) itemView.findViewById(R.id.menuSuggestionPicture);
            tvMenuSuggestion = (TextView) itemView.findViewById(R.id.tvMenuSuggestion);
            tvMenuSuggestionTitle = (TextView) itemView.findViewById(R.id.tvMenuSuggestionTitle);
        }
    }

}

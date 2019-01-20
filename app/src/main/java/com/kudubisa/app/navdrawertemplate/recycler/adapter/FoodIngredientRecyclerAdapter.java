package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.FoodIngredient;

import java.util.List;

public class FoodIngredientRecyclerAdapter extends RecyclerView.Adapter<FoodIngredientRecyclerAdapter.ViewHolder>{

    List<FoodIngredient> foodIngredientList;

    public FoodIngredientRecyclerAdapter(List<FoodIngredient> foodIngredientList) {
        this.foodIngredientList = foodIngredientList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_list, parent, false);

        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, weight, category, gram;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredient_name);
            category = itemView.findViewById(R.id.ingredient_category);
            weight = itemView.findViewById(R.id.ingredient_weight);
            gram = itemView.findViewById(R.id.gram);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(foodIngredientList.get(position).getName());
        holder.category.setText(foodIngredientList.get(position).getCategoryName());
        holder.weight.setText(foodIngredientList.get(position).getWeight());
        holder.gram.setText("gr");
    }

    @Override
    public int getItemCount() {
        return foodIngredientList.size();
    }
}

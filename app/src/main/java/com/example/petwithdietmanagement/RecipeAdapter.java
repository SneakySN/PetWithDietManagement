package com.example.petwithdietmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petwithdietmanagement.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private List<Recipe> filteredList;

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.filteredList = new ArrayList<>();
    }

    public List<Recipe> getFilteredList() {
        return filteredList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = filteredList.get(position);
        holder.recipeNameTextView.setText(recipe.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(List<Recipe> filteredRecipes) {
        filteredList.clear();
        filteredList.addAll(filteredRecipes);
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}

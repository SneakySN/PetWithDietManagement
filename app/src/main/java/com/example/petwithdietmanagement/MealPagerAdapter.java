package com.example.petwithdietmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealPagerAdapter extends RecyclerView.Adapter<MealPagerAdapter.MealViewHolder> {

    private int[] layouts;

    public MealPagerAdapter(int[] layouts) {
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        view.setTag("page_" + viewType);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        // 필요 시 뷰를 바인딩합니다.
    }

    @Override
    public int getItemCount() {
        return layouts.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        MealViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.example.petwithdietmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.database.UserDBManager;

import java.util.List;

public class SlideUpAdapter extends RecyclerView.Adapter<SlideUpAdapter.ViewHolder> {
    private List<Item> items;
    private int selectedPosition = RecyclerView.NO_POSITION; // 현재 선택된 아이템 위치를 저장할 변수
    private ImageView targetImageView;
    private String itemType;
    private int defaultImageResId;
    private UserDBManager userDBManager;
    private String userId;

    public SlideUpAdapter(List<Item> items, ImageView targetImageView, String itemType, int defaultImageResId, UserDBManager userDBManager, String userId) {
        this.items = items;
        this.targetImageView = targetImageView;
        this.itemType = itemType;
        this.defaultImageResId = defaultImageResId;
        this.userDBManager = userDBManager;
        this.userId = userId;

        // equipped 값이 1인 아이템을 찾아 selectedPosition으로 설정
        List<Integer> equippedItemIds = userDBManager.getEquippedItemIds(userId);
        for (int i = 0; i < items.size(); i++) {
            if (equippedItemIds.contains(items.get(i).getItemId())) {
                selectedPosition = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.decorating_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(item.getItemImage(), "drawable", holder.itemView.getContext().getPackageName());

        // Glide를 사용하여 리소스 ID를 이미지로 로드
        Glide.with(holder.itemView.getContext()).load(imageResId).into(holder.itemImage);

        // 아이템 선택 상태 설정
        holder.itemView.setSelected(selectedPosition == position);

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (selectedPosition == currentPosition) {
                    // 이미 선택된 아이템을 다시 클릭한 경우 선택 해제
                    selectedPosition = RecyclerView.NO_POSITION;
                    if (defaultImageResId == 0) {
                        targetImageView.setImageDrawable(null); // 기본값은 아무것도 없는 상태로 설정
                    } else {
                        targetImageView.setImageResource(defaultImageResId); // 기본 이미지로 복구
                    }
                    notifyItemChanged(currentPosition);

                    // DB에서 equipped 상태 업데이트: 현재 아이템의 equipped 값을 0으로 설정
                    userDBManager.updateEquipped(userId, item.getItemId(), true); // 현재 아이템의 equipped 값을 0으로 설정
                } else {
                    // 다른 아이템을 클릭한 경우
                    int previousSelectedPosition = selectedPosition;
                    selectedPosition = currentPosition;
                    // 이전에 선택된 아이템의 선택 상태를 갱신
                    notifyItemChanged(previousSelectedPosition);
                    // 새로 선택된 아이템의 선택 상태를 갱신
                    notifyItemChanged(selectedPosition);

                    // 선택된 아이템의 이미지를 targetImageView에 설정
                    int realImageResId = holder.itemView.getContext().getResources().getIdentifier(item.getItemRealImage(), "drawable", holder.itemView.getContext().getPackageName());
                    int finalImageResId = itemType.equals("Hat") ? realImageResId : imageResId;
                    Glide.with(targetImageView.getContext()).load(finalImageResId).into(targetImageView);

                    // DB에서 equipped 상태 업데이트: 새로운 아이템의 equipped 값을 1로 설정
                    userDBManager.updateEquipped(userId, item.getItemId(), false); // 새로운 아이템의 equipped 값을 1로 설정
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }
}
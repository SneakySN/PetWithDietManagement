package com.example.petwithdietmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.database.ItemDBManager;
import com.example.petwithdietmanagement.database.UserDBManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SlideUpFragment extends BottomSheetDialogFragment {
    private TabHost tabHost;
    private RecyclerView recyclerHat, recyclerBackground, recyclerBadge, recyclerCarpet;
    private List<Item> hatItems, backgroundItems, badgeItems, carpetItems;
    private ImageView hatImageView, backgroundImageView, badgeImageView, carpetImageView;
    private UserDBManager userDBManager;
    private ItemDBManager itemDBManager;
    private String userId;

    public SlideUpFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_up, container, false);

        // Initialize TabHost
        tabHost = view.findViewById(android.R.id.tabhost);
        tabHost.setup();

        // Setup tabs
        TabHost.TabSpec spec = tabHost.newTabSpec("Hat");
        spec.setContent(R.id.tabHat);
        spec.setIndicator("모자");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Background");
        spec.setContent(R.id.tabBackground);
        spec.setIndicator("배경");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Badge");
        spec.setContent(R.id.tabBadge);
        spec.setIndicator("명찰");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Carpet");
        spec.setContent(R.id.tabCarpet);
        spec.setIndicator("카펫");
        tabHost.addTab(spec);

        // Initialize RecyclerViews
        recyclerHat = view.findViewById(R.id.recyclerHat);
        recyclerBackground = view.findViewById(R.id.recyclerBackground);
        recyclerBadge = view.findViewById(R.id.recyclerBadge);
        recyclerCarpet = view.findViewById(R.id.recyclerCarpet);

        // Initialize ImageViews (you may need to pass these from the activity)
        hatImageView = getActivity().findViewById(R.id.hat_image);
        backgroundImageView = getActivity().findViewById(R.id.background_image);
        badgeImageView = getActivity().findViewById(R.id.badge);
        carpetImageView = getActivity().findViewById(R.id.carpet);

        // Initialize database managers
        itemDBManager = new ItemDBManager(getContext());
        userDBManager = new UserDBManager(getContext());

        // User ID 설정
        userId = "1";

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup RecyclerViews
        setupRecyclerViews();
    }

    private void setupRecyclerViews() {
        List<Integer> userItemIds = userDBManager.getUserItemIds(userId);

        hatItems = new ArrayList<>();
        backgroundItems = new ArrayList<>();
        badgeItems = new ArrayList<>();
        carpetItems = new ArrayList<>();

        if (userItemIds != null) {
            for (int itemId : userItemIds) {
                Item item = itemDBManager.getItemById(itemId);
                if (item != null) {
                    switch (item.getItemType()) {
                        case "Hat":
                            hatItems.add(item);
                            break;
                        case "Background":
                            backgroundItems.add(item);
                            break;
                        case "Badge":
                            badgeItems.add(item);
                            break;
                        case "Carpet":
                            carpetItems.add(item);
                            break;
                    }
                }
            }
        }

        setupRecyclerView(recyclerHat, hatItems, hatImageView, "Hat", 0); // 기본값은 아무것도 없는 상태
        setupRecyclerView(recyclerBackground, backgroundItems, backgroundImageView, "Background", 0); // 기본값은 아무것도 없는 상태
        setupRecyclerView(recyclerBadge, badgeItems, badgeImageView, "Badge", R.drawable.advanced_badge);
        setupRecyclerView(recyclerCarpet, carpetItems, carpetImageView, "Carpet", R.drawable.carpet);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Item> items, ImageView targetImageView, String itemType, int defaultImageResId) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new SlideUpAdapter(items, targetImageView, itemType, defaultImageResId, userDBManager, userId));
    }
}









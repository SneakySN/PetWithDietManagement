package com.example.petwithdietmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.database.ItemDBManager;

import java.util.List;

public class ShopActivity extends TabActivity {

    private ItemDBManager dbManager;
    private static final String TAG = "ShopActivity";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        dbManager = new ItemDBManager(this);

        // Sample data insertion (if needed)
        // dbManager.insertSampleData();

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecHat = tabHost.newTabSpec("HAT").setIndicator("모자");
        tabSpecHat.setContent(R.id.tabHat);
        tabHost.addTab(tabSpecHat);

        TabHost.TabSpec tabSpecBackground = tabHost.newTabSpec("BACKGROUND").setIndicator("배경");
        tabSpecBackground.setContent(R.id.tabBackground);
        tabHost.addTab(tabSpecBackground);

        TabHost.TabSpec tabSpecFloor = tabHost.newTabSpec("BADGE").setIndicator("명찰");
        tabSpecFloor.setContent(R.id.tabBadge);
        tabHost.addTab(tabSpecFloor);

        TabHost.TabSpec tabSpecCarpet = tabHost.newTabSpec("CARPET").setIndicator("카펫");
        tabSpecCarpet.setContent(R.id.tabCarpet);
        tabHost.addTab(tabSpecCarpet);

        tabHost.setCurrentTab(0);

        // back 버튼 클릭 시 종료
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        // 각 탭에 아이템 추가
        populateTab(tabHost.getTabContentView(), "Hat", R.id.tabHat);
        populateTab(tabHost.getTabContentView(), "Background", R.id.tabBackground);
        populateTab(tabHost.getTabContentView(), "Badge", R.id.tabBadge);
        populateTab(tabHost.getTabContentView(), "Carpet", R.id.tabCarpet);
    }

    private void populateTab(View tabContentView, String itemType, int tabId) {
        List<Item> items = dbManager.getItemsByType(itemType);
        LinearLayout tabLayout = tabContentView.findViewById(tabId);

        Log.d(TAG, "populateTab: itemType = " + itemType + ", items.size() = " + items.size());

        for (Item item : items) {
            Log.d(TAG, "populateTab: item = " + item.getItemName());
            View itemLayout = getLayoutInflater().inflate(R.layout.item_layout, tabLayout, false);
            ImageView itemImage = itemLayout.findViewById(R.id.item_image);
            TextView itemName = itemLayout.findViewById(R.id.item_name);
            TextView itemPrice = itemLayout.findViewById(R.id.item_price);
            TextView itemDescription = itemLayout.findViewById(R.id.item_description);

            // 이미지를 설정하는 코드 (리소스에서 가져오는 경우)
            int resId = getResources().getIdentifier(item.getItemImage(), "drawable", getPackageName());
            if (resId != 0) {
                itemImage.setImageResource(resId);
            } else {
                Log.w(TAG, "populateTab: Image resource not found for " + item.getItemImage());
                itemImage.setImageResource(R.drawable.ic_launcher_foreground); // 기본 이미지
            }

            itemName.setText(item.getItemName());
            itemPrice.setText(String.valueOf(item.getItemPrice()));
            itemDescription.setText(String.valueOf(item.getDescription()));

            tabLayout.addView(itemLayout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}

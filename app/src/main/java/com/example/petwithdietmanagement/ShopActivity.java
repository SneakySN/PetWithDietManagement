package com.example.petwithdietmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.data.User;
import com.example.petwithdietmanagement.database.ItemDBManager;
import com.example.petwithdietmanagement.database.UserDBManager;

import java.util.List;

public class ShopActivity extends TabActivity {

    private ItemDBManager itemDBManager;
    private static final String TAG = "ShopActivity";
    private UserDBManager userDBManager;

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

        itemDBManager = new ItemDBManager(this);
        userDBManager = new UserDBManager(this);

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

        // User 정보를 가져와서 currentMoney TextView에 설정
        setUserGold();

        // 각 탭에 아이템 추가
        populateTab(tabHost.getTabContentView(), "Hat", R.id.tabHat);
        populateTab(tabHost.getTabContentView(), "Background", R.id.tabBackground);
        populateTab(tabHost.getTabContentView(), "Badge", R.id.tabBadge);
        populateTab(tabHost.getTabContentView(), "Carpet", R.id.tabCarpet);
    }

    private void setUserGold() {
        // User ID는 적절히 설정해주세요
        String userId = "1"; // 예: 로그인 시 저장된 사용자 ID를 사용
        User user = userDBManager.getUserById(userId);

        if (user != null) {
            TextView currentMoneyTextView = findViewById(R.id.currentMoney);
            currentMoneyTextView.setText(String.valueOf(user.getGold()));
        }
    }

    private void populateTab(View tabContentView, String itemType, int tabId) {
        List<Item> items = itemDBManager.getItemsByType(itemType);
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

            // 클릭 리스너 추가
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPurchaseDialog(item);
                }
            });

            tabLayout.addView(itemLayout);
        }
    }

    private void showPurchaseDialog(Item item) {
        PurchaseConfirmationDialog dialog = new PurchaseConfirmationDialog(this, item.getItemName() + "을(를) " + item.getItemPrice() + " 골드로 구매하시겠습니까?", new PurchaseConfirmationDialog.PurchaseConfirmationDialogListener() {
            @Override
            public void onConfirm() {
                purchaseItem(item);
            }

            @Override
            public void onCancel() {
                // No action needed
            }
        });
        dialog.show();
    }

    private void purchaseItem(Item item) {
        String userId = "1";
        User user = userDBManager.getUserById(userId);

        if (item.isPurchased() == 0 && user != null && user.getGold() >= item.getItemPrice()) {
            user.setGold(user.getGold() - item.getItemPrice());
            userDBManager.updateUser(user);

            Toast.makeText(this, "구매 완료!", Toast.LENGTH_SHORT).show();
            setUserGold();

            userDBManager.addItemToUser(userId, item.getItemId(), false);
            item.setPurchased(1);
            itemDBManager.updateItem(item);
        } else if (item.isPurchased() == 1) {
            Toast.makeText(this, "이미 구매한 아이템입니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "골드가 부족합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemDBManager.close();
        userDBManager.close();
    }
}

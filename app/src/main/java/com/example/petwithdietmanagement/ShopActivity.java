package com.example.petwithdietmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;

public class ShopActivity extends TabActivity {

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
        // 캘린더 버튼
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });





    }
}

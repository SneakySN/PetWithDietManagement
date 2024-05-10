package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.petwithdietmanagement.CalendarActivity;
import com.example.petwithdietmanagement.DietActivity;
import com.example.petwithdietmanagement.MenuPageActivity;
import com.example.petwithdietmanagement.MyPageActivity;
import com.example.petwithdietmanagement.PetMenuActivity;
import com.example.petwithdietmanagement.MainActivity;
import com.example.petwithdietmanagement.R;

public class PetMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu); // XML 레이아웃 이름을 입력하세요

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                finish();
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
                finish();
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                finish();
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                finish();
            }
        });

        // 상점 메뉴 버튼
        ImageView shopButton = findViewById(R.id.shop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, ShopActivity.class); // 상점 페이지로 이동
                startActivity(intent);
            }
        });

        // 미션 팝업 설정
        View popupView = LayoutInflater.from(this).inflate(R.layout.activity_mission, null);
        PopupWindow popupWindow = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);

        findViewById(R.id.mission).setOnClickListener(v -> {
            // 팝업 위치 설정: 화면 중앙
            popupWindow.showAtLocation(findViewById(R.id.ic_petMenu), Gravity.CENTER, 0, 0);
        });

    }
}

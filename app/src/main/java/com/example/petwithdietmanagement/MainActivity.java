package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.CalendarActivity;
import com.example.petwithdietmanagement.DietActivity;
import com.example.petwithdietmanagement.MenuPageActivity;
import com.example.petwithdietmanagement.MyPageActivity;
import com.example.petwithdietmanagement.PetMenuActivity;
import com.example.petwithdietmanagement.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // XML 레이아웃 이름을 입력하세요

        //펫 움직이게 하고싶었어요
        int[] petImages = {
                R.drawable.pet_1,
                R.drawable.pet_2,
                R.drawable.pet_3,
                R.drawable.pet_2
        };
        ImageView petImageView = findViewById(R.id.ic_pet);

        Handler handler = new Handler(Looper.getMainLooper());

        Runnable imageSwitcher = new Runnable() {
            int currentIndex = 0;
            @Override
            public void run(){
                petImageView.setImageResource(petImages[currentIndex]);
                currentIndex = (currentIndex + 1) % petImages.length;
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(imageSwitcher);


        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                finish();
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
            }
        });

        // 메뉴 버튼
        ImageButton menuButton = findViewById(R.id.ic_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuPageActivity.class); // 메뉴 페이지로 이동
                startActivity(intent);
            }
        });

        // 마이 페이지 버튼
        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class); // 마이 페이지로 이동
                startActivity(intent);
            }
        });
    }
}

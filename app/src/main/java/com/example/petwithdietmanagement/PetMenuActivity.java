package com.example.petwithdietmanagement;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.example.petwithdietmanagement.CalendarActivity;
import com.example.petwithdietmanagement.DietActivity;
import com.example.petwithdietmanagement.MenuPageActivity;
import com.example.petwithdietmanagement.MyPageActivity;
import com.example.petwithdietmanagement.PetMenuActivity;
import com.example.petwithdietmanagement.MainActivity;
import com.example.petwithdietmanagement.R;

public class PetMenuActivity extends AppCompatActivity {
    private ImageView petImageView;
    private Handler handler;
    private Runnable imageSwitcher;
    private AnimationDrawable petJumpAnimation;
    private  int currentIndex = 0;
    int[] petImages = {
            R.drawable.slime01,
            R.drawable.slime02,
            R.drawable.slime03,
            R.drawable.slime02
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu); // XML 레이아웃 이름을 입력하세요

        petImageView = findViewById(R.id.character_image);
        handler = new Handler(Looper.getMainLooper());
        imageSwitcher = new Runnable() {

            @Override
            public void run(){
                petImageView.setImageResource(petImages[currentIndex]);
                currentIndex = (currentIndex + 1) % petImages.length;
                handler.postDelayed(this, 750);
            }
        };
        handler.post(imageSwitcher);

        petImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeJumpImage();
            }
        });

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

        ImageView missionButton = findViewById(R.id.mission);
        missionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CustomDialog 인스턴스 생성 및 표시
                CustomDialog customDialog = new CustomDialog(PetMenuActivity.this);
                customDialog.show();
            }
        });

    }

    private void changeJumpImage(){
        handler.removeCallbacks(imageSwitcher);

        // 점프 애니메이션 설정
        petImageView.setImageResource(R.drawable.pet_jump_animation);
        petJumpAnimation = (AnimationDrawable) petImageView.getDrawable();
        petJumpAnimation.start();

        // 점프 애니메이션 끝난 후 원래 애니메이션 시퀀스로 돌아가기
        int animationDuration = getAnimationDuration(petJumpAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                petJumpAnimation.stop();
                startOriginalAnimation();
            }
        }, animationDuration);
    }

    private void startOriginalAnimation() {
        currentIndex = 0;
        handler.post(imageSwitcher); // 원래 애니메이션 시퀀스 재시작
    }

    private int getAnimationDuration(AnimationDrawable animationDrawable) {
        int duration = 0;
        for (int i = 0; i < 12; i++) {
            duration += animationDrawable.getDuration(i);
        }
        return duration;
    }
}

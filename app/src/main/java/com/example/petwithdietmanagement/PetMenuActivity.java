package com.example.petwithdietmanagement;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private int currentIndex = 0;
    private boolean isJumping = false;
    int[] petImages = {
            R.drawable.slime01,
            R.drawable.slime02,
            R.drawable.slime03,
            R.drawable.slime02
    };

    // 숨길 뷰 변수 선언
    private View speechBubble, speechBubbleText, currentMoney, coin, shop, mission, decorating, badge, petTitleAndName, shoptext, missiontext, decotext, cameratext, bottom_menu;
    private ImageView cameraButton;
    private ConstraintLayout firstLayout;
    private boolean isCameraMode = false;
    private LinearLayout slideUpLayout;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu); // XML 레이아웃 이름을 입력하세요

        petImageView = findViewById(R.id.character_image);
        handler = new Handler(Looper.getMainLooper());
        imageSwitcher = new Runnable() {
            @Override
            public void run() {
                if (!isJumping)
                    petImageView.setImageResource(petImages[currentIndex]);
                    currentIndex = (currentIndex + 1) % petImages.length;
                    handler.postDelayed(this, 750);
            }
        };
        handler.post(imageSwitcher);

        petImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isJumping)
                    changeJumpImage();
            }
        });

        // 숨길 뷰들 초기화
        speechBubble = findViewById(R.id.speechBubble);
        speechBubbleText = findViewById(R.id.speechBubbleText);
        currentMoney = findViewById(R.id.currentMoney);
        coin = findViewById(R.id.coin);
        shop = findViewById(R.id.shop);
        mission = findViewById(R.id.mission);
        decorating = findViewById(R.id.decorating);
        badge = findViewById(R.id.badge);
        petTitleAndName = findViewById(R.id.petTitleAndName);
        shoptext = findViewById(R.id.shoptext);
        missiontext = findViewById(R.id.missiontext);
        decotext = findViewById(R.id.decotext);
        cameratext = findViewById(R.id.cameratext);
        bottom_menu = findViewById(R.id.bottom_menu);
        firstLayout = findViewById(R.id.firstLayout);
        slideUpLayout = findViewById(R.id.slideUpLayout);

        // 카메라 버튼 클릭 이벤트
        cameraButton = findViewById(R.id.camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCameraMode();
            }
        });

        // 꾸미기 버튼 클릭 이벤트
        decorating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSlideUpLayout();
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
                overridePendingTransition(0,0);
            }
        });

        // 상점 메뉴 버튼
        ImageView shopButton = findViewById(R.id.shop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, ShopActivity.class); // 상점 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
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

        initializeTabs();
    }

    private void initializeTabs() {
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Hat");
        spec.setContent(R.id.tabHat);
        spec.setIndicator("모자");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Background");
        spec.setContent(R.id.tabBackground);
        spec.setIndicator("배경");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Floor");
        spec.setContent(R.id.tabFloor);
        spec.setIndicator("바닥");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Carpet");
        spec.setContent(R.id.tabCarpet);
        spec.setIndicator("카펫");
        tabHost.addTab(spec);
    }


    // 바깥 영역 터치시 slideUpLayout 숨기기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (slideUpLayout.getVisibility() == View.VISIBLE) {
                // slideUpLayout 영역 바깥을 터치한 경우
                if (!isPointInsideView(ev.getRawX(), ev.getRawY(), slideUpLayout)) {
                    hideSlideUpLayout();
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isPointInsideView(float x, float y, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        // View의 사각형 영역 안에 포인트가 있는지 확인
        return (x > viewX && x < (viewX + view.getWidth()) && y > viewY && y < (viewY + view.getHeight()));
    }

    private void showSlideUpLayout() {
        slideUpLayout.setVisibility(View.VISIBLE);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_animation);
        slideUpLayout.startAnimation(slideUp);
    }

    private void hideSlideUpLayout() {
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_animation);
        slideUpLayout.startAnimation(slideDown);
        slideUpLayout.setVisibility(View.GONE);
    }

    private void toggleCameraMode() {
        if (isCameraMode) {
            // 원래 상태로 복구
            showViews();
            cameraButton.setImageResource(R.drawable.camera); // 원래 카메라 이미지로 변경
        } else {
            // 카메라 모드로 전환
            hideViews();
            cameraButton.setImageResource(R.drawable.back); // back 이미지로 변경
        }
        isCameraMode = !isCameraMode;
    }

    private void hideViews() {
        // 숨길 뷰들을 INVISIBLE로 설정
        speechBubble.setVisibility(View.INVISIBLE);
        speechBubbleText.setVisibility(View.INVISIBLE);
        currentMoney.setVisibility(View.INVISIBLE);
        coin.setVisibility(View.INVISIBLE);
        shop.setVisibility(View.INVISIBLE);
        mission.setVisibility(View.INVISIBLE);
        decorating.setVisibility(View.INVISIBLE);
        badge.setVisibility(View.INVISIBLE);
        petTitleAndName.setVisibility(View.INVISIBLE);
        shoptext.setVisibility(View.INVISIBLE);
        missiontext.setVisibility(View.INVISIBLE);
        decotext.setVisibility(View.INVISIBLE);
        cameratext.setVisibility(View.INVISIBLE);
        bottom_menu.setVisibility(View.GONE); // bottom_menu를 GONE으로 설정

        // firstLayout의 높이를 전체 화면으로 변경
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) firstLayout.getLayoutParams();
        layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        firstLayout.setLayoutParams(layoutParams);
    }

    private void showViews() {
        // 숨긴 뷰들을 다시 VISIBLE로 설정
        speechBubble.setVisibility(View.VISIBLE);
        speechBubbleText.setVisibility(View.VISIBLE);
        currentMoney.setVisibility(View.VISIBLE);
        coin.setVisibility(View.VISIBLE);
        shop.setVisibility(View.VISIBLE);
        mission.setVisibility(View.VISIBLE);
        decorating.setVisibility(View.VISIBLE);
        badge.setVisibility(View.VISIBLE);
        petTitleAndName.setVisibility(View.VISIBLE);
        shoptext.setVisibility(View.VISIBLE);
        missiontext.setVisibility(View.VISIBLE);
        decotext.setVisibility(View.VISIBLE);
        cameratext.setVisibility(View.VISIBLE);
        bottom_menu.setVisibility(View.VISIBLE); // bottom_menu를 VISIBLE로 설정

        // firstLayout의 높이를 원래 상태로 변경
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) firstLayout.getLayoutParams();
        layoutParams.height = 0;
        layoutParams.matchConstraintPercentHeight = 0.905f; // 원래 퍼센트로 복구
        firstLayout.setLayoutParams(layoutParams);
    }

    private void changeJumpImage() {
        isJumping = true;
        handler.removeCallbacks(imageSwitcher);

        // 점프 애니메이션 설정
        petImageView.setImageResource(R.drawable.pet_jump_animation);
        petJumpAnimation = (AnimationDrawable) petImageView.getDrawable();
        petJumpAnimation.start();

        // 점프 애니메이션 끝난 후 원래 애니메이션 시퀀스로 돌아가기
        int animationDuration = getAnimationDuration(petJumpAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                petJumpAnimation.stop();
                isJumping = false;
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
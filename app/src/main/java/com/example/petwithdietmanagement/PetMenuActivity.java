package com.example.petwithdietmanagement;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.data.User;
import com.example.petwithdietmanagement.database.ItemDBManager;
import com.example.petwithdietmanagement.database.UserDBManager;
import com.bumptech.glide.Glide;

import java.util.List;

public class PetMenuActivity extends AppCompatActivity {
    private ImageView petImageView, hatImageView, backgroundImageView, carpetImageView, badgeImageView;
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
    private UserDBManager userDBManager;
    private ItemDBManager itemDBManager;

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

        itemDBManager = new ItemDBManager(this); // ItemDBManager 인스턴스 생성
        userDBManager = new UserDBManager(this); // UserDBManager 인스턴스 생성

        petImageView = findViewById(R.id.character_image);
        hatImageView = findViewById(R.id.hat_image);
        backgroundImageView = findViewById(R.id.background_image);
        carpetImageView = findViewById(R.id.carpet);
        badgeImageView = findViewById(R.id.badge);
        handler = new Handler(Looper.getMainLooper());
        imageSwitcher = new Runnable() {
            @Override
            public void run() {
                if (!isJumping) {
                    petImageView.setImageResource(petImages[currentIndex]);
                    currentIndex = (currentIndex + 1) % petImages.length;
                    handler.postDelayed(this, 750);
                }
            }
        };
        handler.post(imageSwitcher);

        petImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJumping)
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
                showSlideUpFragment();
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        // 마이 페이지 버튼
        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetMenuActivity.this, MyPageActivity.class); // 마이 페이지로 이동
                startActivity(intent);
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
                overridePendingTransition(0, 0);
            }
        });

        ImageView missionButton = findViewById(R.id.mission);
        missionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MissionDialog 인스턴스 생성 및 표시
                MissionDialog missionDialog = new MissionDialog(PetMenuActivity.this);
                missionDialog.show();
            }
        });

        // User 정보를 가져와서 currentMoney TextView에 설정
        setUserGold();

        // 장착된 아이템을 화면에 설정
        setEquippedItems();
    }

    private void setUserGold() {
        // User ID는 적절히 설정해주세요
        String userId = "user123"; // 예: 로그인 시 저장된 사용자 ID를 사용
        User user = userDBManager.getUserById(userId);

        if (user != null) {
            TextView currentMoneyTextView = findViewById(R.id.currentMoney);
            currentMoneyTextView.setText(String.valueOf(user.getGold()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUserGold();
    }

    private void setEquippedItems() {
        String userId = "user123";

        setEquippedItemForType(userId, "Hat", hatImageView, 0);
        setEquippedItemForType(userId, "Background", backgroundImageView, 0);
        setEquippedItemForType(userId, "Badge", badgeImageView, R.drawable.advanced_badge);
        setEquippedItemForType(userId, "Carpet", carpetImageView, R.drawable.carpet);
    }

    private void setEquippedItemForType(String userId, String itemType, ImageView imageView, int defaultImageResId) {
        // userDBManager를 통해 user_items 테이블에서 장착된 item_id 가져오기
        List<Integer> equippedItemIds = userDBManager.getEquippedItemIds(userId);

        if (equippedItemIds.isEmpty()) {
            imageView.setImageResource(defaultImageResId);
            return;
        }

        // itemDBManager를 통해 items 테이블에서 item_id에 해당하는 아이템 정보 가져오기
        Item equippedItem = null;
        for (int itemId : equippedItemIds) {
            Item item = itemDBManager.getItemById(itemId);
            if (item != null && item.getItemType().equals(itemType)) {
                equippedItem = item;
                break;
            }
        }

        // 해당 아이템을 ImageView에 설정
        if (equippedItem != null) {
            int imageResId;
            if (itemType.equals("Hat")) {
                imageResId = getResources().getIdentifier(equippedItem.getItemRealImage(), "drawable", getPackageName());
            } else {
                imageResId = getResources().getIdentifier(equippedItem.getItemImage(), "drawable", getPackageName());
            }
            Glide.with(this).load(imageResId).into(imageView);
        } else {
            imageView.setImageResource(defaultImageResId);
        }
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
    }

    private void changeJumpImage() {
        isJumping = true;
        handler.removeCallbacks(imageSwitcher);

        // 점프 애니메이션 설정
        petImageView.setImageResource(R.drawable.pet_jump_animation);
        AnimationDrawable petJumpAnimation = (AnimationDrawable) petImageView.getDrawable();
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

    // showSlideUpFragment 메서드 추가
    private void showSlideUpFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SlideUpFragment slideUpFragment = new SlideUpFragment();
        slideUpFragment.show(fragmentManager, slideUpFragment.getTag());
    }
}
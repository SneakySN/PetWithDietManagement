package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.CalendarActivity;
import com.example.petwithdietmanagement.DietActivity;
import com.example.petwithdietmanagement.MyPageActivity;
import com.example.petwithdietmanagement.PetMenuActivity;
import com.example.petwithdietmanagement.MainActivity;

import com.example.petwithdietmanagement.R;

public class MenuPageActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page); // XML 레이아웃 이름을 입력하세요

        // x버튼 누르면 페이지 닫기
        ImageButton exitButton = findViewById(R.id.ic_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){ finish(); }
        });


        ImageButton alarmButton = findViewById(R.id.ic_alarm);
        alarmButton.setOnClickListener(new View.OnClickListener(){
            // 알람 버튼 누르면 on/off 이미지 변경
            boolean isAlarmOn = true;
            @Override
            public void onClick(View v){
                if (isAlarmOn){
                    alarmButton.setImageResource(R.drawable.alarm_off);
                } else {
                    alarmButton.setImageResource(R.drawable.alarm_on);
                }
                isAlarmOn = !isAlarmOn;
            }
        });

/*
        ImageButton lightModButton = findViewById(R.id.ic_lightMod);
        lightModButton.setOnClickListener(new View.OnClickListener(){
            // 다크모드 버튼 누르면 라이트모드/다크모드 이미지 변경
            boolean isLightMod = true;
            @Override
            public void onClick(View v){
                if (isLightMod){
                    lightModButton.setImageResource(R.drawable.dark_mod);
                } else {
                    lightModButton.setImageResource(R.drawable.light_mod);
                }
                isLightMod = !isLightMod;
            }
        });
*/

        // 공유 버튼 누르면 간단한 텍스트와 함께 공유
        ImageButton shareButton = findViewById(R.id.ic_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareMessage = "너도 식단 관리하고 펫 키워볼래? [앱 링크]";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                startActivity(Intent.createChooser(shareIntent, "앱 공유하기"));
            }
        });
    }
}

package com.example.petwithdietmanagement;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView dateTextView, breakfastInfo, lunchInfo, dinnerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 캘린더 뷰와 텍스트 뷰 초기화
        calendarView = findViewById(R.id.calendar_view);
        dateTextView = findViewById(R.id.date);
        breakfastInfo = findViewById(R.id.breakfast_info);
        lunchInfo = findViewById(R.id.lunch_info);
        dinnerInfo = findViewById(R.id.dinner_info);

        // 캘린더 날짜 변경 이벤트 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
                // Gson 객체 생성 및 JSON 파싱
                GsonMapping gsonMapping = new GsonMapping();
                AssetManager assetManager = CalendarActivity.this.getAssets();
                try (InputStream inputStream = assetManager.open("calendar.json");
                     InputStreamReader reader = new InputStreamReader(inputStream)) {
                    Calendar calendarData = gsonMapping.getCalendar(reader);
                    Map<String, Calendar.User> users = calendarData.getUsers();
                    Calendar.User.Meals meals = users.get("1").getFood_log().get(date);

                    if (meals != null) {
                        updateGraphData(date, meals);
                    } else {
                        updateGraphData(date, null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error reading the JSON file: " + e.getMessage());
                }
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                finish();
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
                finish();
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                finish();
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                finish();
            }
        });

        // 마이 페이지 버튼
        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MyPageActivity.class); // 마이 페이지로 이동
                startActivity(intent);
            }
        });
    }

    private void updateGraphData(String date, Calendar.User.Meals meals) {
        dateTextView.setText("Data for " + date);
        if (meals != null) {
            breakfastInfo.setText("아침: " + String.join(", ", meals.getBreakfast_foodid()));
            lunchInfo.setText("점심: " + String.join(", ", meals.getLunch_foodid()));
            dinnerInfo.setText("저녁: " + String.join(", ", meals.getDinner_foodid()));
        } else {
            breakfastInfo.setText("아침: 정보 없음");
            lunchInfo.setText("점심: 정보 없음");
            dinnerInfo.setText("저녁: 정보 없음");
        }
    }
}

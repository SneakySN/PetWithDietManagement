package com.example.petwithdietmanagement;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.content.Intent;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Items;
import com.example.petwithdietmanagement.data.Pets;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.data.Users;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView graphPlaceholder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 캘린더 뷰와 그래프 플레이스홀더 초기화
        calendarView = findViewById(R.id.calendar_view);

        // 캘린더 날짜 변경 이벤트 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                // Gson 객체 생성 및 JSON 파싱
                GsonMapping gsonMapping = new GsonMapping();
                AssetManager assetManager = CalendarActivity.this.getAssets();
                try (InputStream inputStream = assetManager.open("calendar.json");
                     InputStreamReader reader = new InputStreamReader(inputStream)) {
                    Calendar recipes = gsonMapping.getCalendar(reader);
                    Map<String, Calendar.User> recipe=recipes.getUsers();




                    if (recipe != null) {
                        Log.d(TAG, "Name= " + recipe.get("1").getFood_log().get("2024-05-15").getBreakfast_foodid().get(0));

                    } else {

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

    private void updateGraphData(String date) {
        // 예시로 그래프 데이터를 업데이트하는 메서드
        // 실제로는 데이터를 가져와서 그래프를 업데이트하는 로직을 구현해야 합니다.
        graphPlaceholder.setText("Data for " + date);
    }
}

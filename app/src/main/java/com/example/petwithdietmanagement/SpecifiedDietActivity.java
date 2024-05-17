package com.example.petwithdietmanagement;

import android.content.Intent;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.CalendarActivity;
import com.example.petwithdietmanagement.DietActivity;
import com.example.petwithdietmanagement.MenuPageActivity;
import com.example.petwithdietmanagement.MyPageActivity;
import com.example.petwithdietmanagement.PetMenuActivity;
import com.example.petwithdietmanagement.MainActivity;
import com.example.petwithdietmanagement.R;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SpecifiedDietActivity extends AppCompatActivity {
    private int curStatus;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 각 버튼 함수 내에서 구현할 것들 : 버튼을 누를 시 .json 파일에서 데이터 불러오기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifieddiet);
        ImageButton homeButton = findViewById(R.id.ic_home);
        ImageButton dietButton = findViewById(R.id.ic_diet);
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        ImageButton menuButton = findViewById(R.id.menuButton);
        Button recButton = findViewById(R.id.button_recommended);
        Button korButton = findViewById(R.id.button_korean);
        Button wesButton = findViewById(R.id.button_western);
        Button japButton = findViewById(R.id.button_japanese);
        linearLayout = findViewById(R.id.ItemLinearLayout);

        Intent choiceIntent = getIntent();
        String choice = choiceIntent.getStringExtra("tag");

        if (Objects.equals(choice, "0")) {
            korButton.setBackgroundColor(Color.parseColor("#80000000"));
            curStatus = 1;
        } else if (Objects.equals(choice, "1")) {
            wesButton.setBackgroundColor(Color.parseColor("#80000000"));
            curStatus = 2;
        } else if (Objects.equals(choice, "2")) {
            japButton.setBackgroundColor(Color.parseColor("#80000000"));
            curStatus = 3;
        }

        // 홈 버튼
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecifiedDietActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
            }
        });

        // 음식 버튼
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecifiedDietActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
            }
        });

        // 캘린더 버튼
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecifiedDietActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
            }
        });

        // 펫 메뉴 버튼
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecifiedDietActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
            }
        });

        // 메뉴 버튼
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecifiedDietActivity.this, MenuPageActivity.class); // 메뉴 페이지로 이동
                startActivity(intent);
            }
        });

        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curStatus = 0;
            }
        });

        korButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curStatus = 1;
            }
        });

        wesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curStatus = 2;
            }
        });

        japButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curStatus = 3;
            }
        });
    }
    private void addButton(String buttonText) {
        Button newButton = new Button(this);
        newButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        newButton.setText(buttonText);
        newButton.setBackgroundColor(0x24000000);
        newButton.setPadding(0, 10, 0, 0);

        linearLayout.addView(newButton);
    }

}

/*public class SpecifiedDiet_json_read {
    private static final String TAG = "SpecificDiet_json_read";

    public void recipes() throws IOException {
        GsonMapping gsonMapping = new GsonMapping();
        //Map<String, Recipe> recipeData = (Map<String, Recipe>) gsonMapping.getRecipes("recipeList.json");
    }
}*/
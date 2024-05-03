package com.example.petwithdietmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;
import android.view.View;

import android.os.Bundle;

public class SpecifiedDietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifieddiet);

        ImageView petMenu = findViewById(R.id.ic_home);
        petMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petMenuIntent = new Intent(SpecifiedDietActivity.this, com.yourpackage.name.MainActivity.class);
                startActivity(petMenuIntent);
            }
        });
        ImageView dietMenu = findViewById(R.id.ic_diet);
        petMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petMenuIntent = new Intent(DietActivity.this, com.yourpackage.name.MainActivity.class);
                startActivity(petMenuIntent);
            }
        });
        ImageView calenderMenu = findViewById(R.id.ic_home);
        calenderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petMenuIntent = new Intent(PetMenuActivity.this, com.yourpackage.name.MainActivity.class);
                startActivity(petMenuIntent);
            }
        });
    }
}

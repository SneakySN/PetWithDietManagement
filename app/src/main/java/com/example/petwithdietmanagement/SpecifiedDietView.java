package com.example.petwithdietmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;
import android.view.View;

import android.os.Bundle;

public class SpecifiedDietView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifieddiet);

        ImageView petMenu = findViewById(R.id.ic_home);
        petMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petMenuIntent = new Intent(SpecifiedDietView.this, MainActivity.class);
                startActivity(petMenuIntent);
            }
        });
    }
}

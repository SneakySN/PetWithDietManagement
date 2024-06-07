package com.example.petwithdietmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.database.RecipeDBManager;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpecifiedDietActivity extends AppCompatActivity {

    private TextView dietTitle;
    private ImageView dietImage;
    private TextView dietIngredients;
    private TextView dietNutrients;
    private TextView dietSteps;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifieddiet);


        dietTitle = findViewById(R.id.dietTitle);
        dietImage = findViewById(R.id.dietImage);
        dietIngredients = findViewById(R.id.dietIngredients);
        dietNutrients = findViewById(R.id.dietNutrients);
        dietSteps = findViewById(R.id.dietSteps);


        // 인텐트에서 Recipe 객체 받기
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("Recipe");

        if (recipe != null) {
            dietTitle.setText(recipe.getRecipeName());
            String imageUrl = recipe.getImages().getPreviewImage();

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(dietImage);
            dietIngredients.setText("Ingredients:\n" + recipe.getIngredients());
            dietNutrients.setText("Nutrients:\n" + getNutrientsString(recipe.getNutrients()));

            List<String> manuals = recipe.getManualSteps();
            dietSteps.setText(manuals.get(3));
            Log.d("SpecifiedDietActivity", "Loaded recipe: " + recipe.getRecipeName() + ", Steps: " + manuals);
        } else {
            Log.e("SpecifiedDietActivity", "Recipe is null");
        }
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private String getNutrientsString(Recipe.Nutrients nutrients) {
        return "Calories: " + nutrients.getCalories() + " kcal\n" +
                "Protein: " + nutrients.getProtein() + " g\n" +
                "Fat: " + nutrients.getFat() + " g\n" +
                "Carbohydrate: " + nutrients.getCarbohydrate() + " g\n" +
                "Sodium: " + nutrients.getSodium() + " mg";
    }

    // AsyncTask to load image from URL
    private static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private final String url;
        private final ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }

    }

}

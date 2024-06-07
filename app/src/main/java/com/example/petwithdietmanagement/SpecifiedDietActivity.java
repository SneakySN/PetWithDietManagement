package com.example.petwithdietmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.database.RecipeDBManager;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpecifiedDietActivity extends AppCompatActivity {

    private TextView dietTitle;
    private ImageView dietImage;
    private TextView dietIngredients;
    private TextView dietNutrients;
    private TextView dietSteps;
    private RecipeDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifieddiet);

        dietTitle = findViewById(R.id.dietTitle);
        dietImage = findViewById(R.id.dietImage);
        dietIngredients = findViewById(R.id.dietIngredients);
        dietNutrients = findViewById(R.id.dietNutrients);
        dietSteps = findViewById(R.id.dietSteps);

        // Initialize the DB manager
        dbManager = new RecipeDBManager(this);

        // Get the intent that started this activity
        Intent intent = getIntent();
        String dietId = intent.getStringExtra("DIET_ID");

        // Fetch the recipe details from the database
        Recipe recipe = null;
        try {
            recipe = dbManager.getRecipeById(Integer.parseInt(dietId));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (recipe != null) {
            dietTitle.setText(recipe.getRecipeName());
            new ImageLoadTask(Recipe.Images.getIngredientPreviewImage(), dietImage).execute();
            dietIngredients.setText("Ingredients:\n" + recipe.getIngredients());
            dietNutrients.setText("Nutrients:\n" + recipe.getNutrients().toString());
            dietSteps.setText("Steps:\n" + String.join("\n", recipe.getManualSteps()));
        }

        dbManager.close();
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

package com.example.petwithdietmanagement;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void testNutrientsCalories() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("recipeList.json");
        String json = new String(readAllBytesFromStream(inputStream), StandardCharsets.UTF_8);

        // Gson 객체 생성 및 JSON 파싱
        GsonMapping gsonMapping = new GsonMapping();
        Map<String, Recipe> recipes = gsonMapping.getRecipes(json);

        // 28번 레시피의 이름을 로그에 출력
        Recipe recipe = recipes.get("28");
        assertNotNull(recipe);  // Recipe 객체가 null이 아닌지 확인
        Log.d(TAG, "Recipe Name: " + recipe.getRecipeName());

        // 28번 레시피의 Nutrients 객체의 calories 필드 값 검증
        assertEquals("220", recipe.getNutrients().getCalories());
    }

    private byte[] readAllBytesFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384]; // 16Kb

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }
}

package com.example.petwithdietmanagement;

import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;
import com.google.gson.Gson;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void testNutrientsCalories() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        InputStream inputStream = context.getAssets().open("recipeList.json");
        String json = new String(readAllBytesFromStream(inputStream), StandardCharsets.UTF_8);

        // Gson 객체 생성 및 JSON 파싱
        Gson gson = new Gson();
        Map<String, Recipe> recipes = gson.fromJson(json, new com.google.gson.reflect.TypeToken<Map<String, Recipe>>(){}.getType());

        // 28번 레시피의 Nutrients 객체의 calories 필드 값 검증
        Recipe recipe = recipes.get("28");
        assertNotNull(recipe);  // Recipe 객체가 null이 아닌지 확인
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

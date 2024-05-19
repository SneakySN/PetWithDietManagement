package com.example.petwithdietmanagement;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.petwithdietmanagement.data.Missions;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void testParseMissionJson() {
        // 앱 컨텍스트 가져오기
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // GsonMapping 객체 생성
        GsonMapping gsonMapping = new GsonMapping();
        AssetManager assetManager = appContext.getAssets();

        try (InputStream inputStream = assetManager.open("mission.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            Missions missions = gsonMapping.getMissions(reader);
            assertNotNull("Missions object should not be null", missions);

            List<Missions.Mission> users = missions.getMissions();
            assertNotNull("Missions list should not be null", users);
            assertTrue("Missions list should not be empty", !users.isEmpty());

            // Log를 사용하여 미션 ID 출력
            Log.d(TAG, "Mission_id= " + users.get(0).getMission_id());
            System.out.println("Mission_id= " + users.get(0).getMission_id());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
            assertTrue("Exception should not be thrown", false);
        }
    }
}

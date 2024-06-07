package com.example.petwithdietmanagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petwithdietmanagement.data.Mission;
import com.example.petwithdietmanagement.database.MissionDBManager;

import java.util.List;

public class MissionDialog extends Dialog {

    private MissionDBManager missionDBManager;

    public MissionDialog(Context context) {
        super(context);
        missionDBManager = new MissionDBManager(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_mission);

        // 다이얼로그의 배경을 투명하게 만듭니다.
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        // 닫기 버튼 클릭 이벤트 처리
        ImageView closeButton = findViewById(R.id.exit);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();  // 다이얼로그 닫기
            }
        });

        // 미션 데이터 로드 및 설정
        loadMissions();
    }

    private void loadMissions() {
        List<Mission> missions = missionDBManager.getAllMissions();
        LinearLayout missionsContainer = findViewById(R.id.missionsContainer);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (Mission mission : missions) {
            View missionItemView = inflater.inflate(R.layout.mission_item_layout, missionsContainer, false);

            TextView missionName = missionItemView.findViewById(R.id.mission_name);
            TextView missionDescription = missionItemView.findViewById(R.id.mission_description);
            TextView missionTarget = missionItemView.findViewById(R.id.mission_target);

            missionName.setText(mission.getName());
            missionDescription.setText(mission.getDescription());
            missionTarget.setText("목표: " + mission.getTarget() + " " + mission.getUnit());

            missionsContainer.addView(missionItemView);
        }
    }
}

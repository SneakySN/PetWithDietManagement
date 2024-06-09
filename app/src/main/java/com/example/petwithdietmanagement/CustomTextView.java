package com.example.petwithdietmanagement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;


public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 커스텀 글꼴 로드
        Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.hanbit);
        }
        setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 기존 Paint 객체를 복사
        Paint paint = getPaint();

        // 흰색 테두리를 그릴 Paint 설정
        Paint strokePaint = new Paint(paint);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(8);
        strokePaint.setTextSize(130);
        strokePaint.setColor(0xFFFFFFFF); // 흰색 테두리
        strokePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        strokePaint.setAntiAlias(true);

        String text = getText().toString();

        // 텍스트의 baseline을 찾기 위해 FontMetrics 사용
        Paint.FontMetrics fm = paint.getFontMetrics();
        float baseline = (getHeight() - fm.bottom + fm.top) / 2 - fm.top;

        // 텍스트를 그릴 때 y 좌표를 조정하여 아래쪽으로 이동
        canvas.drawText(text, (getWidth() - strokePaint.measureText(text))  , baseline+12, strokePaint);

        // 기본 텍스트 그리기
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getCurrentTextColor());
        super.onDraw(canvas);
    }
}

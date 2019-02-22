package com.example.rage.assignment_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class Bubble extends View {

    private Paint paintFrame;
    private Paint paintBubble;

    private float[] values = { 0, 0, 0 };
    private float tolerance = 0;
    private int mode = 0;

    public Bubble(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintFrame = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFrame.setColor(Color.BLUE);

        paintBubble = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setMode(int mode) {
        this.mode = mode;

        invalidate();
    }

    public void setValues(float[] values) {
        this.values = values;

        invalidate();
    }

    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = canvas.getWidth();
        float h = canvas.getHeight();

        float cx = w / 2;
        float cy = h / 2;
        float r = Math.min(cx, cy);

        float x = 0;
        float y = 0;

        if (mode == 0) {
            x = values[0];
            y = values[1];

            canvas.drawCircle(cx, cy, r, paintFrame);
        } else {
            x = values[2];

            canvas.drawRect(cx - r, cy - r/4 , cx + r, cy + r/4, paintFrame);
        }

        if (Math.abs(x) > tolerance || Math.abs(y) > tolerance) {
            paintBubble.setColor(Color.RED);
        } else {
            paintBubble.setColor(Color.GREEN);
        }

        canvas.drawCircle(
                cx + r * x,
                cy + r * y,
                dpToPx(20),
                paintBubble
        );
    }

    private float dpToPx(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}

package com.example.rage.assignment_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph extends View {

    private Paint paintData;
    private Paint paintAxis;

    private int mode = 0;
    private float tolerance = 0;
    private LinkedList<float[]> history = new LinkedList<>();

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintData = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintAxis = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintAxis.setStrokeWidth(dpToPx(4));
    }

    public void setMode(int mode) {
        this.mode = mode;

        invalidate();
    }

    public void addValues(float[] values) {
        history.add(values);

        if (history.size() > 50) {
            history.remove();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = canvas.getWidth();
        float h = canvas.getHeight();

        if (history.size() == 0) {
            return;
        }

        canvas.save();
        canvas.translate(0, canvas.getHeight());
        canvas.scale(1,-1);

        List<Integer> indices;

        if (mode == 0) {
            indices = Arrays.asList(0, 1);
        } else {
            indices = Arrays.asList(2);
        }

        for (Integer indice: indices) {
            for (int i = 0; i < history.size(); i++) {
                float cx = 0;
                float cy = 0;

                cx += w / 50 * i;
                cx += w / 50 / 2;

                cy += h / 2 * history.get(i)[indice];
                cy += h / 2;

                if (Math.abs(history.get(i)[indice]) > tolerance) {
                    paintData.setColor(Color.RED);
                } else {
                    paintData.setColor(Color.GREEN);
                }

                canvas.drawCircle(cx, cy, dpToPx(5), paintData);
            }
        }

        canvas.restore();

        paintAxis.setColor(Color.BLACK);
        canvas.drawLine(0, h / 2, w, h / 2, paintAxis);
        paintAxis.setColor(Color.BLUE);
        canvas.drawLine(0, h / 2 + h / 2 * tolerance, w, h / 2 + h / 2 * tolerance, paintAxis);
        canvas.drawLine(0, h / 2 - h / 2 * tolerance, w, h / 2 - h / 2 * tolerance, paintAxis);
    }

    private float dpToPx(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
    }
}

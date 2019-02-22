package com.example.rage.assignment_3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ((EditText)findViewById(R.id.tolerance)).setText(String.valueOf(5));
        ((EditText)findViewById(R.id.tolerance)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    updateTolerance();
                }

                return false;
            }
        });

        mode = 0;

        updateMode();
        updateTolerance();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        updateMode();
        updateTolerance();
    }

    private void updateMode() {
        ((Bubble)findViewById(R.id.bubble)).setMode(mode);
        ((Graph)findViewById(R.id.graph)).setMode(mode);
    }

    private void updateTolerance() {
        float tolerance;

        try {
            tolerance = Float.parseFloat(((EditText) findViewById(R.id.tolerance)).getText().toString());
            tolerance = tolerance / 10;
        } catch (NumberFormatException e) {
            return;
        }

        ((Bubble)findViewById(R.id.bubble)).setTolerance(tolerance);
        ((Graph)findViewById(R.id.graph)).setTolerance(tolerance);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float values[] = rotateSensorValues(event.values);

        values[0] /= 10;
        values[1] /= 10;
        values[2] /= 10;

        if (mode == 0 && Math.abs(values[2]) < 0.4) {
            mode = 1;
            updateMode();
        }

        if (mode == 1 && Math.abs(values[2]) > 0.8) {
            mode = 0;
            updateMode();
        }

        ((Bubble)findViewById(R.id.bubble)).setValues(values);
        ((Graph)findViewById(R.id.graph)).addValues(values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // source: https://stackoverflow.com/questions/5877780/orientation-from-android-accelerometer
    protected float[] rotateSensorValues(float[] values) {
        float[] results = new float[3];
        int[][] matrices = {
                { 1, -1, 0, 1 }, // ROTATION_0
                {-1, -1, 1, 0 }, // ROTATION_90
                {-1,  1, 0, 1 }, // ROTATION_180
                { 1,  1, 1, 0 }  // ROTATION_270
        };

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int[] matrix = matrices[rotation];

        results[0] = values[matrix[2]] * matrix[0];
        results[1] = values[matrix[3]] * matrix[1];
        results[2] = values[2];

        return results;
    }
}

package com.arunkanai.mypetexpert;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    // Constants for shake detection
    private static final float SHAKE_THRESHOLD_GRAVITY = 0.0f;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private int mShakeCount;

    public interface OnShakeListener {
        void onShake();
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener != null) {
            // Get accelerometer values
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Convert to gravity force
            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // Calculate total gravity force
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Check if the gravity force exceeds the threshold
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                // Check if enough time has passed since the last shake
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // Reset shake count if enough time has passed
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }

                // Update shake timestamp and count
                mShakeTimestamp = now;
                mShakeCount++;

                // Trigger the onShake event for the first shake
                if (mShakeCount == 1) {
                    mListener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for accelerometer
    }
}

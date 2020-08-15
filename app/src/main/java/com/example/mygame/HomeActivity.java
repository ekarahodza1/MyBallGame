package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private ImageView ball;
    private ConstraintLayout frame;
    private float ballX, ballY;

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    private ImageView rupa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setContentView(R.layout.activity_home);

        ball = (ImageView) findViewById(R.id.ball);
        frame = findViewById(R.id.frame);



//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        changePosition();
//                    }
//                });
//            }
//        }, 0, 20);

//        rupa = findViewById(R.id.rupa3);
//        rupa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ballX = 10;
//                ballY = 20;
//                ball.setX(ballX+10);
//                ball.setY(ballY+10);
//            }
//        });
    }

    public void changePosition(SensorEvent event){
        ballX = ball.getX();
        ballY = ball.getY();

        ballX += (int) event.values[1] * 8;
        ballY += (int) event.values[0] * 8;

        System.out.println(ballX);

        if (ballY < 0) ballY = 0;
        if (ballY > frame.getHeight()) ballY = frame.getHeight();

        if (ballX < 0) ballX = 0;
        if (ballX > frame.getWidth()) ballX = frame.getWidth();

        ball.setX(ballX);
        ball.setY(ballY);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            changePosition(event);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
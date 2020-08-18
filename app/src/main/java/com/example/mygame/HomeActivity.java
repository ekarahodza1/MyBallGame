package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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

    private ImageView rupa1, rupa2, rupa3, rupa4, rupa5, rupa;
    private int score = 0;

    private Rect ballRect = new Rect();
    private Rect rupaRect = new Rect();
    private Rect rupa1Rect = new Rect();
    private Rect rupa2Rect = new Rect();
    private Rect rupa3Rect = new Rect();
    private Rect rupa4Rect = new Rect();
    private Rect rupa5Rect = new Rect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setContentView(R.layout.activity_home);

        ball = (ImageView) findViewById(R.id.ball);
        frame = findViewById(R.id.frame);

        rupa1 = findViewById(R.id.rupa1);
        rupa2 = findViewById(R.id.rupa2);
        rupa3 = findViewById(R.id.rupa3);
        rupa4 = findViewById(R.id.rupa4);
        rupa5 = findViewById(R.id.rupa5);
        rupa = findViewById(R.id.rupa);



        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (detectCollision() == -1) {
                            Toast.makeText(getBaseContext(), "You reached the ball with " +  score/10 + " hits!",  Toast.LENGTH_SHORT).show();

                        }
                        else if (detectCollision() == 1) {
                            score++;
                        }
                    }
                });
            }
        }, 0, 40);


    }

    public void changePosition(SensorEvent event){
        ballX = ball.getX();
        ballY = ball.getY();

        ballX += (int) event.values[1] * 8;
        ballY += (int) event.values[0] * 8;

        System.out.println(ballX);

        if (ballY < 0) ballY = 0;
        if (ballY > frame.getHeight() - ball.getHeight()) ballY = frame.getHeight() - ball.getHeight();

        if (ballX < 0) ballX = 0;
        if (ballX > frame.getWidth() - ball.getWidth()) ballX = frame.getWidth() - ball.getWidth();

        ball.setX(ballX);
        ball.setY(ballY);
    }

    public int detectCollision () {
        ball.getHitRect(ballRect);
        rupa.getHitRect(rupaRect);
        rupa1.getHitRect(rupa1Rect);
        rupa2.getHitRect(rupa2Rect);
        rupa3.getHitRect(rupa3Rect);
        rupa4.getHitRect(rupa4Rect);
        rupa5.getHitRect(rupa5Rect);

        if (ballRect.intersect(rupa1Rect) || ballRect.intersect(rupa2Rect) ||
                ballRect.intersect(rupa3Rect) || ballRect.intersect(rupa4Rect) || ballRect.intersect(rupa5Rect)) {
            return 1;
        }
        else if (ballRect.intersect(rupaRect)) return -1;
        else return 0;


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
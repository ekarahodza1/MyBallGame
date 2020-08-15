package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private ImageView rupa1, rupa2, rupa3, rupa4, rupa5, rupa;



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
                        if (!touchImage()) {
                            new AlertDialog.Builder(getBaseContext())
                                    .setTitle("You lost")
                                    .setMessage("Try again?")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    }
                });
            }
        }, 0, 20);


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

    public boolean touchImage () {
        float ballHeight = ball.getHeight();
        float ballWidth = ball.getWidth();
        float imageHight = rupa1.getHeight();
        float imageWidth = rupa1.getWidth();

        if ((ball.getX() + ballWidth >= rupa1.getX() && ball.getX() <= rupa1.getX() + imageWidth) &&
                (ball.getY() >= rupa1.getY() && ball.getY() <= rupa1.getY() + imageHight)) return false;
        if ((ball.getX() + ballWidth >= rupa2.getX() && ball.getX() <= rupa2.getX() + imageWidth) &&
                (ball.getY() >= rupa2.getY() && ball.getY() <= rupa2.getY() + imageHight)) return false;
        if ((ball.getX() + ballWidth >= rupa3.getX() && ball.getX() <= rupa3.getX() + imageWidth) &&
                (ball.getY() >= rupa3.getY() && ball.getY() <= rupa3.getY() + imageHight)) return false;
        if ((ball.getX() + ballWidth >= rupa4.getX() && ball.getX() <= rupa4.getX() + imageWidth) &&
                (ball.getY() >= rupa4.getY() && ball.getY() <= rupa4.getY() + imageHight)) return false;
        if ((ball.getX() + ballWidth >= rupa5.getX() && ball.getX() <= rupa5.getX() + imageWidth) &&
                (ball.getY() >= rupa5.getY() && ball.getY() <= rupa5.getY() + imageHight)) return false;

        return true;
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
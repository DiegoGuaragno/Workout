package com.example.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

public class EndWorkout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_workout);

        lockDeviceRotation(true);

        long tempo=WorkoutEx1.totTempo/1000;
        TextView totTime = (TextView) findViewById(R.id.textViewTotTempo);
        totTime.setText("TOTAL WORKOUT TIME : "+(tempo/60 + ":" + tempo%60));
        TextView instagram = (TextView) findViewById(R.id.textViewSpammino);
        String htmlText = "<A HREF ='https://instagram.com/die.guara?igshid=fliyixpw17i3'>@die.guara</A>";
        instagram.setText(Html.fromHtml(htmlText));
        instagram.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void lockDeviceRotation(boolean value) {
        if (value) {
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        }
    }
}
package com.example.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class WorkoutInfo extends AppCompatActivity {

    int cambioB=0;
    boolean flag1=false;
    boolean flag2=false;
    boolean flag3=false;
    boolean flag4=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_info);

        lockDeviceRotation(true);

        Button buttonStart = (Button) findViewById(R.id.button_start);
        buttonStart.setEnabled(false);
        Button btnEx1 = (Button) findViewById(R.id.buttonEx1);
        Button btnEx2 = (Button) findViewById(R.id.buttonEx2);
        Button btnEx3 = (Button) findViewById(R.id.buttonEx3);
        Button btnEx4 = (Button) findViewById(R.id.buttonEx4);

        btnEx1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeEx(0, btnEx1);
                flag1=true;
                if(flag2 && flag3 && flag4)
                    buttonStart.setEnabled(true);
            }
        });

        btnEx2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeEx(1, btnEx2);
                flag2=true;
                if(flag1 && flag3 && flag4)
                    buttonStart.setEnabled(true);
            }
        });

        btnEx3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeEx(2, btnEx3);
                flag3=true;
                if(flag2 && flag1 && flag4)
                    buttonStart.setEnabled(true);
            }
        });

        btnEx4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeEx(3, btnEx4);
                flag4=true;
                if(flag2 && flag3 && flag1)
                    buttonStart.setEnabled(true);
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a = new Intent(WorkoutInfo.this,WorkoutEx1.class);
                startActivity(a);
            }
        });
    }

    static String[] es = new String[4];


    public void changeEx (int x, Button button){
        AlertDialog.Builder esercizi = new AlertDialog.Builder(this);
        esercizi.setTitle("CHOSE EXERCISE");
        String[] ex = {"Push ups","Pull ups","Chin ups","Dips","Plank"};
        esercizi.setItems(ex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int scelta) {
                switch (scelta)
                {
                    case 0:
                        es[x]="Push ups";
                        button.setText("Push ups");
                        break;
                    case 1:
                        es[x]="Pull ups";
                        button.setText("Pull ups");
                        break;
                    case 2:
                        es[x]="Chin ups";
                        button.setText("Chin ups");
                        break;
                    case 3:
                        es[x]="Dips";
                        button.setText("Dips");
                        break;
                    case 4:
                        es[x]="Plank";
                        button.setText("Plank");
                        break;
                }
            }
        });
        AlertDialog alert = esercizi.create();
        alert.show();
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
package com.example.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class WorkoutEx1 extends AppCompatActivity {

    private int n=0;
    private long tempo=0;
    CountDownTimer timer;
    private boolean isRunning=false;
    private int numSets=0;
    private long riposo=0;

    private boolean controlloSet=false;
    private boolean controlloRest=false;
    private boolean controlloTempo=false;

    private boolean controlloEX_REST=true;

    static long totTempo=0;
    private long tempoInizio=0;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lockDeviceRotation(true);

        setContentView(R.layout.activity_workout_ex1);
        TextView exercises = (TextView) findViewById(R.id.textEx1);
        TextView n_ex = (TextView) findViewById(R.id.textViewEx);

        Button setSet = (Button) findViewById(R.id.btn_sets);
        EditText nSets = (EditText) findViewById(R.id.inputSets);
        TextView txtSets = (TextView) findViewById(R.id.textViewSets);

        EditText inputTime = (EditText) findViewById(R.id.InputTime1);
        Button setTime = (Button) findViewById(R.id.Set_time1);

        exercises.setText(WorkoutInfo.es[n]);
        Button setRest = (Button) findViewById(R.id.set_rest);
        EditText inputRest = (EditText) findViewById(R.id.inputRest);

        Button btnStart = (Button) findViewById(R.id.buttonStart);
        btnStart.setVisibility(View.INVISIBLE);

        Button btnNext = (Button) findViewById(R.id.button_next1);

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    if(numSets==1 && n==3)
                    {
                        totTempo=System.currentTimeMillis()-tempoInizio;

                        Intent a = new Intent(WorkoutEx1.this,EndWorkout.class);
                        startActivity(a);
                    }
                    //AZZERARE IL TEMPO PRIMA CHE FINISCA
                    if (isRunning && controlloEX_REST==true) {
                        timer.cancel();
                        tempo = 0;
                        updateCountDownText(tempo);
                        isRunning = false;
                        controlloEX_REST=false;
                    }else if(isRunning && controlloEX_REST==false)
                    {
                        timer.cancel();
                        riposo = 0;
                        updateCountDownText(riposo);
                        isRunning = false;
                        controlloEX_REST=true;
                    }
                    //NEL CASO DI PIU' SET ALTERNO IL REST AL TEMPO DELL' ESERCIZIO
                    if(controlloEX_REST) {
                        //NEL CASO DI PIU' SET UTILIZZO I DATI PRECEDENTI
                        if (numSets > 1) {
                            numSets--;
                            txtSets.setText("" + numSets);
                            exercises.setText(WorkoutInfo.es[n]);
                            setTime.callOnClick();
                            btnStart.callOnClick();
                        }
                        //CAMBIO ESERCIZIO
                        else {
                            txtSets.setText("" + numSets);
                            n++;
                            n_ex.setText("EXERCISE " + (n + 1));
                            exercises.setText(WorkoutInfo.es[n]);
                            setSet.setEnabled(true);
                            setRest.setEnabled(true);
                            setTime.setEnabled(true);
                        }
                    }
                    else
                    {
                        riposo = Long.parseLong(inputRest.getText().toString())*1000;
                        startRest();
                        exercises.setText("REST");
                    }
                }catch (Exception E)
                {
                    Toast.makeText(getApplicationContext(),"YOU HAVE TO SET ALL THE SETTINGS AND THAN CLICK THE BUTTON", Toast.LENGTH_LONG).show();
                }
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tempo = Long.parseLong(inputTime.getText().toString())*1000;
                    setTime.setEnabled(false);
                    controlloTempo=true;
                    if(controlloSet && controlloRest)
                        btnStart.setVisibility(View.VISIBLE);
                }catch (Exception E)
                {
                    Toast.makeText(getApplicationContext(),"YOU HAVE TO SET THE TIME AND CLICK THE BUTTON", Toast.LENGTH_LONG).show();
                }
            }
        });

        setSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    numSets=Integer.parseInt(nSets.getText().toString());
                    txtSets.setText(""+numSets);
                    setSet.setEnabled(false);
                    controlloSet=true;
                    if(controlloTempo && controlloRest)
                        btnStart.setVisibility(View.VISIBLE);
                }catch (Exception E)
                {
                    Toast.makeText(getApplicationContext(),"YOU HAVE TO SET THE SETS AND CLICK THE BUTTON", Toast.LENGTH_LONG).show();
                }
            }
        });

        setRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    riposo = Long.parseLong(inputRest.getText().toString())*1000;
                    setRest.setEnabled(false);
                    controlloRest=true;
                    if(controlloSet && controlloTempo)
                        btnStart.setVisibility(View.VISIBLE);
                }catch (Exception E)
                {
                    Toast.makeText(getApplicationContext(),"YOU HAVE TO SET THE REST AND CLICK THE BUTTON", Toast.LENGTH_LONG).show();
                }
                }

        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btnStart.setVisibility(View.INVISIBLE);
                    startTimer();
                    controlloSet=false;
                    controlloTempo=false;
                    controlloRest= false;
                    if(!flag) {
                        tempoInizio = System.currentTimeMillis();
                        flag=true;
                    }
                }catch (Exception E){}
            }
        });
    }

    private void startTimer(){
        isRunning=true;
        timer = new CountDownTimer( tempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempo = millisUntilFinished;
                updateCountDownText(tempo);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Done!", Toast.LENGTH_SHORT).show();
                isRunning=false;
                controlloEX_REST=false;
                }
        }.start();
    }

    private void startRest(){
        isRunning=true;
        timer = new CountDownTimer( riposo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                riposo = millisUntilFinished;
                updateCountDownText(riposo);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Done!", Toast.LENGTH_SHORT).show();
                isRunning=false;
                controlloEX_REST=true;
            }
        }.start();
    }

    private void updateCountDownText(long x){
        TextView txtTimer = (TextView) findViewById(R.id.timer);
        int minutes = (int) (x / 1000) / 60;
        int seconds = (int) (x / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds) ;
        txtTimer.setText(timeLeft);
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
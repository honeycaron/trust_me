package com.example.alarmproject;

import android.app.AlarmManager;

import android.app.DatePickerDialog;

import android.app.PendingIntent;

import android.app.TimePickerDialog;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;

import android.widget.DatePicker;

import android.widget.TextView;

import android.widget.TimePicker;

import android.widget.Toast;


import java.text.SimpleDateFormat;

import java.util.Calendar;



public class AlarmActivity extends AppCompatActivity {



    //시간 설정을 위한 객체

    Calendar Time;



    //알람 설정을 위한 객체

    private Intent intent;

    private PendingIntent ServicePending;

    private AlarmManager alarmManager;



    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 mm분 ss초");



    TextView textView;





    DatePickerDialog.OnDateSetListener eDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {

            Time.set(Calendar.YEAR, year);

            Time.set(Calendar.MONTH, monthofYear);

            Time.set(Calendar.DAY_OF_MONTH, dayOfMonth);



            updateLabel();



        }

    };

    private TimePickerDialog.OnTimeSetListener sTimeSetListener =

            new TimePickerDialog.OnTimeSetListener() {



                @Override

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    // TODO Auto-generated method stub;



                    Time.set(Calendar.HOUR_OF_DAY, hourOfDay);

                    Log.d("check", "1 = " + hourOfDay);

                    Time.set(Calendar.MINUTE, minute);

                    Log.d("check", "2 = " + minute);

                    Time.set(Calendar.SECOND, 0);



                    updateLabel();

                }

            };

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);



        //타임 피커, 데이트 피커 리스너 및 아이디 등록

        Time = Calendar.getInstance();



        //알람 설정, 해제 버튼

        Button.OnClickListener bClickListener = new View.OnClickListener() {



            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.setAlarm:



                        setAlarm();



                        break;



                    case R.id.removeAlarm:



                        removeAlarm();



                        break;



                    case R.id.button:

                        new TimePickerDialog(AlarmActivity.this, sTimeSetListener, Time.get(Calendar.HOUR_OF_DAY), Time.get(Calendar.MINUTE), false).show();





                        new DatePickerDialog(AlarmActivity.this, eDateSetListener, Time.get(Calendar.YEAR),

                                Time.get(Calendar.MONTH),

                                Time.get(Calendar.DAY_OF_MONTH)).show();

                        break;



                    case R.id.repeatAlarm:



                        setRepeatAlarm();

                        break;



                }

            }

        };



        findViewById(R.id.setAlarm).setOnClickListener(bClickListener);

        findViewById(R.id.removeAlarm).setOnClickListener(bClickListener);

        findViewById(R.id.button).setOnClickListener(bClickListener);

        findViewById(R.id.repeatAlarm).setOnClickListener(bClickListener);



        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        textView = (TextView)findViewById(R.id.textView);



        updateLabel();

    }

    private void updateLabel() {

        textView.setText(simpleDateFormat.format(Time.getTime()));

    }





    void setAlarm(){

        //Receiver로 보내기 위한 인텐트

        //intent = new Intent(this, AlarmReceiver.class);

        intent = new Intent("AlarmReceiver");

        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);





        ServicePending = PendingIntent.getBroadcast(

                AlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        //정해진 시간에 알람 설정

        alarmManager.set(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), ServicePending);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), 1000, ServicePending); // Millisec * Second * Minute



        Toast.makeText(getBaseContext(), "알람 설정" + Time.getTime(), Toast.LENGTH_SHORT).show();





    }

    void removeAlarm(){



        intent = new Intent("AlarmReceiver");

        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);





        ServicePending = PendingIntent.getBroadcast(

                AlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        Toast.makeText(getBaseContext(), "알람 해제" + Time.getTime(), Toast.LENGTH_SHORT).show();



        alarmManager.cancel(ServicePending);



    }

    void setRepeatAlarm(){

        //Receiver로 보내기 위한 인텐트

        //intent = new Intent(this, AlarmReceiver.class);

        intent = new Intent("AlarmReceiver");

        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);





        ServicePending = PendingIntent.getBroadcast(

                AlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("ServicePending : ",""+ServicePending.toString());



        //정해진 시간에 알람 설정

        // alarmManager.set(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), ServicePending);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), 20000, ServicePending); // Millisec * Second * Minute



        Toast.makeText(getBaseContext(), "알람 설정" + Time.getTime(), Toast.LENGTH_SHORT).show();



    }

}


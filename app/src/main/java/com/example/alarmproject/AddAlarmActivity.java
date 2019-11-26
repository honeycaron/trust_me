package com.example.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity {

    TimePicker mtimePicker;
    Button btnAdd;
    ImageButton btnLocation;
    EditText editTextName;
    TextView textViewLocation;
    CheckBox[] checkBoxes;
    Spinner spinner;
    Switch switchMessage;
    Switch switchPassword;

    String passwd;
    boolean isRepeat;
    AlarmManager mAlarmManager;
    AlarmData mAlarmData;
    int hour, min;
    String textDays;
    String[] daysName = {"월","화","수","목","금","토","일"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        mtimePicker = (TimePicker)findViewById(R.id.timepicker);
        btnAdd = (Button)findViewById(R.id.btnadd);
        btnLocation = (ImageButton)findViewById(R.id.btnloc);
        editTextName = (EditText)findViewById(R.id.editname);
        textViewLocation = (TextView)findViewById(R.id.tvlocation);
        checkBoxes = new CheckBox[7];
        for(int i=0;i<7;i++){
            int k = getResources().getIdentifier("check"+(i+1),"id",getPackageName());
            checkBoxes[i] = (CheckBox)findViewById(k);
        }
        spinner = (Spinner)findViewById(R.id.spinner);
        switchMessage = (Switch)findViewById(R.id.switchmessage);
        switchPassword = (Switch)findViewById(R.id.switchpassword);

        ArrayList arrayList = new ArrayList<>();
        arrayList.add("알람음");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(spinnerAdapter);

        passwd = "0";
        mAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

    }


    public void addAlarm(View view) {

        AlarmData newAlarm = new AlarmData();

        newAlarm.setName(editTextName.getText().toString());

        // 시간

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            hour = mtimePicker.getHour();
            min = mtimePicker.getMinute();
        }
        else{
            hour = mtimePicker.getCurrentHour();
            min = mtimePicker.getCurrentMinute();
        }

        newAlarm.setHour(hour);
        newAlarm.setMinute(min);

        // 요일 - 선택된 요일은 1 아니면 0, 월화수목금토일 순서
        String days = "";
        textDays = "";
        ArrayList<String> dayString = new ArrayList<>();
        for(int i=0;i<7;i++) {
            if (checkBoxes[i].isChecked()){
                days += "1";
                dayString.add(daysName[i]);
            }
            else
                days += "0";
        }

        newAlarm.setDays(days);

        newAlarm.setLocation(textViewLocation.getText().toString());
        newAlarm.setMessage(switchMessage.isChecked());
        newAlarm.setHasPassword(switchPassword.isChecked());
        newAlarm.setPassword(passwd);
        newAlarm.setOnoff(true);


        // main의 리스트뷰에 추가하기 위함
        ListViewItem newItem = new ListViewItem();
        newItem.min = min;
        if(hour < 12){
            if(hour == 0)
                newItem.hour = 12;
            else
                newItem.hour = hour;
            newItem.ampm = "오전";
        }
        else{           // 12 ~ 23
            if(hour == 12)
                newItem.hour = 12;
            else
                newItem.hour = hour-12;
            newItem.ampm = "오후";
        }
        newItem.name = editTextName.getText().toString();
        newItem.onoff = true;
        MainActivity.allItems.add(newItem);

        for(int i=0;i<dayString.size();i++){
            if(i == dayString.size()-1) {
                textDays += dayString.get(i);
            }else{
                textDays += dayString.get(i) + ", ";
            }
        }

        newItem.days = textDays;

        registAlarm();
        Toast.makeText(this,"저장하였습니다.",Toast.LENGTH_LONG).show();
        finish();
    }
    private void registAlarm(){

        cancelAlarm();

        boolean[] week = new boolean[7];
        for(int i=0;i<7;i++){
            if(checkBoxes[i].isChecked()){
                week[i] = true;
                isRepeat = true;
            }
            else{
                week[i] = false;
            }
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        long triggerTime = 0;
        long intervalTime = 24*60*60*1000; // 24시간

        if(isRepeat){
            intent.putExtra("one_time", false);
            intent.putExtra("repeat_day", week);
            PendingIntent pending = getPendingIntent(intent);
            triggerTime = setTriggerTime();
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,intervalTime,pending);
        }
        else{
            intent.putExtra("one_time", true);
            PendingIntent pending = getPendingIntent(intent);
            triggerTime = setTriggerTime();
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
        }
        Toast.makeText(getApplicationContext(),"alarm!",Toast.LENGTH_LONG).show();

    }

    private long setTriggerTime() {
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, hour);
        curTime.set(Calendar.MINUTE, min);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

    private PendingIntent getPendingIntent(Intent intent) {
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }
    private void cancelAlarm()
    {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pending = getPendingIntent(intent);
        this.mAlarmManager.cancel(pending);
    }

}

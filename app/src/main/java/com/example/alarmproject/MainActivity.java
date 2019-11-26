package com.example.alarmproject;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    public AlarmListAdapter adapter;
    public static ArrayList<ListViewItem> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button)findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddAlarmActivity.class);
                startActivityForResult(intent,11);
            }
        });
        allItems = new ArrayList<>();
        // 디비를 사용한다면 디비의 알람들을 넣는다.

        listView = (ListView)findViewById(R.id.listview);
        adapter = new AlarmListAdapter(this,allItems);
        listView.setAdapter(adapter);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11){
            adapter = new AlarmListAdapter(this,allItems);
            listView.setAdapter(adapter);
        }
    }
}

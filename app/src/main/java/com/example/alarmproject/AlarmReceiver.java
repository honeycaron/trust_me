package com.example.alarmproject;
import android.annotation.SuppressLint;
import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;


import android.media.RingtoneManager;

import android.net.Uri;

import android.os.PowerManager;

import android.support.v4.app.NotificationCompat;

import android.util.Log;

import android.view.Gravity;

import android.widget.Toast;



import java.io.File;



public class AlarmReceiver extends BroadcastReceiver

{



    Context context;



    @Override

    public void onReceive(final Context context, Intent intent)

    {

        this.context = context;

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

        wakeLock.acquire();





        Log.d("alarm","gogo");





        PendingIntent pendingIntent;



        Toast toast = Toast.makeText(context, "알람이 울립니다.", Toast.LENGTH_LONG);

        toast.setGravity(Gravity.TOP,0,200);

        toast.show();



        wakeLock.release();







        try {

            intent = new Intent(context, RemoveAlarmActivity.class);

            pendingIntent = PendingIntent.getActivity(context, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Log.d("ServicePending++ : ",""+pendingIntent.toString());

            pendingIntent.send();

        } catch (PendingIntent.CanceledException e) {

            e.printStackTrace();

        }

        notification();

    }



    void notification() {



        Intent intent = new Intent();

        //알림 사운드

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //큰 아이콘

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),android.R.drawable.ic_menu_gallery); // 아이콘 ic_menu_gallery를 띄워준다.





        //노티피케이션을 생성할때 매개변수는 PendingIntent이므로 Intent를 PendingIntent로 만들어주어야함.

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);



        //노티피케이션 빌더 : 위에서 생성한 이미지나 텍스트, 사운드등을 설정해줍니다.

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)

                .setSmallIcon(android.R.drawable.ic_menu_gallery)

                .setLargeIcon(bitmap) // 이미지

                .setContentTitle("알람") // 푸시의 타이틀이다.

                .setContentText("알람 딸랑딸랑~") // 서버에서 받은 텍스트

                .setAutoCancel(true)

                .setSound(soundUri) // 푸시가 날아올때 사운드 설정

                .setContentIntent(pendingIntent); // 푸시를 누르면 위에서 설정한 intent에 의해 화면이 넘어간다.



        NotificationManager notificationManager =

                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        //노티피케이션을 생성합니다.

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());





    }



}


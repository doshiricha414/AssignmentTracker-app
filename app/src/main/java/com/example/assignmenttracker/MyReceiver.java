package com.example.assignmenttracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
public class MyReceiver extends BroadcastReceiver {
    int MID=0;
    ArrayList<HashMap<String, String>> nameList;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        int count = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        nameList = gson.fromJson(json, type);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd");
        String month = formatter.format(todayDate);
        String day = formatter1.format(todayDate);
        for (int i = 0; i < nameList.size(); i++) {
            String date = nameList.get(i).get("date");
            String date_arr[] = date.split("/");
            if (Integer.parseInt(month) == Integer.parseInt(date_arr[1])) {
                int check = Integer.parseInt(date_arr[0]) - Integer.parseInt(day);
                if (check == 1) {
                    ++count;
                }
            } else {
                count = 0;
            }
        }
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, Setting.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(count + " " + "Assignments Due For Tomorrow").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;

    }
}

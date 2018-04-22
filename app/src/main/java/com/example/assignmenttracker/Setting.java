package com.example.assignmenttracker;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.R.attr.x;
import static android.R.attr.y;
import static com.example.assignmenttracker.R.id.del1;
import static com.example.assignmenttracker.R.id.del2;
import static com.example.assignmenttracker.R.id.del3;
import static com.example.assignmenttracker.R.id.select;

public class Setting extends AppCompatActivity {

    String status = "AM";

    TextView select,del1,del2,del3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        select = (TextView) findViewById(R.id.select);

        del1 = (TextView) findViewById(R.id.del1);
        del2 = (TextView) findViewById(R.id.del2);
        del3 = (TextView) findViewById(R.id.del3);

        loadData();

        del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Setting.this);
                alert.setTitle("Delete all the completed assignment?");
                alert.setMessage("This action is irreversible!");
                alert.setPositiveButton("SOUNDS GOOD!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences preferences = getSharedPreferences("shared", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.apply();
                        editor.clear();
                        editor.commit();
                        Intent i = new Intent(Setting.this, MainActivity.class);
                        startActivity(i);
                        //finish();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create();
                alert.show();
            }
        });

        del1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Setting.this);
                alert.setTitle("Delete all the assignment?");
                alert.setMessage("This action is irreversible!");
                alert.setPositiveButton("SOUNDS GOOD!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = getSharedPreferences("shared preference", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.apply();
                        editor.clear();
                        editor.commit();
                        Intent i = new Intent(Setting.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create();
                alert.show();
            }
        });

        del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Setting.this);
                alert.setTitle("Delete all the data?");
                alert.setMessage("This action is irreversible!");
                alert.setPositiveButton("SOUNDS GOOD!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = getSharedPreferences("shared preference", MODE_PRIVATE);
                        SharedPreferences preferences2 = getSharedPreferences("shared", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        SharedPreferences.Editor editor2 = preferences2.edit();
                        editor.apply();
                        editor.clear();
                        editor.commit();
                        editor2.apply();
                        editor2.clear();
                        editor2.commit();
                        Intent i = new Intent(Setting.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create();
                alert.show();
            }
        });




        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Setting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        int hr =selectedHour-12;

                        if(selectedMinute!=0) {
                            if (hr < 0) {
                                status = "AM";
                                select.setText(selectedHour + ":" + selectedMinute + " " + status);
                            } else {
                                status = "PM";
                                select.setText(hr + ":" + selectedMinute + " " + status);
                            }
                        }
                        else{
                            if (hr < 0) {
                                status = "AM";
                                select.setText(selectedHour + ":" + "00" + " " + status);
                            } else {
                                status = "PM";
                                select.setText(hr + ":" + "00" + " " + status);
                            }
                        }
                        saveData(selectedHour,selectedMinute);
                        mcurrentTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        mcurrentTime.set(Calendar.MINUTE, selectedMinute);
                        mcurrentTime.set(Calendar.SECOND, 0);
                        Toast.makeText(Setting.this, "HOD="+Calendar.HOUR_OF_DAY, Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Setting.this, MyReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Setting.this, 0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                        am.setRepeating(AlarmManager.RTC_WAKEUP, mcurrentTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

                    }
                }, hour, minute, false);//NO 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
    private void saveData(int x, int y) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor = sharedPreferences.edit();
        editor.putInt("x",x);
        editor.putInt("y",y);
        editor.commit();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        int x,y;
        int a = sharedPreferences.getInt("x",10);
        int b = sharedPreferences.getInt("y",10);
        int hr =a-12;
        x=a;
        y=b;
        if(y!=0) {
            if (hr < 0) {
                status = "AM";
                select.setText(x + ":" + y + " " + status);
            } else {
                status = "PM";
                select.setText(hr + ":" + y + " " + status);
            }
        }
        else{
            if (hr < 0) {
                status = "AM";
                select.setText(x + ":" + "00" + " " + status);
            } else {
                status = "PM";
                select.setText(hr + ":" + "00" + " " + status);
            }
        }
    }
}

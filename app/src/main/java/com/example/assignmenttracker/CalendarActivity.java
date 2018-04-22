package com.example.assignmenttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.assignmenttracker.R.id.date1;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CalendarActivity extends AppCompatActivity {
    ListView eventList;
    ArrayList<HashMap<String, String>> nameList;
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        eventList = (ListView) findViewById(R.id.event_list);
        Intent i = getIntent();
        nameList = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("val");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        for(int j=0;j<nameList.size();j++){
                String date_string = new String(nameList.get(j).get("date"));
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = df.parse(date_string);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            long epoch = date.getTime();
            Event ev1 = new Event(Color.RED,epoch," Day");
            compactCalendar.addEvent(ev1);
        }
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                int count=0;
                Date date1=null,date2=null;
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date_str = df.format(dateClicked);
                try {
                     date1 = df.parse(date_str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for(int j=0;j<nameList.size();j++){
                    try {
                         date2 = df.parse((nameList.get(j).get("date").toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                   if(date1.equals(date2)){
                       count++;
                        }
                }
                String clicked_item[] = new String[count];
                count=0;
                for(int j=0;j<nameList.size();j++){
                    try {
                        date2 = df.parse((nameList.get(j).get("date").toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(date1.equals(date2)){
                        clicked_item[count++] = nameList.get(j).get("asg_name");
                    }
                }
                ArrayAdapter<String> lva = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        clicked_item
                );
                eventList.setAdapter(lva);
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}

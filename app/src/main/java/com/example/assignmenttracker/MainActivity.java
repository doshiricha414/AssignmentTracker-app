package com.example.assignmenttracker;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

import static android.R.attr.id;
import static android.content.Context.MODE_PRIVATE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.assignmenttracker.R.id.date1;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int NOTIFICATION_REMINDER_NIGHT = 3;
    private ListView listView;
    private CustomListViewAdapter customListViewAdapter;
    ArrayList<HashMap<String, String>> nameList;
    String names[];
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.item1);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.item2);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.item3);
        FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.item4);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(),AddAssignment.class);
                p.putExtra("id",4);
                startActivityForResult(p,1);
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(),AddAssignment.class);
                p.putExtra("id",1);
                startActivityForResult(p,1);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(),AddAssignment.class);
                p.putExtra("id",2);
                startActivityForResult(p,1);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(),AddAssignment.class);
                p.putExtra("id",3);
                startActivityForResult(p,1);
            }
        });
        loadData();
        settingListView();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                names = data1.getExtras().getStringArray("stringArray");
                loadData();
                    HashMap<String, String> data = new HashMap<>();
                    data.put("asg_name", names[0]);
                    data.put("sub", names[1]);
                    data.put("date", names[2]);
                    data.put("note", names[3]);
                    data.put("type",names[4]);
                    nameList.add(data);
                Collections.sort(nameList, new MapComparator(names[2]));

               settingListView();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }
            saveData();
        }
    }
    private void settingListView(){
        listView = (ListView) findViewById(R.id.list);

        for(int i=0;i<nameList.size();i++) {
            Collections.sort(nameList, new MapComparator(nameList.get(i).get("date")));
        }
        customListViewAdapter = new CustomListViewAdapter(getApplicationContext(), nameList,MainActivity.this);

        listView.setAdapter(customListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int myPosition = position;
                String itemClickedId = listView.getItemAtPosition(myPosition).toString();
                Intent i =new Intent(MainActivity.this,ViewActivity.class);
                i.putExtra("list",nameList);
                i.putExtra("pos",position);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Id clicked:" + itemClickedId, Toast.LENGTH_LONG).show();
            }
        });
         }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nameList);
        editor.putString("task list",json);
        editor.apply();
        editor.clear();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
        nameList = gson.fromJson(json,type);
        if(nameList==null){
            nameList = new ArrayList<>();
        }
        else{
            listView = (ListView) findViewById(R.id.list);
            for(int i=0;i<nameList.size();i++){                         //Yaaay Finally It Worked : Sorting while Loading...
            Collections.sort(nameList, new MapComparator(nameList.get(i).get("date")));
            }
            customListViewAdapter = new CustomListViewAdapter(getApplicationContext(), nameList,MainActivity.this);
            listView.setAdapter(customListViewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int myPosition = position;
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void gotoCalendarActivity(MenuItem item){
        Intent i = new Intent(MainActivity.this,CalendarActivity.class);
        i.putExtra("val",nameList);
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
              int id = item.getItemId();
        gotoCalendarActivity(item);
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Assignment) {

        }  else if (id == R.id.completed) {
            Intent i = new Intent(this,completedAssignments.class);
            startActivity(i);

        } else if (id == R.id.about) {
            Intent i = new Intent(this,Setting.class);
            startActivity(i);
        }
        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout);;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
class MapComparator implements Comparator<Map<String, String>>
{
    private final String key;
    Date date1,date2;
    public MapComparator(String key)
    {
        this.key = key;
    }
    public int compare(Map<String, String> first,Map<String, String> second) {
        String firstValue = first.get("date");
        String secondValue = second.get("date");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
             date1 = sdf.parse(firstValue);
             date2 = sdf.parse(secondValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date1.after(date2))
            return 1;
        else if(date1.before(date2))
            return -1;
        else
        return 0;
    }
}

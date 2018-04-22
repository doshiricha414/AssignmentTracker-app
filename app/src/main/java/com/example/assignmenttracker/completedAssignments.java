package com.example.assignmenttracker;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
public class completedAssignments extends AppCompatActivity {
    ArrayList<HashMap<String, String>> nameList;
    ArrayList<HashMap<String, String>> completedList;
    private ListView listView;
    private Adapter2 customListViewAdapter;
    HashMap<String,String>newList = new HashMap<>();
    String assignment_name,subject,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_assignments);
        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        int position = i.getIntExtra("position",0);
        nameList = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("list");
        loadData();
        settingListView(id,position);
        listView = (ListView) findViewById(R.id.list2);
        customListViewAdapter = new Adapter2(getApplicationContext(), completedList);
        listView.setAdapter(customListViewAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder alert = new AlertDialog.Builder(completedAssignments.this);
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        completedList.remove(pos);
                        customListViewAdapter.notifyDataSetChanged();
                        saveData();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return false;
            }
        });
        saveData();
    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(completedList);
        editor.putString("task",json);
        editor.apply();
        editor.clear();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task",null);
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
        completedList = gson.fromJson(json,type);
        if(completedList==null){
            completedList = new ArrayList<>();
        }
        else{
            listView = (ListView) findViewById(R.id.list2);
            customListViewAdapter = new Adapter2(getApplicationContext(), completedList);
            listView.setAdapter(customListViewAdapter);
        }
    }
    private void settingListView(int id,int position){
        if(id == 111){
            HashMap<String,String>mNames = new HashMap<>();
            mNames = nameList.get(position);
            assignment_name = mNames.get("asg_name");
            subject = mNames.get("sub");
            date = mNames.get("date");
            newList.put("key1",assignment_name);
            newList.put("key2",subject);
            newList.put("key3",date);
            completedList.add(newList);
            listView = (ListView) findViewById(R.id.list2);
            customListViewAdapter = new Adapter2(getApplicationContext(), completedList);
            listView.setAdapter(customListViewAdapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final int pos = position;
                    AlertDialog.Builder alert = new AlertDialog.Builder(completedAssignments.this);
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            completedList.remove(pos);
                            customListViewAdapter.notifyDataSetChanged();
                            saveData();
                            dialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    return false;
                }
            });

        }


    }
}

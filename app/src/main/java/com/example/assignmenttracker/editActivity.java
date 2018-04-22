package com.example.assignmenttracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.assignmenttracker.R.id.subject;

public class editActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> nameList;
    String assignment_name,subject,date,type_info,note_info;
    EditText name_of_asg,type,sub_of_asg,due_date,notes;
    Button save ;
    String new_asg,new_sub,new_date,new_note,new_type;
    HashMap<String,String>mNames;
    int position;
    private Calendar mcalendar;
    private int day,month,year;

    CustomListViewAdapter customListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        name_of_asg = (EditText) findViewById(R.id.name);
        type = (EditText) findViewById(R.id.ID);
        sub_of_asg = (EditText) findViewById(R.id.subject);
        due_date = (EditText) findViewById(R.id.date1);
        notes = (EditText) findViewById(R.id.notes);
        save = ( Button) findViewById(R.id.save);
        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        position = i.getIntExtra("position",0);
        nameList = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("list");
        if(id == 111){
            mNames = new HashMap<>();
            mNames = nameList.get(position);
            assignment_name = mNames.get("asg_name");
            subject = mNames.get("sub");
            date = mNames.get("date");
            type_info = mNames.get("type");
            note_info = mNames.get("note");
            name_of_asg.setText(assignment_name);
            type.setText(type_info);
            sub_of_asg.setText(subject);
            due_date.setText(date);
            notes.setText(note_info);
        }


        due_date =(EditText)findViewById(R.id.date1);
        due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });
        mcalendar = Calendar.getInstance();
        day=mcalendar.get(Calendar.DAY_OF_MONTH);
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*customListViewAdapter = new CustomListViewAdapter(getApplicationContext(),nameList);
                customListViewAdapter.notifyDataSetChanged();*/
                new_asg = name_of_asg.getText().toString();
                new_type = type.getText().toString();
                new_sub = sub_of_asg.getText().toString();
                new_date = due_date.getText().toString();
                new_note = notes.getText().toString();

                mNames = nameList.get(position);
                mNames.put("asg_name",new_asg);
                mNames.put("sub",new_sub);
                mNames.put("date",new_date);
                mNames.put("type",new_type);
                mNames.put("note",new_note);

                SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(nameList);
                editor.putString("task list",json);
                editor.apply();
                editor.clear();

                nameList.remove(position);

                Intent i = new Intent(editActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                //Toast.makeText(getApplicationContext(),"hey",Toast.LENGTH_LONG).show();

            }
        });
    }
    public void DateDialog(){
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear+=1;
                due_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(editActivity.this, listener, year, month, day);
        dpDialog.show();
    }
}

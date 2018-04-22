package com.example.assignmenttracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static android.R.attr.id;

public class AddAssignment extends AppCompatActivity {
    EditText name;
    ArrayList<String> list_of_asg = new ArrayList<>();
    private Calendar mcalendar;
    private EditText due_date,subject,notes,id;
    private int day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Button save = (Button)findViewById(R.id.save);
        name =(EditText)findViewById(R.id.name);
        subject =(EditText) findViewById(R.id.subject);
        notes = (EditText) findViewById(R.id.notes);
        id = (EditText) findViewById(R.id.ID);
        Intent p = getIntent();
        int a = p.getIntExtra("id",-1);
        //Toast.makeText(getApplicationContext(),"id="+a,Toast.LENGTH_LONG).show();

        if(a == 1)
        id.setText("Assignment", TextView.BufferType.EDITABLE);
        else if(a==2)
            id.setText("Quiz", TextView.BufferType.EDITABLE);
        else if(a==3)
            id.setText("Project", TextView.BufferType.EDITABLE);
        else
            id.setText("Test", TextView.BufferType.EDITABLE);

        id.setKeyListener(null);

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

                String str = name.getText().toString();
                String sub_var = subject.getText().toString();
                String date_var = due_date.getText().toString();
                String note_var = notes.getText().toString();
                String type_var = id.getText().toString();

                if(str.length() == 0){
                    Toast.makeText(AddAssignment.this, "Assignment name is missing", Toast.LENGTH_SHORT).show();
                }
                else if(sub_var.length() == 0){
                    Toast.makeText(AddAssignment.this, "Subject name is missing", Toast.LENGTH_SHORT).show();
                }
                else if(date_var.length() == 0){
                    Toast.makeText(AddAssignment.this, "Due Date is missing", Toast.LENGTH_SHORT).show();
                }
                else if(note_var.length() == 0){
                    Toast.makeText(AddAssignment.this, "Note is missing", Toast.LENGTH_SHORT).show();
                }
                else {
                    list_of_asg.add(str);
                    list_of_asg.add(sub_var);
                    list_of_asg.add(date_var);
                    list_of_asg.add(note_var);
                    list_of_asg.add(type_var);
                    String stringArray[] = new String[list_of_asg.size()];
                    for (int j = 0; j < list_of_asg.size(); j++) {
                        stringArray[j] = list_of_asg.get(j);
                    }

                    Intent returnIntent = new Intent(AddAssignment.this, MainActivity.class);
                    returnIntent.putExtra("stringArray", stringArray);

                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }

            }
        });
           }
    public void DateDialog(){
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
            {
                monthOfYear+=1;
                due_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(AddAssignment.this, listener, year, month, day);
        dpDialog.show();
    }
}


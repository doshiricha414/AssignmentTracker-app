package com.example.assignmenttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> nameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        TextView date_val,asg_val,sub_val,note_val;
        date_val = (TextView) findViewById(R.id.date_val);
        asg_val = (TextView)findViewById(R.id.asg_val);
        sub_val = (TextView)findViewById(R.id.sub_val);
        note_val = (TextView) findViewById(R.id.note_val);
        Intent i = getIntent();
        nameList = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("list");
        int position = i.getIntExtra("pos",111);
        date_val.setText(nameList.get(position).get("date"));
        asg_val.setText(nameList.get(position).get("asg_name"));
        sub_val.setText(nameList.get(position).get("sub"));
        note_val.setText(nameList.get(position).get("note"));

    }
}

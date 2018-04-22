package com.example.assignmenttracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.assignmenttracker.R.id.done;

/**
 * Created by RICHA on 3/11/2018.
 */

public class Adapter2 extends BaseAdapter {

    private Context mcontext;
    private ArrayList<HashMap<String,String>> names;
    private static LayoutInflater inflater= null;


    public Adapter2(Context applicationContext, ArrayList<HashMap<String, String>> nameList) {
        mcontext = applicationContext;
        names = nameList;
        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder2 mHolder;
        if(convertView == null){
            view = inflater.inflate(R.layout.completed_assignment_row,null);
            mHolder = new ViewHolder2();
        }
        else {
            mHolder = (ViewHolder2) convertView.getTag();
        }

        mHolder.mText2 = (TextView) view.findViewById(R.id.asg_name2);
        mHolder.subject2 = (TextView) view.findViewById(R.id.subject_name2);
        mHolder.due_date2 = (TextView) view.findViewById(R.id.due2);


        view.setTag(mHolder);
        HashMap<String,String>mNames = new HashMap<>();

        mNames = names.get(position);
        mHolder.mText2.setText(mNames.get("key1"));

        mHolder.subject2.setText(mNames.get("key2"));

        mHolder.due_date2.setText(mNames.get("key3"));
        return view;
    }
}
class ViewHolder2 {
    TextView mText2,subject2,due_date2;
}
package com.example.assignmenttracker;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.media.CamcorderProfile.get;
import static com.example.assignmenttracker.R.id.asg_name;


public class fragmentAdapter extends BaseAdapter {

    Context mcontext;
    private static LayoutInflater inflater= null;

    ArrayList<HashMap<String, String>> List;
    HashMap<String,String>mNames = new HashMap<>();


    public fragmentAdapter(Context context, ArrayList<HashMap<String, String>> nameList) {
        mcontext = context;
        List = nameList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        final fragHolder mHolder;
        if(convertView == null){
            view = inflater.inflate(R.layout.fragment_row,null);
            mHolder = new fragHolder();
        }
        else {
            mHolder = (fragHolder) convertView.getTag();
        }
        mHolder.sub_frag = (TextView) view.findViewById(R.id.subject_name_frag);
        mHolder.asg_frag = (TextView) view.findViewById(R.id.asg_name_frag);
        mHolder.due_frag = (TextView) view.findViewById(R.id.due_frag);

        mNames = List.get(position);
        mHolder.sub_frag.setText(mNames.get("sub"));
        mHolder.asg_frag.setText(mNames.get("asg_name"));
        mHolder.due_frag.setText(mNames.get("date"));



        return null;
    }
}
class fragHolder {
    TextView sub_frag,due_frag,asg_frag;
}
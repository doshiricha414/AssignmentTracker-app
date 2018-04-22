package com.example.assignmenttracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.R.attr.contextClickable;
import static android.R.attr.data;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_WRITEABLE;
import static com.example.assignmenttracker.R.id.done;
import static java.security.AccessController.getContext;
public class CustomListViewAdapter extends BaseAdapter {
    private Context mcontext;
    Activity a;
    int p;
    int i=0;
    private ArrayList<HashMap<String,String>> names;
    private static LayoutInflater inflater= null;
        public CustomListViewAdapter(Context context, ArrayList<HashMap<String,String>> data,MainActivity mainActivity){
            mcontext = context;
            names = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            a = mainActivity;
        }
    @Override
    public int getCount() {
        return names.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder mHolder;
        String colors[] = {"#F44336","#9C27B0","#F50057","#26C6DA","#E91E63","#3F51B5","#00BCD4","#FF9800","#795548","#FF5722"
                ,"#673AB7","#2196F3","#4CAF50","#00E5FF","#33691E","#607D8B","#FF80AB","#7986CB"
                            ,"#827717","#00E676","#FFC107"
                    };
        if(convertView == null){
            view = inflater.inflate(R.layout.list_row,null);
            mHolder = new ViewHolder();
        }
        else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mText = (TextView) view.findViewById(R.id.asg_name);
        mHolder.subject = (TextView) view.findViewById(R.id.subject_name);
        mHolder.due_date = (TextView) view.findViewById(R.id.due);
        mHolder.done = (TextView) view.findViewById(done);
        mHolder.edit = (TextView) view.findViewById(R.id.edit);
        mHolder.type = (TextView) view.findViewById(R.id.type);
        mHolder.lay1 = (LinearLayout) view.findViewById(R.id.lay1);
        mHolder.trash = (ImageView) view.findViewById(R.id.trash);
        view.setTag(mHolder);
        HashMap<String,String>mNames = new HashMap<>();
        mNames = names.get(position);
        mHolder.mText.setText(mNames.get("asg_name"));
        mHolder.subject.setText(mNames.get("sub"));
        mHolder.due_date.setText(mNames.get("date"));
        mHolder.type.setText(mNames.get("type"));
        Random r = new Random();
        int Low = 0;
        int High = 21;
        int Result = r.nextInt(High-Low) + Low;
                mHolder.lay1.setBackgroundColor(Color.parseColor(colors[Result]));
                mHolder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mcontext,"Done:"+position,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mcontext.getApplicationContext(),completedAssignments.class);
                intent.putExtra("id",111);
                intent.putExtra("position",position);
                intent.putExtra("list",names);
                mcontext.startActivity(intent);
                names.remove(position);
                CustomListViewAdapter.this.notifyDataSetChanged();
                SharedPreferences sharedPreferences = mcontext.getSharedPreferences("shared preference",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(names);
                editor.putString("task list",json);
                editor.apply();
                editor.clear();
            }
        });
        mHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mcontext,"Edit is clicked",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mcontext.getApplicationContext(),editActivity.class);
                intent.putExtra("id",111);
                intent.putExtra("position",position);
                intent.putExtra("list",names);
                mcontext.startActivity(intent);
                a.finish();
            }
        });
        final View finalView = view;
        mHolder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(finalView.getRootView().getContext());
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        names.remove(position);
                        CustomListViewAdapter.this.notifyDataSetChanged();
                        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("shared preference",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(names);
                        editor.putString("task list",json);
                        editor.apply();
                        editor.clear();

                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create();
                alert.show();
            }
        });
        return view;
    }
}
class ViewHolder {
     TextView mText,subject,due_date,done,edit,type;
    LinearLayout lay1;
    ImageView trash;
}

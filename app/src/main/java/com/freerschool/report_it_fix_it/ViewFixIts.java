package com.freerschool.report_it_fix_it;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ViewFixIts extends AppCompatActivity {


    class FixItAdapter extends ArrayAdapter<ThingsToFix>{
        List<ThingsToFix> thingsToFixList;
        public FixItAdapter(List<ThingsToFix> thingsToFixList){
            super(ViewFixIts.this   , R.layout.things_to_fix_layout);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.things_to_fix_layout, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);

        TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
        TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fix_its);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ViewFixIts extends AppCompatActivity {
    EditText editTextUserId, editTextLocationId, editTextImage, editTextDescription;
    CheckBox checkBoxFixed;
    ListView listView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fix_its);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextLocationId = findViewById(R.id.editTextLocationId);
        editTextImage = findViewById(R.id.editTextImage);
        editTextDescription = findViewById(R.id.editTextDescription);
        checkBoxFixed = findViewById(R.id.checkBoxFixed);
        //if I name these controls the same as in another Activity, can I access them?
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listViewFixIts);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    boolean isUpdating = false;

    private void refreshFixIts(JSONArray fixIts) throws JSONException{
        //fixIts

    }

    class FixItAdapter extends ArrayAdapter<ThingsToFix> {

        List<ThingsToFix> thingsToFixList;
        public FixItAdapter(List<ThingsToFix> thingsToFixList) {
            super(ViewFixIts.this, R.layout.things_to_fix_layout);
            this.thingsToFixList = thingsToFixList;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.things_to_fix_layout, null, true);
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
            final ThingsToFix thingsToFix = thingsToFixList.get(position);
            textViewName.setText(thingsToFix.getDescription());
            textViewUpdate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextUserId.setText(String.valueOf(thingsToFix.getUserId()));
                    editTextLocationId.setText(String.valueOf(thingsToFix.getLocationId()));
                    editTextDescription.setText(thingsToFix.getDescription());
                    editTextImage.setText(thingsToFix.getImage());
                    checkBoxFixed.setChecked(thingsToFix.isFixed());
                    button.setText("Update");
                }
            });

            return null;
        }



    }
}

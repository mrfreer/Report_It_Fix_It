package com.freerschool.report_it_fix_it;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewFixIts extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private void readFixIt(){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_FIXIT, null, CODE_GET_REQUEST);
        request.execute();
    }

    EditText editTextUserId, editTextLocationId, editTextImage, editTextDescription;
    CheckBox checkBoxFixed;
    ListView listView;
    Button button;
    List<ThingsToFix> thingsToFix;
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
        thingsToFix = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        readFixIt();
    }
    boolean isUpdating = false;

    private void refreshFixIts(JSONArray fixIts) throws JSONException{
        //fixIts
        thingsToFix.clear();
        Log.v("herehere", "do we");
        for(int i = 0; i < fixIts.length(); i++) {
            JSONObject obj = fixIts.getJSONObject(i);
            thingsToFix.add(new ThingsToFix(
                    obj.getInt("Things_Id"),
                    obj.getInt("UserId"),
                    obj.getInt("LocationId"),
                    obj.getString("Image"),
                    obj.getString("Description"),
                    obj.getBoolean("Fixed")
            ));
        }
            FixItAdapter adapter = new FixItAdapter(thingsToFix);
            listView.setAdapter(adapter);

    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        String url;
        HashMap<String, String> params;
        int requestCode; //whether it is a GET or Post


        public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        protected void onPreExecute(){
            super.onPreExecute();
            //do I need a progressBar?
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try{
                JSONObject object = new JSONObject(s);
                if(!object.getBoolean("error")){
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                    refreshFixIts(object.getJSONArray("fixIts"));
                }
            }
            catch (JSONException e){
                Log.v("gethere", "dowegethere");
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if(requestCode == CODE_POST_REQUEST){
                return requestHandler.sendPostRequest(url, params);
            }

            if(requestCode == CODE_GET_REQUEST){
                return requestHandler.sendGetRequest(url);
            }
            return null; //uh oh
        }
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

            return listViewItem;
        }



    }
}

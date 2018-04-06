package com.freerschool.report_it_fix_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewFixIts extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private void readFixIt(){

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_FIXIT, null, CODE_GET_REQUEST);
        request.execute();
    }

    EditText editTextLocation, editTextDescription;
    ImageView picToFix;
    CheckBox checkBoxFixed;
    ListView listView;
    Button button;
    List<ThingsToFix> thingsToFix;
    String userName;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fix_its);
        picToFix = findViewById(R.id.imageViewIconFix);
        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");
        Log.v("user", userName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDescription = findViewById(R.id.editTextDescription);
        checkBoxFixed = findViewById(R.id.checkBoxFixed);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listViewFixIts);
        thingsToFix = new ArrayList<>();

        readFixIt();
    }
    boolean isUpdating = false;

    private void refreshFixIts(JSONArray fixIts) throws JSONException{
        //fixIts
        thingsToFix.clear();
        for(int i = 0; i < fixIts.length(); i++) {
            JSONObject obj = fixIts.getJSONObject(i);
            boolean curFixed;
            int cf = obj.getInt("Fixed");
            if(cf == 0){
                curFixed = false;
            }
            else{
                curFixed = true;
            }
            thingsToFix.add(new ThingsToFix(
                    obj.getInt("Things_Id"),
                    obj.getString("UserName"),
                    obj.getString("Location"),
                    obj.getString("Image"),
                    obj.getString("Category"),
                    curFixed
            ));
        }
            FixItAdapter adapter = new FixItAdapter(thingsToFix);
            listView.setAdapter(adapter);

    }

    public void delete(View view){
        deleteFixIt(id);
    }

    public void deleteFixIt(String fix_it_id){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_FIXIT + fix_it_id, null, CODE_GET_REQUEST);
        request.execute();
        readFixIt();
    }

    public void updateFixIt(View view){
        String location = editTextLocation.getText().toString();
        String description = editTextDescription.getText().toString();
        String checked;
        if(checkBoxFixed.isChecked()){
            checked = "1";
        }
        else{
            checked = "0";
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("Location", location);
        params.put("Category", description);
        params.put("Fixed", checked);
        params.put("Things_Id", id);
        for (Map.Entry<String,String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.v("TESTINGMAP", key + " key " + value + " value " );
            // do stuff
        }
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_FIXIT, params, CODE_POST_REQUEST);
        request.execute();
        editTextDescription.setText("");
        editTextLocation.setText("");
        readFixIt();
        isUpdating = false;
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
                    refreshFixIts(object.getJSONArray("fixit"));
                }
            }
            catch (JSONException e){
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
            return null;
        }
    }

    class FixItAdapter extends ArrayAdapter<ThingsToFix> {


        List<ThingsToFix> thingsToFixList;
        public FixItAdapter(List<ThingsToFix> thingsToFixList) {
            super(ViewFixIts.this, R.layout.things_to_fix_layout, thingsToFixList);
            this.thingsToFixList = thingsToFixList;
        }


        public Bitmap StringToBitMap(String encodedString) {
            try {
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.things_to_fix_layout, null, true);
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
            ImageView curImage = listViewItem.findViewById(R.id.imageViewIconFix);
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
            Bitmap bm = StringToBitMap(thingsToFixList.get(position).getImage());
            curImage.setImageBitmap(bm);
            final ThingsToFix thingsToFix = thingsToFixList.get(position);
            textViewName.setText(thingsToFix.getCategory());
            textViewUpdate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextLocation.setEnabled(true);
                    editTextDescription.setEnabled(true);
                    id = Integer.toString(thingsToFix.getThings_id());
                    editTextLocation.setText(String.valueOf(thingsToFix.getLocation()));
                    editTextDescription.setText(thingsToFix.getCategory());
                    checkBoxFixed.setChecked(thingsToFix.isFixed());
                    button.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    id = Integer.toString(thingsToFix.getThings_id());
                    deleteFixIt(id);
                }
            });

            return listViewItem;
        }
    }
}

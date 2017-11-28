package com.freerschool.report_it_fix_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FixStuff extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String>{
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

            return null; //uh oh
        }
    }

    //Attributes
    ImageButton camera;
    ImageView imageViewFixIt;
    EditText UserId, LocationId, Image, Description;
    CheckBox Fixed;
    List<ThingsToFix> thingsToFixList;
    static final int CAMERA_PIC_REQUEST = 1;
    int  TAKE_PICTURE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_stuff);
        thingsToFixList = new ArrayList<>();
    }

    public void addImage(View view){
        camera = findViewById(R.id.imageButtonCamera);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data); //I left this out!
        if(requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewFixIt = findViewById(R.id.imageViewFixIt);
            imageViewFixIt.setImageBitmap(imageBitmap);
            camera.setImageBitmap(imageBitmap);
        }
        else{
            Toast.makeText(this, "Picture NOT taken", Toast.LENGTH_LONG).show();
        }
    }

    public void fixIt(View view){
        UserId = findViewById(R.id.editTextUserId);
        LocationId = findViewById(R.id.editTextLocationId);
        Image = findViewById(R.id.editTextImage);
        Description = findViewById(R.id.editTextDescription);
        Fixed = findViewById(R.id.checkBoxFixed);
        createFixIt();
        UserId.setText("");
        LocationId.setText("");
        Image.setText("");
        Description.setText("");
        Fixed.setChecked(false);
    }

    private void createFixIt(){
        int uId, lId, fix;
        String image, description;
        image = Image.getText().toString().trim();
        description = Description.getText().toString().trim();
        uId = Integer.parseInt(UserId.getText().toString().trim());
        lId = Integer.parseInt(LocationId.getText().toString().trim());
        if(Fixed.isChecked()){
            fix = 1;
        }
        else{
            fix = 0;
        }
        if(TextUtils.isEmpty(description)){
            Description.setError("Please enter description");
            Description.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(Integer.toString(uId))){
            UserId.setError("Please enter a user id");
            UserId.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("UserId", Integer.toString(uId));
        params.put("LocationId", Integer.toString(lId));
        params.put("Image", image);
        params.put("Description", description);
        params.put("Fixed", Integer.toString(fix));

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_FIXIT, params, CODE_POST_REQUEST);
        request.execute();

    }

    private void readFixIt(){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_FIXIT, null, CODE_GET_REQUEST);
        request.execute();
    }


}

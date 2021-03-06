package com.freerschool.report_it_fix_it;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dfreer on 11/20/2017.
 */

public class RequestHandler {


    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams){
        URL url;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                while((response = br.readLine()) != null){
                    sb.append(response);
                }
            }

        }
        catch (java.net.MalformedURLException i){
            i.printStackTrace();
        }
        catch (IOException i){
            i.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestURL){
        Log.v("checkURL", requestURL);
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s;
            while((s = bufferedReader.readLine()) != null){
                sb.append(s + "\n");
                Log.v("checks", s.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String, String> postDataParams) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : postDataParams.entrySet()){
            if(first){
                first = false;
            }
            else{
                result.append("&");
            }
            //i love this code here:  great!
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

}

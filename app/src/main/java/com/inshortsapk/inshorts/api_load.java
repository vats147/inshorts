package com.inshortsapk.inshorts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class api_load extends AppCompatActivity {
    List<usermodel> alluserslist;
    String url="https://jsonplaceholder.typicode.com/photos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_load);
        //getData();
        // alluserslist=new ArrayList<>();
    }
    private void getData() {
        final TextView textView = (TextView) findViewById(R.id.textView3);




        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject singleObject=array.getJSONObject(i);
                                usermodel singlemodel=new usermodel(
                                        singleObject.getInt("albumId"),
                                        singleObject.getInt("id"),
                                        singleObject.getString("title"),
                                        singleObject.getString("url"),
                                        singleObject.getString("thumbnailUrl")

                                );
                                alluserslist.add(singlemodel);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(api_load.this, "on response call", Toast.LENGTH_SHORT).show();
                        Log.e("api","onError messge"+response.toString());
                        //  textView.setText("Response is: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(api_load.this, "Error is occure", Toast.LENGTH_SHORT).show();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
package com.inshortsapk.inshorts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class clonner extends AppCompatActivity {
    //now create list of type slider item
    List<SliderItems> sliderItems=new ArrayList<>();

    ArrayList<String> titles=new ArrayList<>();
    ArrayList<String> desc=new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    ArrayList<String> newslinks=new ArrayList<>();
    ArrayList<String> heads=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clonner);



        final VeticalViewPager verticalViewPager=(VeticalViewPager) findViewById(R.id.verticalViewPager);

        //api calling



        String url="https://jsonplaceholder.typicode.com/photos/";

        // GET https://newsapi.org/v2/top-headlines?country=us&apiKey=dd106b6e9235434a9bda0dd973a445de
       // String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=dd106b6e9235434a9bda0dd973a445de";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("loop","inside = ");
                        try {

                            JSONArray array = new JSONArray(response);
                            Log.e("loop","Inside try= ");
                            List<String> titles = new ArrayList<>();

                            ArrayList<String> desc=new ArrayList<>();
                            ArrayList<String> images=new ArrayList<>();
                            ArrayList<String> newslinks=new ArrayList<>();
                            ArrayList<String> heads=new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Log.e("loop","inside = "+i);
                                JSONObject singleObject = array.getJSONObject(i);

                                String title = singleObject.getString("title");
                                titles.add(title);

                                desc.add(singleObject.getString("albumId"));
                                images.add(singleObject.getString("thumbnailUrl"));
                                newslinks.add(singleObject.getString("url"));
                                heads.add(singleObject.getString("title"));
                                sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
                            }

                            //  ViewPagerAdapter adapter = new ViewPagerAdapter((Context) MainActivity.this, (ArrayList<SliderItems>) sliderItems, (ArrayList<String>) titles);
                            ViewPagerAdapter adapter = new ViewPagerAdapter((Context) clonner.this, (ArrayList<SliderItems>) sliderItems,  (ArrayList<String>)titles, desc, images, newslinks, heads);
                            verticalViewPager.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(clonner.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("api","onError messge"+e.toString());
                        }
                        //Here, we are passing the titles list as a parameter to the ViewPagerAdapter constructor, and then using it in the instantiateItem() method to set the text of the titleTextView view.







                        //  textView.setText("Response is: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(clonner.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
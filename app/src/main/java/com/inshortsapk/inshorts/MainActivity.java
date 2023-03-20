package com.inshortsapk.inshorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //now create list of type slider item
    List<SliderItems> sliderItems=new ArrayList<>();

    ArrayList<String> titles=new ArrayList<>();
    ArrayList<String> desc=new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    ArrayList<String> newslinks=new ArrayList<>();
    ArrayList<String> heads=new ArrayList<>();


    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final VeticalViewPager verticalViewPager=(VeticalViewPager) findViewById(R.id.verticalViewPager);

//        mRef= FirebaseDatabase.getInstance().getReference("news");
//        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds:snapshot.getChildren())
//                {
//                    //add data to array list
//                    titles.add(ds.child("title").getValue(String.class));
//                    desc.add(ds.child("desc").getValue(String.class));
//                    heads.add(ds.child("head").getValue(String.class));
//                    images.add(ds.child("imagelink").getValue(String.class));
//                    newslinks.add(ds.child("newslink").getValue(String.class));
//
//                }
//                for (int i=0;i<images.size();i++)
//                {
//                        //here we add slider items with the images that are store in images array list...
//                        sliderItems.add(new SliderItems(images.get(i)));
//
//                        //change int to string becuase now we are able to retrive image link and save to array list
//
////                    verticalViewPager.setAdapter(new ViewPagerAdapter(MainActivity.this,sliderItems),titles,desc,images,newslinks,heads,verticalViewPager);
////
////                    //now add all array list in adapter
//                }
//                                    verticalViewPager.setAdapter(new ViewPagerAdapter(MainActivity.this,sliderItems,titles,desc,newslinks,heads,verticalViewPager));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //add some image in sliderItems and pass this list to our adapter...


// sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
//        sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
//        sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
//
        //  verticalViewPager.setAdapter(new ViewPagerAdapter(MainActivity.this,sliderItems));

        //api calling



        String url="https://jsonplaceholder.typicode.com/photos/";
        // GET https://newsapi.org/v2/top-headlines?country=us&apiKey=dd106b6e9235434a9bda0dd973a445de
        // String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=dd106b6e9235434a9bda0dd973a445de";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<String> titles = new ArrayList<>();

                            ArrayList<String> desc=new ArrayList<>();
                            ArrayList<String> images=new ArrayList<>();
                            ArrayList<String> newslinks=new ArrayList<>();
                            ArrayList<String> heads=new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject singleObject = array.getJSONObject(i);
                                String title = singleObject.getString("title");
                                titles.add(title);

                                desc.add(singleObject.getString("thumbnailUrl"));
                                images.add(singleObject.getString("url"));
                                newslinks.add(singleObject.getString("thumbnailUrl"));
                                heads.add(singleObject.getString("title"));
                                sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
                            }

                            //  ViewPagerAdapter adapter = new ViewPagerAdapter((Context) MainActivity.this, (ArrayList<SliderItems>) sliderItems, (ArrayList<String>) titles);
                            ViewPagerAdapter adapter = new ViewPagerAdapter((Context) MainActivity.this, (ArrayList<SliderItems>) sliderItems,  (ArrayList<String>)titles, desc, images, newslinks, heads);
                            verticalViewPager.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Here, we are passing the titles list as a parameter to the ViewPagerAdapter constructor, and then using it in the instantiateItem() method to set the text of the titleTextView view.






                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("api","onError messge"+response.toString());
                        //  textView.setText("Response is: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


    private void getData() {


    }
}
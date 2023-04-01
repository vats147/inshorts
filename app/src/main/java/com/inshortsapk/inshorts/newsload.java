package com.inshortsapk.inshorts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class newsload extends AppCompatActivity {
    

    //now create list of type slider item
    List<SliderItems> sliderItems = new ArrayList<>();

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> newslinks = new ArrayList<>();
    ArrayList<String> heads = new ArrayList<>();


    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Inside newsload", Toast.LENGTH_SHORT).show();

        fetchData();
        Toast.makeText(this, "After fetchdata", Toast.LENGTH_SHORT).show();







    }





    private void getData() {


    }

    public void fetchData() {
        Toast.makeText(this, "inside fetchdata", Toast.LENGTH_SHORT).show();
        //volley library
       // String url = "https://api.nytimes.com/svc/topstories/v2/arts.json?api-key=TeDmUv89uyyv5oJLMzw7jYgww6hcBTHh";
        String url="https://gnews.io/api/v4/search?q=example&lang=en&country=us&max=10&apikey=e1a303a726b13fbc8ead06c221362838";
        final VeticalViewPager verticalViewPager = (VeticalViewPager) findViewById(R.id.verticalViewPager);
        //making a request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(newsload.this, "Inside response", Toast.LENGTH_SHORT).show();
                            JSONArray newsJsonArray = response.getJSONArray("articles");

                          //  ArrayList<News> newsArray = new ArrayList<News>();
                            List<String> titles = new ArrayList<>();

                            ArrayList<String> desc = new ArrayList<>();
                            ArrayList<String> images = new ArrayList<>();
                            ArrayList<String> newslinks = new ArrayList<>();
                            ArrayList<String> heads = new ArrayList<>();

                            for (int i = 0; i < newsJsonArray.length(); i++) {
                                Log.e("sample","inside = "+i);
                                Toast.makeText(newsload.this, "Loop response"+i, Toast.LENGTH_SHORT).show();

                                JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
//                                News news = new News(
//                                        newsJsonObject.getString("title"),
//                                        newsJsonObject.getString("author"),
//                                        newsJsonObject.getString("url"),
//                                        newsJsonObject.getString("urlToImage")
//                                );

                                String title = newsJsonObject.getString("title");
                                String description = newsJsonObject.getString("description");
                                String imageUrl = newsJsonObject.getString("image");
                                String newsUrl = newsJsonObject.getString("url");

                                titles.add(title);
                                desc.add(description);
                                images.add(imageUrl);
                                newslinks.add(newsUrl);
                                heads.add(title);

                               // newsArray.add(news);
                                sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
                            }
                            ViewPagerAdapter adapter = new ViewPagerAdapter((Context) newsload.this, (ArrayList<SliderItems>) sliderItems, (ArrayList<String>) titles, desc, images, newslinks, heads);
                            verticalViewPager.setAdapter(adapter);

                           // mAdapter.updateNews(newsArray);
                        } catch (JSONException e) {
                            Toast.makeText(newsload.this, "Catch called", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };

     //   newsload.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}
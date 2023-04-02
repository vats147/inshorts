package com.inshortsapk.inshorts;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newnewsload extends AppCompatActivity {

    // Declare variables
    private List<String> titles;
    private ArrayList<String> desc;
    private ArrayList<String> images;
    private ArrayList<String> newslinks;
    private ArrayList<String> heads;
    private ArrayList<String> BTNSHARE;

    List<SliderItems> sliderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        titles = new ArrayList<>();
        desc = new ArrayList<>();
        images = new ArrayList<>();
        newslinks = new ArrayList<>();
        heads = new ArrayList<>();
        BTNSHARE=new ArrayList<>();

        // Call API function
        fetchData();
    }

    private void fetchData() {
        final VeticalViewPager verticalViewPager = (VeticalViewPager) findViewById(R.id.verticalViewPager);
        // Define API url
        String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=dd106b6e9235434a9bda0dd973a445de";
        //String url = "https://gnews.io/api/v4/search?q=example&lang=en&country=us&max=10&apikey=e1a303a726b13fbc8ead06c221362838";

        // Initialize request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Make a request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Parse JSON response
                        try {
                            JSONArray newsJsonArray = response.getJSONArray("articles");

                            for (int i = 0; i < newsJsonArray.length(); i++) {
                                JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);

                                String title = newsJsonObject.getString("title");
                                String description = newsJsonObject.getString("description");
                                String imageUrl = newsJsonObject.getString("urlToImage");
                                String newsUrl = newsJsonObject.getString("url");




                                titles.add(title);
                                desc.add(description);
                                images.add(imageUrl);
                                newslinks.add(newsUrl);
                                heads.add(title);
                                BTNSHARE.add(newsUrl);
                                // newsArray.add(news);
                                sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
                            }

                            ViewPagerAdapter adapter = new ViewPagerAdapter((Context) newnewsload.this, (ArrayList<SliderItems>) sliderItems, (ArrayList<String>) titles, desc, images, newslinks, heads,BTNSHARE);
                            verticalViewPager.setAdapter(adapter);

                            // Debugging logs
                            Log.d("Titles", titles.toString());
                            Log.d("Descriptions", desc.toString());
                            Log.d("Images", images.toString());
                            Log.d("NewsLinks", newslinks.toString());
                            Log.d("Heads", heads.toString());

                            // Do something with the parsed data
                            // ...
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(newnewsload.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(newnewsload.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };

        // Add request to queue
        queue.add(jsonObjectRequest);
    }
}

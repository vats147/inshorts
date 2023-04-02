package com.inshortsapk.inshorts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    //now create list of type slider item
    List<SliderItems> sliderItems=new ArrayList<>();
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ArrayList<String> titles=new ArrayList<>();
    ArrayList<String> desc=new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    ArrayList<String> newslinks=new ArrayList<>();
    ArrayList<String> heads=new ArrayList<>();
    ArrayList<String> BTNSHARE=new ArrayList<>();

    //DatabaseReference mRef;
    ImageView loadingGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingGif=findViewById(R.id.imageView4);
        Glide.with(this).asGif().load(R.drawable.loading).into(loadingGif);
        loadingGif.setVisibility(View.VISIBLE);



        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        final VeticalViewPager verticalViewPager=(VeticalViewPager) findViewById(R.id.verticalViewPager);


        //api calling



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
                        loadingGif.setVisibility(View.INVISIBLE);
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

                            ViewPagerAdapter adapter = new ViewPagerAdapter((Context) MainActivity.this, (ArrayList<SliderItems>) sliderItems, (ArrayList<String>) titles, desc, images, newslinks, heads,BTNSHARE);
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
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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
        private MenuItem username;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu,menu);
        username = menu.findItem(R.id.username);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {

            String personName = acct.getDisplayName();
            //String personEmail=acct.getEmail();
            username.setTitle(personName);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.username:

                break;

            case R.id.logout:

                signOut();
                break;
        }
        return true;
    }
    public void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
                Intent intent=new Intent(MainActivity.this,my_login.class);

                startActivity(intent);
            }
        });

    }


    }

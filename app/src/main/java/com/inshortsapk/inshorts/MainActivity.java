package com.inshortsapk.inshorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Slide;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


        //add some image in sliderItems and pass this list to our adapter....
        sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
        sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));
        sliderItems.add(new SliderItems(R.drawable.ic_launcher_background));

        verticalViewPager.setAdapter(new ViewPagerAdapter(MainActivity.this,sliderItems));
    }
}
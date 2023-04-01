package com.inshortsapk.inshorts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    List<SliderItems> sliderItems;
    LayoutInflater mLayoutInflater;
    Context context;
    ArrayList<String> titles;
    ArrayList<String> desc;
    ArrayList<String> images;
    ArrayList<String> newslinks;
    ArrayList<String> heads;
    ArrayList<String> BTNSHARE;
    VeticalViewPager verticalViewPager;


    public ViewPagerAdapter(Context context, List<SliderItems> sliderItems, ArrayList<String> titles, ArrayList<String> desc, ArrayList<String> newslinks, ArrayList<String> heads, VeticalViewPager verticalViewPager) {
        this.context=context;
        this.sliderItems = sliderItems;
        this.titles=titles;
        this.desc=desc;
        this.newslinks=newslinks;
        this.heads=heads;
        this.verticalViewPager=verticalViewPager;
        mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public ViewPagerAdapter(Context context, ArrayList<SliderItems> sliderItems, ArrayList<String> titles, ArrayList<String> desc, ArrayList<String> images, ArrayList<String> newslinks, ArrayList<String> heads) {
        this.context = context;
        this.sliderItems = sliderItems;
        this.titles = titles;
        this.desc = desc;
        this.images = images;
        this.newslinks = newslinks;
        this.heads = heads;
        mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public ViewPagerAdapter(Context context, ArrayList<SliderItems> sliderItems, ArrayList<String> titles, ArrayList<String> desc, ArrayList<String> images, ArrayList<String> newslinks, ArrayList<String> heads,ArrayList<String> BTNSHARE) {
        this.context = context;
        this.sliderItems = sliderItems;
        this.titles = titles;
        this.desc = desc;
        this.images = images;
        this.newslinks = newslinks;
        this.heads = heads;
        this.BTNSHARE=BTNSHARE;
        mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public ViewPagerAdapter(Context context, List<SliderItems> sliderItems) {
        this.context=context;
        this.sliderItems = sliderItems;


        mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public ViewPagerAdapter(Context context, ArrayList<SliderItems> sliderItems, ArrayList<String> titles) {
        this.context = context;
        this.sliderItems = sliderItems;
        this.titles = titles;
        mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return sliderItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);
    }

    public void api_config(){
        //NewsApiClient newsApiClient = new NewsApiClient("dd106b6e9235434a9bda0dd973a445de");
        //NewsApiClient newsApiClient=new NewsApiClient();
    }
    public void fetch_Data(){
        String url="GET https://newsapi.org/v2/top-headlines?country=in&apiKey=dd106b6e9235434a9bda0dd973a445de";
        JSONObject JsonObjectRequest;


    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = mLayoutInflater.inflate(R.layout.item_container, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);

        // Set image to imageview inside items container
        imageView.setImageResource(sliderItems.get(position).getImage());

        TextView title = itemView.findViewById(R.id.headline);
        TextView desctv = itemView.findViewById(R.id.desc);
        TextView head = itemView.findViewById(R.id.head);
        FloatingActionButton btnshare = itemView.findViewById(R.id.floatingActionButton);

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Link");
                shareIntent.putExtra(Intent.EXTRA_TEXT, BTNSHARE.get(position));
                context.startActivity(Intent.createChooser(shareIntent, "Share link via"));
            }
        });

        // Set data from array list to textView
        title.setText(titles.get(position));
        desctv.setText(desc.get(position));
        head.setText(heads.get(position));

        Glide.with(context)
                .load(images.get(position))
                .centerCrop()
                .into(imageView);

        container.addView(itemView);
        return itemView;
//        View itemView=mLayoutInflater.inflate(R.layout.item_container,container,false);
//        ImageView imageView=itemView.findViewById(R.id.imageView);
//        TextView title=itemView.findViewById(R.id.headline);
//
//        // Set the data to the views
//        imageView.setImageResource(sliderItems.get(position).getImage());
//        title.setText(titles.get(position));
//
//        container.addView(itemView);
//        return itemView;


//        View itemView=mLayoutInflater.inflate(R.layout.item_container,container,false);
//        ImageView imageView=itemView.findViewById(R.id.imageView);
//
//        //we will set image to imageview inside items container
//        imageView.setImageResource(sliderItems.get(position).getImage());

//        ImageView imageView2=itemView.findViewById(R.id.imageView2);
//        TextView title=itemView.findViewById(R.id.headline);
//        TextView desctv=itemView.findViewById(R.id.desc);
//        TextView head=itemView.findViewById(R.id.head);
//
//        //set data from an array list to textView
//        title.setText(titles.get(position));
//        desctv.setText(desc.get(position));
//        head.setText(heads.get(position));
//
//
//        Glide.with(context)
//                .load(sliderItems.get(position).getImage())
//                .centerCrop()
//                .into(imageView);
//        Glide.with(context)
//                .load(sliderItems.get(position).getImage())
//                .centerCrop()
//                .override(12,12)
//                .into(imageView2);
//
//      container.addView(itemView);
//        return itemView;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);
    }
}

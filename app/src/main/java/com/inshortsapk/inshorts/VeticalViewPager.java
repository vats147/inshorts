/*
    1. extends ViewPafer and add its method



 */
package com.inshortsapk.inshorts;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class VeticalViewPager extends ViewPager {
    public VeticalViewPager(@NonNull Context context) {
        super(context);
    }

    public VeticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true,new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }



    public class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View page, float position) {
            //now here we will check positions
            if(position<-1)
            {
                //[-infinty,-1]
                //if this page is way off screen to the left
                page.setAlpha(0);
            }
            else if(position<=0)
            {
                //[-1,0]
                //  the default slide transition when moving to the left page
                page.setAlpha(1);
                page.setTranslationX(page.getWidth()*-position);

                //set Y position to swipe in from top
                float yPosition=position*page.getHeight();
                page.setTranslationY(yPosition);
                page.setScaleX(1);
                page.setScaleY(1);


            }
            else if(position<=1)
            {
                //[0,1]
                //counteract the default slide translition....
                page.setTranslationY(page.getWidth()*-position);
                //to scale the page down
                float scale=0.75f +(1-075f)*(1-Math.abs(position));
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
            else{
                //[1,+infinty]
                page.setAlpha(0);
            }


        }
    }

    private MotionEvent swapXYCordinates(MotionEvent event)
    {
        //now we will swap x and y coordinates using this,.....
        float width=getWidth();
        float height=getHeight();
        float newX=(event.getY()/height)*width;
        float newY=(event.getX()/width)*height;


        event.setLocation(newX,newY);
        return event;
    }
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        boolean intercepted=super.onInterceptTouchEvent(swapXYCordinates(ev));
        swapXYCordinates(ev); //return touch coordinates to original refrences frame for any child value...
        return  intercepted;

    }
    public boolean onTouchEvent(MotionEvent ev)
    {
        return  super.onTouchEvent(swapXYCordinates(ev));
    }
}

package com.example.khalid.bloodbank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.khalid.bloodbank.R;

public class SlideShowAdapter extends PagerAdapter {

    private Context context;
    LayoutInflater inflater;

    public int[] images={
            R.drawable.slideshow_image1,
            R.drawable.slideshow_image2,
            R.drawable.slideshow_image1};
    public SlideShowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.slideshow_layout,container,false);
        ImageView img=(ImageView) view.findViewById(R.id.slideshow_iv);
        //img.setImageResource(images[position]);
        Glide.with(context).load(images[position]).into(img);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
container.removeView((RelativeLayout)object);    }
}

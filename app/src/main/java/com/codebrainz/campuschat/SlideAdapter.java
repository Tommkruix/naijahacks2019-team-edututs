package com.codebrainz.campuschat;

/**
 * Created by Tommkruix on 12/11/2019.
 */
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class SlideAdapter extends PagerAdapter{
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }
    // Arrays

    public int[] slide_images = {
            R.drawable.slidea,
            R.drawable.slideb,
            R.drawable.slided,
            R.drawable.slidec
    };
    public  String[] slide_headings = {
            "GETTING STARTED",
            "10,000+ MATERIALS AND QUESTIONS",
            "FREE CHAT",
            "YOU ARE ALL SET"
    };
    public String[] slide_description = {
            "CampusChat gives you the chance to meet other friends all over the higher institutions.",
            "There are lot of campus materials and questions available at your fingertip",
            "Also provides a free one - one and group chatting system",
            "Welcome to Campus Chat \nPowered by: Team-EduTuts at Naijahacks2019"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_description);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_description[position]);

        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}

package com.codebrainz.campuschat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Tommkruix on 12/11/2019.
 */
public class slidingActivity extends Activity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SlideAdapter slideAdapter;

    private Button mNextBtn;
    private Button mBsckBtn;
    private Button btnf;

    private int mCurrentPage;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);

        mNextBtn = findViewById(R.id.nextBtn);
        mBsckBtn = findViewById(R.id.prevBtn);
        btnf = findViewById(R.id.btnFinish);

        slideAdapter = new SlideAdapter(this);

        mSlideViewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        //OnClickListener
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
              /*  if(mNextBtn.getText().toString().equalsIgnoreCase("Finish")){
                    Intent i = new Intent(slidingActivity.this, LoginActivity.class);
                    startActivity(i);
                }*/
            }
        });
        mBsckBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
        btnf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(slidingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();

        // check if user is null
        if(currentUser != null){
            Intent intent = new Intent(slidingActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void addDotsIndicator(int position){
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;

            if(i == 0){
                btnf.setVisibility(View.INVISIBLE);
                mNextBtn.setEnabled(true);
                mBsckBtn.setEnabled(false);
                mBsckBtn.setVisibility(View.INVISIBLE);

                mBsckBtn.setText("");
                btnf.setVisibility(View.INVISIBLE);
            }else if(i == mDots.length - 1 ){
                mNextBtn.setEnabled(true);
                mBsckBtn.setEnabled(true);
                mBsckBtn.setVisibility(View.VISIBLE);

              //mNextBtn.setText("Finish");
                mNextBtn.setText("");
                btnf.setVisibility(View.VISIBLE);
                mBsckBtn.setText("Back");
            }else{
                btnf.setVisibility(View.INVISIBLE);
                mNextBtn.setEnabled(true);
                mBsckBtn.setEnabled(true);
                mBsckBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBsckBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}

package com.example.appbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter adapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpaper);
        circleIndicator = findViewById(R.id.circle_indicator);

        mListPhoto = getListPhoto();
        adapter = new PhotoAdapter(this, mListPhoto);
        viewPager.setAdapter(adapter);

        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        bottomNavi();

        autoSlideImage();
    }

    private void bottomNavi(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Xử lý khi người dùng chọn Home
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_dashboard:
                        // Xử lý khi người dùng chọn Dashboard
                        Toast.makeText(MainActivity.this, "Thông báo", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_notifications:
                        // Xử lý khi người dùng chọn Notifications
                        Toast.makeText(MainActivity.this, "User", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }


        });
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img_1));
        list.add(new Photo(R.drawable.img_2));
        list.add(new Photo(R.drawable.img_3));
        list.add(new Photo(R.drawable.img_4));

        return list;
    }

    private void autoSlideImage(){
        if (mListPhoto ==null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }
        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if(currentItem < totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}
package com.arty.domino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.arty.domino.game.AreaActivity;

public class MainActivity extends AppCompatActivity {
    private Toast toast;
    private long backPressedTime = 0;
    private int positionImageArea = 0;
    private ViewPager viewPager;
    private SlideAdapter adapter;
    String tagArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_main);

        setArrImgAreas();
        setVisibleBtnLeftRight(positionImageArea);

        tagArea = Constants.TAG_PYRAMID;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFullScreen();
    }

    protected void setFullScreen(){
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void setArrImgAreas(){
        int[] arrImgAreas = new int[3];

        arrImgAreas[0] = R.drawable.pyramid_clean_200_350;
        arrImgAreas[1] = R.drawable.cross_clean_300_330;
        arrImgAreas[2] = R.drawable.fountain_clean;

        adapter = new SlideAdapter(this, arrImgAreas);
        viewPager = findViewById(R.id.viewPager_id);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTagAreaOnPosition(position);
                setVisibleBtnLeftRight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onLeftClick(View view) {
        positionImageArea = adapter.getPosition();
        if (positionImageArea > 0){
            positionImageArea--;
        }
        setVisibleBtnLeftRight(positionImageArea);

        adapter.setPosition(positionImageArea);
        changeTagAreaOnPosition(positionImageArea);

        viewPager.setCurrentItem(positionImageArea);
    }

    public void onRightClick(View view) {
        positionImageArea = adapter.getPosition();
        if (positionImageArea < adapter.getCount()-1){
            positionImageArea++;
        }
        setVisibleBtnLeftRight(positionImageArea);

        adapter.setPosition(positionImageArea);
        changeTagAreaOnPosition(positionImageArea);

        viewPager.setCurrentItem(positionImageArea);
    }

    private void setVisibleBtnLeftRight(int position){
        Button btnLeftRight;

        switch (position){
            case 0:
                btnLeftRight = findViewById(R.id.btnRight);
                btnLeftRight.setVisibility(View.VISIBLE);

                btnLeftRight = findViewById(R.id.btnLeft);
                btnLeftRight.setVisibility(View.INVISIBLE);

                break;
            case 2:
                btnLeftRight = findViewById(R.id.btnLeft);
                btnLeftRight.setVisibility(View.VISIBLE);

                btnLeftRight = findViewById(R.id.btnRight);
                btnLeftRight.setVisibility(View.INVISIBLE);

                break;
            default:
                btnLeftRight = findViewById(R.id.btnLeft);
                btnLeftRight.setVisibility(View.VISIBLE);

                btnLeftRight = findViewById(R.id.btnRight);
                btnLeftRight.setVisibility(View.VISIBLE);
        }

    }

    private void changeTagAreaOnPosition(int position){
        switch (position){
            case 0:
                tagArea = Constants.TAG_PYRAMID;
                break;
            case 1:
                tagArea = Constants.TAG_CROSS;
                break;
            case 2:
                tagArea = Constants.TAG_FOUNTAIN;
                break;
            case 3:
                tagArea = Constants.TAG_SNOWFLAKE;
                break;
        }
    }

    public void onClickStartGame(View v){
        Intent intent = new Intent(MainActivity.this, AreaActivity.class);
        intent.putExtra("TAG", tagArea);
        startActivity(intent);
    }

    public void onClickRecords(View view) {
        Intent intent = new Intent(MainActivity.this, StatsActivity.class);
        intent.putExtra("TAG", tagArea);
        startActivity(intent);
    }

    public void onClickAbout(View view) {
        Intent intent = new Intent(MainActivity.this, AboutGame.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }else {
            toast = Toast.makeText(this, R.string.message_exit_app, Toast.LENGTH_SHORT);
            toast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void ocRateClick(View view) {

    }

    public void onExitClick(View view) {
        finishAffinity();
    }
}
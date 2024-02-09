package com.arty.domino.game;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.arty.domino.Constants;
import com.arty.domino.R;

public class AreaActivity extends AppCompatActivity {
//    private AdView mAdView;

    private FragmentManager fragmentManager;
    private String tagArea = "";

    private long backPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_main_area);

//        setmAdView();

        fragmentManager = getSupportFragmentManager();
        tagArea = getIntent().getStringExtra("TAG");
        switch(tagArea){
            case Constants.TAG_PYRAMID:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentPyramid.class, null, tagArea)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                break;
            case Constants.TAG_CROSS:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentCross.class, null, tagArea)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                break;
            case Constants.TAG_FOUNTAIN:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentFountain.class, null, tagArea)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                break;
            case Constants.TAG_SPRUCE:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentSpruce.class, null, tagArea)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button btnExit = findViewById(R.id.btnAreaExit);
        btnExit.setOnClickListener(v -> finish());

        Button btnRefresh = findViewById(R.id.btnAreaRefresh);

        switch(tagArea){
            case Constants.TAG_PYRAMID:
                FragmentPyramid pyramidFragment = (FragmentPyramid) fragmentManager.findFragmentByTag(tagArea);
                btnRefresh.setOnClickListener(v -> {
                    assert pyramidFragment != null;
                    pyramidFragment.restartGame();
                });

                break;
            case Constants.TAG_CROSS:
                FragmentCross crossFragment = (FragmentCross) fragmentManager.findFragmentByTag(tagArea);
                btnRefresh.setOnClickListener(v -> {
                    assert crossFragment != null;
                    crossFragment.restartGame();
                });

                break;
            case Constants.TAG_FOUNTAIN:
                FragmentFountain fountainFragment = (FragmentFountain) fragmentManager.findFragmentByTag(FragmentFountain.TAG);
                btnRefresh.setOnClickListener(v -> {
                    assert fountainFragment != null;
                    fountainFragment.restartGame();
                });

                break;
            case Constants.TAG_SPRUCE:
                FragmentSpruce spruceFragment = (FragmentSpruce) fragmentManager.findFragmentByTag(FragmentSpruce.TAG);
                btnRefresh.setOnClickListener(v -> {
                    assert spruceFragment != null;
                    spruceFragment.restartGame();
                });

                break;
        }
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

    private void setmAdView() {
//        mAdView = new AdView(this);
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId("ca-app-pub-5025438987556699/3565623496");
//
//        MobileAds.initialize(this, initializationStatus -> mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Code to be executed when an impression is recorded
//                // for an ad.
//            }
//
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//        }));
//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            toast.cancel();

            finish();
        }else {
            toast = Toast.makeText(this, R.string.message_exit_app, Toast.LENGTH_SHORT);
            toast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}

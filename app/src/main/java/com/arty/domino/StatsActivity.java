package com.arty.domino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class StatsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        w.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_stats_dialog);

        showStats();
    }

    private void showStats(){
        int resAreaName;
        TextView tv;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(StatsActivity.this);

        String tagArea = getIntent().getStringExtra("TAG");
        switch(tagArea){
            case "AreaPyramid":
                resAreaName = R.string.area_pyramid;
                break;
            case "AreaCross":
                resAreaName = R.string.area_cross;
                break;
            case "AreaFountain":
                resAreaName = R.string.area_fountain;
                break;
            case "AreaSnowflake":
                resAreaName = R.string.area_snowflake;
                break;
            default:
                resAreaName = R.string.area_pyramid;
        }
        tv = findViewById(R.id.aboutAreaName);
        tv.setText(resAreaName);

        int total_wins = sharedPreferences.getInt(tagArea + Constants.TOTAL_WINS, 0);
        tv = findViewById(R.id.statsTVAllCount);
        tv.setText(Integer.toString(total_wins));

        int majors = sharedPreferences.getInt(tagArea + Constants.MAJOR, 0);
        tv = findViewById(R.id.statsTVMajorCount);
        tv.setText(Integer.toString(majors));

        int colonels = sharedPreferences.getInt(tagArea + Constants.COLONEL, 0);
        tv = findViewById(R.id.statsTVColonelCount);
        tv.setText(Integer.toString(colonels));

        int generals = sharedPreferences.getInt(tagArea + Constants.GENERAL, 0);
        tv = findViewById(R.id.statsTVGeneralCount);
        tv.setText(Integer.toString(generals));
    }

    public void onOkClick(View view) {
        this.finish();
    }
}
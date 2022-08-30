package com.arty.domino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Locale;

public class EndGameDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        w.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_end_game_dialog);

        showResultGame();
    }

    private void showResultGame(){
        String key;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EndGameDialogActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int rank = getIntent().getExtras().getInt("idRank");
        String tagArea = getIntent().getStringExtra("TAG");

        TextView tvResultGame = findViewById(R.id.txResultGame);
        ImageView imgVictory = findViewById(R.id.imgVictory);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setIsIndicator(true);
        ratingBar.setRating(rank);

        switch (rank){
            case 1:
                tvResultGame.setText(R.string.got_major);

                key = tagArea + Constants.MAJOR;
                int majors = sharedPreferences.getInt(key, 0);
                editor.putInt(key, majors+1);

                break;
            case 2:
                tvResultGame.setText(R.string.got_colonel);

                key = tagArea + Constants.COLONEL;
                int colonels = sharedPreferences.getInt(key, 0);
                editor.putInt(key, colonels+1);

                break;
            case 3:
                tvResultGame.setText(R.string.got_general);

                key = tagArea + Constants.GENERAL;
                int generals = sharedPreferences.getInt(key, 0);
                editor.putInt(key, generals+1);

                break;
            default:
                tvResultGame.setPadding(0,0,0,0);
                tvResultGame.setGravity(Gravity.CENTER_HORIZONTAL);
                tvResultGame.setText(R.string.congratulations);
        }

        switch (Locale.getDefault().getLanguage()){
            case "en":
                imgVictory.setImageResource(R.drawable.victory_en_300);
                break;
            case "ru":
                imgVictory.setImageResource(R.drawable.victory_ru_300);
                break;
        }

        key = tagArea + Constants.TOTAL_WINS;
        int total_wins = sharedPreferences.getInt(key, 0);
        editor.putInt(key, total_wins+1);

        editor.apply();
    }

    public void onClickDialog(View view) {
        this.finish();
    }
}
package com.arty.domino;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutGame extends Activity {
    private int nomDescription = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_about_game_dialog);

        showDescription();
    }

    private void showDescription(){
        ImageView imgAboutGame = findViewById(R.id.imgAboutGame);
        ImageView btnBack = findViewById(R.id.btnAboutBack);
        ImageView btnBForward = findViewById(R.id.btnAboutForward);
        ImageView imgAbout1 = findViewById(R.id.btnAboutPage1);
        ImageView imgAbout2 = findViewById(R.id.btnAboutPage2);
        ImageView imgAbout3 = findViewById(R.id.btnAboutPage3);
        TextView txtAboutGame = findViewById(R.id.txtAboutGame);

        btnBack.setImageResource(R.drawable.ic_back_space_24);
        btnBForward.setImageResource(R.drawable.ic_forward_space_24);

        switch (nomDescription){
            case 1:
                imgAboutGame.setImageResource(R.drawable.pyramid_200_333);
                btnBack.setVisibility(View.INVISIBLE);
                btnBForward.setVisibility(View.VISIBLE);

                imgAbout1.setImageResource(R.drawable.ic_filled_circle_24);
                imgAbout2.setImageResource(R.drawable.ic_circle_24);
                imgAbout3.setImageResource(R.drawable.ic_circle_24);

                txtAboutGame.setText(R.string.text_about_1);

                break;
            case 2:
                imgAboutGame.setImageResource(R.drawable.pyramid_200_333_selected_darck);
                btnBack.setVisibility(View.VISIBLE);
                btnBForward.setVisibility(View.VISIBLE);

                imgAbout1.setImageResource(R.drawable.ic_circle_24);
                imgAbout2.setImageResource(R.drawable.ic_filled_circle_24);
                imgAbout3.setImageResource(R.drawable.ic_circle_24);

                txtAboutGame.setText(R.string.text_about_2);

                break;
            case 3:
                imgAboutGame.setImageResource(R.drawable.pyramid_removed_selected_darck_200_330);
                btnBack.setVisibility(View.VISIBLE);
                btnBForward.setVisibility(View.INVISIBLE);

                imgAbout1.setImageResource(R.drawable.ic_circle_24);
                imgAbout2.setImageResource(R.drawable.ic_circle_24);
                imgAbout3.setImageResource(R.drawable.ic_filled_circle_24);

                txtAboutGame.setText(R.string.text_about_3);

                break;
        }
    }

    public void onBtnPageClickListener(View view){
        switch (view.getId()){
            case R.id.btnAboutPage1:
                nomDescription = 1;
                showDescription();

                break;
            case R.id.btnAboutPage2:
                nomDescription = 2;
                showDescription();

                break;
            case R.id.btnAboutPage3:
                nomDescription = 3;
                showDescription();

                break;
        }
    }

    public void onAboutBackClick(View view) {
        if(nomDescription > 1) {
            nomDescription--;
            showDescription();
        }
    }

    public void onAboutForwardClick(View view) {
        if(nomDescription < 3) {
            nomDescription++;
            showDescription();
        }
    }

    public void onAboutCloseClick(View view) {
        this.finish();
    }
}
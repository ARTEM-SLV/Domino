package com.arty.domino.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arty.domino.Constants;
import com.arty.domino.R;

public class FragmentPyramid extends Fragment {
    public static final String TAG = Constants.TAG_PYRAMID;

    private View viewFragment;
    private Context context;

    private DominoesManager dominoesManager;
    private ImageView imageViewDomino;
    private boolean animationFinish;

    int countRestarts = 0;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pyramid, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startGame();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!dominoesManager.isGameStarted()){
            startGame();
        } else if(dominoesManager.isStoppedThread()){
            dominoesManager.startCheckingVariants();
        }
     }

    public void restartGame(){
        dominoesManager.cancelToast();
        dominoesManager.stopAnimationDomino();
        dominoesManager.setStoppedThread(true);
        dominoesManager.setGameStarted(false);

        startGame();
    }

    private void startGame(){
        LinearLayout pyramidLine;
        Animation animTranslateUpToDown;

        viewFragment = getView();
        context = getContext();

        animationFinish = false;

        dominoesManager = new DominoesManager(viewFragment, context, TAG, countRestarts);
        dominoesManager.initDominoes();
        dominoesManager.setGameStarted(true);
        dominoesManager.setStoppedThread(false);
        countRestarts++;

        RelativeLayout area = viewFragment.findViewById(R.id.pyramidLainLayout);
        area.setOnClickListener(v -> {
            stopAnimationLines();
            dominoesManager.checkDominoesValues(v);
        });

        initArea();
        setOpenDominoes();

        int animTranslate = R.anim.translate_from_up_to_down;
        // animation Line 1
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-100);

        pyramidLine = viewFragment.findViewById(R.id.p_line1);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);
        animTranslateUpToDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animationFinish){
                    dominoesManager.openFreeDominoes();

                    animationFinish = false;

                    dominoesManager.checkVariants(false);
                    dominoesManager.startCheckingVariants();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // animation Line 2
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-200);

        pyramidLine = viewFragment.findViewById(R.id.p_line2);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 3
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-300);

        pyramidLine = viewFragment.findViewById(R.id.p_line3);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 4
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-400);

        pyramidLine = viewFragment.findViewById(R.id.p_line4);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 5
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-500);

        pyramidLine = viewFragment.findViewById(R.id.p_line5);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 6
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-600);

        pyramidLine = viewFragment.findViewById(R.id.p_line6);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 7
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-700);

        pyramidLine = viewFragment.findViewById(R.id.p_line7);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);
    }

    private void initArea(){
        String nameView;

        int i = 0;
        for (int line = 1; line < 8; line++) {
            for (int num = 1; num < line + 1; num++) {
                dominoesManager.dominoes[i].setLine(line);
                dominoesManager.dominoes[i].setNum(num);

                nameView = "p" + line + num;
                try {
                    dominoesManager.dominoes[i].setIdView(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "set ID view: ", e);
                }

                i++;
            }
        }

        for (Domino d: dominoesManager.dominoes) {
            imageViewDomino = viewFragment.findViewById(d.getIdView());
            imageViewDomino.setVisibility(View.VISIBLE);
            imageViewDomino.setImageResource(R.drawable.casing_dragon);
            imageViewDomino.clearColorFilter();
            imageViewDomino.setOnClickListener(v -> {
                stopAnimationLines();

                if (dominoesManager.checkDominoesValues(v)){
                    setOpenDominoes();
                    dominoesManager.openFreeDominoes();
                    dominoesManager.checkVariants(false);
                }
            });
        }
    }

    private void setOpenDominoes(){
        Domino d;

        for (int i = 0; i < dominoesManager.dominoes.length; i++) {
            d = dominoesManager.dominoes[i];
            imageViewDomino = viewFragment.findViewById(d.getIdView());

            if(d.getLine() == 1 || d.getLine() == 7){
                d.setOpen(true);
            }else if(topDominoesIsRemoved(d.getLine(), d.getNum()) || bottomDominoesIsRemoved(d.getLine(), d.getNum())){
                d.setOpen(true);
            }
        }
    }

    private boolean topDominoesIsRemoved(int line, int num){
        Domino d;
        boolean isRemoved = true;
        int topLine = line-1;

        for (int i = 0; i < 2; i++) {
            int topNum = num+i-1;
            if(topNum == 0 || topNum == line){
                continue;
            }
            d = dominoesManager.findDomino(topLine, topNum);
            if(d != null) {
                isRemoved = isRemoved & d.isRemoved();
            }
        }

        return isRemoved;
    }

    private boolean bottomDominoesIsRemoved(int line, int num){
        Domino d;
        boolean isRemoved = true;

        for (int i = 0; i < 2; i++) {
            d = dominoesManager.findDomino(line+1, num+i);
            if(d != null) {
                isRemoved = isRemoved & d.isRemoved();
            }
        }

        return isRemoved;
    }

    private void stopAnimationLines(){
        LinearLayout pyramidLine;

        pyramidLine = viewFragment.findViewById(R.id.p_line1);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.p_line2);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.p_line3);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.p_line4);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.p_line5);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.p_line6);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.p_line7);
        pyramidLine.clearAnimation();
    }

    @Override
    public void onStop() {
        dominoesManager.cancelToast();
        dominoesManager.setStoppedThread(true);

        super.onStop();
    }
}

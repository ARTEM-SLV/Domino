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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arty.domino.Constants;
import com.arty.domino.R;

public class FragmentCross extends Fragment {
    public static final String TAG = Constants.TAG_CROSS;

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

        return inflater.inflate(R.layout.fragment_cross, null);
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

        RelativeLayout area = viewFragment.findViewById(R.id.crossLainLayout);
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

        pyramidLine = viewFragment.findViewById(R.id.c_line1);
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

        pyramidLine = viewFragment.findViewById(R.id.c_line2);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 3
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-300);

        pyramidLine = viewFragment.findViewById(R.id.c_line3);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 4
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-400);

        pyramidLine = viewFragment.findViewById(R.id.c_line4);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 5
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-500);

        pyramidLine = viewFragment.findViewById(R.id.c_line5);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 6
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-600);

        pyramidLine = viewFragment.findViewById(R.id.c_line6);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 7
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-700);

        pyramidLine = viewFragment.findViewById(R.id.c_line7);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 8
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-800);

        pyramidLine = viewFragment.findViewById(R.id.c_line8);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 9
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-900);

        pyramidLine = viewFragment.findViewById(R.id.c_line9);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);

        // animation Line 10
        animTranslateUpToDown = AnimationUtils.loadAnimation(context.getApplicationContext(), animTranslate);
        animTranslateUpToDown.setDuration(animTranslateUpToDown.getDuration()-1000);

        pyramidLine = viewFragment.findViewById(R.id.c_line10);
        pyramidLine.clearAnimation();
        pyramidLine.setAnimation(animTranslateUpToDown);
    }

    private void initArea(){
        // line 1
        dominoesManager.dominoes[0].setLine(1);
        dominoesManager.dominoes[0].setNum(1);
        setIdViewImage(0, 1, 1);

        // line 2
        dominoesManager.dominoes[1].setLine(2);
        dominoesManager.dominoes[1].setNum(1);
        setIdViewImage(1, 2, 1);
        dominoesManager.dominoes[1].setRecumbent(true);
        dominoesManager.dominoes[2].setLine(2);
        dominoesManager.dominoes[2].setNum(2);
        setIdViewImage(2, 2, 2);
        dominoesManager.dominoes[2].setRecumbent(true);

        // line 3
        dominoesManager.dominoes[3].setLine(3);
        dominoesManager.dominoes[3].setNum(1);
        setIdViewImage(3, 3, 1);

        // line 4
        dominoesManager.dominoes[4].setLine(4);
        dominoesManager.dominoes[4].setNum(1);
        setIdViewImage(4, 4, 1);
        dominoesManager.dominoes[4].setRecumbent(true);
        dominoesManager.dominoes[5].setLine(4);
        dominoesManager.dominoes[5].setNum(2);
        setIdViewImage(5, 4, 2);
        dominoesManager.dominoes[5].setRecumbent(true);
        dominoesManager.dominoes[6].setLine(4);
        dominoesManager.dominoes[6].setNum(3);
        setIdViewImage(6, 4, 3);
        dominoesManager.dominoes[6].setRecumbent(true);

        // line 5
        dominoesManager.dominoes[7].setLine(5);
        dominoesManager.dominoes[7].setNum(1);
        setIdViewImage(7, 5, 1);


        int i = 8;
        int lineTrue;
        for (int line = 2; line < 7; line++) {
            for (int num = 1; num < line + 1; num++) {
                lineTrue = line+4;
                dominoesManager.dominoes[i].setLine(lineTrue);
                dominoesManager.dominoes[i].setNum(num);
                dominoesManager.dominoes[i].setRecumbent(true);

                setIdViewImage(i, lineTrue, num);

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

    private void setIdViewImage(int i, int line, int num){
        String nameView = "c" + line + num;
        try {
            dominoesManager.dominoes[i].setIdView(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "set ID view: ", e);
        }
    }

    private void setOpenDominoes(){
        Domino d;

        for (int i = 0; i < dominoesManager.dominoes.length; i++) {
            d = dominoesManager.dominoes[i];
            imageViewDomino = viewFragment.findViewById(d.getIdView());

            if(d.getLine() == 1 || d.getLine() == 10 || (d.getLine() == 4 & (d.getNum() == 1 || d.getNum() == 3))){
                d.setOpen(true);
            } else if(topDominoesIsRemoved(d.getLine(), d.getNum()) || bottomDominoesIsRemoved(d.getLine(), d.getNum())){
                d.setOpen(true);
            }
        }
    }

    private boolean topDominoesIsRemoved(int line, int num){
        Domino d1, d2;
        boolean isRemoved = true;

        if (line < 6) {
            switch (line) {
                case 2:
                    d1 = dominoesManager.findDomino(1, 1);
                    if (d1.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
                case 3:
                    d1 = dominoesManager.findDomino(2, 1);
                    d2 = dominoesManager.findDomino(2, 2);
                    if (d1.isRemoved() & d2.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
                case 4:
                    d1 = dominoesManager.findDomino(3, 1);
                    if (d1.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
                case 5:
                    d1 = dominoesManager.findDomino(4, 2);
                    if (d1.isRemoved() ) {
                        return true;
                    } else isRemoved = false;

                    break;
            }
        } else {
            int falseLine = line - 4;
            int topLine = line-1;
            for (int i = 0; i < 2; i++) {
                int topNum = num + i - 1;

                if (topNum == 0 || topNum == falseLine) {
                    continue;
                }
                d1 = dominoesManager.findDomino(topLine, topNum);
                if (d1 != null) {
                    isRemoved = isRemoved & d1.isRemoved();
                }
            }
        }

        return isRemoved;
    }

    private boolean bottomDominoesIsRemoved(int line, int num){
        Domino d1, d2;
        boolean isRemoved = true;

        if (line < 6) {
            switch (line) {
                case 2:
                    d2 = dominoesManager.findDomino(3, 1);
                    if (d2.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
                case 3:
                    d1 = dominoesManager.findDomino(4, 2);
                    if (d1.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
                case 4:
                    d1 = dominoesManager.findDomino(5, 1);
                    if (d1.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
                case 5:
                    d1 = dominoesManager.findDomino(6, 1);
                    d2 = dominoesManager.findDomino(6, 2);
                    if (d1.isRemoved() & d2.isRemoved()) {
                        return true;
                    } else isRemoved = false;

                    break;
            }
        } else {
            for (int i = 0; i < 2; i++) {
                d1 = dominoesManager.findDomino(line + 1, num + i);
                if (d1 != null) {
                    isRemoved = isRemoved & d1.isRemoved();
                }
            }
        }

        return isRemoved;
    }

    @Override
    public void onStop() {
        dominoesManager.cancelToast();
        dominoesManager.setStoppedThread(true);

        super.onStop();
    }

    private void stopAnimationLines(){
        LinearLayout pyramidLine;

        pyramidLine = viewFragment.findViewById(R.id.c_line1);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line2);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line3);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line4);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line5);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line6);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line7);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line8);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line9);
        pyramidLine.clearAnimation();
        pyramidLine = viewFragment.findViewById(R.id.c_line10);
        pyramidLine.clearAnimation();
    }
}

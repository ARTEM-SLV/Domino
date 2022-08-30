package com.arty.domino.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arty.domino.Constants;
import com.arty.domino.R;

public class FragmentFountain extends Fragment {
    public static final String TAG = Constants.TAG_FOUNTAIN;

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

        return inflater.inflate(R.layout.fragment_fountain, null);
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
        viewFragment = getView();
        context = getContext();

        animationFinish = false;

        dominoesManager = new DominoesManager(viewFragment, context, TAG, countRestarts);
        dominoesManager.initDominoes();
        dominoesManager.setGameStarted(true);
        dominoesManager.setStoppedThread(false);
        countRestarts++;

        RelativeLayout area = viewFragment.findViewById(R.id.fountainMainLayout);
        area.setOnClickListener(v -> {
            stopAnimationLines();
            dominoesManager.checkDominoesValues(v);
        });

        initArea();
        setOpenDominoes();

        setAnimationStart();
    }

    private void setAnimationStart(){
        Animation animAlphaFull;

        int animAlpha = R.anim.alpha_full;
        int offset = 0;
        for (int line = 1; line < 10; line++) {
            if (line < 5){
                for (int num = 1; num < 4; num++) {
                    animAlphaFull = AnimationUtils.loadAnimation(context.getApplicationContext(), animAlpha);
                    animAlphaFull.setStartOffset(offset);
                    offset +=50;

                    String nameView = "f" + line + num;
                    imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                    imageViewDomino.clearAnimation();
                    imageViewDomino.setAnimation(animAlphaFull);
                }
            } else if (line >= 5 & line < 9){
                for (int num = 1; num < 3; num++) {
                    animAlphaFull = AnimationUtils.loadAnimation(context.getApplicationContext(), animAlpha);
                    animAlphaFull.setStartOffset(offset);
                    offset +=50;

                    String nameView = "f" + line + num;
                    imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                    imageViewDomino.clearAnimation();
                    imageViewDomino.setAnimation(animAlphaFull);
                }
            }else if (line==9) {
                for (int num = 1; num < 5; num++) {
                    animAlphaFull = AnimationUtils.loadAnimation(context.getApplicationContext(), animAlpha);
                    animAlphaFull.setStartOffset(offset);
                    offset +=50;

                    String nameView = "f" + line + num;
                    imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                    imageViewDomino.clearAnimation();
                    imageViewDomino.setAnimation(animAlphaFull);
                }
            }
        }
        animAlphaFull = AnimationUtils.loadAnimation(context.getApplicationContext(), animAlpha);
        animAlphaFull.setStartOffset(offset+50);
        for (int num = 1; num < 5; num++) {
            String nameView = "f0" + num;
            imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
            imageViewDomino.clearAnimation();
            imageViewDomino.setAnimation(animAlphaFull);
            animAlphaFull.setAnimationListener(new Animation.AnimationListener() {
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
        }
    }

    private void initArea(){
        int i = 0;

        for (int num = 1; num < 5; num++) {
            dominoesManager.dominoes[i].setLine(0);
            dominoesManager.dominoes[i].setNum(num);

            String nameView = "f0" + num;
            dominoesManager.dominoes[i].setIdView(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));

            i++;
        }
        for (int line = 1; line < 10; line++) {
            if (line < 5){
                for (int num = 1; num < 4; num++) {
                    dominoesManager.dominoes[i].setLine(line);
                    dominoesManager.dominoes[i].setNum(num);
                    dominoesManager.dominoes[i].setRecumbent(line%2==0);

                    String nameView = "f" + line + num;
                    dominoesManager.dominoes[i].setIdView(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));

                    i++;
                }
            } else if (line >= 5 & line < 9){
                for (int num = 1; num < 3; num++) {
                    dominoesManager.dominoes[i].setLine(line);
                    dominoesManager.dominoes[i].setNum(num);
                    dominoesManager.dominoes[i].setRecumbent(line%2==0);

                    String nameView = "f" + line + num;
                    dominoesManager.dominoes[i].setIdView(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));

                    i++;
                }
            }else if (line==9) {
                for (int num = 1; num < 5; num++) {
                    dominoesManager.dominoes[i].setLine(line);
                    dominoesManager.dominoes[i].setNum(num);
                    dominoesManager.dominoes[i].setRecumbent(num%2==0);

                    String nameView = "f" + line + num;
                    dominoesManager.dominoes[i].setIdView(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));

                    i++;
                }
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
        int line;

        for (int i = 0; i < dominoesManager.dominoes.length; i++) {
            d = dominoesManager.dominoes[i];
            imageViewDomino = viewFragment.findViewById(d.getIdView());

            line = d.getLine();
            if(line == 0){
                d.setOpen(true);
            } else if((line == 1 || line == 2 || line == 3 || line == 4) & d.getNum() == 2){
                d.setOpen(true);
            }else if(topDominoesIsRemoved(line, d.getNum()) || botDominoesIsRemoved(line, d.getNum())){
                d.setOpen(true);
            }
        }
    }

    private boolean topDominoesIsRemoved(int line, int num){
        Domino d;
        boolean isRemoved = true;

        if (line == 1 & num == 1 || line == 4 & num == 3){
            d = dominoesManager.findDomino(0, 1);
            isRemoved = d.isRemoved();
        } else if (line == 1 & num == 3 || line == 2 & num == 1) {
            d = dominoesManager.findDomino(0, 2);
            isRemoved = d.isRemoved();
        } else if (line == 2 & num == 3 || line == 3 & num == 1) {
            d = dominoesManager.findDomino(0, 3);
            isRemoved = d.isRemoved();
        } else if (line == 3 & num == 3 || line == 4 & num == 1) {
            d = dominoesManager.findDomino(0, 4);
            isRemoved = d.isRemoved();
        } else if (line < 9) {
            int topLine = line-4;
            for (int i = 0; i < 2; i++) {
                int topNum = num+i;
                d = dominoesManager.findDomino(topLine, topNum);
                if(d != null) {
                    isRemoved = isRemoved & d.isRemoved();
                }
            }
        } else {
            int topLine = line+num-5;
            for (int i = 1; i < 3; i++) {
                d = dominoesManager.findDomino(topLine, i);
                if(d != null) {
                    isRemoved = isRemoved & d.isRemoved();
                }
            }
        }

        return isRemoved;
    }

    private boolean botDominoesIsRemoved(int line, int num){
        Domino d1, d2;
        boolean isRemoved = false;

        if (line == 9 & num == 1){
            d1 = dominoesManager.findDomino(9, 4);
            isRemoved = d1.isRemoved();
        } else if (line == 9 & num == 2) {
            d1 = dominoesManager.findDomino(9, 1);
            isRemoved = d1.isRemoved();
        } else if (line == 9 & num == 3) {
            d1 = dominoesManager.findDomino(9, 2);
            isRemoved = d1.isRemoved();
        } else if (line == 9 & num == 4) {
            d1 = dominoesManager.findDomino(9, 3);
            isRemoved = d1.isRemoved();
        } else if (line == 5 & num == 1) {
            d1 = dominoesManager.findDomino(9, 1);
            d2 = dominoesManager.findDomino(8, 2);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 5 & num == 2) {
            d1 = dominoesManager.findDomino(9, 1);
            d2 = dominoesManager.findDomino(9, 2);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 6 & num == 1) {
            d1 = dominoesManager.findDomino(5, 2);
            d2 = dominoesManager.findDomino(9, 2);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 6 & num == 2) {
            d1 = dominoesManager.findDomino(9, 2);
            d2 = dominoesManager.findDomino(9, 3);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 7 & num == 1) {
            d1 = dominoesManager.findDomino(6, 2);
            d2 = dominoesManager.findDomino(9, 3);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 7 & num == 2) {
            d1 = dominoesManager.findDomino(9, 3);
            d2 = dominoesManager.findDomino(9, 4);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 8 & num == 1) {
            d1 = dominoesManager.findDomino(7, 2);
            d2 = dominoesManager.findDomino(9, 4);
            isRemoved = d1.isRemoved() & d2.isRemoved();
        } else if (line == 8 & num == 2) {
            d1 = dominoesManager.findDomino(9, 1);
            d2 = dominoesManager.findDomino(9, 4);
            isRemoved = d1.isRemoved() & d2.isRemoved();
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
        for (int line = 1; line < 10; line++) {
            if (line < 5){
                for (int num = 1; num < 4; num++) {
                    String nameView = "f" + line + num;
                    imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                    imageViewDomino.clearAnimation();
                }
            } else if (line >= 5 & line < 9){
                for (int num = 1; num < 3; num++) {
                    String nameView = "f" + line + num;
                    imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                    imageViewDomino.clearAnimation();
                }
            }else if (line==9) {
                for (int num = 1; num < 5; num++) {
                    String nameView = "f" + line + num;
                    imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
                    imageViewDomino.clearAnimation();
                }
            }
        }
        for (int num = 1; num < 5; num++) {
            String nameView = "f0" + num;
            imageViewDomino = viewFragment.findViewById(this.getResources().getIdentifier(nameView, "id", context.getPackageName()));
            imageViewDomino.clearAnimation();
        }
    }
}

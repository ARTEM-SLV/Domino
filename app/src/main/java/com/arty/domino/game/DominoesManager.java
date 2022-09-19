package com.arty.domino.game;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.arty.domino.Constants;
import com.arty.domino.EndGameDialogActivity;
import com.arty.domino.R;

import java.util.Random;

public class DominoesManager {
    public final String tagArea;
    private Toast toast;

    public final Domino[] dominoes;
    private final Random random;

    private final View viewFragment;
    private final Context fragmentContext;

    private Domino selectedDomino = null;
    private ImageView imageViewDomino, im1, im2;

    private int countPairDomino = 14;
    private long checkedVariantTime = 0;

    private boolean stoppedThread = true;
    private boolean gameStarted = false;

    private final int countRestarts;

    public DominoesManager(View view, Context context, String TAG, int countRestarts) {
        this.viewFragment = view;
        this.fragmentContext = context;
        this.tagArea = TAG;
        this.countRestarts = countRestarts;

        dominoes = new Domino[28];
        random = new Random();

        for (int i = 0; i < dominoes.length; i++) {
            dominoes[i] = new Domino();
        }
    }

    public boolean isStoppedThread() {
        return this.stoppedThread;
    }

    public void setStoppedThread(boolean stop){
        this.stoppedThread = stop;
    }

    public boolean isGameStarted() {
        return this.gameStarted;
    }

    public void setGameStarted(boolean start) {
        this.gameStarted = start;
    }

    public void initDominoes(){
        int[] arrValue = new int[28];
        int[] arrDominoImage = new int[28];

        fillArrKeyImage(arrValue, arrDominoImage);

        String key = tagArea + Constants.TOTAL_WINS;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragmentContext);
        int total_wins = sharedPreferences.getInt(key, 0);

        int i = 0;
        while (i<28){
            int randomNum = random.nextInt(13);

            if ((countRestarts != 0 && countRestarts%10 == 0 ) || total_wins < 3){
                if(tagArea != Constants.TAG_FOUNTAIN) {
                    if (i < 8 || i > 19) {
                        if (randomNum <= 3 || randomNum >= 9) {
                            randomNum = random.nextInt(4) + 4;
                        }
                    } else if (randomNum > 3 && randomNum < 9) continue;
                }
            }

            int countValues = getCountValuesInArr(dominoes, randomNum);

            if (countValues != 0){
                if(randomNum == 0 || randomNum == 1 || randomNum == 11 || randomNum == 12){
                    continue;
                }
                else if((randomNum == 2 || randomNum == 3 || randomNum == 9 || randomNum == 10) & countValues == 2){
                    continue;
                }
                else if((randomNum == 4 || randomNum == 5 || randomNum == 7 || randomNum == 8) & countValues == 3){
                    continue;
                }
                else if(randomNum == 6 & countValues == 4){
                    continue;
                }
            }

            Domino d = dominoes[i];

            d.setValue(randomNum);

            int index = findImageIndexOnValue(arrValue, randomNum);
            d.setSkin(arrDominoImage[index]);

            i++;
        }
    }

    public void startCheckingVariants(){
        checkedVariantTime =  System.currentTimeMillis();
        this.stoppedThread = false;
        Thread checkingVariants = new Thread(() -> {
            while(!stoppedThread){
                try {
                    Thread.sleep(3000);

                    if(checkedVariantTime + 15000 < System.currentTimeMillis() & !stoppedThread){
                        checkVariants(true);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(tagArea, "Thread checkingVariants error: ", e);
                }
            }
        });
        checkingVariants.start();
    }

    public void checkVariants(boolean justChecking){
        Domino d1, d2;

        checkedVariantTime = System.currentTimeMillis();

        for (int i = 0; i < 28; i++) {
            d1 = dominoes[i];
            if (!d1.isOpen() || d1.isRemoved()){
                continue;
            }

            for (int j = i+1; j < 28; j++) {
                d2 = dominoes[j];
                if (!d2.isOpen() || d2.isRemoved()){
                    continue;
                }

                if (d1.getValue() + d2.getValue() == 12){
                    Animation animBlink = AnimationUtils.loadAnimation(fragmentContext.getApplicationContext(), R.anim.blink);
                    if (justChecking) {
                        im1 = viewFragment.findViewById(d1.getIdView());
                        im1.clearAnimation();
                        im1.setAnimation(animBlink);

                        im2 = viewFragment.findViewById(d2.getIdView());
                        im2.clearAnimation();
                        im2.setAnimation(animBlink);
                        animBlink.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                checkedVariantTime = System.currentTimeMillis(); // если вариант найден, то обновляем время до следующего запуска проверки
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    return;
                }
            }
        }

        if (!justChecking) {
            this.stoppedThread = true;
            gameStarted = false;

            showMessageNoVariants();
        }
    }

    private void showMessageNoVariants(){
        cancelToast();

        toast = Toast.makeText(viewFragment.getContext(), R.string.text_no_more_variants, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void cancelToast(){
        if (toast != null){
            toast.cancel();
        }
    }

    public boolean checkDominoesValues(View v) {
        Domino currentDomino = findDominoOnIdView(dominoes, v.getId());
        boolean result = false;

        if (!gameStarted){
            if (currentDomino != null){
                showMessageNoVariants();
            }

            return false;
        }

        stopAnimationDomino();

        if(currentDomino == null){
            if(selectedDomino != null) {
                imageViewDomino = viewFragment.findViewById(selectedDomino.getIdView());
                imageViewDomino.clearColorFilter();

                selectedDomino = null;
            }

            return false;
        }

        if (currentDomino.isOpen()){
            if (selectedDomino == null){
                selectedDomino = currentDomino;
                imageViewDomino = (ImageView) v;
                imageViewDomino.setColorFilter(R.color.foreground);
            }else if(selectedDomino == currentDomino){
                selectedDomino = null;

                imageViewDomino = viewFragment.findViewById(currentDomino.getIdView());
                imageViewDomino.clearColorFilter();
            }else if(selectedDomino.getValue() + currentDomino.getValue() == 12){
                imageViewDomino = viewFragment.findViewById(selectedDomino.getIdView());
                imageViewDomino.setVisibility(View.INVISIBLE);
                selectedDomino.setRemoved(true);

                imageViewDomino = viewFragment.findViewById(currentDomino.getIdView());
                imageViewDomino.setVisibility(View.INVISIBLE);
                currentDomino.setRemoved(true);

                countPairDomino--;
                if(countPairDomino > 0) {
                    result = true;
                } else {
                    this.stoppedThread = true;
                    this.gameStarted = false;

                    showDialogEndGame(selectedDomino.getValue(), currentDomino.getValue());
                }

                selectedDomino = null;
            }else {
                imageViewDomino = viewFragment.findViewById(currentDomino.getIdView());
                imageViewDomino.setColorFilter(R.color.foreground);

                imageViewDomino = viewFragment.findViewById(selectedDomino.getIdView());
                imageViewDomino.clearColorFilter();

                selectedDomino = currentDomino;
            }
        }

        return result;
    }

    private void showDialogEndGame(int v1, int v2) {
        int idRank = 0;

        if (v1 == 12 || v2 == 12) {
            idRank = 3;
        } else if (v1 == 11 || v2 == 11) {
            idRank = 2;
        } else if (v1 == 10 || v2 == 10) {
            idRank = 1;
        }

        Intent intent = new Intent(fragmentContext, EndGameDialogActivity.class);
        intent.putExtra("idRank", idRank);
        intent.putExtra("TAG", tagArea);
        fragmentContext.startActivity(intent);
    }

    public void openFreeDominoes(){
        Domino d;

        for (Domino domino : dominoes) {
            ObjectAnimator animation;
            imageViewDomino = viewFragment.findViewById(domino.getIdView());
            d = domino;
            if (!d.isRemoved() & d.isOpen() & d.wasClosed()) {
                if(!domino.isRecumbent()){
                    animation = ObjectAnimator.ofFloat(imageViewDomino, "rotationY", 90f, 0.0f);
                } else animation = ObjectAnimator.ofFloat(imageViewDomino, "rotationX", 90f, 0.0f);
                animation.setDuration(800);
                animation.setRepeatCount(0);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.start();

                imageViewDomino.setImageResource(d.getSkin());

                d.setWasClosed(false);
            }
        }
    }

    public void stopAnimationDomino(){
        if (im1 == null || im2 == null){
            return;
        }

        im1.clearAnimation();
        im2.clearAnimation();
    }

    public Domino findDomino(int line, int num){
        Domino result = null;

        for (Domino domino : dominoes) {
            if (domino.getLine() == line & domino.getNum() == num) {
                result = domino;

                break;
            }
        }

        return result;
    }

    public Domino findDominoOnIdView(Domino[] dominoes, int idView) {
        Domino result = null;

        for (Domino domino : dominoes) {
            if (domino.getIdView() == idView) {
                result = domino;

                break;
            }
        }

        return result;
    }

    private static int getCountValuesInArr(Domino[] dominoes, int elementToSearch) {
        int count = 0;

        for (Domino domino : dominoes) {
            if (domino.getValue() == elementToSearch)
                count++;
        }
        return count;
    }

    private int findImageIndexOnValue(int[] arrValue, int value) {
        StringBuilder findValues = new StringBuilder();

        for (int i = 0; i < arrValue.length; i++) {
            if (arrValue[i] == value) {
                if(findValues.length() == 0){
                    findValues = new StringBuilder("" + i);
                }else findValues.append(",").append(i);
            }
        }

        String[] arrIndexes = findValues.toString().split(",");
        int index = Integer.parseInt(arrIndexes[random.nextInt(arrIndexes.length)]);

        arrValue[index] = -1;

        return index;
    }

    private void fillArrKeyImage(int[] arrKeys, int[] arrDominoImage){
        arrKeys[0] = 0;
        arrDominoImage[0] = R.drawable.d00;

        arrKeys[1] = 1;
        arrDominoImage[1] = R.drawable.d01;

        arrKeys[2] = 2;
        arrDominoImage[2] = R.drawable.d02;
        arrKeys[3] = 2;
        arrDominoImage[3] = R.drawable.d11;

        arrKeys[4] = 3;
        arrDominoImage[4] = R.drawable.d03;
        arrKeys[5] = 3;
        arrDominoImage[5] = R.drawable.d12;

        arrKeys[6] = 4;
        arrDominoImage[6] = R.drawable.d04;
        arrKeys[7] = 4;
        arrDominoImage[7] = R.drawable.d13;
        arrKeys[8] = 4;
        arrDominoImage[8] = R.drawable.d22;

        arrKeys[9] = 5;
        arrDominoImage[9] = R.drawable.d05;
        arrKeys[10] = 5;
        arrDominoImage[10] = R.drawable.d14;
        arrKeys[11] = 5;
        arrDominoImage[11] = R.drawable.d23;

        arrKeys[12] = 6;
        arrDominoImage[12] = R.drawable.d06;
        arrKeys[13] = 6;
        arrDominoImage[13] = R.drawable.d15;
        arrKeys[14] = 6;
        arrDominoImage[14] = R.drawable.d24;
        arrKeys[15] = 6;
        arrDominoImage[15] = R.drawable.d33;

        arrKeys[16] = 7;
        arrDominoImage[16] = R.drawable.d16;
        arrKeys[17] = 7;
        arrDominoImage[17] = R.drawable.d25;
        arrKeys[18] = 7;
        arrDominoImage[18] = R.drawable.d34;

        arrKeys[19] = 8;
        arrDominoImage[19] = R.drawable.d26;
        arrKeys[20] = 8;
        arrDominoImage[20] = R.drawable.d35;
        arrKeys[21] = 8;
        arrDominoImage[21] = R.drawable.d44;

        arrKeys[22] = 9;
        arrDominoImage[22] = R.drawable.d36;
        arrKeys[23] = 9;
        arrDominoImage[23] = R.drawable.d45;

        arrKeys[24] = 10;
        arrDominoImage[24] = R.drawable.d46;
        arrKeys[25] = 10;
        arrDominoImage[25] = R.drawable.d55;

        arrKeys[26] = 11;
        arrDominoImage[26] = R.drawable.d56;

        arrKeys[27] = 12;
        arrDominoImage[27] = R.drawable.d66;
    }
}

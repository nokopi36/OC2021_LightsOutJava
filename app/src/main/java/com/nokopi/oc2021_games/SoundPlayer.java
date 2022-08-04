package com.nokopi.oc2021_games;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private static SoundPool soundpool;
    private static int touchLightSound;
    private static int startSound;
    private static int selectGameSound;
    private static int gameClearSound;

    private AudioAttributes audioAttributes;

    public SoundPlayer(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundpool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(2)
                    .build();

        } else {
            soundpool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        touchLightSound = soundpool.load(context, R.raw.button02b, 1);
        startSound = soundpool.load(context, R.raw.start, 1);
        selectGameSound = soundpool.load(context, R.raw.selectgame, 1);
        gameClearSound = soundpool.load(context, R.raw.clear, 1);

    }

    public void playTouchLightSound(){
        soundpool.play(touchLightSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playStartSound(){
        soundpool.play(startSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSelectGameSound(){
        soundpool.play(selectGameSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playGameClearSound(){
        soundpool.play(gameClearSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}

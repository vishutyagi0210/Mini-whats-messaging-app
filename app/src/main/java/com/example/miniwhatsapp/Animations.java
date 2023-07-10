package com.example.miniwhatsapp;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animations {
    Context context;

    public Animations(Context context){
        this.context = context;
    }

    public Animation increaseSizeAnim(int duration){
        Animation anim = AnimationUtils.loadAnimation(context , R.anim.size_increase);
        anim.setDuration(duration);
        return anim;
    }

}

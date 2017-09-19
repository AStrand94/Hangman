package com.example.astrand.hangman.Helper;

import android.content.Context;
import android.util.AttributeSet;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.io.Serializable;

public class MyBootstrapButton extends BootstrapButton implements Serializable {
    public MyBootstrapButton(Context context) {
        super(context);
    }

    public MyBootstrapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBootstrapButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}

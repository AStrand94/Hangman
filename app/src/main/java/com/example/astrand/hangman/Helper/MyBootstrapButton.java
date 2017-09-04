package com.example.astrand.hangman.Helper;

import android.content.Context;
import android.util.AttributeSet;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.io.Serializable;

/**
 * Created by strand117 on 04.09.2017.
 */

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

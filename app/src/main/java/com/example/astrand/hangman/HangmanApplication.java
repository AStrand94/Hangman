package com.example.astrand.hangman;

import android.app.Application;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;

import java.util.Locale;

/**
 * Created by strand117 on 21.08.2017.
 */

public class HangmanApplication extends Application {

    @Override
    public void onCreate(){
        Log.d("HangmanApplication","Custom application class, locale: " + Locale.getDefault());
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}

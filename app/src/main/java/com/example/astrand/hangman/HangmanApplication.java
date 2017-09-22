package com.example.astrand.hangman;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.astrand.hangman.Activities.GameActivity;
import com.example.astrand.hangman.Activities.MainActivity;

import java.util.Locale;


public class HangmanApplication extends Application {

    @Override
    public void onCreate(){
        Log.d("HangmanApplication","Custom application class, locale: " + Locale.getDefault());
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}

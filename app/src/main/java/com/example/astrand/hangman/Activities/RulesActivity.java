package com.example.astrand.hangman.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.example.astrand.hangman.R;


public class RulesActivity extends AppCompatActivity{

    AwesomeTextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.rules));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (AwesomeTextView)findViewById(R.id.rulesText);
        textView.setTextColor(Color.BLACK);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        textView.setTextColor(Color.BLACK);
    }
}

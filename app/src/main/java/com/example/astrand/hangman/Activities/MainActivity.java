package com.example.astrand.hangman.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.astrand.hangman.ModelSettings.Language;
import com.example.astrand.hangman.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /*
    TODO: Set correct spinner text
    TODO: Inherit from BootstrapButton
     */

    Spinner languageSelector;
    BootstrapButton startButton, statisticsButton, rulesButton,setLanguageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (BootstrapButton)findViewById(R.id.startButton);
        statisticsButton = (BootstrapButton)findViewById(R.id.statisticsButton);
        rulesButton = (BootstrapButton)findViewById(R.id.rulesButton);
        setLanguageButton = (BootstrapButton)findViewById(R.id.languageButton);
        languageSelector =  (Spinner)findViewById(R.id.select_language_spinner);

        instantiateButtonListeners();
        setSpinnerValues();

        Log.d("LANGUAGE",getResources().getConfiguration().locale.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSpinnerValues();
    }

    private void instantiateButtonListeners(){
        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                goToGame();
            }
        });
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchStatisticsActivity();
            }
        });
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRulesActivity();
            }
        });

        setLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Language lan = (Language)languageSelector.getSelectedItem();
                if (!lan.getLocale().getLanguage().equals(getResources().getConfiguration().locale.getLanguage()))
                    setLocale(lan.getLocale());
            }
        });
    }

    private void setSpinnerValues(){
        final Language[] languages = Language.getDefaultLanguages(getResources());
        languageSelector.setAdapter(new ArrayAdapter<Language>(this,R.layout.support_simple_spinner_dropdown_item,languages));
    }

    private void goToGame(){
        launchGameActivity();
    }

    private void setLocale(Locale locale){
        Resources resources = getResources();
        resources.getConfiguration().setLocale(locale);

        resources.updateConfiguration(resources.getConfiguration(), getBaseContext().getResources().getDisplayMetrics());
        recreate(); //only to be called on top-level activity. Needed to show changes when language is changed
    }


    private void launchGameActivity(){
        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }

    private void launchStatisticsActivity(){
        Intent intent = new Intent(MainActivity.this,StatisticsActivity.class);
        startActivity(intent);
    }

    private void launchRulesActivity(){
        Intent intent = new Intent(MainActivity.this,RulesActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Do something with 'Intent data' e.g. highscore.
    }
}
package com.example.astrand.hangman.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.astrand.hangman.ModelSettings.Language;
import com.example.astrand.hangman.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /*
    TODO: Set correct spinner text
    TODO: Enable horizontal view from phone
    TODO: Rules
     */

    Spinner languageSelector;
    BootstrapButton startButton, statisticsButton, rulesButton;
    boolean spinnerFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (BootstrapButton)findViewById(R.id.startButton);
        statisticsButton = (BootstrapButton)findViewById(R.id.statisticsButton);
        rulesButton = (BootstrapButton)findViewById(R.id.rulesButton);

        instantiateButtonListeners();
        instantiateSpinner();

        Log.d("LANGUAGE",getResources().getConfiguration().locale.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        instantiateSpinner();
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
    }

    private void instantiateSpinner(){
        languageSelector =  (Spinner)findViewById(R.id.select_language_spinner);

        final Language[] languages = Language.getDefaultLanguages(getResources());

        languageSelector.setAdapter(new ArrayAdapter<Language>(this,R.layout.support_simple_spinner_dropdown_item,languages));

        languageSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerFlag){
                    spinnerFlag = true;
                    return;
                }
                Locale currentLocale = getResources().getConfiguration().locale;
                Locale selectedLocale = ((Language)languageSelector.getSelectedItem()).getLocale();
                if (!currentLocale.getCountry().equals(selectedLocale.getCountry())) setLocale(selectedLocale);

                //if(!currentLocale.equals((Language)languageSelector.getSelectedItem()))   setLocale((Locale)languageSelector.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });
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
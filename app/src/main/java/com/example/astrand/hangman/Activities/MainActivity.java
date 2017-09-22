package com.example.astrand.hangman.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.astrand.hangman.R;
import com.example.astrand.hangman.Services.RandomWordService;
import com.example.astrand.hangman.Services.StatisticsService;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /*
        TODO: Bakgrunn i MainActivity? I det minste annen bakgrunnsfarge.
     */

    BootstrapButton startButton, statisticsButton, rulesButton;// setLanguageButton;
    ImageView enFlag, noFlag;
    private static final Locale NO_LOCALE = new Locale("nb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (BootstrapButton) findViewById(R.id.startButton);
        statisticsButton = (BootstrapButton) findViewById(R.id.statisticsButton);
        rulesButton = (BootstrapButton) findViewById(R.id.rulesButton);
        enFlag = (ImageView) findViewById(R.id.flag1);
        noFlag = (ImageView) findViewById(R.id.flag2);

        instantiateButtonListeners();

        if(!hasSetStartLocale()) setStartLanguage();
        else setStartFlag();

        Log.d("LANGUAGE", getResources().getConfiguration().locale.toString());
    }

    private void setStartFlag() {
        if (getResources().getConfiguration().locale.getLanguage().equals("nb")) {
            setNoLanguageFlagActive();
        } else {
            setEnLanguageFlagActive();
        }
    }

    private void setStartLanguage(){
        String lastLocale = getLastLocale();
        if (lastLocale.equals("")) {
            if (getResources().getConfiguration().locale.getLanguage().equals("nb")) {
                setNoLanguageFlagActive();
                setLastLocale("nb");
            } else {
                setEnLanguageFlagActive();
                setLastLocale("en");
            }
        }else if (lastLocale.equals("nb")){
            setNoLanguage();
            setNoLanguageFlagActive();
        }else{
            setEnLanguage();
            setEnLanguageFlagActive();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startButton.setTextColor(getColor(R.color.bootstrap_gray_dark));
        statisticsButton.setTextColor(getColor(R.color.bootstrap_gray_dark));
        rulesButton.setTextColor(getColor(R.color.bootstrap_gray_dark));
    }


    private void instantiateButtonListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        enFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnLanguage();
                setLastLocale("en");
            }
        });
        noFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoLanguage();
                setLastLocale("nb");
            }
        });
    }

    private void setNoLanguage() {
        getResources().getConfiguration().setLocale(NO_LOCALE);
        RandomWordService.clearList();
        setLastLocale("nb");
        setLocale(NO_LOCALE);
    }

    private void setEnLanguage() {
        getResources().getConfiguration().setLocale(Locale.ENGLISH);
        RandomWordService.clearList();
        setLastLocale("en");
        setLocale(Locale.ENGLISH);
    }

    private void setEnLanguageFlagActive() {
        noFlag.setColorFilter(Color.argb(150,200,200,200));
        enFlag.setColorFilter(null);
    }

    private void setNoLanguageFlagActive() {
        enFlag.setColorFilter(Color.argb(150,200,200,200));
        noFlag.setColorFilter(null);
    }

    private void goToGame() {
        launchGameActivity();
    }

    private void setLocale(Locale locale) {
        Resources resources = getResources();
        resources.getConfiguration().setLocale(locale);

        resources.updateConfiguration(resources.getConfiguration(), getBaseContext().getResources().getDisplayMetrics());
        recreate(); //only to be called on top-level activity. Needed to show changes when language is changed
    }


    private void launchGameActivity() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    private void launchStatisticsActivity() {
        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    private void launchRulesActivity() {
        Intent intent = new Intent(MainActivity.this, RulesActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getLastLocale(){
        return getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE)
                .getString("locale","");
    }

    private void setLastLocale(String code){
        getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE)
                .edit()
                .putString("locale",code)
                .apply();
    }

    private boolean hasSetStartLocale() {
        SharedPreferences s = getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE);

        if (s.getBoolean("localeIsSet",true)){
            s.edit().putBoolean("localeIsSet",false).apply();
            return true;
        }else{
            s.edit().putBoolean("localeIsSet",true).apply();
            return false;
        }
    }
}
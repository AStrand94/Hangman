package com.example.astrand.hangman.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.astrand.hangman.R;
import com.example.astrand.hangman.Services.StatisticsService;

import java.util.HashMap;

public class StatisticsActivity extends AppCompatActivity {

    BootstrapLabel numberWon, numberLost, leastLetter, mostLetter;
    HashMap<String,String> statistics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        loadStatistics();
        instantiateMembers();
    }

    private void instantiateMembers() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.statistics));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numberLost = (BootstrapLabel)findViewById(R.id.numberLost);
        numberWon = (BootstrapLabel)findViewById(R.id.numberWon);
        mostLetter = (BootstrapLabel)findViewById(R.id.mostLetter);
        leastLetter = (BootstrapLabel)findViewById(R.id.leastLetter);

        setStatistics();
    }

    private void setStatistics() {
        if (statistics == null) return;
        numberLost.setText(numberLost.getText() + " " + statistics.get(StatisticsService.NUMBER_LOST));
        numberWon.setText(numberWon.getText() + " " + statistics.get(StatisticsService.NUMBER_WON));
        mostLetter.setText(mostLetter.getText() + " " + statistics.get(StatisticsService.MOST_USED));
        leastLetter.setText(leastLetter.getText() + " " + statistics.get(StatisticsService.LEAST_USED));
    }

    private void loadStatistics(){
        statistics = StatisticsService.getStatistics(
                        getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE),
                        getString(R.string.alphabet).split(""));
    }




}

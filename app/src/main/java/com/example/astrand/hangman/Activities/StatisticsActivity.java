package com.example.astrand.hangman.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.example.astrand.hangman.R;
import com.example.astrand.hangman.Services.StatisticsService;

import java.util.HashMap;

public class StatisticsActivity extends AppCompatActivity {

    AwesomeTextView numberWon, numberLost, leastLetter, mostLetter, numberLostVal, numberWonVal, leastLetterVal,mostLetterVal,bestTime,bestTimeVal;
    HashMap<String,String> statistics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        loadStatistics();
        instantiateMembers();
        setStatistics();
    }

    @SuppressWarnings("ConstantConditions")
    private void instantiateMembers() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.statistics));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numberLost = (AwesomeTextView)findViewById(R.id.numberLost);
        numberLostVal = (AwesomeTextView)findViewById(R.id.numberLost_val);

        numberWon = (AwesomeTextView)findViewById(R.id.numberWon);
        numberWonVal = (AwesomeTextView)findViewById(R.id.numberWon_val);

        mostLetter = (AwesomeTextView)findViewById(R.id.mostLetter);
        mostLetterVal = (AwesomeTextView)findViewById(R.id.mostLetter_val);

        leastLetter = (AwesomeTextView)findViewById(R.id.leastLetter);
        leastLetterVal = (AwesomeTextView)findViewById(R.id.leastLetter_val);

        bestTime = (AwesomeTextView)findViewById(R.id.bestTime);
        bestTimeVal = (AwesomeTextView)findViewById(R.id.bestTime_val);

        setColors();
    }

    private void setStatistics() {
        if (statistics == null || statistics.isEmpty()) setNoStatistics();
        numberLostVal.setText(statistics.get(StatisticsService.NUMBER_LOST));
        numberWonVal.setText(statistics.get(StatisticsService.NUMBER_WON));
        mostLetterVal.setText(statistics.get(StatisticsService.MOST_USED));
        leastLetterVal.setText(statistics.get(StatisticsService.LEAST_USED));
        String val = statistics.get(StatisticsService.TIME_WON);
        bestTimeVal.setText((val == null || val.isEmpty() || val.equals("0")) ? getString(R.string.no_statistics) : val + getString(R.string.seconds));
    }

    private void setNoStatistics() {
        numberLost.setText(getString(R.string.no_statistics));
        numberLostVal.setText("");
        numberWon.setText("");
        numberWonVal.setText("");
        mostLetter.setText("");
        mostLetterVal.setText("");
        leastLetter.setText("");
        leastLetterVal.setText("");
        bestTime.setText("");
        bestTimeVal.setText("");
    }

    private void setColors(){
        numberLost.setTextColor(Color.BLACK);
        numberWon.setTextColor(Color.BLACK);
        mostLetter.setTextColor(Color.BLACK);
        leastLetter.setTextColor(Color.BLACK);
        bestTime.setTextColor(Color.BLACK);
    }

    private void loadStatistics(){
        statistics = StatisticsService.getStatistics(
                        getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE),
                        getString(R.string.alphabet).split(""));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setColors();
    }
}

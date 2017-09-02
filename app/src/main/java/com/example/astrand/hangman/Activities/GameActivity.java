package com.example.astrand.hangman.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.api.attributes.ViewGroupPosition;
import com.example.astrand.hangman.Gamemodel.Hangman;
import com.example.astrand.hangman.Gamemodel.Word;
import com.example.astrand.hangman.R;
import com.example.astrand.hangman.Services.ButtonCreator;
import com.example.astrand.hangman.Services.RandomWordService;
import com.example.astrand.hangman.Services.StatisticsService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    TextView letterView;
    Hangman game;
    ImageView imageView;
    GridLayout letterLayout;
    HashMap<String,BootstrapButton> letterButtons;
    HashMap<String,Integer> statistics;

    private char[] alphabet;

    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.game_activity);
        alphabet = getResources().getString(R.string.alphabet).toCharArray();

        String randomWord = RandomWordService.getRandomWord(getResources());

        configureInstances();

        game = new Hangman(randomWord,alphabet);
        statistics = new HashMap<>();
        letterView.setText(formatGuessedString(game.getCurrentGuess()));
        setCharButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //ArrayList<String> savedButtons = new ArrayList<>();
        /*for (HashMap.Entry<String,BootstrapButton> entry : letterButtons.entrySet()){
            savedButtons.add(entry.getKey() + entry.getValue());
        }
        outState.putStringArrayList("savedButtons",savedButtons);*/

        outState.putSerializable("buttons",letterButtons);
        outState.putSerializable("hangman",game);
        outState.putSerializable("wordHelper",game.getWordHelper());
        letterButtons.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        game = (Hangman)savedInstanceState.getSerializable("hangman");
        letterButtons = (HashMap<String,BootstrapButton>)savedInstanceState.getSerializable("buttons");
        game.setWordHelper((Word)savedInstanceState.getSerializable("wordHelper"));

        formatGuessedString(game.getCurrentGuess());
        configureInstances();

        for (BootstrapButton button : letterButtons.values()){
            addToView(letterLayout,button);
        }
    }

    private void configureInstances(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        letterView = (TextView)findViewById(R.id.letterView);
        imageView = (ImageView)findViewById(R.id.hangmanImage);
        letterLayout = (GridLayout)findViewById(R.id.letterLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void correctGuess(){
        letterView.setText(formatGuessedString(game.getCurrentGuess()));
    }

    private void falseGuess(){
        imageView.setImageResource(getImageResource());
        if (!game.isGameValid())
            showAlert("You failed", "The word was: \"" + game.getWord() + '"', "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
    }

    private int getImageResource(){
        int falseTries = game.getNumberOfFalseTries();

        switch (falseTries){
            case 0:
                return R.drawable.hang01;
            case 1:
                return R.drawable.hang03;
            case 2:
                return R.drawable.hang04;
            case 3:
                return R.drawable.hang05;
            case 4:
                return R.drawable.hang06;
            case 5:
                return R.drawable.hang07;
            case 6:
                return R.drawable.hang08;
        }
        throw new IllegalStateException("This method should not have been called. False tries: " + falseTries);
    }

    private String formatGuessedString(String guessedString){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < guessedString.length()-1; i++){
            sb.append(guessedString.charAt(i)).append(' ');
        }

        sb.append(guessedString.charAt(guessedString.length() - 1));

        return sb.toString();
    }



    private void showAlert(String title,String message,String buttonText, DialogInterface.OnClickListener onClickListener){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL,buttonText,onClickListener);
        alert.show();
    }

    private void showYesNoDialog(String title,String message,String positiveText,String negativeText, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText,onClickListener)
                .setNegativeButton(negativeText,onClickListener)
                .create()
                .show();
    }

    private void gameWon(){
        showAlert("Game Won!", "Congratulations, you guessed the word \"" + game.getWord() + '"', "WOHOO!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        disableButtons();
    }

    private void disableButtons() {
        for (BootstrapButton button : letterButtons.values()) button.setEnabled(false);
    }

    /*private void endActivity(String key, String message){
        Intent intent = new Intent();
        intent.putExtra(key,message);
        setResultAndFinish(intent);
    }

    private void setResultAndFinish(Intent intent){
        setResult(RESULT_OK,intent);
        finish();

    }*/

    private void setCharButtons() {
        letterButtons = ButtonCreator.createButtonGrid(getApplicationContext(), alphabet,letterLayout);
        for (BootstrapButton button : letterButtons.values()){
            addToView(letterLayout,button);
            setLetterButtonAction(button);
        }
    }

    private void addToView(ViewGroup viewGroup, View view) {
        viewGroup.addView(view);
    }

    public void setLetterButtonAction(final BootstrapButton button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLetter(button.getText().toString().charAt(0));
                button.setEnabled(false);
                addValueToStatistics(button.getText().toString());
            }
        });
    }

    private void checkLetter(char letter){
        boolean checkLetter = game.guessLetter(letter);

        if (checkLetter){
            correctGuess();
        }else if (game.isGameValid()){
            falseGuess();
        }

        if (game.gameFinished()) wrapUpGame();
    }

    private void wrapUpGame(){
        for (BootstrapButton button : letterButtons.values()){
            button.setEnabled(false);
        }

        storeStatistics();
        String newGameText = game.hasWon() ? getString(R.string.new_game_text_win1) + game.getWord() + getString(R.string.new_game_text_win2) : getString(R.string.new_game_text);
        showYesNoDialog(getString(R.string.new_game), newGameText, getString(R.string.yes), getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        newGameRequested();
                        break;
                }
            }
        });
    }

    private void addValueToStatistics(String letter){
        statistics.put(letter,1);
    }

    private void storeStatistics(){
        String[] arr = getString(R.string.alphabet).split("");

        StatisticsService.updateAlphabetStatistics(statistics,getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE),arr);

        if (game.hasWon()) StatisticsService.gameWon(getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE));
        else StatisticsService.gameLost(getSharedPreferences(getString(R.string.statistics_file_key), Context.MODE_PRIVATE));
    }

    private void newGameRequested(){
        recreate();
    }

    private void restoreButtons(Map<String, Boolean> buttonsStates){

    }
}

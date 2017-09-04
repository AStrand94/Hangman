package com.example.astrand.hangman.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.astrand.hangman.Gamemodel.Hangman;
import com.example.astrand.hangman.Gamemodel.Word;
import com.example.astrand.hangman.R;
import com.example.astrand.hangman.Services.ButtonCreator;
import com.example.astrand.hangman.Services.RandomWordService;
import com.example.astrand.hangman.Services.StatisticsService;

import java.util.Collection;
import java.util.HashMap;

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
        configureInstances();

        if (instance != null) return;

        String randomWord = RandomWordService.getRandomWord(getResources());
        game = new Hangman(randomWord,alphabet);
        statistics = new HashMap<>();
        letterView.setText(formatGuessedString(game.getCurrentGuess()));
        setCharButtons();
        //checkForOrientationChanges();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("buttons",letterButtons);
        outState.putSerializable("hangman",game);
        outState.putSerializable("wordHelper",game.getWordHelper());
        outState.putSerializable("statistics",statistics);
        removeFromView(letterLayout,letterButtons.values()); // to be able to put in new view in onRestoreInstanceState
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        game = (Hangman)savedInstanceState.getSerializable("hangman");
        letterButtons = (HashMap<String,BootstrapButton>)savedInstanceState.getSerializable("buttons");
        game.setWordHelper((Word)savedInstanceState.getSerializable("wordHelper"));
        statistics = (HashMap<String, Integer>)savedInstanceState.getSerializable("statistics");

        letterView.setText(formatGuessedString(game.getCurrentGuess()));
        configureInstances();
        imageView.setImageResource(getImageResource());


        for (BootstrapButton button : letterButtons.values()){
            addToView(letterLayout,button);
        }

        setLetterButtonAction(letterButtons.values());
        //checkForOrientationChanges();
    }

    private void configureInstances(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        letterView = (TextView)findViewById(R.id.letterView);
        imageView = (ImageView)findViewById(R.id.hangmanImage);
        letterLayout = (GridLayout)findViewById(R.id.letterLayout);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        //return 0;
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

    private void setCharButtons() {
        letterButtons = ButtonCreator.createButtonGrid(getApplicationContext(), alphabet,letterLayout);
        configureButtons();
    }

    private void configureButtons(){
        for (BootstrapButton button : letterButtons.values()){
            addToView(letterLayout,button);
            setLetterButtonAction(button);
        }
    }

    private void addToView(ViewGroup viewGroup, View view) {
        viewGroup.addView(view);
    }

    private void setLetterButtonAction(Collection<BootstrapButton> bootstrapButtons){
        for (BootstrapButton button : bootstrapButtons) setLetterButtonAction(button);
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
        String newGameText = game.hasWon() ? getString(R.string.new_game_text_win1) + game.getWord() + getString(R.string.new_game_text_win2) :
                getString(R.string.new_game_text_loss1) + game.getWord() + getString(R.string.new_game_text_loss2);
        showYesNoDialog(getString(R.string.new_game), newGameText, getString(R.string.yes), getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        newGame();
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

    private void removeFromView(ViewGroup viewGroup, Collection< ? extends View> views){
        for (View view : views) viewGroup.removeView(view);
    }

    private void newGame(){
        storeStatistics();
        String randomWord = RandomWordService.getRandomWord(getResources());
        game = new Hangman(randomWord,alphabet);
        statistics = new HashMap<>();
        letterView.setText(formatGuessedString(game.getCurrentGuess()));
        if(letterButtons != null && !letterButtons.isEmpty()) removeFromView(letterLayout,letterButtons.values());
        setCharButtons();
        imageView.setImageResource(getImageResource());
        checkForOrientationChanges();
    }

    private void checkForOrientationChanges() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            int size = ButtonCreator.getScreenWidth(getApplicationContext()) - imageView.getWidth();
            ButtonCreator.updateButtonWidth(size,letterLayout.getColumnCount(),letterButtons.values());
        }else{
            ButtonCreator.updateButtonWidth(ButtonCreator.getScreenWidth(getApplicationContext()),letterLayout.getColumnCount(),letterButtons.values());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        checkForOrientationChanges();
    }
}

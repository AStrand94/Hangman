package com.example.astrand.hangman.Gamemodel;


import com.example.astrand.hangman.BuildConfig;

import java.io.Serializable;

/**
 * Created by strand117 on 22.08.2017.
 */

public class Hangman implements Serializable{

    private Word wordHelper;
    private int numberOfFalseTries;
    private static final int MAX_FALSE_TRIES = 7;
    private boolean[] checkedCharacters;
    private char[] alphabet;

    public Hangman(String word,char[] alphabet) {
        if (alphabet == null || alphabet.length < 1 || word == null)
            throw new IllegalStateException("null values are not accepted");

        numberOfFalseTries = 0;
        this.wordHelper = new Word(word.toUpperCase());
        this.alphabet = alphabet;
        checkedCharacters = new boolean[alphabet.length];
    }

    public boolean guessLetter(char letter){

        checkOfLetter(letter);

        if (wordHelper.containsChar(letter)){
            wordHelper.put(letter);
            return true;
        }
        numberOfFalseTries++;
        return false;
    }

    //Return -1 if char is not accepted
    private int getAlphabetIndex(char c){
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) return i;
        }
        throw new IllegalArgumentException("The char '" + c + "' is not accepted in this language!");
    }

    public int getNumberOfFalseTries() {
        return numberOfFalseTries;
    }

    public static int getMaxFalseTries() {
        return MAX_FALSE_TRIES;
    }

    public boolean guessWord(String guess){
        return wordHelper.equals(guess);
    }

    public String getCurrentGuess(){
        return wordHelper.getCurrentGuess();
    }

    public boolean isGameValid(){
        return numberOfFalseTries < MAX_FALSE_TRIES;
    }

    public boolean isChecked(char c) {
        return checkedCharacters[getAlphabetIndex(c)];
    }

    private void checkOfLetter(char c){
        checkedCharacters[getAlphabetIndex(c)] = true;
    }

    public boolean[] getCheckedCharacters(){
        return checkedCharacters;
    }

    public String getWord(){
        return wordHelper.getWord();
    }

    public boolean hasWon(){
        return wordHelper.hasWon();
    }

    public boolean hasLost(){
        return numberOfFalseTries >= MAX_FALSE_TRIES;
    }

    public boolean gameFinished(){
        return hasWon() || hasLost();
    }

    public Word getWordHelper(){
        return wordHelper;
    }

    public void setWordHelper(Word wordHelper) {
        this.wordHelper = wordHelper;
    }
}

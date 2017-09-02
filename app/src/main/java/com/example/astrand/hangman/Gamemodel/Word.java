package com.example.astrand.hangman.Gamemodel;


import java.io.Serializable;

public class Word implements Serializable{

    private String word;
    private char[] guessedLetters;

    public Word(String word) {
        this.word = word;
        init();
    }

    private void init(){
        guessedLetters = new char[word.length()];
        for (int i = 0; i < guessedLetters.length; i++){
            guessedLetters[i] = '_';
        }
    }

    public boolean containsChar(char c) {
        return word.contains(Character.toString(c));
    }

    public boolean equals(String word){
        return !(word == null) && this.word.equals(word);
    }

    public String put(char c){
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == c){
                guessedLetters[i] = c;
            }
        }
        return new String(guessedLetters);
    }

    public String revealWord(){
        guessedLetters = word.toCharArray();
        return word;
    }

    public String getCurrentGuess(){
        return new String(guessedLetters);
    }

    public String getWord(){
        return word;
    }

    public boolean hasWon(){
        for (char c : guessedLetters){
            if (c == '_') return false;
        }
        return true;
    }
}

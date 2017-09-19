package com.example.astrand.hangman.Services;

import android.content.res.Resources;

import com.example.astrand.hangman.R;

import java.util.ArrayList;
import java.util.List;

public final class RandomWordService {

    private static List<String> usedWords = new ArrayList<>();

    public static String getRandomWord(Resources resources){
        String randomWords[] = resources.getStringArray(R.array.word);

        if (usedWords.size() == randomWords.length) usedWords.clear();

        int index = Math.abs((int)System.currentTimeMillis()) % randomWords.length;
        String word = randomWords[index];

        if(!usedWords.contains(word)) usedWords.add(word);
        else return getRandomWord(resources);

        return word;
    }

    public static boolean usedAllWords(Resources resources){
        String randomWords[] = resources.getStringArray(R.array.word);
        return randomWords.length == usedWords.size();
    }
}

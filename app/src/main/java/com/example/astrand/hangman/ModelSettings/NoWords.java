package com.example.astrand.hangman.ModelSettings;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by strand117 on 23.08.2017.
 */

@Deprecated
public final class NoWords{

    private static final List<String> words = new ArrayList<>();
    private static final int MAX_SIZE_OF_WORDS = 25;

    public static void initWords(BufferedReader reader){
        List<String> allWords = new ArrayList<>();
        try {
            String word;
            while ((word = reader.readLine()) != null){
                allWords.add(word);
            }
        }catch (IOException e){
            Log.d("Exception",e.toString());
        }

        shrinkList(allWords,MAX_SIZE_OF_WORDS);
    }

    private static void shrinkList(List<String> allWords,int prefSize){
        for (int i = 0; i < prefSize; i++){
            int index = (int)System.currentTimeMillis() % allWords.size();
            words.add(allWords.get(index));
            allWords.remove(index);
        }
    }

    //Returns a new word
    public static String getNextWord(){

        if (isEmpty()) return null;
        int index = (int)System.currentTimeMillis() % words.size();

        String temp = words.get(index);
        words.remove(index);

        return temp;
    }

    public static boolean isEmpty(){
        return words.isEmpty();
    }


}

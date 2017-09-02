package com.example.astrand.hangman.Services;

import android.content.SharedPreferences;

import com.example.astrand.hangman.DTO_Objects.StatisticsTransfer;

import java.util.HashMap;

/**
 * Created by strand117 on 31.08.2017.
 */

public class StatisticsService {

    //keys
    public static final String MOST_USED = "most_used";
    public static final String LEAST_USED = "least_used";
    public static final String NUMBER_LOST = "numbers_lost";
    public static final String NUMBER_WON = "number_won";

    public static HashMap<String,Integer> getAlphabetStatistics(SharedPreferences smap, String[] alphabet){

        HashMap<String, Integer> map = new HashMap<>();

        for (String s : alphabet){
            if (smap.contains(s)) {
                map.put(s,smap.getInt(s,0));
            }

        }

        return map;

    }

    public static void updateAlphabetStatistics(HashMap<String,Integer> map, SharedPreferences sharedPreferences, String[] alphabet){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (String s : alphabet){
            if (s.isEmpty()) continue;
            int addedValue = map.containsKey(s) ? map.get(s) : 0;
            editor.putInt(s,sharedPreferences.getInt(s,0) + addedValue);
            editor.apply();
        }

    }

    public static void gameLost(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMBER_LOST,sharedPreferences.getInt(NUMBER_LOST,0) + 1);
        editor.apply();
    }

    public static void gameWon(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMBER_WON,sharedPreferences.getInt(NUMBER_WON,0) + 1);
        editor.apply();
    }

    /**
     *
     * @param sharedPreferences getSharedPreferences(String,int) from an activity
     * @param alphabet as String[]
     * @return hasmap with 4 keys: most_used, least_used, numbers_lost, number_won
     */
    public static HashMap<String,String> getStatistics(SharedPreferences sharedPreferences,String[] alphabet){
        HashMap<String,Integer> alphabetStats = getAlphabetStatistics(sharedPreferences,alphabet);

        HashMap<String,String> result = new HashMap<>();
        StatisticsTransfer tempDTO;

        tempDTO = maxLetter(alphabetStats);
        result.put(MOST_USED,nullTextOrString(tempDTO)); //MOST USED LETTER

        tempDTO = minLetter(alphabetStats);
        result.put(LEAST_USED,nullTextOrString(tempDTO)); //LEAST USED LETTER

        tempDTO = getNumberOfLosses(sharedPreferences);
        result.put(NUMBER_LOST,nullTextOrStringValue(tempDTO));

        tempDTO = getnNumberOfWins(sharedPreferences);
        result.put(NUMBER_WON,nullTextOrStringValue(tempDTO));

        return result;
    }

    private static String nullTextOrStringValue(StatisticsTransfer dto){
        String returnValue;

        if (dto == null || dto.key == null || dto.value == null){
            returnValue = "";
        }else{
            returnValue = Integer.toString(dto.value);
        }

        return returnValue;
    }

    private static String nullTextOrString(StatisticsTransfer dto){
        String returnValue;

        if (dto == null || dto.key == null || dto.value == null){
            returnValue = "";
        }else{
            returnValue = dto.key + ',' + dto.value;
        }

        return returnValue;
    }

    private static StatisticsTransfer maxLetter(HashMap<String,Integer> hashMap){

        StatisticsTransfer dto = new StatisticsTransfer();  dto.value = 0;
        for (String s : hashMap.keySet()){
            if (hashMap.get(s) > dto.value){
                dto.key = s; dto.value = hashMap.get(s);
            }
        }

        return dto;
    }

    private static StatisticsTransfer minLetter(HashMap<String,Integer> hashMap){

        StatisticsTransfer dto = new StatisticsTransfer();  dto.value = 0;
        for (String s : hashMap.keySet()){
            if (hashMap.get(s) < dto.value){
                dto.key = s; dto.value = hashMap.get(s);
            }
        }

        return dto;
    }

    private static StatisticsTransfer getNumberOfLosses(SharedPreferences sharedPreferences){
        StatisticsTransfer dto = new StatisticsTransfer();
        dto.key = NUMBER_LOST;
        dto.value = sharedPreferences.getInt(NUMBER_LOST,0);

        return dto;
    }

    private static StatisticsTransfer getnNumberOfWins(SharedPreferences sharedPreferences){
        StatisticsTransfer dto = new StatisticsTransfer();
        dto.key = NUMBER_WON;
        dto.value = sharedPreferences.getInt(NUMBER_WON,0);

        return dto;
    }




}

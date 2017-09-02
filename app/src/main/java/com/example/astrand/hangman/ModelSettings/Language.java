package com.example.astrand.hangman.ModelSettings;

import android.content.res.Resources;

import com.example.astrand.hangman.R;

import java.util.Locale;

public final class Language {

    private static Locale no = new Locale("no","NO");
    private static Locale en = Locale.ENGLISH;

    private static Language NO;
    private static Language EN;

    public static Language[] getDefaultLanguages(Resources resources){
        setDefaultLanguages(resources);
        Language language1, language2;

        //CURRENT
        language1 = resources.getConfiguration().locale.getLanguage().equals(no.getLanguage()) ? NO : EN;
        language2 = language1.equals(NO) ? EN : NO;
        return new Language[]{language1,language2};
    }

    private static void setDefaultLanguages(Resources resources){
        NO = new Language(no,resources.getString(R.string.norwegian));
        EN = new Language(en,resources.getString(R.string.english));
    }

    private Locale locale;
    private String name;

    private Language(Locale locale, String name){
        this.locale = locale;
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return name;
    }
}

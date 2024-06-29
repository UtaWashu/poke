package com.example.pokemon;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonSharedPreferences {
    private static final String PREF_NAME = "PokemonBookmarks";
    private static final String KEY_BOOKMARKS = "bookmarks";

    public static void addBookmark(Context context, Pokemon pokemon) {
        List<Pokemon> bookmarks = getBookmarks(context);
        bookmarks.add(pokemon);
        saveBookmarks(context, bookmarks);
    }

    public static void removeBookmark(Context context, Pokemon pokemon) {
        List<Pokemon> bookmarks = getBookmarks(context);
        bookmarks.remove(pokemon);
        saveBookmarks(context, bookmarks);
    }

    public static List<Pokemon> getBookmarks(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = preferences.getString(KEY_BOOKMARKS, null);
        if (json == null) {
            return new ArrayList<>();
        } else {
            Gson gson = new Gson();
            Pokemon[] bookmarkArray = gson.fromJson(json, Pokemon[].class);
            return new ArrayList<>(Arrays.asList(bookmarkArray));
        }
    }

    private static void saveBookmarks(Context context, List<Pokemon> bookmarks) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks.toArray(new Pokemon[0]));
        editor.putString(KEY_BOOKMARKS, json);
        editor.apply();
    }
}

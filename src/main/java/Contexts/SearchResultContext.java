package Contexts;

import javafx.util.Pair;

import java.util.ArrayList;

public class SearchResultContext{


    static ArrayList<String> keywords = new ArrayList<>();
    public static void initialiseContext(String[] listOfKeys){
        for(String a:listOfKeys){
            keywords.add(a);
        }
    }

    public static ArrayList<String> getPossibilities(String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (String a : keywords) {
            if (a.contains(key)) {
                hints.add(a);
            }
        }
        return hints;
    }
    public static Pair<String, String> getSpellingCheck(String key) {
        return null;
    }
    public static void AddKeyWord(String key) {
        keywords.add(key);
    }

    public static void clearContext() {
        keywords.clear();
    }

    public static void removeKeyWords(String key) {
        keywords.remove(key);

    }
}
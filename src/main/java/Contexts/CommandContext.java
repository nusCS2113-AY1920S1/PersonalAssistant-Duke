package Contexts;

import Commands.COMMAND_KEYS;
import Commands.CommandStructure;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CommandContext {

    static ArrayList<String> keywordsRoot = new ArrayList<>();
    static ArrayList<String> keywordsSubRoot = new ArrayList<>();

    public static void initialiseContext(){

        for( Map.Entry<COMMAND_KEYS, COMMAND_KEYS[]> e: CommandStructure.cmdStructure.entrySet()){
            keywordsRoot.add(e.getKey().toString());
            for(COMMAND_KEYS a: e.getValue()){
                keywordsSubRoot.add(a.toString());
            }
        }

    }

    public static ArrayList<String> getRoot(){
        return keywordsRoot;
    }

    public static ArrayList<String> getPossibilitiesRoot(String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (String a : keywordsRoot) {
            if (a.toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a);
            }
        }
        return hints;
    }

    public static ArrayList<String> getPossibilitiesSubRoot(String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (String a : keywordsSubRoot) {
            if (a.toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a);
            }
        }
        return hints;
    }

    public static Pair<String, String> getSpellingCheck(String key) {
        return null;
    }


}

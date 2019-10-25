package entertainment.pro.logic.Contexts;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class CommandContext {

    static ArrayList<String> keywordsRoot = new ArrayList<>();
    static ArrayList<String> keywordsSubRoot = new ArrayList<>();


    public static void initialiseContext() {

        if (keywordsRoot.size() == 0) {
            for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> e: CommandStructure.cmdStructure.entrySet()) {
                keywordsRoot.add(e.getKey().toString());
                for (COMMANDKEYS a: e.getValue()) {
                    keywordsSubRoot.add(a.toString());
                }

            }
        }

    }

    public static ArrayList<String> getRoot() {
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

    public static ArrayList<String> getPossibilitiesSubRootForRoot(String root) {
        ArrayList<String> hints = new ArrayList<>();

        for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> e: CommandStructure.cmdStructure.entrySet()) {

            if (e.getKey().toString().trim().equals(root.trim())) {
                for (COMMANDKEYS sr: e.getValue()) {
                    hints.add(sr.toString());
                }

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
package entertainment.pro.logic.contexts;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;

import java.util.ArrayList;
import java.util.Map;

/**
 * Represents the context for all Root and Subroot commands.
 * Auto complete makes use of helper functions from this class to perform autocompletion for commands
 *
 */
public class CommandContext {

    static ArrayList<String> keywordsRoot = new ArrayList<>();
    static ArrayList<String> keywordsSubRoot = new ArrayList<>();


    /**
     * Initialises the Context to the all root and subroot commands.
     *
     */
    public static void initialiseContext() {

        if (keywordsRoot.size() == 0) {
            for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> e: CommandStructure.cmdStructure.entrySet()) {
                keywordsRoot.add(e.getKey().toString().toLowerCase());
                for (COMMANDKEYS a: e.getValue()) {
                    keywordsSubRoot.add(a.toString().toLowerCase());
                }

            }
        }

    }

    /**
     * Gets all Root Commands.
     *
     */
    public static ArrayList<String> getRoot() {
        return keywordsRoot;
    }


    /**
     * Given the incomplete keyword, returns all possible root commands.
     * @param key Incomplete root command from user input
     * @return An arraylist of possible root commands that the user might be trying to type
     */
    public static ArrayList<String> getPossibilitiesForRoot(String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (String a : keywordsRoot) {
            if (a.toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a.toLowerCase());
            }
        }
        return (ArrayList<String>) hints.clone();
    }


    /**
     * Given the a certain Root command, the possible subRoot commands are returned.
     * @param root index of the movie to mark as done
     */
    public static ArrayList<String> getPossibilitiesSubRootGivenRoot(String root) {
        ArrayList<String> hints = new ArrayList<>();

        for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> e: CommandStructure.cmdStructure.entrySet()) {

            if (e.getKey().toString().toLowerCase().trim().equals(root.trim().toLowerCase())) {
                for (COMMANDKEYS sr: e.getValue()) {
                    hints.add(sr.toString().toLowerCase());
                }

            }
        }
        return hints;

    }


    /**
     * Given the incomplete keyword, returns all possible subroot commands.
     * @param key Incomplete subroot command from user input
     * @return An arraylist of possible root commands that the user might be trying to type
     */
    public static ArrayList<String> getPossibilitiesSubRoot(String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (String a : keywordsSubRoot) {
            if (a.toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a.toLowerCase());
            }
        }
        return hints;

    }


    /**
     * Given the incomplete keyword, returns all possible subroot commands.
     * @param key Incomplete subroot command from user input
     * @param root The root command
     * @return An arraylist of possible root commands that the user might be trying to type
     */
    public static ArrayList<String> getPossibilitiesSubRoot(String root, String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (COMMANDKEYS a : CommandStructure.cmdStructure.get(COMMANDKEYS.valueOf(root.toUpperCase()))) {
            if (a.toString().toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a.toString().toLowerCase());
            }
        }
        return hints;

    }


}

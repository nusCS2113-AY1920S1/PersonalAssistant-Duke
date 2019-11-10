package entertainment.pro.logic.contexts;

import entertainment.pro.commons.assertions.CommandAssertions;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Contains helper functions for autocompletion.
 */
public class ContextHelper {

    private static final int NO_WORDS = 0;
    private static final int ONE_WORD = 1;
    private static final int TWO_WORDS = 2;
    private static final int MORE_THAN_TWO_WORDS = 3;


    /**
     * test function to gain access to private isRootCommandComplete function for the purpose of testing.
     *
     * @param testRoot testcase for root
     * @return true if the testRoot command is completed
     */
    public static boolean testisRootCommandComplete(String testRoot) {
        return isRootCommandComplete(testRoot);
    }


    /**
     * Checks if root command is complete or incomplete.
     *
     * @param root String root, either in complete or incomplete form
     * @return true if the Root command is completed
     */
    private static boolean isRootCommandComplete(String root) {
        assert (CommandAssertions.assertIsLowerString(root));
        for (COMMANDKEYS c : CommandStructure.AllRoots) {
            if (c.toString().toLowerCase().equals(root)) {
                return true;
            }
        }
        return false;

    }


    /**
     * test function to gain access to private isSubRootCommandComplete function for the purpose of testing.
     *
     * @param testSubRoot testcase for subroot
     * @return true if the testSubRoot command is completed
     */
    public static boolean testisSubRootCommandComplete(String testSubRoot) {
        assert (CommandAssertions.assertIsLowerString(testSubRoot));
        return isSubRootCommandComplete(testSubRoot);
    }

    /**
     * Checks if subroot command is complete or incomplete.
     *
     * @param subRoot String subRoot, either in complete or incomplete form
     * @return true if the subRoot command is completed
     */
    private static boolean isSubRootCommandComplete(String subRoot) {
        assert (CommandAssertions.assertIsLowerString(subRoot));
        for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> e: CommandStructure.cmdStructure.entrySet()) {
            for (COMMANDKEYS a: e.getValue()) {
                if (a.toString().toLowerCase().equals(subRoot)) {

                    return true;
                }
            }

        }
        return false;

    }

    /**
     * Returns the Incomplete words of the user input.
     * If the incomplete word is only the command, then a single word is returned.
     * If payload is incomplete, the entire payload is returned
     *
     * @param command    Incomplete user input
     * @param controller Ui controller
     * @return String of incomplete words
     */
    public static String getLastIncompleteWords(String command, Controller controller) {
        String[] splitCommand = command.split(" ");

        String incompleteCommand = "";
        if (splitCommand.length == 0) {
            return "";
        } else if (splitCommand.length <= 2) {
            int lastIndex = splitCommand.length;
            lastIndex -= 1;
            incompleteCommand = splitCommand[lastIndex];
        } else {

            String processedCommand = String.join(" ",
                    Arrays.copyOfRange(splitCommand, 2, splitCommand.length));
            String[] commandFlagSplit = processedCommand.split("-[a-z]");
            String[] lastinput = commandFlagSplit[commandFlagSplit.length - 1].split(",");
            incompleteCommand = lastinput[lastinput.length - 1];

        }
        return incompleteCommand;
    }

    /**
     * Returns the index of the end of the common substring between between 2 input Strings.
     *
     * @param a first String
     * @param b second String
     * @return index of the end of common substring
     */
    public static int subStringIndex(String a, String b) {

        assert (CommandAssertions.assertIsLowerString(a));
        assert (CommandAssertions.assertIsLowerString(b));
        int counter = 0;
        for (int i = 0;; i++) {
            if (i >= a.length() || i >= b.length()) {
                break;
            }
            if (!(a.charAt(i) == b.charAt(i))) {
                break;
            }
            counter++;
        }
        return counter;
    }


    /**
     * Given all posssible strings that the user can be typing,
     * compute the common substring amongst all thee possibilities.
     * Function returns the difference in string between the
     * incomplete user input and common substring of all ossibilites.
     *
     * @param allPossibilities  Arraylist of all possible Strings the user could be trying to type
     * @param incompleteCommand incomplete String by user
     * @return String to be added to incomplete command by autocomplete
     */
    public static String completeCommand(ArrayList<String> allPossibilities, String incompleteCommand) {
        assert (CommandAssertions.assertIsLowerString(incompleteCommand));
        if (allPossibilities.size() == 0) {
            return "";
        }
        int lengthOfLongestCommonSubstring = allPossibilities.get(0).length();
        for (int i = 0; i < allPossibilities.size() - 1; i++) {
            int commonSubIndex = subStringIndex(allPossibilities.get(i).toLowerCase(),
                    allPossibilities.get(i + 1).toLowerCase());
            if (commonSubIndex < lengthOfLongestCommonSubstring) {
                lengthOfLongestCommonSubstring = commonSubIndex;
            }
        }

        if (incompleteCommand.length() > lengthOfLongestCommonSubstring) {
            return "";
        }

        String completed = allPossibilities.get(0).substring(incompleteCommand.length(),
                lengthOfLongestCommonSubstring);

        return completed.trim();

    }


    /**
     * Function to Filter hints based on incomplete command received.
     * @param hints all hints
     * @param incompleteCmd incomplete command
     * @return possible hints based on incomplete Command.
     */
    private static ArrayList<String> filterHints(ArrayList<String> hints , String incompleteCmd) {
        assert (CommandAssertions.assertIsLowerString(incompleteCmd));
        ArrayList<String> filteredHints =  new ArrayList<>();

        for (String s : hints) {
            if (s.toLowerCase().contains(incompleteCmd.toLowerCase())) {
                filteredHints.add(s);
            }
        }

        return filteredHints;
    }

    /**
     * Gets all hints pertaining to the current user input and specific to the root command and subRoot command.
     *
     * @param root              Root command entered
     * @param subRoot           SubRoot Command entered
     * @param incompleteCommand Incomplete portion of the user input
     * @returns all possible strings
     */
    private static ArrayList<String> commandSpecificHints(String root, String subRoot, String incompleteCommand) {
        assert (CommandAssertions.assertIsLowerString(root));
        assert (CommandAssertions.assertIsLowerString(subRoot));
        assert (CommandAssertions.assertIsLowerString(incompleteCommand));
        switch (root) {
            case ("blacklist"):
                ArrayList<String> hints = Blacklist.getBlackListHints(incompleteCommand);
                if (!subRoot.equals("remove")) {
                    hints.addAll(SearchResultContext.getPossibilities(incompleteCommand));
                }
                return hints;
            case ("watchlist"):
                ArrayList<String> watchlisthints = WatchlistHandler.getWatchListHints(incompleteCommand);
                if (!subRoot.equals("remove")) {
                    watchlisthints.addAll(SearchResultContext.getPossibilities(incompleteCommand));
                }
                return watchlisthints;

            case ("playlist"):
                try {
                    ArrayList<String> playlistNames = new EditProfileJson().load().getPlaylistNames();
                    return filterHints(playlistNames, incompleteCommand);
                } catch (IOException e) {
                    return new ArrayList<String>();
                }
            default:
                return SearchResultContext.getPossibilities(incompleteCommand);
        }
    }


    /**
     * Updates the command input field based on the hints.
     *
     * @param controller
     * @param allPossibilities
     * @param incompleteCommand
     */
    private static void updateCommandInputFieldWithHints(Controller controller
            , ArrayList<String> allPossibilities
            , String incompleteCommand) {

        String update = completeCommand(allPossibilities, incompleteCommand);
        ((MovieHandler) controller).updateTextField(update);
    }


    /**
     * Creates a sublist of size newSize.
     *
     * @param hints
     * @param newSize
     * @return a subset of the hints. Used when the number of hints is far too long.
     */
    private static ArrayList<String> getSubList(ArrayList<String> hints, int newSize) {
        ArrayList<String> sublist = new ArrayList<>();
        for (String s : hints) {
            if (newSize-- < 0) {
                break;
            }
            sublist.add(s);
        }

        sublist.add("...");
        return sublist;
    }


    /**
     * Gets all hints pertaining to the current user input.
     *
     * @param command    the current user input
     * @param controller for the UI.
     * @returns all possible strings
     */
    public static ArrayList<String> getAllHints(String command, Controller controller) {

        assert (CommandAssertions.assertIsLowerString(command));

        command = command.toLowerCase();
        String [] splitCommand = command.split(" ");
        String incompleteCommand = getLastIncompleteWords(command, controller);


        ArrayList<String> allPossibilities = new ArrayList<>();

        if (splitCommand.length == NO_WORDS) {
            return CommandContext.getRoot();
        } else if (splitCommand.length == ONE_WORD && isRootCommandComplete(splitCommand[0])) {
            allPossibilities =  CommandContext.getPossibilitiesSubRootGivenRoot(splitCommand[0]);
            updateCommandInputFieldWithHints(controller, allPossibilities, "");
        } else if (splitCommand.length == ONE_WORD) {
            allPossibilities =  CommandContext.getPossibilitiesForRoot(incompleteCommand);
            updateCommandInputFieldWithHints(controller, allPossibilities, incompleteCommand);
        } else if (splitCommand.length == TWO_WORDS && isSubRootCommandComplete(splitCommand[1])) {
            allPossibilities = commandSpecificHints(
                    splitCommand[0],
                    splitCommand[1],
                    "");

            if (allPossibilities.size() > 10) {
                allPossibilities = getSubList(allPossibilities, 10);
            }
        } else if (splitCommand.length == TWO_WORDS) {
            allPossibilities = CommandContext
                    .getPossibilitiesSubRoot(splitCommand[0], incompleteCommand);
            updateCommandInputFieldWithHints(controller, allPossibilities, incompleteCommand);
        } else {
            allPossibilities = commandSpecificHints(
                    splitCommand[0],
                    splitCommand[1],
                    incompleteCommand);

            updateCommandInputFieldWithHints(controller, allPossibilities, incompleteCommand);
        }

        return allPossibilities;

    }

}


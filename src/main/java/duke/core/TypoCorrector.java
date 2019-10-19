package duke.core;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a command typo corrector for Duke user command.
 * It provides a method TypoCorrector.CommandCorrection which takes in an invalid input command
 * and return a possible matched command.
 *
 * @author HUANG XUAN KUN
 * @version 1.2
 */
public class TypoCorrector {

    //The maximum ratio changes of a text in % that is acceptable
    private static final double MAX_DISTANCE_DIFF_RATIO = 0.5;

    //Sets of "Dictionaries" for the command keyword, categorised by number of keywords contain in a supported commands.
    private static final ArrayList<String> oneKeywordCommand = new ArrayList<String>(
            Arrays.asList("bye"));
    private static final ArrayList<String> twoKeywordsCommands = new ArrayList<String>(
            Arrays.asList("list patients", "list tasks"));
    private static final ArrayList<String> otherCommands = new ArrayList<String>(
            Arrays.asList("update patient", "update task",
                    "delete patient", "delete task", "add task", "add patient",
                    "assign by", "find patient", "find task"));

    /**
     * This method take in an user input command with typo and return a possible matches
     * If the return string is equal to the input command, there is no match.
     *
     * @param command the full userInput command without parsing
     * @return a string of correctedCommand
     */
    public static String commandCorrection(String command) {
        String[] splitCommand = command.split("\\s+");
        int commandSize = splitCommand.length;
        String closestMatch;
        String firstTwoKeywords;
        if (commandSize == 1 || command.length() <= 6) {
            closestMatch = matchStringFromDict(command, oneKeywordCommand);
            if (isSimilar(command, closestMatch)) {
                return closestMatch;
            }
        } else if (commandSize == 2) {
            firstTwoKeywords = command.toLowerCase();
            closestMatch = matchStringFromDict(firstTwoKeywords, twoKeywordsCommands);
            if (isSimilar(firstTwoKeywords, closestMatch)) {
                return closestMatch;
            }
        } else {
            firstTwoKeywords = (splitCommand[0] + " " + splitCommand[1]).toLowerCase();
            closestMatch = matchStringFromDict(firstTwoKeywords, otherCommands);
            if (isSimilar(firstTwoKeywords, closestMatch)) {
                splitCommand[0] = "";
                splitCommand[1] = "";
                return closestMatch + " " + String.join(" ", splitCommand).trim();
            }
        }
        return command;
    }

    /**
     * Get the closest match string from the array targetDict.
     *
     * @param str the arbitrary string
     * @return
     */
    private static String matchStringFromDict(String str, ArrayList<String> targetDict) {
        int minDist = 256;
        String closestMatch = null;
        for (String keyword : targetDict) {
            int currDist = getDistance(keyword, str);
            if (currDist < minDist) {
                if (currDist == 0) {
                    return keyword;
                }
                minDist = currDist;
                closestMatch = keyword;
            }
        }
        return closestMatch;
    }

    /**
     * Get Levenshtein distance between target and an arbitrary string.
     *
     * @param str the arbitrary string
     * @return
     */
    private static Integer getDistance(String str, String target) {
        LevenshteinDistance distance = new LevenshteinDistance();
        return distance.apply(str, target);
    }

    /**
     * Method indicating if a message can be considered similar, based on Levenshtein distance
     * calculation with an allowed variation of 50%.
     *
     * <b>Note:</b> Max tolerated distance is derived from the current scenario's error message
     * The arbitrary MAX_DISTANCE_DIFF_RATIO (50%) means we consider 10% change to be acceptable
     *
     * @param referenceText the reference text
     * @param targetText    the target text for the comparison
     * @return true if the can be considered similar else false
     */
    public static boolean isSimilar(String referenceText, String targetText) {
        int threshold = (int) Math.round(MAX_DISTANCE_DIFF_RATIO * referenceText.length());
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(threshold);
        return levenshteinDistance.apply(referenceText, targetText) != -1;
    }
}

package duke.core;

import org.apache.commons.text.similarity.LevenshteinDistance;

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
    private static final String[] simpleCommands = {"bye", "duke", "help", "list patients", "list tasks"};
    private static final String[] otherCommands = {"update patient", "update task", "delete patient",
        "delete task", "delete assigned task", "add task", "add patient", "find patient", "find task",
        "find assigned task", "assign deadline task", "assign event task"};

    /**
     * This method take in an user input command with typo and return a possible matches
     * If the return string is equal to the input command, there is no match.
     *
     * @param command the full userInput command without parsing
     * @return a string of correctedCommand
     */
    public static String commandCorrection(String command) {
        String[] splitCommand = command.split(":", 2);
        int commandSize = splitCommand.length;
        String closestMatch;
        if (commandSize == 1) {
            // Type A command with only command keywords
            String fullCommand = command.trim().toLowerCase();//Ignore spaces(back and fore)and upper/lower cases
            closestMatch = matchStringFromDict(fullCommand, simpleCommands); //get closest match
            if (isSimilar(fullCommand, closestMatch)) {
                return closestMatch;
            }
        } else if (commandSize == 2){
            // Type B command with command keywords and other info/data
            String keyword = splitCommand[0].trim().toLowerCase(); //Ignore spaces(back and fore)and upper/lower cases
            closestMatch = matchStringFromDict(keyword, otherCommands); //get closest match
            if (isSimilar(keyword, closestMatch)) {
                splitCommand[0] = "";
                return closestMatch + " " + String.join(":", splitCommand).trim();
            }
        }
        return command; // The input command will be return if there is no matched found
    }

    /**
     * Get the closest match string from the array targetDict.
     *
     * @param str the arbitrary string
     * @return the closest matching from the target dict
     */
    private static String matchStringFromDict(String str, String[] targetDict) {
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
     * @return the difference of two strings measured in distance
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

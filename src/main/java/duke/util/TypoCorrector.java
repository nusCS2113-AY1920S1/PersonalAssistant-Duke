package duke.util;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * This is a command typo corrector for Duke user command.
 * It provides a method TypoCorrector.CommandCorrection which takes in an invalid input command
 * and return a possible matched command.
 *
 * @author HUANGXUANKUN
 * @version 1.4
 */
public class TypoCorrector {

    //The maximum ratio changes of a text in % that is acceptable
    private static final double MAX_DISTANCE_DIFF_RATIO = 0.8;

    //Sets of "Dictionaries" for the command keyword, categorised by number of keywords contain in a supported commands.
    private static final String[] SIMPLE_COMMANDS = {"bye", "duke", "help", "list patients", "list tasks"};
    private static final String[] OTHER_COMMANDS = {"update patient", "update task", "delete patient",
        "delete task", "delete assigned task", "add task", "add patient", "find patient", "find task",
        "find assigned tasks", "assign deadline task", "assign period task"};

    private boolean isCorrected = false;
    private String correctedCommand;
    private String commandInput;

    /**
     * It takes in full command by user and process it with auto-correction.
     * If the correctedCommand is not equal to the original command, the command is
     * considered as corrected.
     *
     * @param commandInput the full command entered by user
     */
    public TypoCorrector(String commandInput) {
        this.commandInput = commandInput;
        correctedCommand = commandCorrection(commandInput);
        String inputKeyword = commandInput.split(":", 2)[0].trim();
        String correctedKeyword = correctedCommand.split(":", 2)[0].trim();
        if (!inputKeyword.equals(correctedKeyword)) {
            isCorrected = true;
        }
    }

    /**
     * It returns the correction status of the command.
     * if true, the command can be corrected, user can then call the getCorrectedCommand() to get the corrected version.
     * Otherwise, the command cannot be corrected.
     *
     * @return if input command can be corrected.
     */
    public boolean isCommandCorrected() {
        return isCorrected;
    }

    /**
     * It return the correctedCommand if the command can be corrected.
     * Otherwise, return the original command.
     *
     * @return the full corrected command
     */
    public String getCorrectedCommand() {
        if (isCommandCorrected()) {
            return correctedCommand;
        } else {
            return commandInput;
        }
    }

    /**
     * This method take in an user input command with typo and return a possible matches
     * If the return string is equal to the input command, there is no match.
     *
     * @param command the full userInput command without parsing
     * @return a string of correctedCommand
     */
    private String commandCorrection(String command) {
        String[] splitCommand = command.split(":", 2);
        int commandSize = splitCommand.length;
        String closestMatch;
        if (commandSize == 1) {
            // Type A command with only command keywords
            String fullCommand = command.trim().toLowerCase();//Ignore spaces(back and fore)and upper/lower cases
            closestMatch = matchStringFromDict(fullCommand, SIMPLE_COMMANDS); //get closest match
            if (isSimilar(fullCommand, closestMatch)) {
                return closestMatch;
            }
        } else if (commandSize == 2) {
            // Type B command with command keywords and other info/data
            String keyword = splitCommand[0].trim().toLowerCase(); //Ignore spaces(back and fore)and upper/lower cases
            closestMatch = matchStringFromDict(keyword, OTHER_COMMANDS); //get closest match
            if (isSimilar(keyword, closestMatch)) {
                splitCommand[0] = "";
                return closestMatch + String.join(":", splitCommand).trim();
            }
        }
        return command; // The original command will be return if there is no matched found
    }

    /**
     * Get the closest match string from the array targetDict.
     *
     * @param str the arbitrary string
     * @return the closest matching from the target dict
     */
    private String matchStringFromDict(String str, String[] targetDict) {
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
    private Integer getDistance(String str, String target) {
        LevenshteinDistance distance = new LevenshteinDistance();
        return distance.apply(str, target);
    }

    /**
     * Method indicating if a message can be considered similar, based on Levenshtein distance
     * calculation with an allowed variation of 70%.
     * Max tolerated distance is derived from the current scenario's error message
     * The arbitrary MAX_DISTANCE_DIFF_RATIO (50%) means we consider 10% change to be acceptable
     *
     * @param referenceText the reference text
     * @param targetText    the target text for the comparison
     * @return true if the can be considered similar else false
     */
    public boolean isSimilar(String referenceText, String targetText) {
        int threshold = (int) Math.round(MAX_DISTANCE_DIFF_RATIO * referenceText.length());
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(threshold);
        return levenshteinDistance.apply(referenceText, targetText) != -1;
    }
}

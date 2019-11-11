package ducats.components;

import java.io.Serializable;
import ducats.DucatsLogger;

/**
 * A class that that gets closest word from the given word from the list of commands.
 */
public class WordGetter implements Serializable {

    public WordGetter() {

    }

    /**
     * A function that returns the number of common elements between the two list, i.e. union
     *
     * @param stringToCheck - This is the string to get the closest word.
     */
    //@@author rishi12438
    public String closestWord(String stringToCheck) {

        //dictionary of the commands in Ducats.
        String [] commandList = {"bye", "list", "delete", "deletebar","editbar",
            "find","done", "new","help","view","addbar",
            "overlay","group","overlay_bar_group", "metronome",
            "overlay_group_group", "overlay_bar_song", "ascii",
            "redo", "undo", "open",
            "copy", "insertbar", "swapbar","yes","no","play", "list_group"};

        double maximumVal = 0;
        String commandName = "sasaff";

        Jaccard similarityChecker = new Jaccard();
        for (String temp: commandList) {
            double similarityValue = similarityChecker.similarity(temp,stringToCheck);
            if (maximumVal < similarityValue && similarityValue >= .35) {
                maximumVal = similarityValue;
                commandName = temp;
            }
        }
        DucatsLogger.fine("wordgetter identified " + stringToCheck + "to be " + commandName);
        return commandName;
    }
}

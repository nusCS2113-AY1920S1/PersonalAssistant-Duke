package ducats.components;

import java.io.Serializable;

public class WordGetter implements Serializable {

    public WordGetter() {

    }

    /**
     * A function that returns the number of common elements between the two list, i.e. union
     *
     * @param stringToCheck - This is the string to get the closest word.
     */
    public String closestWord(String stringToCheck) {
        String [] commandList = {"bye", "list", "delete", "deletebar","editbar",
            "find","done", "new","help","view","addbar",
            "overlay","group","overlay_bar_group", "metronome",
            "overlay_group_group", "overlay_bar_song", "ascii",
            "redo", "undo", "open",
            "copy", "insertbar", "swapbar"};
        double maximumVal = 0;
        String commandName = "sasaff";
        Jaccard similarityChecker = new Jaccard();
        for (String temp: commandList) {
            double similarityValue = similarityChecker.similarity(temp,stringToCheck);
            if (maximumVal < similarityValue && similarityValue >= .25) {
                maximumVal = similarityValue;
                commandName = temp;
            }
        }
        return commandName;
    }
}

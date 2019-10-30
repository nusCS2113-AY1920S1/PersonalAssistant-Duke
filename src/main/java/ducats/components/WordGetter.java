package ducats.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import ducats.components.Jaccard;

public class WordGetter implements Serializable {

    public WordGetter() {

    }

    /**
     * A function that returns the number of common elements between the two list, i.e. union
     *
     * @param stringToCheck - This is the string to get the closest word.
     */
    public String closestWord(String stringToCheck) {
        String [] commandList = {"bye", "list", "delete", "deletebar","edit",
                                    "find","done", "new","help","view","addbar",
                                    "overlay","group","overlay_bar_group",
                                    "overlay_group_group","overlay_bar_song","ascii","redo","undo"};
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

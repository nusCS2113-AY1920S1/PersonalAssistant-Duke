package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.exception.DukeUtilException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Functions for command autocompletion and autocorrection.
 */
public class CommandUtils {

    /**
     * For autocorrect, do not consider strings whose lengths differ from the input by more than this value.
     */
    private static final int MAX_LEN_DIFF = 2;
    private static final Map<Character, Coord> keyboardMap =
            Map.ofEntries(Map.entry('q', new Coord(0, 1)), Map.entry('w', new Coord(1, 1)),
                    Map.entry('e', new Coord(2, 1)), Map.entry('r', new Coord(3, 1)),
                    Map.entry('t', new Coord(4, 1)), Map.entry('y', new Coord(5, 1)),
                    Map.entry('u', new Coord(6, 1)), Map.entry('i', new Coord(7, 1)),
                    Map.entry('o', new Coord(8, 1)), Map.entry('p', new Coord(9, 1)),
                    Map.entry('a', new Coord(0, 2)), Map.entry('z', new Coord(0, 3)),
                    Map.entry('s', new Coord(1, 2)), Map.entry('x', new Coord(1, 3)),
                    Map.entry('d', new Coord(2, 2)), Map.entry('c', new Coord(2, 3)),
                    Map.entry('f', new Coord(3, 2)), Map.entry('b', new Coord(4, 3)),
                    Map.entry('m', new Coord(5, 3)), Map.entry('j', new Coord(6, 2)),
                    Map.entry('g', new Coord(4, 2)), Map.entry('h', new Coord(5, 2)),
                    Map.entry('k', new Coord(7, 2)), Map.entry('l', new Coord(8, 2)),
                    Map.entry('v', new Coord(3, 3)), Map.entry('n', new Coord(5, 3)),
                    Map.entry('1', new Coord(0, 0)), Map.entry('2', new Coord(1, 0)),
                    Map.entry('3', new Coord(2, 0)), Map.entry('4', new Coord(3, 0)),
                    Map.entry('5', new Coord(4, 0)), Map.entry('6', new Coord(5, 0)),
                    Map.entry('7', new Coord(6, 0)), Map.entry('8', new Coord(7, 0)),
                    Map.entry('9', new Coord(8, 0)), Map.entry('0', new Coord(9, 0)),
                    Map.entry('[', new Coord(10, 1)), Map.entry(']', new Coord(9, 1)),
                    Map.entry(';', new Coord(9, 2)), Map.entry('\'', new Coord(10, 2)),
                    Map.entry(',', new Coord(8, 3)), Map.entry('.', new Coord(9, 3)),
                    Map.entry('/', new Coord(10, 3)), Map.entry('-', new Coord(10, 0)),
                    Map.entry('+', new Coord(11, 0)));
    private static final Map<Character, Integer> charMap;

    static class Coord {
        private int xcoord;
        private int ycoord;

        Coord(int x, int y) {
            this.xcoord = x;
            this.ycoord = y;
        }
    }

    static {
        HashMap<Character, Integer> tempMap = new HashMap<Character, Integer>();
        int i = 0;
        for (char c : keyboardMap.keySet()) {
            tempMap.put(c, i);
            ++i;
        }
        charMap = Collections.unmodifiableMap(tempMap);
    }

    // I hate Java

    /**
     * Given a switch name provided by the user, finds the switch it is referring to, or the closest match,
     * allowing the user to disambiguate.
     *
     * @param word    The name provided by the user, which may not match any switch.
     * @param command The command that the word is supposed to be a switch for.
     * @return The full name of the switch of the command that matches the word.
     */
    public static String findSwitch(String word, ArgCommand command) {
        String corrStr = command.getSwitchAliases().get(word);
        if (corrStr != null) {
            return corrStr;
        }
        int wordLen = word.length();

        HashMap<String, String> suggestions = new HashMap<String, String>();
        int minDist = 0;
        for (Map.Entry<String, String> entry : command.getSwitchAliases().entrySet()) {
            String alias = entry.getKey();
            if (Math.abs(alias.length() - wordLen) > MAX_LEN_DIFF) {
                int dist = stringDistance(alias, word, minDist);
                if (dist < minDist) {
                    suggestions.clear();
                    suggestions.put(entry.getValue(), entry.getKey());
                    minDist = dist;
                } else if (dist == minDist) {
                    suggestions.put(entry.getValue(), entry.getKey());
                } //ignore if dist > minDist
            }
        }

        return disambiguateSwitches(word, suggestions, command.getSwitchMap().keySet());
    }

    /**
     * Identifies a switch which is not matched exactly. Returns the closest match if it exists, and provides the user
     * with a window offering the choice between the closest possible options and a list of valid options otherwise,
     * including the choice to enter his own input.
     *
     * @param word        The user-provided switch name.
     * @param suggestions A map of the closest matching switch aliases to the switch names they represent.
     * @param valid       The set of valid switches for this command.
     * @return The string that the user has selected.
     */
    public static String disambiguateSwitches(String word, HashMap<String, String> suggestions, Set<String> valid) {
        StringBuilder builder = new StringBuilder("I didn't understand '").append(word)
                .append("'. Here are the closest matches:").append(System.lineSeparator());
        int i = 1;
        for (Map.Entry<String, String> entry : suggestions.entrySet()) {
            builder.append("  ").append(i).append(". '").append(entry.getKey())
                    .append("' - for switch '").append(entry.getValue()).append("'")
                    .append(System.lineSeparator());
            ++i;
        }

        builder.append(System.lineSeparator()).append("Enter the number corresponding to a suggestion to")
                .append("select it, or enter the full form of one of the valid options listed below:")
                .append(System.lineSeparator()).append(System.lineSeparator()).append("  ");
        for (String validStr : valid) {
            builder.append(validStr).append(", ");
        }
        //delete trailing comma and add newline
        builder.delete(builder.length() - 2, builder.length()).append(System.lineSeparator());

        String selectedStr = ""; //TODO: get from user
        return selectedStr;
    }

    /**
     * Algorithm to compute a hybrid version of the Damerau-Levenshtein distance that takes into account distance
     * between keys on a standard QWERTY keyboard when computing substitution cost. Search is pruned if it exceeds
     * the minimum distance found so far, returning the distance computed up to that point.
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @param minDist The minimum string distance found so far, or 0 if no distance computations have been
     *                performed yet.
     * @return The hybrid Damerau-Levenshtein distance between str1 and str2.
     */
    private static int stringDistance(String str1, String str2, int minDist) {
        // TODO: use logging library, collected data can help optimise distance weights
        //setup values
        int len1 = str1.length();
        int len2 = str2.length();
        int[] d1 = new int[keyboardMap.size()]; //values initialised to 0
        int[][] d = new int[len1 + 2][len2 + 2];
        int maxdist = len1 + len2;

        //populate boundary values
        d[0][0] = maxdist;
        for (int i = 1; i <= len1 + 1; ++i) {
            d[i][0] = maxdist;
            d[i][1] = i;
        }
        for (int j = 1; j <= len2 + 1; ++j) {
            d[0][j] = maxdist;
            d[1][j] = j;
        }

        for (int i = 2; i <= len1 + 1; ++i) {
            int d2 = 2;
            char c1 = str1.charAt(i - 2);
            for (int j = 2; j <= len2 + 1; ++j) {
                int k = d1[charMap.get(str2.charAt(j - 2))];
                int l = d2;
                int subCostInc;
                char c2 = str2.charAt(j - 2);
                if (c1 == c2) {
                    subCostInc = 0;
                    d2 = j;
                } else {
                    // TODO: implement scaling factor?
                    subCostInc = Math.abs(keyboardMap.get(c1).xcoord - keyboardMap.get(c2).xcoord)
                            + Math.abs(keyboardMap.get(c1).ycoord - keyboardMap.get(c2).ycoord);
                }

                //calculate cost of each edit and find minimum
                int subCost = d[i - 2][j - 2] + subCostInc;
                int insCost = d[i - 1][j - 2] + 1;
                int delCost = d[i - 2][j - 1] + 1;
                int transCost = d[k - 2][l - 2] + (i - k - 3) + 1 + (j - l - 3);
                List<Integer> costs = Arrays.asList(subCost, insCost, delCost, transCost);
                // TODO: verify that this pruning works; won't work if the distance decreases as it converges
                int min = Collections.min(costs);
                assert (min != 0);
                if (min > minDist && minDist != 0) {
                    return min;
                } else {
                    d[i - 1][j - 1] = min;
                }
            }
            d1[charMap.get(str1.charAt(i - 2))] = i;
        }
        return d[len1 + 1][len2 + 1];
    }

    // TODO might move these to respective DukeObjects

    /**
     * Find a {@code Patient} with the supplied identifier. Only 1 of either bed number or displayed index
     * should be used to identify said patient.
     *
     * @param core  DukeCore object.
     * @param bedNo Bed number of patient.
     * @param nameOrIdx Displayed index of patient (Home context), or name of Patient.
     * @return Patient object
     * @throws DukeException If 1 of the following 3 conditions applies.
     *                       1. No identifier is provided.
     *                       2. 2 identifiers are provided.
     *                       3. 1 unique identifier is provided but said patient does not exist.
     */
    public static Patient findFromHome(DukeCore core, String bedNo, String nameOrIdx) throws DukeException {
        if (nameOrIdx == null && bedNo == null) {
            throw new DukeUtilException("Please provide a way to identify the patient! Patients can be searched for"
                    + "by name/index or by bed.");
        }
        if (nameOrIdx != null && bedNo != null) {
            throw new DukeUtilException("I don't know if you want me to find the patient ");
        }

        if (bedNo != null) {
            return core.patientList.getPatientByBed(bedNo);
        }
        int index = idxFromString(nameOrIdx);
        if (index != -1) {
            // TODO: Law of demeter
            List<DukeObject> patientList = core.ui.getIndexedList("patient");
            int count = patientList.size();
            if (index > count) {
                throw new DukeException("I have only " + ((count == 1) ? ("1 patient") : (count + "patients")) + " in "
                        + "my list!");
            }
            return (Patient) patientList.get(index - 1);
        } else {
            // TODO proper searching
            return core.patientList.findPatientsByName(nameOrIdx).get(0);
        }
    }

    /**
     * Find a {@code DukeObject} with the supplied identifier. Only 1 of either name or displayed index should be used
     * to identify said DukeObject.
     *
     * @param core    DukeCore object.
     * @param patient Patient object.
     * @param type    Type of DukeObject.
     * @param nameOrIdx   Name of DukeObject or displayed index.
     * @return DukeObject object,
     * @throws DukeException If 1 of the following 3 conditions applies.
     *                       1. No identifier is provided.
     *                       2. 2 identifiers are provided.
     *                       3. 1 unique identifier is provided but said DukeObject does not exist.
     */
    public static DukeObject findFromPatient(DukeCore core, Patient patient, String type, String nameOrIdx)
            throws DukeException {
        int index = idxFromString(nameOrIdx);
        if (index != -1) {
            try {
                return core.ui.getIndexedList(type).get(index - 1);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("No such " + type + " exists in the list!");
            }
        } else {
            // TODO proper search
            ArrayList<DukeObject> resultList = new ArrayList<DukeObject>();
            if (type == null) {
                resultList.addAll(patient.findImpressionsByName(nameOrIdx));
                resultList.addAll(patient.findCriticalsByName(nameOrIdx));
                resultList.addAll(patient.findFollowUpsByName(nameOrIdx));
            }

            if ("impression".equals(type)) {
                resultList.addAll(patient.findImpressionsByName(nameOrIdx));
            } else if ("critical".equals(type)) {
                resultList.addAll(patient.findCriticalsByName(nameOrIdx));
            } else {
                resultList.addAll(patient.findFollowUpsByName(nameOrIdx));
            }

            if (resultList.size() == 0) {
                throw new DukeUtilException("Can't find anything with those search parameters!");
            }
            return resultList.get(0);
        }
    }

    /**
     * Extracts an index from a string argument.
     *
     * @param inputStr The string to parse, generally a command argument.
     * @return The index represented by the string, or -1 if the string does not represent an index.
     */
    public static int idxFromString(String inputStr) {
        try {
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException excp) {
            return -1;
        }
    }

    /**
     * Checks two possibly-null strings for equality in a null-safe way.
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @return True if the strings are both null, or both equal. False otherwise.
     */
    public static boolean equalsIfNotBothNull(String str1, String str2) {
        if (str1 != null) {
            return str1.equals(str2);
        } else {
            return str2 == null;
        }
    }
}
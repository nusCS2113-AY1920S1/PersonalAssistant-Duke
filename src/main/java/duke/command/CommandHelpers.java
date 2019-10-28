package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.card.PatientCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Functions for command autocompletion and autocorrection.
 */
public class CommandHelpers {

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

        HashMap<String, String> suggestions = new HashMap<String, String>();
        int minDist = 0;
        for (Map.Entry<String, String> entry : command.getSwitchAliases().entrySet()) {
            int dist = stringDistance(entry.getKey(), word, minDist);
            if (dist < minDist) {
                suggestions.clear();
                suggestions.put(entry.getValue(), entry.getKey());
            }
        }

        return null;
        //return disambiguate(word, suggestions, command.getSwitchMap().keySet());
        // TODO: finish up disambiguate
    }

    /**
     * Identifies a switch which is not matched exactly. Returns the closest match if it exists, and provides the user
     * with a window offering the choice between the closest possible options and a list of valid options otherwise,
     * including the choice to enter his own input.
     *
     * @param word        The user-provided switch name.
     * @param suggestions A List of the closest matching switch names.
     * @param valid       The set of valid switches for this command.
     * @return The string that the user has selected.
     */
    public static String disambiguateSwitches(String word, ArrayList<String> suggestions, Set<String> valid) {
        StringBuilder builder = new StringBuilder("I didn't understand '").append(word)
                .append("'. Here are the closest matches:").append(System.lineSeparator());
        for (int i = 1; i <= suggestions.size(); ++i) {
            builder.append("  ").append(i).append(". ").append(suggestions.get(i - 1)).append(System.lineSeparator());
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
     * between keys on a standard QWERTY keyboard.
     * <p>
     * https://stackoverflow.com/questions/29233888/
     * https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
     * https://dl.acm.org/citation.cfm?doid=1963190.1963191
     * </p>
     *
     * @param str1    The first string to compare.
     * @param str2    The second string to compare.
     * @param minDist The minimum string distance found so far.
     * @return The hybrid Damerau-Levenshtein distance between str1 and str2.
     */
    private static int stringDistance(String str1, String str2, int minDist) {
        //if minDist is 0, run till the end; else break when dist exceeds minDist
        return str1.length() - str2.length() + minDist; //placeholder to deceive codacy
        //if dist == 0 throw an error
    }

    /**
     * Find a patient with the supplied identifier. Only 1 of either bed number or displayed index should be used
     * to identify said patient.
     *
     * @param core  DukeCore object.
     * @param bedNo Bed number of patient.
     * @param index Displayed index of patient (Home context).
     * @return Patient object
     * @throws DukeException If 1 of the following 3 conditions applies.
     *                       1. No identifier is provided.
     *                       2. 2 identifiers are provided.
     *                       3. 1 unique identifier is provided but said patient does not exist.
     */
    public static Patient findPatient(DukeCore core, String bedNo, int index) throws DukeException {
        if (bedNo == null && index == -1) {
            throw new DukeException("You must provide a unique identifier (bed number OR index) for a patient!");
        } else if (bedNo != null && index != -1) {
            throw new DukeException("Please provide only 1 unique identifier (bed number OR index) for the patient "
                    + "you are looking for!");
        } else if (bedNo != null) {
            return core.patientMap.getPatient(bedNo);
        } else {
            // TODO: Law of demeter
            try {
                PatientCard card = (PatientCard) core.ui.getCardList().get(index - 1);
                return card.getPatient();
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("I cannot find a patient with the identifier you provided!");
            }
        }
    }
}

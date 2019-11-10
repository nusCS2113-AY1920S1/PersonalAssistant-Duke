package duke.command.patient;

import duke.DukeCore;
import duke.command.CommandUtils;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeUtilException;

import java.util.ArrayList;
import java.util.List;

public class PatientUtils {

    /**
     * Gets the Patient that is the parent of an Impression.
     * @param core The DukeCore that the app is running on.
     * @return The current Patient.
     * @throws DukeException If called when the current context is the Patient context, should not happen.
     */
    public static Patient getPatient(DukeCore core) throws DukeException {
        try {
            return (Patient) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("The current context is not a patient!");
        }
    }

    // TODO clarify if we are doing get by idx from the UI or from the data

    /**
     * Find a {@code DukeObject} with the supplied identifier. Only 1 of either name or displayed index should be used
     * to identify said DukeObject.
     *
     * @param patient The patient whose data we want to search.
     * @param type    Type of DukeObject.
     * @param nameOrIdx   Name of DukeObject or displayed index.
     * @return DukeObject object,
     * @throws DukeException If 1 of the following 3 conditions applies.
     *                       1. No identifier is provided.
     *                       2. 2 identifiers are provided.
     *                       3. 1 unique identifier is provided but said DukeObject does not exist.
     */
    public static DukeObject findFromPatient(Patient patient, String type, String nameOrIdx)
            throws DukeException {
        int idx = CommandUtils.idxFromString(nameOrIdx);
        if (idx != -1) {
            if (type == null) {
                throw new DukeUtilException("I don't know which list you want me to get from!");
            }
            try {
                List<? extends DukeObject> objList;
                switch (type) {
                case "critical":
                    objList = patient.getCriticalList();
                    break;
                case "followup":
                    objList = patient.getFollowUpList();
                    break;
                case "impression":
                    objList = patient.getImpressionList();
                    break;
                default:
                    throw new DukeException("'" + type + "' is not a valid type for searching from a patient!");
                }
                return objList.get(idx);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("I don't have a " + type + " with that index!");
            }
        } else {
            return null;
        }
    }

    /**
     * This function builds a SearchResult based on a searchTerm from the patient context.
     * Seach dependent on the type specified to filter the search.
     * @param patient The patient object to search in
     * @param type To determine what kind of objects in the patient to search about
     * @param searchTerm the substring that the objects should contain
     * @return the results in a SearchResult object
     * @throws DukeUtilException if no search results exist for the specified term
     */
    public static SearchResults searchFromPatient(Patient patient, String type, String searchTerm)
            throws DukeUtilException {
        SearchResults results = new SearchResults(searchTerm, new ArrayList<DukeObject>(), patient);
        if (type == null) {
            results.addAll(patient.findImpressionsByName(searchTerm));
            results.addAll(patient.findCriticalsByName(searchTerm));
            results.addAll(patient.findFollowUpsByName(searchTerm));
        }

        if ("impression".equals(type)) {
            results.addAll(patient.findImpressionsByName(searchTerm));
        } else if ("critical".equals(type)) {
            results.addAll(patient.findCriticalsByName(searchTerm));
        } else {
            results.addAll(patient.findFollowUpsByName(searchTerm));
        }

        if (results.getCount() == 0) {
            throw new DukeUtilException("Can't find anything with those search parameters!");
        }
        return results;
    }
}

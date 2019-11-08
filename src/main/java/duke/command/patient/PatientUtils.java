package duke.command.patient;

import duke.DukeCore;
import duke.command.CommandUtils;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeUtilException;

import java.util.ArrayList;

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
     * @param core    DukeCore object.
     * @param type    Type of DukeObject.
     * @param nameOrIdx   Name of DukeObject or displayed index.
     * @return DukeObject object,
     * @throws DukeException If 1 of the following 3 conditions applies.
     *                       1. No identifier is provided.
     *                       2. 2 identifiers are provided.
     *                       3. 1 unique identifier is provided but said DukeObject does not exist.
     */
    public static DukeObject findFromPatient(DukeCore core, String type, String nameOrIdx)
            throws DukeException {
        int index = CommandUtils.idxFromString(nameOrIdx);
        if (index != -1) {
            if (type == null) {
                throw new DukeUtilException("I don't know which list you want me to get from!");
            }
            try {
                return core.ui.getIndexedList(type).get(index - 1);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("I don't have a " + type + " of that index!");
            }
        } else {
            return null;
        }
    }

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

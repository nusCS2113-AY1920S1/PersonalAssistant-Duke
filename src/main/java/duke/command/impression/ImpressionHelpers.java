package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeUtilException;

import java.util.ArrayList;
import java.util.List;

// TODO: refactor into helper class
public class ImpressionHelpers {

    public static Impression getImpression(DukeCore core) throws DukeException {
        try {
            return (Impression) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("The current context is not an Impression!");
        }
    }

    public static Patient getPatient(Impression impression) throws DukeException {
        try {
            return (Patient) impression.getParent();
        } catch (ClassCastException excp) {
            throw new DukeException("This Impression is not attached to a Patient!");
        }
    }

    // TODO: proper search
    public static Evidence findEvidence(Impression impression, String searchStr) throws DukeException {
        int idx = idxFromString(searchStr);
        if (idx < 0) {
            List<Evidence> searchResults = impression.findEvidencesByName(searchStr);
            if (searchResults.size() == 0) {
                throw new DukeException("I can't find any treatment with that in its name!");
            }
            return searchResults.get(0); // I hate this
        } else {
            // get by idx
            return null;
        }
    }

    public static Treatment findTreatment(Impression impression, String searchStr) throws DukeException {
        int idx = idxFromString(searchStr);
        if (idx < 0) {
            List<Treatment> searchResults = impression.findTreatmentsByName(searchStr);
            if (searchResults.size() == 0) {
                throw new DukeException("I can't find any treatment with that in its name!");
            }
            return searchResults.get(0);
        } else {
            // get by idx
            return null;
        }
    }

    public static DukeData findData(Impression impression, String searchStr) throws DukeException {
        int idx = idxFromString(searchStr);
        if (idx < 0) {
            List<DukeData> searchResults = impression.findByName(searchStr);
            if (searchResults.size() == 0) {
                throw new DukeException("I can't find any treatment with that in its name!");
            }
            return searchResults.get(0);
        } else {
            // get by idx
            return null;
        }
    }

    public static int idxFromString(String inputStr) {
        try {
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException excp) {
            return -1;
        }
    }

    public static DukeData findVarTypeData(String arg, String evArg, String treatArg, Impression impression)
            throws DukeException {
        DukeData data;
        DukeException dataNotFound;
        List<DukeData> findList;

        // TODO handle idx
        if (arg != null && evArg == null && treatArg == null) {
            findList = new ArrayList<DukeData>(impression.findByName(arg));
            dataNotFound = new DukeException("Can't find any data item with that name!");
        } else if (arg == null && evArg != null && treatArg == null) {
            findList = new ArrayList<DukeData>(impression.findEvidencesByName(evArg));
            dataNotFound = new DukeException("Can't find any evidences with that name!");
        } else if (arg == null && evArg == null && treatArg != null) {
            findList = new ArrayList<DukeData>(impression.findTreatmentsByName(treatArg));
            dataNotFound = new DukeException("Can't find any treatments with that name!");
        } else {
            throw new DukeUtilException("I don't know what you want me to look for!");
        }

        // TODO proper search
        if (findList.size() != 0) {
            data = findList.get(0);
        } else {
            throw dataNotFound;
        }

        return data;
    }

    public static int checkPriority(int priority) throws DukeUtilException {
        if (priority < 0 || priority > 4) {
            throw new DukeUtilException("Priority must be between 0 and 4!");
        } else {
            return priority;
        }
    }

    /**
     * Checks if a status is a string or an integer, and returns the appropriate integer if it is a string.
     * @param status The String supplied as an argument to the status switch.
     * @param statusList The status descriptions that the numeric value of the status represent. The numeric value of
     *                  the status is the index of the corresponding description in the array.
     * @return The Integer that the string represents, or 0 if it is null.
     * @throws NumberFormatException If the string is not a valid representation of an integer.
     */
    public static int processStatus(String status, List<String> statusList)
            throws DukeUtilException {
        assert (status != null);
        if ("".equals(status)) {
            return 0;
        } else {
            try {
                int convertedStatus = Integer.parseInt(status);
                if (convertedStatus < 0 || convertedStatus >= statusList.size()) {
                    throw new DukeUtilException(status + "is not a valid numeric value for the status!");
                }
                return convertedStatus;
            } catch (NumberFormatException excp) { // not numeric
                // TODO: parse with autocorrect?
                for (int i = 0; i < statusList.size(); ++i) {
                    if (statusList.get(i).equalsIgnoreCase(status)) {
                        return i;
                    }
                }
                throw new DukeUtilException("'" + status + "' is not a valid status name!");
            }
        }
    }
}

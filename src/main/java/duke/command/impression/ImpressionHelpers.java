package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

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

    public static DukeData findVarTypeData(String arg, String evArg, String treatArg, Impression impression,
                                           ArgCommand command)
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
            throw new DukeHelpException("I don't know what you want me to look for!", command);
        }

        // TODO proper search
        if (findList.size() != 0) {
            data = findList.get(0);
        } else {
            throw dataNotFound;
        }

        return data;
    }
}

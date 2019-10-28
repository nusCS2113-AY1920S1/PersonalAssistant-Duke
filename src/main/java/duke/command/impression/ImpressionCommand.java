package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

import java.util.List;

public abstract class ImpressionCommand extends ArgCommand {

    protected Impression getImpression(DukeCore core) throws DukeException {
        try {
            return (Impression) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("The current context is not an Impression!");
        }
    }

    // TODO: proper search
    protected Evidence findEvidence(Impression impression, String searchStr) throws DukeException {
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

    protected Treatment findTreatment(Impression impression, String searchStr) throws DukeException {
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

    protected DukeData findData(Impression impression, String searchStr) throws DukeException {
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

    protected int idxFromString(String inputStr) {
        try {
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException excp) {
            return -1;
        }
    }
}

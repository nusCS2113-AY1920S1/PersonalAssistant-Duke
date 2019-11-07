package duke.data;

import duke.exception.DukeException;
import duke.ui.card.UiCard;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends DukeObject {

    private List<Pair<DukeObject, Class<? extends DukeObject>>> searchList;

    /**
     * TODO fix documentation
     * Abstraction of the evidence or treatment data of a patient.
     * A DukeData object corresponds to the evidence or treatment a doctor has,
     * the impression that led to that data as well as an integer
     * between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the list of DukeObjects
     * @param parent the impression object the data is tagged to
     */
    public SearchResult(String name, DukeObject parent,
                        List<Pair<DukeObject, Class<? extends DukeObject>>> searchList) {
        super(name, parent);
        this.searchList = searchList;
    }

    public Pair<DukeObject, Class<? extends DukeObject>> getResult(int idx) throws DukeException{
        if (idx > searchList.size()) {
            throw new DukeException("I don't have a search result with that number!");
        } else {
            return searchList.get(idx - 1);
        }
    }

    public List<DukeObject> getSearchList() {
        List<DukeObject> objectList = new ArrayList<DukeObject>();
        for (Pair<DukeObject, Class<? extends DukeObject>> entry : searchList) {
            objectList.add(entry.getKey());
        }
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toReportString() {
        return "";
    }

    @Override
    public UiCard toCard() {
        return null;
    }
}

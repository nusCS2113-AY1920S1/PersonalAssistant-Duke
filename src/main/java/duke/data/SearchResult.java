package duke.data;

import duke.exception.DukeException;
import duke.ui.card.UiCard;

import java.util.ArrayList;
import java.util.List;

// TODO parametrise types

public class SearchResult extends DukeObject {

    private List<DukeObject> searchList;

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
    public SearchResult(String name, List<? extends DukeObject> searchList, DukeObject parent) {
        super(name, parent);
        this.searchList = new ArrayList<DukeObject>(searchList);
    }

    public DukeObject getResult(int idx) throws DukeException{
        if (idx > searchList.size()) {
            throw new DukeException("I don't have a search result with that number!");
        } else {
            return searchList.get(idx - 1);
        }
    }

    public List<DukeObject> getSearchList() {
        return searchList;
    }

    public void addAll(SearchResult other) {
        searchList.addAll(other.searchList);
    }

    public int getCount() {
        return searchList.size();
    }

    @Override
    public String toString() {
        StringBuilder searchDetails = new StringBuilder();
        searchDetails.append("There are ").append(searchList.size()).append(" result(s).");
        searchDetails.append(System.lineSeparator());
        int patCount = 0;
        int impCount = 0;
        int evCount = 0;
        int treatCount = 0;

        for (DukeObject obj : searchList) {
            if (obj.getClass() == Patient.class) {
                ++patCount;
            } else if (obj.getClass() == Impression.class) {
                ++impCount;
            } else if (obj.getClass() == Evidence.class) {
                ++evCount;
            } else if (obj.getClass() == Treatment.class) {
                ++treatCount;
            }
        }

        if (patCount != 0) {
            searchDetails.append("There are ").append(patCount).append(" patient(s)");
            searchDetails.append(System.lineSeparator());
        }
        if (impCount != 0) {
            searchDetails.append("There are ").append(impCount).append(" impression(s)");
            searchDetails.append(System.lineSeparator());
        }
        if (evCount != 0) {
            searchDetails.append("There are ").append(evCount).append(" evidence(s)");
            searchDetails.append(System.lineSeparator());
        }
        if (treatCount != 0) {
            searchDetails.append("There are ").append(treatCount).append(" treatment(s)");
        }
        return searchDetails.toString();
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

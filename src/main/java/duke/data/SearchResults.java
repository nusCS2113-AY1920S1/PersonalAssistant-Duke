package duke.data;

import duke.exception.DukeException;
import duke.ui.card.UiCard;
import duke.ui.context.Context;

import java.util.ArrayList;
import java.util.List;

// TODO parametrise types

public class SearchResults extends DukeObject {

    private List<DukeObject> searchList;

    /**
     * The SearchResult object represents a list of DukeObjects.
     * It corresponds to the DukeObjects which string representation,
     * contain a particular SearchTerm. The name stores the searchTerm,
     * and the parent the context it was called from.
     * This is used when the user finds a particular string or when the
     * string entered to reference an object by the user is ambiguous.
     * Attributes:
     * @param name the search term
     * @param parent the DukeObject object the search is tagged to
     */
    public SearchResults(String name, List<? extends DukeObject> searchList, DukeObject parent) {
        super(name, parent);
        this.searchList = new ArrayList<DukeObject>(searchList);
    }

    /**
     * This returns the DukeObject in the SearchResult list at an particular index.
     * Ensures index is within bounds.
     * @param idx the index in the list
     * @return the DukeObject in the list
     * @throws DukeException if the index specified is not within the bounds of the list
     */
    public DukeObject getResult(int idx) throws DukeException {
        if (idx >= searchList.size()) {
            throw new DukeException("I don't have a search result with that number!");
        } else {
            return searchList.get(idx);
        }
    }

    public List<DukeObject> getSearchList() {
        return searchList;
    }

    public void addAll(SearchResults other) {
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

    @Override
    public Context toContext() {
        return Context.SEARCH;
    }
}

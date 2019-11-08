package duke.command.impression;

import duke.DukeCore;
import duke.command.CommandUtils;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeUtilException;

public class ImpressionUtils {

    /**
     * Gets the current Impression being displayed.
     * @param core The DukeCore that the app is running on.
     * @return The current Impression.
     * @throws DukeException If the current context is not an Impression, should not happen.
     */
    public static Impression getImpression(DukeCore core) throws DukeException {
        try {
            return (Impression) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("The current context is not an Impression!");
        }
    }

    /**
     * Gets the DukeData object specified by the index.
     * Determines if it is in evidence list or treatment list by checking nullity of strings.
     * @param allStr Not applicable in this context
     * @param evidStr if the DukeData is in the evidence list
     * @param treatStr if the DukeData is in the treatment list
     * @param impression The impression object containing the lists
     * @return the DukeData object if method call is valid
     * @throws DukeException if the index is not valid or invalid parameters used
     */
    public static DukeData getDataByIdx(String allStr, String evidStr, String treatStr, Impression impression)
            throws DukeException {
        if (allStr != null) {
            return null;
        }

        try {
            if (evidStr != null && treatStr == null) {
                int evidIdx = CommandUtils.idxFromString(evidStr);
                if (evidIdx == -1) {
                    return null;
                } else {
                    return impression.getEvidenceAtIdx(evidIdx);
                }
            } else if (evidStr == null && treatStr != null) {
                int treatIdx = CommandUtils.idxFromString(treatStr);
                if (treatIdx == -1) {
                    return null;
                } else {
                    return impression.getTreatmentAtIdx(treatIdx);
                }
            } else {
                throw new DukeUtilException("I don't know what index you want me to access!");
            }
        } catch (NumberFormatException excp) {
            return null;
        }
    }

    /**
     * General-purpose function for finding DukeData by name or index. One and only one of allStr (search evidences
     * and treatments), treatStr (search treatments) and evidStr (search evidences) is to be non-null. The appropriate
     * search in the names will then be performed.
     * @param allStr A search term that will be searched for amongst both treatments and evidences.
     * @param evidStr A search term that will be searched for amongst evidences.
     * @param treatStr A search term that will be searched for amongst treatments.
     * @param impression The impression whose DukeData we want to find.
     * @return The required DukeData matching the user's query.
     * @throws DukeException If a matching evidence or treatment cannot be found, given the provided search terms, or
     *                       more than one search term was non-null.
     */
    public static SearchResults searchData(String allStr, String evidStr, String treatStr, Impression impression)
            throws DukeException {
        DukeException dataNotFound;
        SearchResults results;

        if (allStr != null && evidStr == null && treatStr == null) {
            results = impression.findByName(allStr);
            dataNotFound = new DukeException("Can't find any data item with that name!");
        } else if (allStr == null && evidStr != null && treatStr == null) {
            results = impression.findEvidencesByName(evidStr);
            dataNotFound = new DukeException("Can't find any evidences with that name!");
        } else if (allStr == null && evidStr == null && treatStr != null) {
            results = impression.findTreatmentsByName(treatStr);
            dataNotFound = new DukeException("Can't find any treatments with that name!");
        } else {
            throw new DukeUtilException("I don't know what you want me to look for!");
        }

        if (results.getCount() == 0) {
            throw dataNotFound;
        } else {
            return results;
        }
    }
}

package duke.command;

import duke.DukeCore;
import duke.data.SearchResult;
import duke.exception.DukeException;

public class SearchSpec extends ArgSpec {

    private static final SearchSpec spec = new SearchSpec();
    private static String input;

    public static SearchSpec getSpec() {
        return getSpec(null);
    }

    public static SearchSpec getSpec(String input) {
        SearchSpec.input = input;
        return spec;
    }

    private SearchSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        SearchResult result;
        try {
            int idx = Integer.parseInt(input);
        } catch (NumberFormatException excp) {
            throw new DukeException("Please enter the index of a search result!");
        }
        try {
            result = (SearchResult) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("Search command called from non-search context!");
        }

        // TODO: send the DukeObject back to the caller

    }
}

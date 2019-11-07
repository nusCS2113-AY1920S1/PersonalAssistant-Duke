package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.SearchResult;
import duke.exception.DukeException;
import duke.ui.context.Context;

import java.util.ArrayList;

public class ImpressionFindSpec extends ArgSpec {

    private static final ImpressionFindSpec spec = new ImpressionFindSpec();

    public static ImpressionFindSpec getSpec() {
        return spec;
    }

    private ImpressionFindSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
    super.execute(core);
        String searchTerm = cmd.getArg();
        ArrayList<DukeObject> searchResult = new ArrayList<>();
        Impression impression = (Impression) core.uiContext.getObject();
        if (cmd.getSwitchVals().isEmpty()) {
            searchResult.addAll(impression.searchAll(searchTerm));
        } else {
            if (cmd.getSwitchVals().containsKey("evidence")) {
                searchResult.addAll(impression.findEvidences(searchTerm));
            }
            if (cmd.getSwitchVals().containsKey("treatment")) {
                searchResult.addAll(impression.findTreatments(searchTerm));
            }
        }

        if (!searchResult.isEmpty()) {
            SearchResult search = new SearchResult(searchTerm, searchResult, impression);
            core.uiContext.setContext(Context.SEARCH, search);
            core.updateUi("Returning result of search of " + searchTerm);
        } else {
            throw new DukeException("There were no relevant treatments or evidences.");
        }

    }
}

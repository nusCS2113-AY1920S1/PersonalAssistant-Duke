package duke.command.impression;


import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.SearchResult;
import duke.exception.DukeException;
import duke.ui.context.Context;

import java.util.ArrayList;

public class ImpressionFindCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionFindSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String searchTerm = getArg();
        String findStr = "Here are the objects that contain '" + getArg() + "':\n";
        ArrayList<DukeObject> searchResult = new ArrayList<>();
        Impression impression = (Impression) core.uiContext.getObject();
        if (getSwitchVals().isEmpty()) {
            searchResult.addAll(impression.find(searchTerm));
        } else {
            if (getSwitchVals().containsKey("evidence")) {
                searchResult.addAll(impression.findEvidences(searchTerm));
            }
            if (getSwitchVals().containsKey("treatment")) {
                searchResult.addAll(impression.findTreatments(searchTerm));
            }
        }

        /*StringBuilder information = new StringBuilder("");

        for (int i = 0; i < searchResult.size(); i++) {
            information.append(i + 1).append(". ");
            information.append(searchResult.get(i).getName()).append(System.lineSeparator());
        }
        core.ui.print(findStr + information.toString());*/
        if (!searchResult.isEmpty()) {
            SearchResult search = new SearchResult(searchTerm, searchResult, impression);
            core.uiContext.setContext(Context.SEARCH, search);
            core.ui.print("Returning result of search of " + searchTerm);
        } else {
            throw new DukeException("There were no relevant treatments or evidences.");
        }

    }
}

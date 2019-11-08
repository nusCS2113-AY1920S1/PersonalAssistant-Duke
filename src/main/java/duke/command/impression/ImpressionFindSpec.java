package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.SearchResults;
import duke.exception.DukeException;

import java.util.ArrayList;

public class ImpressionFindSpec extends ObjSpec {

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
        SearchResults results = new SearchResults(searchTerm, new ArrayList<DukeObject>(), impression);
        if (cmd.getSwitchVals().isEmpty()) {
            results = impression.searchAll(searchTerm);
        } else {
            if (cmd.getSwitchVals().containsKey("evidence")) {
                results.addAll(impression.findEvidences(searchTerm));
            }
            if (cmd.getSwitchVals().containsKey("treatment")) {
                results.addAll(impression.findTreatments(searchTerm));
            }
        }

        processResults(core, results);
    }


    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) {
        core.uiContext.open(obj);
    }
}

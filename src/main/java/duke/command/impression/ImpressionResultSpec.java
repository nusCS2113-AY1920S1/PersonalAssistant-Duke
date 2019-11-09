package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.SearchResults;
import duke.exception.DukeException;

import java.util.ArrayList;
import java.util.List;

public class ImpressionResultSpec extends ObjSpec {
    private static final ImpressionResultSpec spec = new ImpressionResultSpec();

    public static ImpressionResultSpec getSpec() {
        return spec;
    }

    private ImpressionResultSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Impression impression = ImpressionUtils.getImpression(core);
        DukeObject obj = ImpressionUtils.getDataByIdx(null, null, cmd.getArg(), impression);
        if (obj == null) {
            SearchResults results = ImpressionUtils.searchData(null, null, cmd.getArg(), impression);
            List<DukeObject> searchList = results.getSearchList();
            List<DukeObject> invxList = new ArrayList<DukeObject>();
            for (DukeObject result : searchList) {
                if (result instanceof Investigation) {
                    invxList.add(result);
                }
            }
            processResults(core, new SearchResults(results.getName(), invxList, results.getParent()));
        } else if (!(obj instanceof Investigation)) {
            throw new DukeException("Only investigations can be converted into results!");
        } else {
            executeWithObj(core, obj);
        }
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Impression impression = ImpressionUtils.getImpression(core);
        Investigation invx = (Investigation) obj;
        impression.addNewEvidence(invx.toResult(cmd.getSwitchVal("summary")));
        impression.deleteTreatment(obj.getName());
        core.writeJsonFile();
        core.ui.updateUi("Converted the '" + obj.getName() + "' investigation to a report!");
    }
}

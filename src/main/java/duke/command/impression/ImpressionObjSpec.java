package duke.command.impression;

import duke.DukeCore;
import duke.command.ObjSpec;
import duke.data.Impression;
import duke.data.SearchResults;
import duke.exception.DukeException;

public abstract class ImpressionObjSpec extends ObjSpec {

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Impression impression = ImpressionUtils.getImpression(core);
        SearchResults results = ImpressionUtils.getData(cmd.getArg(), cmd.getSwitchVal("evidence"),
                cmd.getSwitchVal("treatment"), impression);

    }
}

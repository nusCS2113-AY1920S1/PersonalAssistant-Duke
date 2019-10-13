package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;

public class EndCommand extends ModuleCommand {

    @Override
    public void execute(
            HashMap<String, ModuleInfoSummary> summaryMap,
            HashMap<String, ModuleInfoDetailed> detailedMap,
            PlannerUi plannerUi,
            Storage store) {
        plannerUi.goodbyeMsg();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

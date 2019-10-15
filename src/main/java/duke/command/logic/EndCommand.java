package duke.command.logic;

import duke.modules.data.ModuleInfoDetailed;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;

public class EndCommand extends ModuleCommand {

    /**
     * Constructor for EndCommand.
     */
    public EndCommand() {

    }

    @Override
    public void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            ModuleTasksList tasks,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper) {
        plannerUi.goodbyeMsg();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

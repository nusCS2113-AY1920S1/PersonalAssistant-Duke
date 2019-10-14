package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.TaskList;

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
            TaskList tasks,
            PlannerUi plannerUi,
            Storage store) {
        plannerUi.goodbyeMsg();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

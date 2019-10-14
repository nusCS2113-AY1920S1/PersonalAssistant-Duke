package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.TaskList;

import java.util.HashMap;

public abstract class ModuleCommand {

    public abstract void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            TaskList tasks,
            PlannerUi plannerUi,
            Storage store) throws ModException;

    public abstract boolean isExit();
}

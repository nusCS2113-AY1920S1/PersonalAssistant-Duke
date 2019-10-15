package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;

public abstract class ModuleCommand {

    public abstract void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            ModuleTasksList tasks,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper) throws ModException;

    public abstract boolean isExit();
}

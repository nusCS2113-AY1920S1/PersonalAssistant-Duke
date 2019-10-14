package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;

public abstract class ModuleCommand {

    public abstract void execute(
            HashMap<String, ModuleInfoSummary> summaryMap,
            HashMap<String, ModuleInfoDetailed> detailedMap,
            PlannerUi plannerUi,
            Storage store) throws ModException;

    public abstract boolean isExit();
}

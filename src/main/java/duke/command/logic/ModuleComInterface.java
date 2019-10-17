package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.util.PlannerUi;
import java.util.HashMap;

public interface ModuleComInterface {

    void execute(
            HashMap<String, ModuleInfoSummary> summaryHashMap,
            HashMap<String, ModuleInfoDetailed> detailedHashMap,
            PlannerUi plannerUi) throws ModException;
}

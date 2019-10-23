package planner.logic.command;

import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleInfoSummary;
import planner.ui.cli.PlannerUi;
import java.util.HashMap;

public interface ModuleComInterface {

    void execute(
            HashMap<String, ModuleInfoSummary> summaryHashMap,
            HashMap<String, ModuleInfoDetailed> detailedHashMap,
            PlannerUi plannerUi) throws ModException;
}

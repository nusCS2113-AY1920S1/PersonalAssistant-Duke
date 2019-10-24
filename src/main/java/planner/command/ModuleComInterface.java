package planner.command;

import planner.exceptions.original.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleInfoSummary;
import planner.util.commons.PlannerUi;
import java.util.HashMap;

public interface ModuleComInterface {

    void execute(
            HashMap<String, ModuleInfoSummary> summaryHashMap,
            HashMap<String, ModuleInfoDetailed> detailedHashMap,
            PlannerUi plannerUi) throws ModException;
}

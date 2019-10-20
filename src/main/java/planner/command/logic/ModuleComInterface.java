package planner.command.logic;

import planner.exceptions.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleInfoSummary;
import planner.util.PlannerUi;
import java.util.HashMap;

public interface ModuleComInterface {

    void execute(
            HashMap<String, ModuleInfoSummary> summaryHashMap,
            HashMap<String, ModuleInfoDetailed> detailedHashMap,
            PlannerUi plannerUi) throws ModException;
}

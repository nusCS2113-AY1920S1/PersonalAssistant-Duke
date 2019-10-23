package planner.command;

import planner.exceptions.original.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.commons.JsonWrapper;
import planner.util.commons.ModuleTasksList;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.periods.CcaList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SortByMcCommand extends ModuleCommand {

    public SortByMcCommand() {
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        plannerUi.sortModuleMsg();
        List<ModuleTask> hold = tasks.getTasks();
        hold.sort(Comparator.comparing(ModuleTask::getModuleCredit));
        plannerUi.showSortedModules(hold);
    }
}

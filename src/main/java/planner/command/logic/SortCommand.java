package planner.command.logic;

import planner.exceptions.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import planner.util.commons.ModuleTasksList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SortCommand extends ModuleCommand {

    public SortCommand() {
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
        hold.sort(Comparator.comparing(ModuleTask::getModuleCode));
        //hold.sort(Comparator.comparing(ModuleTask::getModuleCredit));
        plannerUi.showSortedModules(hold);
    }
}

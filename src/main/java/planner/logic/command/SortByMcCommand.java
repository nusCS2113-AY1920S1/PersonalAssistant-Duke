package planner.logic.command;

import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.util.crawler.JsonWrapper;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.cca.CcaList;

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

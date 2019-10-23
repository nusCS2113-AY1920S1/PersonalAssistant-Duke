package planner.logic.command;

import planner.logic.modules.cca.Cca;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.module.ModuleTasksList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SortCommand extends ModuleCommand {

    public SortCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        String toSort = arg("toSort");
        plannerUi.sortMsg(toSort);
        List<?> hold;
        switch (toSort) {
            case ("ccas"): {
                hold = ccas;
                hold.sort(Comparator.comparing((Object t) -> ((Cca) t).getTask()));
                break;
            }
            case ("modules"):
            default: {
                hold = tasks.getTasks();
                hold.sort(Comparator.comparing((Object t) -> ((ModuleTask) t).getModuleCode()));
                break;
            }
        }
        //hold.sort(Comparator.comparing(ModuleTask::getModuleCredit));
        plannerUi.showSorted(hold);
    }
}
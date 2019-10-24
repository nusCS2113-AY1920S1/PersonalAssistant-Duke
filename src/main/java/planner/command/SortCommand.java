package planner.command;

import planner.modules.inherited.Cca;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;

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
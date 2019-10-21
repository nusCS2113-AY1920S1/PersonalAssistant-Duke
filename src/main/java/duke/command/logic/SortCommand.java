package duke.command.logic;

import duke.modules.Cca;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
package duke.command.logic;

import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;
import java.util.List;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.commons.ModuleTasksList;

public class ShowModuleCommand extends ModuleCommand {

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        plannerUi.listMsg();
        int counter = 1;
        List<ModuleTask> hold = tasks.getTasks();
        for (ModuleTask temp : hold) {
            System.out.print(counter++ + " ");
            plannerUi.showObject(temp);
        }
    }
}

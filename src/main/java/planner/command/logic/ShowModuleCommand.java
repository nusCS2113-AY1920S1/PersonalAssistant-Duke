package planner.command.logic;

import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import java.util.HashMap;
import java.util.List;

import planner.exceptions.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.commons.ModuleTasksList;

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

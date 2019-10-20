package planner.command.logic;

import planner.exceptions.ModException;
import planner.exceptions.planner.ModNotFoundException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import planner.util.commons.ModuleTasksList;

import java.util.HashMap;

public class SearchThenAddCommand extends ModuleCommand {

    private String moduleCode;

    public SearchThenAddCommand(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        if (detailedMap.containsKey(moduleCode)) {
            ModuleInfoDetailed mod = detailedMap.get(moduleCode);
            ModuleTask temp = new ModuleTask(moduleCode, mod);
            tasks.getTasks().add(temp);
            plannerUi.addedMsg(temp);
            jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
        } else {
            throw new ModNotFoundException();
        }
    }
}

package duke.command.logic;

import duke.exceptions.ModException;
import duke.exceptions.planner.ModNotFoundException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;

public class SearchThenAddCommand extends ModuleCommand {

    private String moduleCode;

    public SearchThenAddCommand(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
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

    @Override
    public boolean isExit() {
        return false;
    }
}

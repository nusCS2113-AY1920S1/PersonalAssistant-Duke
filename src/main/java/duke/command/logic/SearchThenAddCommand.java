package duke.command.logic;

import duke.exceptions.ModException;
import duke.exceptions.planner.ModNotFoundException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.modules.data.ModuleTask;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.TaskList;

import java.util.HashMap;

public class SearchThenAddCommand extends ModuleCommand {

    private String moduleCode;

    public SearchThenAddCommand(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        TaskList tasks,
                        PlannerUi plannerUi,
                        Storage store) throws ModException {
        if (detailedMap.containsKey(moduleCode)) {
            ModuleInfoDetailed temp = detailedMap.get(moduleCode);
            ModuleTask task = new ModuleTask(moduleCode, temp);
            tasks.add(task);
        } else {
            throw new ModNotFoundException();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

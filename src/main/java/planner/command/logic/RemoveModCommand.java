package planner.command.logic;

import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import java.util.HashMap;

import planner.exceptions.ModEmptyListException;
import planner.exceptions.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.commons.ModuleTasksList;

public class RemoveModCommand extends ModuleCommand {

    private int index;

    public RemoveModCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        if (index < 0 || index >= tasks.getSize() || tasks.getTasks().isEmpty()) {
            throw new ModEmptyListException();
        }
        ModuleTask delMod = tasks.getTasks().get(index);
        plannerUi.deleteMsg(delMod);
        tasks.delete(index);
        jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
    }
}

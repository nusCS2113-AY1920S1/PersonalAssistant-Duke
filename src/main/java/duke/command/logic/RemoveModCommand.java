package duke.command.logic;

import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;

import duke.exceptions.ModEmptyCommandException;
import duke.exceptions.ModEmptyListException;
import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.commons.ModuleTasksList;

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

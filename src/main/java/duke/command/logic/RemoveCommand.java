package duke.command.logic;

import duke.exceptions.ModEmptyListException;
import duke.exceptions.ModException;
import duke.exceptions.ModOutOfBoundException;
import duke.modules.Cca;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;

import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.commons.ModuleTasksList;

public class RemoveCommand extends ModuleCommand {

    public RemoveCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        int index = arg("index", int.class);
        switch (arg("toRemove")) {
            case "cca": {
                if (ccas.size() == 0) {
                    throw new ModEmptyListException("ccas");
                }
                if (index < 0 || index >= ccas.size()) {
                    throw new ModOutOfBoundException();
                }
                Cca delCca = ccas.get(index);
                plannerUi.deleteMsg(delCca);
                ccas.remove(index);
                break;
            }

            case "module":
            default: {
                if (index < 0 || index >= tasks.getSize() || tasks.getTasks().isEmpty()) {
                    throw new ModEmptyListException();
                }
                ModuleTask delMod = tasks.getTasks().get(index);
                plannerUi.deleteMsg(delMod);
                tasks.delete(index);
                jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
                break;
            }
        }
    }
}

package planner.command;

import planner.exceptions.original.ModEmptyListException;
import planner.exceptions.original.ModException;
import planner.exceptions.original.ModOutOfBoundException;
import planner.modules.inherited.Cca;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import java.util.HashMap;

import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.commons.ModuleTasksList;

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

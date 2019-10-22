package planner.logic.command;

import planner.logic.exceptions.legacy.ModEmptyListException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.legacy.ModOutOfBoundException;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import java.util.HashMap;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;

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
        int index = arg("index", Integer.class) - 1;
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

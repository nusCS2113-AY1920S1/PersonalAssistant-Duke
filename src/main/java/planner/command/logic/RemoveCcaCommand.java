package planner.command.logic;

import planner.exceptions.ModEmptyListException;
import planner.exceptions.ModException;
import planner.exceptions.ModOutOfBoundException;
import planner.modules.Cca;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import java.util.HashMap;

import planner.modules.data.ModuleInfoDetailed;
import planner.util.commons.ModuleTasksList;

public class RemoveCcaCommand extends ModuleCommand {

    private int index;

    public RemoveCcaCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        if (ccas.size() == 0) {
            throw new ModEmptyListException("ccas");
        }
        if (index < 0 || index >= ccas.size()) {
            throw new ModOutOfBoundException();
        }
        Cca delCca = ccas.get(index);
        plannerUi.deleteMsg(delCca);
        ccas.remove(index);
    }
}

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
import duke.util.commons.ModuleTasksList;

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
        if (index < 0 || index > ccas.size()) {
            throw new ModOutOfBoundException();
        }
        Cca delCca = ccas.get(index);
        plannerUi.deleteMsg(delCca);
        ccas.remove(index);
    }
}

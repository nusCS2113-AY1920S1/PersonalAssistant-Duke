package duke.command.logic;

import duke.modules.Cca;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;

import duke.modules.data.ModuleInfoDetailed;
import duke.util.commons.ModuleTasksList;

public class ShowCcaCommand extends ModuleCommand {

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        plannerUi.listCcaMsg();
        int counter = 0;
        for (Cca cca : ccas) {
            plannerUi.println(++counter + " " + cca);
        }
    }
}

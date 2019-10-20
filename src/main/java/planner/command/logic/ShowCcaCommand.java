package planner.command.logic;

import planner.modules.Cca;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import java.util.HashMap;

import planner.modules.data.ModuleInfoDetailed;
import planner.util.commons.ModuleTasksList;

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

package planner.command;

import planner.modules.data.ModuleInfoDetailed;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;

import java.util.HashMap;

public class EndCommand extends ModuleCommand {

    @Override
    public void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            ModuleTasksList tasks,
            CcaList ccas,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper) {
        plannerUi.goodbyeMsg();
        System.exit(0); //Safer
        //Runtime.getRuntime().halt(0); //Forced kill
    }
}

package planner.command.logic;

import planner.modules.data.ModuleInfoDetailed;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
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

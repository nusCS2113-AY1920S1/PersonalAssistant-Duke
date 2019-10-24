//@@author namiwa

package planner.logic.command;

import java.util.HashMap;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.util.crawler.JsonWrapper;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.cca.CcaList;

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
        //System.exit(0); // Causes test cases to throw exceptions
        //Runtime.getRuntime().halt(0); //Forced kill
    }
}

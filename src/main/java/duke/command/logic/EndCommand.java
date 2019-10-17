package duke.command.logic;

import duke.modules.data.ModuleInfoDetailed;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;

public class EndCommand extends ModuleCommand {

    /**
     * Constructor for EndCommand.
     */
    public EndCommand() {

    }

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

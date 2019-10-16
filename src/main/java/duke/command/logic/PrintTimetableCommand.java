package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.modules.data.ModuleTime;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;
import java.util.List;

public class PrintTimetableCommand extends ModuleCommand {

    public PrintTimetableCommand() {
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        plannerUi.printTimetableMsg();
        //List<ModuleTime> hold = tasks.getTasks();
        List<ModuleTask> hold = tasks.getTasks();
        //sort the modules into days
        plannerUi.showAccordingToDays(hold);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

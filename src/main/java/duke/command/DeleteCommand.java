package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import javafx.util.Pair;

import java.util.Map;

public class DeleteCommand extends ArgCommand {

    private static Map<String, ArgLevel> switches = Map.of();
    private static Map<String, String> switchAliases = Map.of();

    @Override
    public void execute(DukeCore core) throws DukeException {
        String delStr = core.taskList.deleteTask(getArg());
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.ui.print(core.taskList.getDelReport(System.lineSeparator() + "  " + delStr, 1));
    }

    @Override
    String getEmptyArgMsg() {
        return "You didn't tell me which task to delete!";
    }

    @Override
    ArgLevel getCmdArgLevel() {
        return ArgLevel.REQUIRED;
    }

    @Override
    Map<String, ArgLevel> getSwitches() {
        return switches;
    };

    @Override
    Map<String, String> getSwitchAliases() {
        return switchAliases;
    };

}

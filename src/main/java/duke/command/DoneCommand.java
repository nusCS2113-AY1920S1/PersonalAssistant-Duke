package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

public class DoneCommand extends ArgCommand {

    private static Map<String, ArgLevel> switches = Map.of();
    private static Map<String, String> switchAliases = Map.of();

    @Override
    public void execute(DukeCore core) throws DukeException {
        String taskStr = core.taskList.markDone(getArg());
        taskStr = "Nice! I've marked this task as done:" + System.lineSeparator() + "  " + taskStr;
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.ui.print(taskStr);
    }

    @Override
    ArgLevel getCmdArgLevel() {
        return ArgLevel.REQUIRED;
    }

    @Override
    String getEmptyArgMsg() {
        return "You didn't tell me which task you did!";
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

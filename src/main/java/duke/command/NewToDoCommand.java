package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.task.ToDoTask;
import javafx.util.Pair;

import java.util.Map;

public class NewToDoCommand extends ArgCommand {

    // TODO: adapt further

    private static Map<String, ArgLevel> switches;
    private static Map<String, String> switchAliases;

    static {
        String[] switchNameArr = new String[]{"switch"};
        ArgLevel[] argLevelArr = new ArgLevel[]{ArgLevel.OPTIONAL};
        String[] switchRootArr = new String[]{"s"};
        Pair<Map<String, ArgLevel>, Map<String, String>> switchData =
                switchInit(switchNameArr, argLevelArr, switchRootArr);
        switches = switchData.getKey();
        switchAliases = switchData.getValue();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addStr = core.taskList.addTask(new ToDoTask(getArg()));
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(core.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }

    @Override
    ArgLevel getCmdArgLevel() {
        return ArgLevel.OPTIONAL;
    }

    @Override
    String getEmptyArgMsg() {
        return "You didn't tell me what to do!";
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

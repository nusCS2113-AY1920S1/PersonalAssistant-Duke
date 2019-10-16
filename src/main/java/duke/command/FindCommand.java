package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import javafx.util.Pair;

import java.util.Map;

public class FindCommand extends ArgCommand {
    private static Map<String, ArgLevel> switches;
    private static Map<String, String> switchAliases;

    static {
        // TODO: Update these
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
        String findStr = "Here are the tasks that contain '" + getArg()+ "':";
        findStr = findStr + core.taskList.find(getArg()).replace(System.lineSeparator(),
                System.lineSeparator() + "  ");
        core.ui.print(findStr);
    }

    @Override
    ArgLevel getCmdArgLevel() {
        return ArgLevel.REQUIRED;
    }

    @Override
    String getEmptyArgMsg() {
        return "You didn't tell me what to search for!";
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

package duke.command;

import duke.DukeCore;
import javafx.util.Pair;

import java.util.Map;

/**
 * Stub Command for testing new Parser.
 */

public class DoctorCommand extends ArgCommand {

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
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + super.getArg() + System.lineSeparator() + "Switch: " +
                super.getSwitchVal("switch"));
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
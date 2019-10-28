package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.exception.DukeException;

public class ImpressionPriorityCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionPrioritySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        DukeData prioData = ImpressionHelpers.getData(getArg(), getSwitchVal("evidence"),
                getSwitchVal("treatment"), ImpressionHelpers.getImpression(core));
        int priority = ImpressionHelpers.checkPriority(switchToInt("set"));
        prioData.setPriority(priority);
        core.writeJsonFile();
        core.ui.print("Updated '" + prioData.getName() + "' priority to " + priority + "!");
    }
}

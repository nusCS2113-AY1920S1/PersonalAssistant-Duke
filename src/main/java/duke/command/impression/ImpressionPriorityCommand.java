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
        DukeData prioData = ImpressionUtils.getData(getArg(), getSwitchVal("evidence"),
                getSwitchVal("treatment"), ImpressionUtils.getImpression(core));
        int priority = switchToInt("set");
        ImpressionUtils.checkPriority(priority);
        prioData.setPriority(priority);
        core.writeJsonFile();
        core.updateUi("Updated '" + prioData.getName() + "' priority to " + priority + "!");
    }
}

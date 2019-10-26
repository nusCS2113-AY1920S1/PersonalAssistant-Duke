package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.Arrays;

public class ImpressionNewCommand extends DukeDataCommand {

    private static final String[] medicineStatusArr = {"Not ordered", "In progress", "Completed"};
    private static final String[] invxStatusArr = {"Not ordered", "In progress"};
    private static final String[] planStatusArr = {"Not ordered", "In progress", "Reported"};

    @Override
    protected ArgSpec getSpec() {
        return ImpressionNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addType = uniqueDataType();
        checkTypeSwitches(addType);
        switch(addType) {
        case "medicine":

            break;
        case "plan": //fallthrough
        case "investigation":
            break;
        case "result":
            break;
        case "observation":
            break;
        default:
            throw new DukeException("Invalid data type!");
        }
    }
}

package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class ImpressionEditCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionEditSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addType = uniqueDataType();
        if (addType == null) { // edit patients
            
        }
        checkTypeSwitches(addType);
    }

}

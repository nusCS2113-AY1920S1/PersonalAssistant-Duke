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
        boolean isAppending = false;
        if (isSwitchSet("append")) {
            isAppending = true;
        }
        if (addType == null) { // edit patients
            if (isAppending) {

            } else {

            }
        }

        // find DukeData
    }

}

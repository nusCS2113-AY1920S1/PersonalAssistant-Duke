package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

public class ImpressionDeleteCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionDeleteSpec.getSpec();
    }


}

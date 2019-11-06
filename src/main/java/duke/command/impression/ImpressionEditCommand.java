package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;
import duke.exception.DukeUtilException;

import java.util.List;
import java.util.Map;

public class ImpressionEditCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionEditSpec.getSpec();
    }

    // TODO: refactor, this method is a behemoth

}

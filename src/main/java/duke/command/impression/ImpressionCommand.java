package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.data.Impression;
import duke.exception.DukeException;

public abstract class ImpressionCommand extends ArgCommand {

    protected Impression getImpression(DukeCore core) throws DukeException {
        try {
            return (Impression) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("The current context is not an Impression!");
        }
    }
}

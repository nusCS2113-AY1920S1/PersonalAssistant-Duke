package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class ReportCommand extends ArgCommand {

    @Override
    public void execute(DukeCore core) throws DukeException {
        System.out.println("hej");
    }
    @Override
    protected ArgSpec getSpec() {
        return null;
    }
}

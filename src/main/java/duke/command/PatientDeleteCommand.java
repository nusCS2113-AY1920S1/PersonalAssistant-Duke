package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class PatientDeleteCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() { return PatientDeleteSpec.getSpec(); }

    @Override
    public void execute(DukeCore core) throws DukeException {
        System.out.println("TA BORT NÅGOT");
    }
}

package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class ReportCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ReportSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        if(core.patientMap.patientExist(getArg())){
            System.out.println("NU SKA EN RAPPORT SKAPAS");
        }
        core.patientMap.deletePatient(getArg());
    }
}

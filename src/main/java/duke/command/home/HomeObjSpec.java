package duke.command.home;

import duke.DukeCore;
import duke.command.CommandUtils;
import duke.command.ObjSpec;
import duke.data.Patient;
import duke.exception.DukeException;

public abstract class HomeObjSpec extends ObjSpec {

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String bed = cmd.getSwitchVal("bed");
        Patient patient = CommandUtils.findFromHome(core, bed, cmd.getArg());
        if (patient == null) {
            core.search(core.patientData.findPatients(cmd.getArg()), cmd);
        } else {
            executeWithObj(core, patient);
        }
    }
}

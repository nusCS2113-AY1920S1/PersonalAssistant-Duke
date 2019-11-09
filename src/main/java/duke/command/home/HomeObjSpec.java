package duke.command.home;

import duke.DukeCore;
import duke.command.ObjSpec;
import duke.data.Patient;
import duke.exception.DukeException;

/**
 * Template class for Home context commands that require a patient to be found by bed, index or name.
 */
public abstract class HomeObjSpec extends ObjSpec {

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String bed = cmd.getSwitchVal("bed");
        Patient patient = HomeUtils.findFromHome(core, bed, cmd.getArg());
        if (patient == null) {
            processResults(core, core.patientData.findPatientsByName(cmd.getArg()));
        } else {
            executeWithObj(core, patient);
        }
    }
}

package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientHistorySpec extends ArgSpec {

    private static final PatientHistorySpec spec = new PatientHistorySpec();

    public static PatientHistorySpec getSpec() {
        return spec;
    }

    private PatientHistorySpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Patient patient = (Patient) core.uiContext.getObject();
        patient.appendHistory(cmd.getArg());
        core.writeJsonFile();
        core.updateUi("Note appended!");
    }
}

package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;

public class PatientHistorySpec extends ArgSpec {

    private static final PatientHistorySpec spec = new PatientHistorySpec();

    public static PatientHistorySpec getSpec() {
        return spec;
    }

    private PatientHistorySpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }
}

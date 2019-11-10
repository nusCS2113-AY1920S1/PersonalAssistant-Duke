package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeHistorySpec extends HomeObjSpec {
    private static final HomeHistorySpec spec = new HomeHistorySpec();

    public static HomeHistorySpec getSpec() {
        return spec;
    }

    private HomeHistorySpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("message", String.class, false, ArgLevel.REQUIRED, "m"),
                new Switch("rewrite", String.class, true, ArgLevel.REQUIRED, "r")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Patient patient = (Patient) obj;
        String patientDetails = cmd.getArg() + ", " + patient.getName();
        String message = cmd.getSwitchVal("message");
        String rewrite = cmd.getSwitchVal("rewrite");
        String newHistory = message;

        if (rewrite != null && (rewrite.toLowerCase().equals("y")
                || rewrite.toLowerCase().equals("yes") || rewrite.toLowerCase().equals("ye"))) {
            patient.setHistory(message);
        } else {
            newHistory = patient.appendHistory(message);
        }
        core.writeJsonFile();
        core.updateUi(patient.getName() + "'s history updated to:\n\n" + newHistory + "\n");
    }
}

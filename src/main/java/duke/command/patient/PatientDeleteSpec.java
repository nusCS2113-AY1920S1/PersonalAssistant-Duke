package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeException;

public class PatientDeleteSpec extends PatientObjSpec {
    private static final PatientDeleteSpec spec = new PatientDeleteSpec();

    public static PatientDeleteSpec getSpec() {
        return spec;
    }

    private PatientDeleteSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("critical", String.class, true, ArgLevel.NONE, "c"),
                new Switch("investigation", String.class, true, ArgLevel.NONE, "in"),
                new Switch("impression", String.class, true, ArgLevel.NONE, "im")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        if (obj instanceof Impression) {
            Patient patient = (Patient) obj.getParent();
            patient.deleteImpression(obj.getName());
            core.ui.updateUi("Deleted the '" + obj.getName() + "' impression from " + patient.getName() + "!");
        } else {
            Impression impression = (Impression) obj.getParent();
            if (obj instanceof Evidence) {
                impression.deleteEvidence(obj.getName());
            } else if (obj instanceof Treatment) {
                impression.deleteTreatment(obj.getName());
            } else { // should not happen
               throw new DukeException("Invalid deletion!");
            }
        }
    }
}

package duke.command.home;

import duke.DukeCore;
import duke.command.CommandUtils;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.exception.DukeUtilException;

import java.util.List;

public class HomeUtils {
    /**
     * Find a {@code Patient} with the supplied identifier. Only 1 of either bed number or displayed index
     * should be used to identify said patient.
     *
     * @param core  DukeCore object.
     * @param bedNo Bed number of patient.
     * @param nameOrIdx Displayed index of patient (Home context), or name of Patient.
     * @return Patient object
     * @throws DukeException If 1 of the following 3 conditions applies.
     *                       1. No identifier is provided.
     *                       2. 2 identifiers are provided.
     *                       3. 1 unique identifier is provided but said patient does not exist.
     */
    public static Patient findFromHome(DukeCore core, String bedNo, String nameOrIdx) throws DukeException {
        if (nameOrIdx == null && bedNo == null) {
            throw new DukeUtilException("Please provide a way to identify the patient! Patients can be searched for"
                    + "by name/index or by bed.");
        }
        if (nameOrIdx != null && bedNo != null) {
            throw new DukeUtilException("I don't know if you want me to find the patient ");
        }
        if (bedNo != null) {
            return core.patientData.getPatientByBed(bedNo);
        }

        int index = CommandUtils.idxFromString(nameOrIdx);
        if (index != -1) {
            // TODO: Law of demeter
            List<DukeObject> patientList = core.ui.getIndexedList("patient");
            int count = patientList.size();
            if (index >= count) {
                throw new DukeException("I have only " + ((count == 1) ? ("1 patient") : (count + " patients")) + " in "
                        + "my list!");
            }
            return (Patient) patientList.get(index);
        } else {
            return null;
        }
    }
}

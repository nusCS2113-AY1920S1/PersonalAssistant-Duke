package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeUtilException;

import java.util.List;
import java.util.Map;

public abstract class Treatment extends DukeData {

    private Integer statusIdx;

    /**
     * Abstraction of the actions taken to treat an impression the Doctor has about a patient.
     * A Treatment object corresponds to what actions the doctor is taking to treat the symptoms of a Patient,
     * the information that led to that particular treatment, the status of the treatment,
     * as well as an integer between 1-4 representing the priority or significance of the evidence.
     * Attributes:
     * - name: the treatment given
     * - impression: the impression object the treatment is tagged to
     * - status: the current status of the treatment
     * - priority: the priority level of the treatment
     */
    public Treatment(String name, Impression impression, int priority, String status) throws DukeException {
        super(name, impression, priority);
        setStatus(status);
    }

   @Override
    public Integer setPriority(Integer priorityVal) throws DukeException {
        if (priorityVal >= 0 && priorityVal < 5) {
            super.setPriority(priorityVal);
            return super.getPriority();
        } else {
            throw new DukeException("The priority must be within 0 to 4!");
        }
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Status: " + this.statusIdx + "\n";
        informationString += "Status Description: " + getStatusStr() + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toReportString() {
        String informationString;
        informationString = "Status of treatment: " + this.statusIdx + "\n";
        informationString += "Status Description: " + getStatusStr() + "\n";
        return informationString;
    }

    @Override
    public void edit(String newName, int newPriority, String newSummary, Map<String, String> editVals,
                     boolean isAppending) throws DukeException {
        super.edit(newName, newPriority, newSummary, editVals, isAppending);
        String status = editVals.get("status");
        if (status != null) {
            setStatus(status);
        }
    }

    /**
     * Checks if a status is a string or an integer, and sets this Treatment's status accordingly, if it is valid.
     * @param status The String supplied representing either a status index or name.
     * @return The Integer that the string represents, or 0 if it is null.
     * @throws NumberFormatException If the string is not a valid representation of an integer.
     */
    public void setStatus(String status) throws DukeException {
        if (status == null || "".equals(status)) {
            statusIdx = 0;
        } else {
            try {
                setStatus(Integer.parseInt(status));
            } catch (NumberFormatException excp) { // not numeric
                // TODO: parse with autocorrect?
                for (int i = 0; i < getStatusArr().size(); ++i) {
                    if (getStatusArr().get(i).equalsIgnoreCase(status)) {
                        statusIdx = i;
                    }
                }
                throw new DukeUtilException("'" + status + "' is not a valid status name!");
            }
        }
    }

    public void setStatus(int status) throws DukeException {
        if (status < 0 || status >= getStatusArr().size()) {
            throw new DukeException(status + "is not a valid numeric value for the status!");
        }
        statusIdx = status;
    }

    public abstract List<String> getStatusArr();

    public Integer getStatusIdx() {
        return statusIdx;
    }

    public abstract String getStatusStr();

    /**
     * Checks for equality with another Treatment object - all fields have the same value and all references point to
     * the same objects. Primarily for testing.
     * @param treatment The treatment to compare against.
     * @return True if all fields and references are the same, false otherwise.
     */
    public boolean equals(Treatment treatment) {
        if (super.equals(treatment)) {
            return statusIdx.equals(treatment.statusIdx);
        } else {
            return false;
        }
    }
}

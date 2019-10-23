package duke.data;

public class Medicine extends Treatment {

    private String dose;
    private String startDate;
    private String duration;

    /**
     * Represents the medicine needed to treat a symptom or impression of a patient.
     * A Medicine object corresponds to the medicine prescribed by a doctor to treat the signs
     * and symptoms of a Patient, the impression that led to that particular medicine being necessary,
     * the status of the treatment, a description of the status, the dosage, start date, duration of the treatment
     * as well as an integer between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the medicine needed
     * @param impression the impression object the medicine is tagged to
     * @param priority the priority level of the medicine
     * @param status the current status of the medicine
     * @param statusArr description of the status tagged to this medicine
     * @param dose the dosage of the medicine needed
     * @param startDate the starting date when the patient should be on the medicine
     * @param duration the duration the patient needs to take the medicine
     */
    public Medicine(String name, Impression impression, int priority, int status, String[] statusArr,
                    String dose, String startDate, String duration) {
        super(name, impression, priority, status, statusArr);
        this.dose = dose;
        this.startDate = startDate;
        this.duration = duration;
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Dose: " + this.dose + "\n";
        informationString += "Start Date: " + this.startDate + "\n";
        informationString += "Duration: " + this.duration + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toDisplayString() {
        // todo
        return null;
    }

    @Override
    public String toReportString() {
        // todo
        return null;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }
}

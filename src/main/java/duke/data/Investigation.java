package duke.data;

public class Investigation extends Treatment {

    private String summary;

    /**
     * Represents the investigation needed to investigate an impression the Doctor has about a patient.
     * A Investigation object corresponds to the investigation a doctor needs to better understand the
     * signs and symptoms of a Patient, the impression that led to that particular investigation being necessary,
     * the status of the treatment, a description of the status, a summary of why the investigation is needed
     * as well as an integer between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the investigation needed
     * @param impression the impression object the investigation is tagged to
     * @param priority the priority level of the investigation
     * @param status the current status of the investigation
     * @param statusArr description of the status tagged to this investigation
     * @param summary description of the investigation
     */
    public Investigation(String name, Impression impression, int priority,
                         int status, String[] statusArr, String summary) {
        super(name, impression, priority, status, statusArr);
        this.summary = summary;
    }

    /**
     * This toResult function returns the result or conclusion from the investigation done.
     * @param resultName name of result
     * @param resultPriority importance of the result between 1 to 4
     * @param resultSummary description of the result
     * @return the result object
     */
    public Result toResult(String resultName, int resultPriority, String resultSummary) {
        //String resultName = "Result of " + this.getName();
        //String resultSummary = this.summary;
        //int resultPriority = this.getPriority
        Result newResult = new Result(resultName, this.getImpression(), resultPriority, resultSummary);
        return newResult;
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Summary " + this.summary + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toDisplayString() {
        return null;
    }

    @Override
    public String toReportString() {
        return null;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

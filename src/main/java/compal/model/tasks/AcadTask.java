package compal.model.tasks;

/**
 * Represents all Academic Tasks such as Lectures, Tutorials, Lab sessions and Sectionals.
 */
public class AcadTask extends Task {

    /**
     * Constructs AcadTask object.
     *
     * @param description Description of Academic Task.
     * @param date        Starting date of Academic Task.
     * @param startTime   Starting time of Academic Task.
     * @param priority    priority level of task type
     * @param endTime     End time of deadline
     */
    public AcadTask(String description, Priority priority, String date,
                    String startTime, String endTime, String symbol) {
        super(description, priority);
        super.setDate(date);
        super.setStartTime(startTime);
        super.setEndTime(endTime);
        super.setSymbol(symbol);
    }
}

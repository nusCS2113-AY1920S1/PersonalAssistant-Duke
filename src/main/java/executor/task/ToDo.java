package executor.task;

import duke.exception.DukeException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDo extends Task {
    private Date dateFrom;
    private Date dateTo;

    /**
     * Constructor for Class.
     * @param name The user input from CLI
     */
    public ToDo(String name) {
        super(name);
        this.taskType = TaskType.TODO;
        this.recordTaskDetails(name);
        try {
            this.parseForTaskPeriod();
        } catch  (DukeException invalidInput) {
            invalidInput.printStackTrace();
        }
    }

    private void parseForTaskPeriod() throws DukeException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        if (this.detailDesc == null) {
            return;
        }
        if (this.detailDesc.equals("between")) {
            try {
                String[] dates = taskDetails.trim().split(" ");
                this.dateFrom = dateFormat.parse(dates[0]);
                this.dateTo = dateFormat.parse(dates[1]);
                System.out.println("Date Interpreted as: From " + dateFormat.format(this.dateFrom)
                        + " to " + dateFormat.format(this.dateTo));
            } catch (Exception e) {
                //System.out.println("Invalid Input. Unable to interpret Datetime (use: dd/mm/yyyy HHmm)");
                this.dateFrom = new Date();
                this.dateTo = new Date();
                throw new DukeException("Invalid Input. Unable to interpret Datetime (use: dd/mm/yyyy)");
            }
        }
    }

    // -- Setters & Getters

    /**
     * Sets the dateFrom variable.
     * @param dateFrom date to do the task from
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Sets the dateFrom variable.
     * @param dateTo date to do the task from
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }
}

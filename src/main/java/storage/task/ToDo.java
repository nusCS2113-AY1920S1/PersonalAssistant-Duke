package storage.task;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ToDo extends Task {
    private LocalDate dateFrom;
    private LocalDate dateTo;

    /**
     * Constructor for Class.
     * @param name The user input from CLI
     */
    public ToDo(String name) {
        super(name);
        this.taskType = TaskType.TODO;
        this.recordTaskDetails(name);
        this.parseForTaskPeriod();
    }


    private void parseForTaskPeriod()  {
        if (this.detailDesc == null) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        if (this.detailDesc.equals("between")) {
            try {
                String[] dates = taskDetails.trim().split(" ");
                setDateFrom(LocalDate.parse(dates[0], formatter));
                setDateTo(LocalDate.parse(dates[1], formatter));
                this.setDate(getDateFrom());
                System.out.println("Date Interpreted as: From "
                        + this.getDateFrom().format(formatter)
                        + " to "
                        + this.getDateTo().format(formatter));
            } catch (Exception e) {
                this.dateFrom = LocalDate.now();
                this.dateTo = LocalDate.now();
                System.out.println("Invalid Input. Unable to interpret Datetime (use: dd/MM/yy)");
            }
        }
    }

    // -- Setters & Getters

    /**
     * Sets the dateFrom variable.
     * @param dateFrom date to do the task from
     */
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Sets the dateFrom variable.
     * @param dateTo date to do the task from
     */
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}

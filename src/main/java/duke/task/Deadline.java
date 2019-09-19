package duke.task;

import duke.exception.DukeException;

import java.text.SimpleDateFormat;
import java.util.Date;

class Deadline extends Task {


    // Initialization
    Deadline(String name) {
        super(name);
        this.taskType = TaskType.DEADLINE;
        this.recordTaskDetails(name);
        try {
            this.parseDateTime();
        } catch  (DukeException invalidInput) {
            invalidInput.printStackTrace();
        }

    }

    private void parseDateTime() throws DukeException {
        SimpleDateFormat formatx = new SimpleDateFormat("dd/MM/yyyy HHmm");
        if (this.detailDesc == null) {
            return;
        }
        if (this.detailDesc.equals("by")) {
            try {
                this.setDatetime(formatx.parse(this.taskDetails));
                System.out.println("Date Interpreted: " + formatx.format(this.datetime));
            } catch (Exception e) {
                //System.out.println("Invalid Input. Unable to interpret Datetime (use: dd/mm/yyyy HHmm)");
                this.setDatetime(new Date());
                throw new DukeException("Invalid Input. Unable to interpret Datetime (use: dd/mm/yyyy HHmm)");
            }
        }
    }

    // -- Setters & Getters

    /**
     * Getter for datetime.
     * @return Datetime stored in this Deadline Object
     */
    public Date getDatetime() {
        return datetime;
    }
}

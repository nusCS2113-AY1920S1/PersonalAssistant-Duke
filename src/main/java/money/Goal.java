package money;

import java.time.LocalDate;
import java.util.Date;

public class Goal extends Expenditure {

    public enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    private Priority priority;

    //@@author therealnickcheong
    public Goal(float price, String description, String category, LocalDate goalBy, String priorityLevel) {
        super(price, description, category, goalBy);
        switch (priorityLevel) {
        case "LOW" : {
            this.priority = Priority.LOW;
            break;
        }
        case "MEDIUM": {
            this.priority = Priority.MEDIUM;
            break;
        }
        case "HIGH": {
            this.priority = Priority.HIGH;
            break;
        }
        default: {
            break;
        }
        }
    }

    @Override
    public String toString() {
        return "[GS]" + " " + super.getDescription() + "(target: $" + super.getPrice() + ")\n (to achieve by: "
                + getGoalBy() + ") " + getPriorityString();
    }

    public String getPriorityString() {
        return priority.toString();
    }

    public Priority getPriority(){
        return priority;
    }

    public String getGoalBy() {
        String goalByDateTime = super.getBoughtDate();
        String goalByDate = goalByDateTime.split(" ")[0];
        return goalByDate;
    }

    public String getCategory() {
        return super.getCategory();
    }

}

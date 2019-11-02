package money;

import controlpanel.DukeException;

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
    public Goal(float price, String description, String category, LocalDate goalBy, String priorityLevel) throws DukeException {
        super(price, description, category, goalBy);
        switch (priorityLevel) {
        case "LOW" :
            case "low" : {
            this.priority = Priority.LOW;
            break;
        }
        case "MEDIUM":
            case "medium": {
            this.priority = Priority.MEDIUM;
            break;
        }
        case "HIGH":
            case "high": {
            this.priority = Priority.HIGH;
            break;
        }
            default: {
            throw new DukeException("Please enter in the format: " +
                    "goal <desc> /amt <amount> /by <date> /priority <HIGH/MEDIUM/LOW>\n");
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

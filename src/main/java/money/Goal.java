package money;

import java.time.LocalDate;
import java.util.Date;

public class Goal extends Expenditure {

    private enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Priority priority;

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
                + getGoalBy() + ") " + getPriority();
    }

    public String getPriority() {
        return priority.toString();
    }

    public String getGoalBy() {
        String goalByDateTime = super.getBoughtTime();
        String goalByDate = goalByDateTime.split(" ")[0];
        return goalByDate;
    }

    public String getCategory() {
        return super.getCategory();
    }

}

package task;

import java.io.Serializable;

public class RecurringTask extends Task implements Serializable {
    public RecurringTask(String description, String by, String period) {
        super(description);
        this.period = period;
        this.by = by;
        this.type = "R";
    }

    @Override
    public String giveTask() {
        return "[R]" + super.giveTask() + "(by: " + by + ")(period:"+period+")";
    }

}

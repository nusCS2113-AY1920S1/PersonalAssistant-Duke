package duke.task;

import java.util.Date;

public class DoWithinPeriodTasks extends Task{

    private Date from;
    private Date to;

    public DoWithinPeriodTasks(String d, String f, String t) {
        //CONSTRUCTOR
        super(d);
        from = super.getDate(f);
        to = super.getDate(t);
    }

    public String printInFile(){
        return this.toString();
    }

    @Override
    public String toString(){
        return "[P]" + super.toString() + "(from: " + "TODO" + " to: " + "TODO";
    }
}

package duke.task;

import duke.parser.Parser;

import java.util.Date;

public class DoWithinPeriodTasks extends Task{

    private Date from;
    private Date to;

    public DoWithinPeriodTasks(String d, String f, String t) {
        //CONSTRUCTOR
        super(d);
        from = Parser.getDate(f);
        to = Parser.getDate(t);
    }

    public String printInFile(){
        return this.toString();
    }

    @Override
    public void setNewDate(String date) {
        //do nothing
    }

    @Override
    public Date getCurrentDate() {
        return null;
    }

    @Override
    public String toString(){
        return "[P]" + super.toString() + "(from: " + "TODO" + " to: " + "TODO";
    }
}

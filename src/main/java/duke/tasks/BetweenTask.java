package duke.tasks;

public class BetweenTask extends Task {
    private String start_date;
    private String end_date;

    public BetweenTask(String description, String start_date, String end_date){
        super(description);
        this.start_date = start_date;
        this.end_date = end_date;
    }

    @Override
    public String toString(){
        return "[B]" + super.toString() + " (between: " + start_date + " and " + end_date +")";
    }

}

package duke.task;

public class FixDuration extends Task{

    private String duration;
    public FixDuration(String description, String duration){
        super(description);
        type = 'W';
        this.duration = duration;
    }

    /**
     * This method will return the duration of the task
     * @return a string containing "within" and the duration of the task
     */
    @Override
    public String getDateStr(){
        return "(within " + duration + ")";
    }

    public String formatDateSave(){
        return " | " + duration;
    }
}

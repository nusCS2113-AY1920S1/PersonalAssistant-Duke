package duke.task;

public class DoAfter extends Task{
    private String event;

    public DoAfter(String description, String event) {
        super(description);
        type = 'A';
        this.event = event;
    }

    /**
     * This method will return the string showing the do after and the event.
     * @return a string containing do after and the event to do after.
     */
    @Override
    public String getDateStr() {
        return "(do after: " + event + ")";
    }

    /**
     * This method will return the save format of the event
     * @return a string containg the save format to do after
     */
    @Override
    public String formatDateSave() {
        return " | " + event;
    }
}

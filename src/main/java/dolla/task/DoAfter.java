package dolla.task;

public class DoAfter extends Task {
    private String event;

    /**
     * This method will set the type of a do after task to A and set the description and event as
     * the input parameters.
     * @param description is the description of the string
     * @param event is the event that the user want to do his/her activity after.
     */
    public DoAfter(String description, String event) {
        super(description);
        type = 'A';
        this.event = event;
    }

    /**
     * This method will return the string showing the do after and the event.
     * @return a string containing do after and the event.
     */
    @Override
    public String getDateStr() {
        return "(do after: " + event + ")";
    }

    /**
     * This method will return the save format of the event.
     * @return a string containg the save format to do after.
     */
    @Override
    public String formatDateSave() {
        return " | " + event;
    }
}

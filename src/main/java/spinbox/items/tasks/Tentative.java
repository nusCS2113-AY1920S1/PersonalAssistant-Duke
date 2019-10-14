package spinbox.items.tasks;

import spinbox.DateTime;

public class Tentative extends Task {

    /**
     * Constructor for SpinBox.Tasks.Tentative object.
     * @param description name of the tentative event.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Tentative(String description, DateTime startDate, DateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }


    /**
     * This constructor is used for recreation of SpinBox.Tasks.Tentative from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the tentative event.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Tentative(int done, String description, DateTime startDate, DateTime endDate) {
        super(description);
        this.setDone(done == 1);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String storeString() {
        return "TE | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString();
    }

    @Override
    String getStartDateString() {
        return this.startDate.toString();
    }

    @Override
    String getEndDateString() {
        return this.endDate.toString();
    }

    @Override
    public String toString() {
        return "[TE]" + super.toString() + " (around: "
                + this.getStartDateString() + " to " + this.getEndDateString()
                + " - date not fixed)";
    }

    public String getDescription() {
        return super.getName();
    }
}

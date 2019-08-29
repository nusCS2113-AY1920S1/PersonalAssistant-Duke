public class Deadline extends Task {
    protected String by;
    private DateTimeRecognition convertDate;

    public Deadline(String description, String by)throws DukeException {
        super(description);
        this.by = by;
        convertDate = new DateTimeRecognition(this.by);
        convertDate.dateTime();
    }

    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: "
                + by + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("T" + super.fileOutFormat() + "|" + by);
    }
}
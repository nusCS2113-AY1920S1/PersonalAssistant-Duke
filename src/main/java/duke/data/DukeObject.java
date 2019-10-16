package duke.data;

/*
 * Highest level of abstraction for all DukeObjects storing Patient related Data
 * A Duke object has a relevant name specifying what it is.
 *
 * Attributes:
 * - name: what the object is
 */
public abstract class DukeObject {

    private String name;

    public DukeObject(String name) {
        this.name = name;
    }

    /*
     * The toString function returns a String representing the information stored in
     * the `DukeObject`
     *
     * @return the String representation of the object
     */
    public abstract String toString();

    /*
     * The toDisplayString function returns a String formatted
     * for pretty printing to the GUI / Display.
     *
     * @return the String in pretty print format for display
     */
    public abstract String toDisplayString();

    /*
     * The toReportString function returns a String formatted
     * to be used in a Report about the relevant DukeObject.
     *
     * @return the String in Report format
     */
    public abstract String toReportString();

    public String getName() {
        return this.name;
    }
}

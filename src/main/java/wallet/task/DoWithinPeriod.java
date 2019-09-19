package wallet.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoWithinPeriod extends Task {

    private Date dateStart;
    private Date dateEnd;

    /**
     * Constructs the DoWithinPeriod Object.
     *
     * @param description Description of the DoWithinPeriod Object.
     * @param dateStart Start date of the DoWithinPeriod Object.
     * @param dateEnd End date of the DowWithinPeriod Object.
     */
    public DoWithinPeriod(String description, Date dateStart, Date dateEnd) {
        super(description);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Concatenates all the fields into a String.
     *
     * @return The concatenated String.
     */
    @Override
    public String toString() {
        return "[DW]" + super.toString() + " (from: " + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateStart)
                + " to: " + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateEnd) + ")";
    }

    /**
     * Outputs the string with the correct format for writing to output file.
     *
     * @return The string formatted for writing to output file.
     */
    @Override
    public String writeToFile() {
        return "DW," + super.writeToFile() + "," + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateStart)
                + "," + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateEnd);
    }
}

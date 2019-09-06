import java.io.Serializable;
import java.util.List;

public class DateTime implements Serializable {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    /**
     * Create a DateTime object from user input.
     *
     * @param tokens tokenized user input
     * @throws DukeException error if user input is invalid
     */
    public DateTime(List<String> tokens) throws DukeException {
        try {
            String[] date = tokens.get(0).split("/");

            this.day = Integer.parseInt(date[0]);
            this.month = Integer.parseInt(date[1]);
            this.year = Integer.parseInt(date[2]);

            this.hour = Integer.parseInt(tokens.get(1)) / 100;
            this.minute = Integer.parseInt(tokens.get(1)) % 100;

            if (this.day < 0 || this.month < 0 || this.year < 0
                    || this.day > 31 || this.month > 12 || this.hour < 0
                    || this.minute < 0 || this.hour > 23 || this.minute > 59 || this.year < 1000) {
                throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
        }
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d %02d%02d", this.day, this.month, this.year, this.hour, this.minute);
    }
}

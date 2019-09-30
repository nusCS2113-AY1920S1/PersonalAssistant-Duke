package Tasks;

import UI.Ui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Date;

public class Timebound extends Task {
    public LocalDate dateStart;
    public LocalDate dateEnd;

    public Timebound (String description, String period) {
        super(description);

        try {
            DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String[] date = period.split(" and ");

            this.dateStart = LocalDate.parse(date[0], fmtED);
            this.dateEnd = LocalDate.parse(date[1], fmtED);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            Ui.showEventDateFormatError();
        }
    }
    @Override
    public String toString() {
        String dateStartString = dateStart.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));// You can change to this format
        String dateEndString = dateEnd.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));// You can change to this format
        return "P"+ " | " + super.getStatusIcon() + " | " + super.description + " | " + "between: " + dateStartString + " and " + dateEndString;
    }

    @Override

    public String listFormat(){
        String dateStartString = dateStart.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));// You can change to this format
        String dateEndString = dateEnd.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));// You can change to this format

        return "[P]" + "[" + super.getStatusIcon() + "] " + super.description + "(between: " + dateStartString + " and " + dateEndString + ")";
    }
}
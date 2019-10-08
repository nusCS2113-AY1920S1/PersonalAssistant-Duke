import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking /*extends Facility*/ {


    protected LocalDateTime dateTime;

    protected LocalDateTime dateTimeStart;

    protected LocalDateTime dateTimeEnd;

    protected String venue;

    protected int pax;

    protected String description;

    /**
     * Facility.Booking constructor to make booking
     *
     * @param roomcode      the specific room code
     * @param dateTimeStart when you are booking the facility
     * @param dateTimeEnd   when your booked period ends
     * @param people        how many people you are accommodating
     * @param description   what you are going to use the room for
     */
    public Booking(String roomcode, String dateTimeStart, String dateTimeEnd, int people, String description) {
        this.venue = roomcode;
        //this.pax = people;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HH:mm");
        //this.dateTime = LocalDateTime.parse(timing, formatter); //add start time and end time
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, formatterEnd);
        this.description = description;

    }

    public Booking(String venue, String user, String description, String dateTimeStart, String dateTimeEnd) {
        this.venue =  venue;
        this.description = description;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HH:mm");
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatter);
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, formatterEnd);

    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        return venue + " " + dateTime.format(formatter) + " " + pax;
    }



    public String toWriteFile() {
        //int boolToInt = isDone ? 1 : 0;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTimeStart = dateTimeStart.format(formatter);
        String formattedDateTimeEnd = dateTimeEnd.format(formatter);
        return this.venue + " | " + "user" + " | " + this.description + "\n" + formattedDateTimeStart + " | "
                + formattedDateTimeEnd + "\n";
    }
}

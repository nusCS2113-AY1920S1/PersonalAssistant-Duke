import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking /*extends Facility*/ {


    protected LocalDateTime dateTime;

    protected String venue;

    protected int pax;

    protected String description;

    /**
     * Facility.Booking constructor to make booking
     * @param roomcode the specific room code
     * @param timing when you are booking the facility
     * @param people how many people you are accommodating
     */
    public Booking (String roomcode, String timing, String people) {

        this.venue = roomcode;
        this.pax = Integer.parseInt(people);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        this.dateTime = LocalDateTime.parse(timing, formatter); //add start time and end time
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        return venue + " " + dateTime.format(formatter) + " " + pax;
    }
}

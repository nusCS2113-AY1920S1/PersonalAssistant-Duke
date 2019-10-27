package booking;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class Booking {

    protected LocalDateTime dateTimeStart;
    protected LocalDate dateStart;
    protected LocalTime timeEnd;
    protected String venue;
    protected String name;
    private String description;
    private String status;

    /**
     * Facility.booking.Booking constructor to make booking
     *
     * @param username      the requestor
     * @param roomcode      the specific room code
     * @param description   what you are going to use the room for
     * @param dateTimeStart when you are booking the facility
     * @param dateTimeEnd   when your booked period ends
     */
    public Booking(String username, String roomcode, String description, String dateTimeStart, String dateTimeEnd) {
        this.venue = roomcode;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
        this.dateStart = this.dateTimeStart.toLocalDate();
        this.timeEnd = LocalTime.parse(dateTimeEnd, formatterEnd);
        this.description = description;
        this.name = username;
        this.status = "P";

    }

    /*
    public Booking (String roomcode, String username, String description, long atStart, long atEnd) {
        this.venue =  roomcode;
        this.description = description;
        Instant instantStart = Instant.ofEpochMilli(atStart);
        Instant instantEnd = Instant.ofEpochMilli(atEnd);
        this.dateTimeStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.dateTimeEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalDateTime();

    }*/

    /**
     * Facility.booking.Booking constructor to generate booking entry from file
     * @param username the requestor
     * @param roomcode the venue
     * @param description for what use
     * @param atStart start date and time
     * @param atEnd end date and time
     * @param status request status
     */
    public Booking(String username, String roomcode, String description, String atStart, String atEnd, String status) {
        this.venue = roomcode;
        this.description = description;
        Instant instantStart = Instant.ofEpochMilli(Long.parseLong(atStart));
        Instant instantEnd = Instant.ofEpochMilli(Long.parseLong(atEnd));
        this.dateTimeStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.timeEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalTime();
        this.name = username;
        this.status = status;
    }

    /**
     * String version of the booking entry.
     * @return booking entry string version
     */
    public String toString() {
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        return name + " " + venue + " " + dateTimeStart.format(formatterStart) + " to "
                + timeEnd.format(formatterEnd) + " " +  status;
    }

    /**
     * Version of entry to be stored in file
     * @return
     */
    public String toWriteFile() {
        return this.name + " | " + this.venue + " | " + this.description + " | "
                + this.dateTimeStart.getLong(ChronoField.EPOCH_DAY) + " | "
                + this.timeEnd.getLong(ChronoField.MINUTE_OF_HOUR) + " | " + this.status + "\n";
    }

    public LocalDateTime getDateTimeStart() {
        return this.dateTimeStart;
    }

    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setStatus(String newstatus) {
        this.status = newstatus;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public Month getStartMonth() {
        return dateStart.getMonth();
    }


}

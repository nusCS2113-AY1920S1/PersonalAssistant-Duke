package booking;

import exception.DukeException;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Booking {

    protected LocalDateTime dateTimeStart;
    protected LocalDate dateStart;
    protected LocalTime timeEnd;
    protected String venue;
    protected String name;
    private String description;
    private String status;
    private String approvedBy;

    /**
     * Facility.booking.Booking constructor to make booking
     * @param username      the requestor
     * @param roomcode      the specific room code
     * @param description   what you are going to use the room for
     * @param dateTimeStart when you are booking the facility
     * @param dateTimeEnd   when your booked period ends
     */
    public Booking(String username, String roomcode, String description, String dateTimeStart,
                   String dateTimeEnd) throws DukeException {
        this.venue = roomcode;
        try {
            DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
            this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
            this.dateStart = this.dateTimeStart.toLocalDate();
            this.timeEnd = LocalTime.parse(dateTimeEnd, formatterEnd);
            this.description = description;
            this.name = username;
            this.status = "P";
            this.approvedBy = null;
        } catch (DateTimeParseException error) {
            throw new DukeException("Not able to parse the date for all patterns given, "
        + "please use this format: add NAME DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
        + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM");
        }
    }

    /**
     * Facility.booking.Booking constructor to generate booking entry from file
     * @param username the requestor
     * @param roomcode the venue
     * @param description for what use
     * @param atStart start date and time
     * @param atEnd end date and time
     * @param status request status
     */
    public Booking(String username, String roomcode, String description, String atStart,
                   String atEnd, String status, String approvedBy) {
        this.venue = roomcode;
        this.description = description;
        Date storedStart = new Date(Long.parseLong(atStart));
        Date storedEnd = new Date(Long.parseLong(atEnd));
        this.dateTimeStart = storedStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.dateStart = storedStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.timeEnd = storedEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        this.name = username;
        this.status = status;
        this.approvedBy = approvedBy;
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
     * Version of entry to be stored in file.
     * @return String entry for file
     */
    public String toWriteFile() {
        Date storeTimeStart = Date.from(dateTimeStart.atZone(ZoneId.systemDefault()).toInstant());
        Instant timeEndInstant = timeEnd.atDate(dateStart).atZone(ZoneId.systemDefault()).toInstant();
        Date storeTimeEnd = Date.from(timeEndInstant);
        return this.name + " | " + this.venue + " | " + this.description + " | "
                + storeTimeStart.getTime() + " | "
                + storeTimeEnd.getTime() + " | " + this.status + " | " + this.approvedBy + "\n";
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

    public int getStartMonth() {
        return dateStart.getMonth().getValue();
    }

    public LocalTime getTimeStart() {
        return dateTimeStart.toLocalTime();
    }

    public int getStartYear() {
        return dateStart.getYear();
    }

    public String getDescription() {
        return description;
    }

    public void approveStatus(String username) {
        status = "A";
        approvedBy = username;
    }

    public void rejectStatus(String username) {
        status = "R";
        approvedBy = username;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

}

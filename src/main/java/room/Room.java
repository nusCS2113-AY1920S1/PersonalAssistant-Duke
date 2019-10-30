package room;

import java.time.Month;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Room {
    protected String roomcode;
    protected LocalDateTime dateTimeStart;
    protected LocalDate date;
    protected LocalTime timeEnd;

    //@@author zkchang97
    /**
     * Public constructor, returns the details of the room to be added.
     * @param roomcode Room code
     * @param dateTimeStart Available booking date and starting time of the room
     * @param dateTimeEnd Available booking date and ending time of the room
     */
    public Room(String roomcode, String dateTimeStart, String dateTimeEnd) {
        this.roomcode = roomcode;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
        this.date = this.dateTimeStart.toLocalDate();
        this.timeEnd = LocalTime.parse(dateTimeEnd, formatterEnd);
    }

    /**
     * Converts text file to Room object.
     * @param roomcode room code
     * @param longTimeStart Available booking date and starting time of the room
     * @param longTimeEnd Available booking date and ending time of the room
     */
    public Room(String roomcode, Long longTimeStart, Long longTimeEnd) {
        this.roomcode = roomcode;
        Date storedTimeStart = new Date(longTimeStart);
        Date storedTimeEnd = new Date(longTimeEnd);
        this.dateTimeStart = storedTimeStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.date = this.dateTimeStart.toLocalDate();
        this.timeEnd = storedTimeEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Returns the format written into the room list.
     * @return returns the statement and symbols as shown in room list
     */
    public String toString() {
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        return (this.roomcode + " " + dateTimeStart.format(formatterStart) + " to " + timeEnd.format(formatterEnd));
    }

    /**
     * Returns the format written into the text file.
     * @return returns the statement and symbols as shown in the text file
     */
    public String toWriteFile() {
        Date storeTimeStart = Date.from(dateTimeStart.atZone(ZoneId.systemDefault()).toInstant());
        Instant timeEndInstant = timeEnd.atDate(date).atZone(ZoneId.systemDefault()).toInstant();
        Date storeTimeEnd = Date.from(timeEndInstant);
        return (this.roomcode + " | " + storeTimeStart.getTime() + " | " + storeTimeEnd.getTime() + " | " + "stored\n");
    }

    /**
     * Getter, returns room code.
     * @return the room code
     */
    public String getRoomcode() {
        return this.roomcode;
    }

    public LocalDateTime getDateTimeStart() {
        return this.dateTimeStart;
    }

    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }

    public LocalDate getDateStart() {
        return date;
    }

    public Month getStartMonth() {
        return date.getMonth();
    }

    public LocalTime getTimeStart() {
        return dateTimeStart.toLocalTime();
    }
}

package room;
import java.util.Date;

public abstract class Room {
    protected String roomcode;
    protected boolean isBooked;
    protected Date dateTime;

    public Room(String roomcode) {
        this.roomcode = roomcode;
        this.isBooked = false;
    }

    public String getStatusIcon() {
        return (isBooked ? "✓" : "✗");
    }

    public void markAsBooked() {
        this.isBooked = true;
        System.out.println("Nice! The room is now booked!");
        System.out.println("    " + this.toString());
    }

    @Override
    public String toString() {
        return "[ " + this.getStatusIcon() + "] " + this.roomcode;
    }

    public String toWriteFile() {
        return this.roomcode;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}

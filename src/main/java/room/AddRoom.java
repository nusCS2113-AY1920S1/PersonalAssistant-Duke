package room;

public class AddRoom {

    protected String roomcode;
    private String date;
    private String timeslot;

    public AddRoom(String roomcode, String date, String timeslot) {
        this.roomcode = roomcode;
        this.date = date;
        this.timeslot = timeslot;
    }

    /*
    public AddRoom(String isBooked, String roomcode, String date, String timeslot) {
        this.roomcode = roomcode
        this.date = date;
        this.timeslot = timeslot;
        this.isBooked = isBooked.equals("1");
    }
     */

    public String toString() {
        return ("[RM]" + this.roomcode + " date: " + this.date + " timeslot: " + this.timeslot);
    }

    public String toWriteFile() {
        return ("RM | " + this.roomcode + " | " + this.date + " | " + this.timeslot);
    }

    public String getRoomcode() {
        return this.roomcode;
    }
}

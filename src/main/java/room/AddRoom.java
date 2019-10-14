package room;

public class AddRoom extends Room {

    private String date;
    private String timeslot;

    public AddRoom(String roomcode, String date, String timeslot) {
        super(roomcode);
        this.date = date;
        this.timeslot = timeslot;
    }

    public AddRoom(String isBooked, String roomcode, String date, String timeslot) {
        super(roomcode);
        this.date = date;
        this.timeslot = timeslot;
        this.isBooked = isBooked.equals("1");
    }

    @Override
    public String toString() {
        return ("[RM]" + super.toString() + " date: " + this.date + " timeslot: " + this.timeslot);
    }

    @Override
    public String toWriteFile() {
        int boolToInt = isBooked ? 1 : 0;
        return ("RM | " + boolToInt + " | " + this.roomcode + " | " + this.date + " | " + this.timeslot);
    }

}

package room;

public class AddRoom {
    protected String roomcode;
    private String date;
    private String timeslot;

    /**
     * Public constructor, returns the details of the room to be added
     * @param roomcode Room code
     * @param date Available booking date of the room
     * @param timeslot Available booking time slot of the room
     */
    public AddRoom(String roomcode, String date, String timeslot) {
        this.roomcode = roomcode;
        this.date = date;
        this.timeslot = timeslot;
    }

    /**
     * @return returns the statement and symbols as shown in room list
     */
    public String toString() {
        return ("[RM]" + this.roomcode + " date: " + this.date + " timeslot: " + this.timeslot);
    }

    /**
     * @return returns the statement and symbols as shown in the text file
     */
    public String toWriteFile() {
        return ("RM | " + this.roomcode + " | " + this.date + " | " + this.timeslot);
    }

    /**
     * Getter, returns room code
     * @return the room code
     */
    public String getRoomcode() {
        return this.roomcode;
    }
}

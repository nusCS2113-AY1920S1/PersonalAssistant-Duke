package room;

public class Room {
    protected String roomcode;
    protected int capacity;

    //@@author AmosChan97
    /**
     * Public constructor, returns the details of the room to be added.
     * @param roomcode Room code
     * @param capacity number of people able to use the room at the same time
     */
    public Room(String roomcode, int capacity) {
        this.roomcode = roomcode;
        this.capacity = capacity;
    }

    /**
     * Converts text file to Room object.
     * @param roomcode room code
     * @param capacity number of people that can use the room at the same time
     */
    public Room(String roomcode, String capacity) {
        this.roomcode = roomcode;
        this.capacity = Integer.parseInt(capacity);
    }

    /**
     * Returns the format written into the room list.
     * @return returns the statement and symbols as shown in room list
     */
    public String toString() {
        return (this.roomcode + " capacity: " + capacity);
    }

    /**
     * Returns the format written into the text file.
     * @return returns the statement and symbols as shown in the text file
     */
    public String toWriteFile() {
        return (this.roomcode + " | " + capacity + "\n");
    }

    /**
     * Getter, returns room code.
     * @return the room code
     */
    public String getRoomcode() {
        return this.roomcode;
    }

    public int getCapacity() {
        return capacity;
    }
}

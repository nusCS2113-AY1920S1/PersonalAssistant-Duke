package duke.patient;

/**
 * Represents a Patient.
 */
public class Patient {
    private int id = 0;
    private String nric;
    private String name;
    private String remark;
    private String room;

    /**
     * .
     *
     * @param id     .
     * @param name   .
     * @param nric   .
     * @param room   .
     * @param remark .
     */
    public Patient(int id, String name, String nric, String room, String remark) {
        this.id = id;
        this.name = name;
        this.nric = nric;
        this.remark = remark;
        this.room = room;
    }

    /**
     * Initialises the minimum fields required to setup a Patient.
     *
     * @param name   A String that represent the full name of the patient.
     * @param remark Remark leaves by nurses.
     */
    public Patient(String name, String nric, String room, String remark) {
        this.name = name;
        this.nric = nric;
        this.remark = remark;
        this.room = room;
    }

    /**
     * .
     *
     * @return .
     */
    public String getName() {
        return name; //return tick or X symbols
    }

    /**
     * .
     *
     * @return .
     */
    public int getID() {
        return id;
    }

    /**
     * .
     *
     * @return .
     */
    public String getRemark() {
        return remark;
    }

    /**
     * .
     *
     * @return .
     */
    public String getRoom() {
        return room;
    }

    /**
     * .
     *
     * @return .
     */
    public String getNric() {
        return nric;
    }

    /**
     * .
     *
     * @param id .
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * .
     *
     * @param name .
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * .
     *
     * @param nric .
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * .
     *
     * @param room .
     */
    public void setRoom(String room) {
        this.room = room;
    }
}
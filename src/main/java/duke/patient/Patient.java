package duke.patient;
/**
 * Represents a Patient.
 */
public class Patient {
    private int id;
    private String name;
    private String preference;
    private String remark;
    private boolean isHospitalised;
    private String room;

    /**
     * Initialises the minimum fields required to setup a Patient.
     *
     * @param id A unique integer represents id of the patient.
     * @param name A String that represent the full name of the patient.
     * @param isHospitalised A boolean value that represents the hospitalised data of the patient.
     * @param remark Remark leaves by nurses.
     * @param preference A string that represents the daily preference of the patient.
     */
    public Patient(int id, String name, boolean isHospitalised, String room, String remark, String preference) {
        this.id = id;
        this.name = name;
        this.isHospitalised = isHospitalised;
        this.remark = remark;
        this.room = room;
        this.preference = preference;
    }

    public String getName() {
        return name; //return tick or X symbols
    }

    public int getID(){
        return id;
    }

    public boolean isHospitalised(){
        return isHospitalised;
    }

    public String getRemark(){
        return remark;
    }

    public String getPreference(){
        return preference;
    }

    public String getRoom(){
        return room;
    }

    public void checkOut() {
        isHospitalised = false;
    }
}
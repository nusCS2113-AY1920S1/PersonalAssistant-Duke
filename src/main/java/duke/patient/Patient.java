package duke.patient;

import java.util.ArrayList;

/**
 * Represents a Patient.
 */
public class Patient {
    private int id;
    private String name;
    private String preference;
    private String remark;
    private ArrayList<String> remarkList = new ArrayList<String>();
    private boolean isHospitalised;
    private String room;

    /**
     * Initialises the minimum fields required to setup a Patient.
     *
     * @param id A unique integer represents id of the patient.
     * @param name A String that represent the full name of the patient.
     * @param isHospitalised A boolean value that represents the hospitalised data of the patient.
     * @param remark Remark leaves by nurses.
     */
    public Patient(int id, String name, String room, String remark, boolean isHospitalised) {
        this.id = id;
        this.name = name;
        this.isHospitalised = isHospitalised;
        this.remark = remark;
        this.room = room;
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

    public String getRoom(){
        return room;
    }

    public void checkOut() {
        isHospitalised = false;
    }
}
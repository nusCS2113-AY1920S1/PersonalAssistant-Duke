package duke.patient;

import java.util.ArrayList;

/**
 * Represents a Patient.
 */
public class Patient {
    private int id = 0;
    private String nric;
    private String name;
    private String remark;
    private ArrayList<String> remarkList = new ArrayList<String>();
    private String room;

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

    public String getName() {
        return name; //return tick or X symbols
    }

    public int getID() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public String getRoom() {
        return room;
    }

    public String getNRIC() {
        return nric;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public void setRoom(String room) {
        this.room = room;
    }



}
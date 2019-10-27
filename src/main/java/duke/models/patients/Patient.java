package duke.models.patients;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a Patient.
 */
public class Patient {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty name;
    private SimpleStringProperty nric;
    private SimpleStringProperty room;
    private SimpleStringProperty remark;

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
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.nric = new SimpleStringProperty(nric);
        this.room = new SimpleStringProperty(room);
        this.remark = new SimpleStringProperty(remark);
    }

    /**
     * Initialises the minimum fields required to setup a Patient.
     *
     * @param name   A String that represent the full name of the patient.
     * @param remark Remark leaves by nurses.
     */
    public Patient(String name, String nric, String room, String remark) {
        this.name = new SimpleStringProperty(name);
        this.nric = new SimpleStringProperty(nric);
        this.room = new SimpleStringProperty(room);
        this.remark = new SimpleStringProperty(remark);
    }

    /**
     * .
     *
     * @return .
     */
    public String getName() {
        return name.get(); //return tick or X symbols
    }

    /**
     * .
     *
     * @return .
     */
    public int getId() {
        return id.get();
    }

    /**
     * .
     *
     * @return .
     */
    public String getRemark() {
        return remark.get();
    }

    /**
     * .
     *
     * @return .
     */
    public String getRoom() {
        return room.get();
    }

    /**
     * .
     *
     * @return .
     */
    public String getNric() {
        return nric.get();
    }

    /**
     * .
     *
     * @param id .
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * .
     *
     * @param name .
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * .
     *
     * @param nric .
     */
    public void setNric(String nric) {
        this.nric.set(nric);
    }

    /**
     * .
     *
     * @param room .
     */
    public void setRoom(String room) {
        this.room.set(room);
    }
}
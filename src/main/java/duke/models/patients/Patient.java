//@@author kkeejjuunn

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
     * Initialises the fields of a Patient.
     *
     * @param id     contains the patient's id which is an integer.
     * @param name   contains the patient's name which is a string.
     * @param nric   contains the patient's nric which is a string.
     * @param room   contains the patient's room number which is a string.
     * @param remark contains remark of the patient which is a string.
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
     * @param name   contains the patient's name which is a string.
     * @param nric   contains the patient's nric which is a string.
     * @param room   contains the patient's room number which is a string.
     * @param remark contains remark of the patient which is a string.
     */
    public Patient(String name, String nric, String room, String remark) {
        this.name = new SimpleStringProperty(name);
        this.nric = new SimpleStringProperty(nric);
        this.room = new SimpleStringProperty(room);
        this.remark = new SimpleStringProperty(remark);
    }

    /**
     * It retrieves the patient's name.
     *
     * @return the patient's name.
     */
    public String getName() {
        return name.get();
    }

    /**
     * It retrieves the patient's id.
     *
     * @return the patient's id.
     */
    public int getId() {
        return id.get();
    }

    /**
     * It retrieves the remark of the patient.
     *
     * @return the remark of the patient.
     */
    public String getRemark() {
        return remark.get();
    }

    /**
     * It retrieves the patient's room number.
     *
     * @return the patient's room number.
     */
    public String getRoom() {
        return room.get();
    }

    /**
     * It retrieves the nric of the patient.
     *
     * @return nric of the patient.
     */
    public String getNric() {
        return nric.get();
    }

    /**
     * It sets the id of the patient.
     *
     * @param id contains the id of the patient which is an integer.
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * It sets the name of the patient.
     *
     * @param name contains the name of the patient which is a string.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * It sets the nric of the patient.
     *
     * @param nric contains the nric of the patient which is a string.
     */
    public void setNric(String nric) {
        this.nric.set(nric);
    }

    /**
     * It sets the room number of the patient.
     *
     * @param room contains the room number of the patient which is a string.
     */
    public void setRoom(String room) {
        this.room.set(room);
    }

    /**
     * It sets the remark of the patient.
     *
     * @param remark contains the remark of the patient which is a string.
     */
    public void setRemark(String remark) {
        this.remark.set(remark);
    }
}
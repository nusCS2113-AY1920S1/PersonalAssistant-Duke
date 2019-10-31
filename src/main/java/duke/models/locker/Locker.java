package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.models.student.Student;
import duke.models.tag.Tag;


import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Locker {
    private final SerialNumber serialNumber;
    private final Address address;
    private final Zone zone;
    private final Tag tag;

    /**
     * Locker stores all the information regarding the status of the locker.
     *
     * @param serialNumber stores the serial numbers associated with each locker
     * @param address      stores the location of the locker
     * @param zone         stores the zone to which the locker belongs
     * @param tag          instance of the class Tag that stores the status of the locker
     */

    @JsonCreator
    public Locker(@JsonProperty("serial") SerialNumber serialNumber,
                  @JsonProperty("address") Address address,
                  @JsonProperty("zone") Zone zone,
                  @JsonProperty("tag") Tag tag) {
        requireNonNull(serialNumber);
        requireNonNull(address);
        requireNonNull(zone);
        requireNonNull(tag);
        this.serialNumber = serialNumber;
        this.address = address;
        this.zone = zone;
        this.tag = tag;
    }

    public void setTagAs(String tagName) {
        tag.tagName = tagName;
    }

    public void setStatusAsBroken() {
        tag.tagName = Tag.BROKEN;
    }

    public void setStatusAsUnAuthorized() {
        tag.tagName = Tag.UNAUTHORIZED;
    }

    public void setStatusAsNotInUse() {
        tag.tagName = Tag.NOT_IN_USE;
    }

    public void setStatusAsInUse() {
        tag.tagName = Tag.IN_USE;
    }

    /**
     * checks if the locker is already present in the lockerList.
     * @param other to check if the object is already present
     * @return true if the object is present, false otherwise
     */
    public boolean isPresent(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Locker)) {
            return false;
        }

        return this.getSerialNumber().equals(((Locker) other).getSerialNumber());
    }

    /**
     * This function is used to convert the locker info into displayable strings.
     *
     * @return a string in a format that can be used for printing out the current locker
     */
    public String toString() {
        return "Locker #" + serialNumber.getSerialNumberForLocker() + ": " + "Area: " + address.getAddress()
                + " Zone: " + zone.getZone()
                + " [" + getTag().tagName + "]";
    }

    @JsonGetter("tag")
    public Tag getTag() {
        return tag;
    }

    @JsonGetter("serial")
    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    @JsonGetter("address")
    public Address getAddress() {
        return address;
    }

    @JsonGetter("zone")
    public Zone getZone() {
        return zone;
    }

    /* We need to override function equals() and hashCode() in order to account
       for user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; //both objects are the same
        }

        if (!(other instanceof Locker)) {
            return false; //handles all the cases for null and irrelevant references
        }

        Locker otherLocker = (Locker) other;
        return otherLocker.getSerialNumber().equals(this.getSerialNumber())
                && otherLocker.getAddress().equals(this.getAddress())
                && otherLocker.getZone().equals(this.getZone())
                && otherLocker.getTag().equals(this.getTag());//handles checks for equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, address, zone, tag);
    }
}

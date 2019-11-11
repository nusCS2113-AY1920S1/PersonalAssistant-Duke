package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;
import duke.logic.commands.FindCommand;
import duke.models.student.Student;
import duke.models.tag.Tag;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Objects;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;

/**
 * Stores all the information pertaining to a locker.
 */
public class Locker {
    private final SerialNumber serialNumber;
    private final Address address;
    private final Zone zone;
    private Tag tag;
    private Usage usage;

    /**
     * Stores all the information regarding the status of the locker.
     * @param serialNumber stores the serial numbers associated with each locker
     * @param address      stores the location of the locker
     * @param zone         stores the zone to which the locker belongs
     * @param tag          instance of the class Tag that stores the status of the locker
     */
    @JsonCreator
    public Locker(@JsonProperty("LockerSerial") SerialNumber serialNumber,
                  @JsonProperty("LockerAddress") Address address,
                  @JsonProperty("LockerZone") Zone zone,
                  @JsonProperty("LockerTag") Tag tag,
                  @JsonProperty("Usage") Usage usage) {
        requireNonNull(serialNumber);
        requireNonNull(address);
        requireNonNull(zone);
        requireNonNull(tag);
        this.serialNumber = serialNumber;
        this.address = address;
        this.zone = zone;
        this.tag = tag;
        this.usage = usage;
    }

    public void setStatusAsInUse() {
        tag.tagName = Tag.IN_USE;
    }

    /**
     * Checks if the locker is currently being used i.e checks if it is currently subscribed
     * by a student.
     * @return true if it is in-use
     * @throws DukeException if the tag is invalid
     */
    public boolean isOfTypeInUse() throws DukeException {
        return getUsage().isPresent() && getTag().equals(new Tag(Tag.IN_USE));
    }

    /**
     * Checks whether the locker as the same status as {@code checkTag} .
     */
    public boolean hasSameTagAs(Tag checkTag) {
        return getTag().equals(checkTag);
    }

    /**
     * Checks if the locker is of invalid type as per the constraints.
     * @return true if the locker is of invalid type, false otherwise
     * @throws DukeException if the Tag is invalid
     */
    public boolean isOfInValidType() throws DukeException {
        Tag testInUse = new Tag(Tag.IN_USE);
        return (hasSameTagAs(testInUse) && getUsage().isEmpty())
                || (!hasSameTagAs(testInUse) && getUsage().isPresent());
    }

    /**
     * Checks if the locker is already present in the lockerList.
     * @param other to check if the object is already present
     * @return true if the object is present, false otherwise
     */
    public boolean hasSameSerialNumber(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Locker)) {
            return false;
        }

        return this.getSerialNumber().equals(((Locker) other).getSerialNumber());
    }

    /**
     * Converts the locker info into displayable strings.
     * @return a string in a format that can be used for printing out the current locker
     */
    public String toString() {
        return " Locker #" + serialNumber.getSerialNumberForLocker() + ": " + "Area: " + address.getAddress()
                + " Zone: " + zone.getZone()
                + " [" + getTag().tagName + "]";
    }

    /**
     * This function is used to convert the serial number of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the serial number of a locker
     */
    public String serialNumberToString() {
        return serialNumber.getSerialNumberForLocker();
    }

    /**
     * This function is used to convert the tag of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the tag of a locker
     */
    public String tagToString() {
        return getTag().tagName;
    }

    /**
     * This function is used to convert the located zone of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the located zone of a locker
     */
    public String zoneToString() {
        return zone.getZone();
    }

    /**
     * This function is used to convert the located area of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the located area of a locker
     */
    public String areaToString() {
        return address.getAddress();
    }

    @JsonGetter("LockerTag")
    public Tag getTag() {
        return tag;
    }

    @JsonGetter("LockerSerial")
    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    @JsonGetter("LockerAddress")
    public Address getAddress() {
        return address;
    }

    @JsonGetter("LockerZone")
    public Zone getZone() {
        return zone;
    }

    @JsonGetter("Usage")
    public Optional<Usage> getUsage() {
        return Optional.ofNullable(usage);
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    /* Need to override function equals() and hashCode() in order to account
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
                && otherLocker.getTag().equals(this.getTag())
                && otherLocker.getUsage().equals(this.getUsage());//handles checks for equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, address, zone, tag);
    }

    /**
     * This function is used to compare the locker info with a locker that was searched.
     * This is used in conjunction with Java in-streams.
     * @param findLocker has all or some the attributes the locker that was searched for.
     * @param findStudent has all or some the details of the student that was searched for.
     * @return refers to a boolean value to check if the comparison was true or false.
     */

    public boolean compare(FindCommand.FindLocker findLocker, FindCommand.FindStudent findStudent) {

        if (findLocker.getSerialNumber() != null && findLocker.getSerialNumber().equals(this.getSerialNumber())) {
            return true;
        } else if (findLocker.getAddress() != null && findLocker.getAddress().equals(this.getAddress())) {
            return true;
        } else if (findLocker.getZone() != null && findLocker.getZone().equals(this.getZone())) {
            return true;
        } else if (findLocker.getTag() != null && findLocker.getTag().equals(this.getTag())) {
            return true;
        }

        try {

            Student student = this.usage.getStudent();

            if (findStudent.getName() != null && findStudent.getName().equals(student.getName())) {
                return true;
            } else if (findStudent.getStudentID() != null && findStudent.getStudentID()
                    .equals(student.getStudentId())) {
                return true;
            } else if (findStudent.getEmail() != null && findStudent.getEmail()
                    .equals(student.getEmail())) {
                return true;
            } else if (findStudent.getMajor() != null && findStudent.getMajor()
                    .equals(student.getMajor())) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

    }

    /**
     * This function checks if the expiry date is within 3 days of the current date.
     * @param now which refers to the current date.
     * @return refers to a boolean value to check if the comparison was true or false.
     */

    public boolean findExpiryDate(LocalDate now) {

        try {
            String endDate = this.usage.getEndDate().getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            LocalDate expiryDate = LocalDate.parse(endDate,formatter);
            int daysBetween = (int) DAYS.between(now, (Temporal) expiryDate);

            if (daysBetween <= 7) {

                return true;

            } else {

                return false;

            }
        } catch (NullPointerException e) {

            return false;

        }
    }

}

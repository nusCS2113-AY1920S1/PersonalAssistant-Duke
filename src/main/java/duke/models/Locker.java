package duke.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Locker {
    private int serialNumber;
    private String address;
    private String zone;
    private Tag tag;

    /**
     * Locker stores all the information regarding the status of the locker.
     * @param serialNumber stores the serial numbers associated with each locker
     * @param address stores the location of the locker
     * @param zone stores the zone to which the locker belongs
     * @param tag instance of the class Tag that stores the status of the locker
     */

    @JsonCreator
    public Locker(@JsonProperty("serial") int serialNumber,@JsonProperty("address") String address,
                  @JsonProperty("zone") String zone,@JsonProperty("tag") Tag tag) {
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

    public String toString() {
        return "Locker #" + getSerialNumber() + ": " + "Area: " + address + " Zone: " + zone
                + " [" + getTag().tagName + "]";
    }

    @JsonGetter("tag")
    public Tag getTag() {
        return tag;
    }

    @JsonGetter("serialNumber")
    public int getSerialNumber() {
        return serialNumber;
    }

    @JsonGetter("address")
    public String getAddress() {
        return address;
    }

    @JsonGetter("zone")
    public String getZone() {
        return zone;
    }


}

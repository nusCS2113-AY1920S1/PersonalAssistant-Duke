package duke.models;

import duke.models.locker.Address;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.locker.Zone;
import duke.models.tag.Tag;

import java.util.Comparator;

public class ObjectComparator {
    private SerialNumber serialNumber;
    private Address address;
    private Zone zone;
    private Tag tag;

    /**
     * This constructor instantiates the ObjectComparator object.
     * @param locker stores the locker attributes to be compared.
     */

    public ObjectComparator(Locker locker) {
        this.serialNumber = locker.getSerialNumber();
        this.address = locker.getAddress();
        this.zone = locker.getZone();
        this.tag = locker.getTag();
    }

    public static Comparator<Locker> SerialNumberComparatorAsc = new Comparator<Locker>() {
        @Override
        public int compare(Locker sn1, Locker sn2) {

            String serialNumber1 = sn1.getSerialNumber().getSerialNumberForLocker();
            String serialNumber2 = sn2.getSerialNumber().getSerialNumberForLocker();

            //ascending order
            return serialNumber1.compareTo(serialNumber2);

        }
    };

    public static Comparator<Locker> AddressComparatorAsc = new Comparator<Locker>() {
        @Override
        public int compare(Locker a1, Locker a2) {

            String address1 = a1.getAddress().getAddress();
            String address2 = a2.getAddress().getAddress();

            //ascending order
            return address1.compareTo(address2);

        }

    };

    public static Comparator<Locker> ZoneComparatorAsc = new Comparator<Locker>() {
        @Override
        public int compare(Locker z1, Locker z2) {

            String zone1 = z1.getZone().getZone();
            String zone2 = z2.getZone().getZone();

            //ascending order
            return zone1.compareTo(zone2);

        }

    };

    public static Comparator<Locker> TagComparatorAsc = new Comparator<Locker>() {
        @Override
        public int compare(Locker t1, Locker t2) {

            String tag1 = t1.getTag().getTagName();
            String tag2 = t2.getTag().getTagName();

            //ascending order
            return tag1.compareTo(tag2);

        }

    };

    public static Comparator<Locker> SerialNumberComparatorDes = new Comparator<Locker>() {
        @Override
        public int compare(Locker sn1, Locker sn2) {

            String serialNumber1 = sn1.getSerialNumber().getSerialNumberForLocker();
            String serialNumber2 = sn2.getSerialNumber().getSerialNumberForLocker();

            //descending order
            return serialNumber2.compareTo(serialNumber1);

        }
    };

    public static Comparator<Locker> AddressComparatorDes = new Comparator<Locker>() {
        @Override
        public int compare(Locker a1, Locker a2) {

            String address1 = a1.getAddress().getAddress();
            String address2 = a2.getAddress().getAddress();

            //descending order
            return address2.compareTo(address1);

        }

    };

    public static Comparator<Locker> ZoneComparatorDes = new Comparator<Locker>() {
        @Override
        public int compare(Locker z1, Locker z2) {

            String zone1 = z1.getZone().getZone();
            String zone2 = z2.getZone().getZone();

            //descending order
            return zone2.compareTo(zone1);

        }

    };

    public static Comparator<Locker> TagComparatorDes = new Comparator<Locker>() {
        @Override
        public int compare(Locker t1, Locker t2) {

            String tag1 = t1.getTag().getTagName();
            String tag2 = t2.getTag().getTagName();

            //descending order
            return tag2.compareTo(tag1);

        }

    };

}


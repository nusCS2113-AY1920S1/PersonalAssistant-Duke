package rims.core;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;
import rims.resource.Item;
import rims.resource.Room;
import rims.exception.RimsException;

public class ResourceList {
    protected ArrayList<Resource> resources;

    public ResourceList(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public void add(Resource thisResource) {
        resources.add(thisResource);
    }

    public void deleteResourceByName(String resourceName) throws RimsException {
        boolean deleted = false;
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getName().equals(resourceName)) {
                resources.remove(i);
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            throw new RimsException("No such resource found!");
        }
    }

    public void deleteResourceById(int resourceId) throws RimsException {
        boolean deleted = false;
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getResourceId() == resourceId) {
                resources.remove(i);
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            throw new RimsException("No such resource ID found!");
        }
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public int size() {
        return resources.size();
    }

    public int generateResourceId() {
        for (int i = 0; i < size(); i++) {
            try {
                Resource thisResource = getResourceById(i);
            }
            catch (RimsException e) {
                return i;
            }
        }
        return size();
    }

    public Resource getResourceByIndex(int indexNo) {
        return resources.get(indexNo);
    }

    public Resource getResourceByName(String resourceName) throws RimsException {
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getName().equals(resourceName)) {
                return thisResource;
            }
        }
        throw new RimsException("No such resource!");
    }

    public Resource getResourceById(int resourceId) throws RimsException {
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getResourceId() == resourceId) {
                return thisResource;
            }
        }
        throw new RimsException("No such resource ID!");
    }

    public boolean isItem(String resourceName) throws RimsException {
        Resource thisResource = getResourceByName(resourceName);
        return (thisResource instanceof Item);
    }

    public boolean isRoom(String resourceName) throws RimsException {
        Resource thisResource = getResourceByName(resourceName);
        return (thisResource instanceof Room);
    }

    public ArrayList<Resource> getAllOfResource(String resourceName) {
        ArrayList<Resource> allOfResource = new ArrayList<Resource>();
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getName().equals(resourceName)) {
                allOfResource.add(thisResource);
            }
        }
        return allOfResource;
    }

    public int getNumberOfResource(String resourceName) {
        int number = 0;
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getName().equals(resourceName)) {
                number++;
            }
        }
        return number;
    }

    public int getAvailableNumberOfResource(String resourceName) {
        ArrayList<Resource> allOfResource = getAllOfResource(resourceName);
        int number = 0;
        for (int i = 0; i < allOfResource.size(); i++) {
            if (allOfResource.get(i).isCurrentlyAvailable()) {
                number++;
            }
        }
        return number;
    }

    // overloaded
    public int getAvailableNumberOfResource(String resourceName, Date dateFrom, Date dateTill) {
        ArrayList<Resource> allOfResource = getAllOfResource(resourceName);
        int number = 0;
        for (int i = 0; i < allOfResource.size(); i++) {
            if (allOfResource.get(i).isCurrentlyAvailable()) {
                number++;
            }
        }
        return number;
    }

    public int getBookedNumberOfResource(String resourceName) {
        ArrayList<Resource> allOfResource = getAllOfResource(resourceName);
        int number = 0;
        for (int i = 0; i < allOfResource.size(); i++) {
            if (!allOfResource.get(i).isCurrentlyAvailable()) {
                number++;
            }
        }
        return number;
    }

    public ReservationList getUserBookings(int userId) {
        ReservationList userBookings = new ReservationList();
        for (int i = 0; i < size(); i++) {
            ReservationList thisResourceUserReservations = getResourceByIndex(i).getUserReservations(userId);
            for (int j = 0; j < thisResourceUserReservations.size(); j++) {
                userBookings.add(thisResourceUserReservations.getReservationByIndex(j));
            }
        }
        return userBookings;
    }

    public int generateReservationId() {
        int reservationId = 0;
        for (int i = 0; i < size(); i++) {
            Resource thisResource = resources.get(i);
            reservationId += thisResource.getReservations().size();
        }
        return reservationId;
    }

    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

    public String dateToString(Date thisDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String stringDate = format.format(thisDate);
        return stringDate;
    }

    public String getDateToPrint(Date date) {
        DateFormat dayFormat = new SimpleDateFormat("d");
        int day = Integer.parseInt(dayFormat.format(date)) % 10;
        String suffix = day == 1 ? "st" : (day == 2 ? "nd" : (day == 3 ? "rd" : "th"));
        String stringDate = (new SimpleDateFormat("EEEEE, ")).format(date) + (dayFormat.format(date)) + suffix + " of " + (new SimpleDateFormat("MMMMM yyyy, hh:mm aaa")).format(date);
        return stringDate;
    }

}

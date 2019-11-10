package rims.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rims.core.Ui;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;
import rims.resource.Item;
import rims.resource.Room;
import rims.exception.RimsException;

//@@author rabhijit
/**
 * Stores an array of the Resources created thus far, as well as functions to
 * search, find for, create and delete Resources.
 */
public class ResourceList {
    protected Ui ui;
    protected ArrayList<Resource> resources;

    // @@author hin1
    /**
     * Constructor for the ResourceList. Takes in an array of Resources from the
     * Storage instance and saves it.
     * 
     * @param resources the array of Resources, as converted from text in the
     *                  save-file by the Storage instance
     */
    public ResourceList(Ui ui, ArrayList<Resource> resources) throws RimsException {
        this.ui = ui;
        this.resources = resources;
        printResourcesDueSoon(3);
    }

    // @author rabhijit
    /**
     * This method prints all the resources that are due soon (deadline within 3 days).
     */
    public void printResourcesDueSoon(int daysDue) throws RimsException {
        ReservationList allDueReservations = new ReservationList();
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            ReservationList thisResourceDueReservations = thisResource.getDueReservations(daysDue);
            if (!thisResourceDueReservations.isEmpty()) {
                // add only the earliest reservation that's still due
                allDueReservations.add(thisResourceDueReservations.getReservationByIndex(0));
            }
        }
        Collections.sort(allDueReservations.getReservationList(), new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                try {
                    return o1.getEndDate().compareTo(o2.getEndDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });
        if (!allDueReservations.isEmpty()) {
            ui.printLine();
            ui.print("REMINDER - The following loans are due soon (within " + daysDue + " days, or overdue):");
            ui.printEmptyLine();
            for (int i = 0; i < allDueReservations.size(); i++) {
                ui.print(getResourceById(allDueReservations.getReservationByIndex(i).getResourceId()).toString());
                ui.print("\t" + allDueReservations.getReservationByIndex(i));
            }
        }
        ui.printLine();
    }

    /**
     * Adds a new Resource to the ResourceList.
     * 
     * @param thisResource the newly created Resource.
     */
    public void add(Resource thisResource) {
        resources.add(thisResource);
    }

    /**
     * Removes a Resource from the ResourceList, as specified by the resource's
     * name.
     * 
     * @param resourceName the name of the Resource
     * @throws RimsException if there is no such Resource of that name.
     */
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

    // @@author isbobby
    /**
     * Removes a Resource from the ResourceList, as specified by the ID of that
     * resource.
     * 
     * @param resourceId the ID of the Resource
     * @throws RimsException if there is no such resource with that ID.
     */
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

    // @@author rabhijit
    /**
     * Returns the ResourceList itself.
     * 
     * @return the array of Resources.
     */
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * Assigns a new ArrayList of Resources within ResourceList.
     * 
     * @param resources Takes in the resource list
     */
    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    /**
     * Returns the number of items in the ResourceList.
     * 
     * @return the number of items in the ResourceList.
     */
    public int size() {
        return resources.size();
    }

    /**
     * Generates a resource ID for a newly created Resource.
     * 
     * @return a new resource ID.
     */
    public int generateResourceId() {
        for (int i = 0; i < size(); i++) {
            try {
                Resource thisResource = getResourceById(i);
            } catch (RimsException e) {
                return i;
            }
        }
        return size();
    }

    /**
     * Returns a Resource in the Resource array by its index number in the array.
     * 
     * @param indexNo the index number of the desired Resource.
     * @return the Resource itself.
     */
    public Resource getResourceByIndex(int indexNo) {
        return resources.get(indexNo);
    }

    /**
     * Returns the first Resource in the Resource array that matches a certain name.
     * 
     * @param resourceName the name of the desired Resource.
     * @return the Resource itself.
     * @throws RimsException if no such resource has that name.
     */
    public Resource getResourceByName(String resourceName) throws RimsException {
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getName().equals(resourceName)) {
                return thisResource;
            }
        }
        throw new RimsException("This resource does not exist in your inventory!");
    }

    /**
     * Returns a Resource in the Resource array by its ID number.
     * 
     * @param resourceId the resource ID of the desired Resource.
     * @return the Resource itself.
     * @throws RimsException if no such resource has that ID.
     */
    public Resource getResourceById(int resourceId) throws RimsException {
        for (int i = 0; i < size(); i++) {
            Resource thisResource = getResourceByIndex(i);
            if (thisResource.getResourceId() == resourceId) {
                return thisResource;
            }
        }
        throw new RimsException("No such resource ID!");
    }

    /**
     * Checks if a Resource is an Item.
     * 
     * @param resourceName the name of the Resource to be checked.
     * @return a boolean: true if it is an item, false if it is a room.
     * @throws RimsException if no such resource has that name.
     */
    public boolean isItem(String resourceName) throws RimsException {
        Resource thisResource = getResourceByName(resourceName);
        return (thisResource instanceof Item);
    }

    /**
     * Checks if a Resource is an Room.
     * 
     * @param resourceName the name of the Resource to be checked.
     * @return a boolean: true if it is a room, false if it is an item.
     * @throws RimsException if no such resource has that name.
     */
    public boolean isRoom(String resourceName) throws RimsException {
        Resource thisResource = getResourceByName(resourceName);
        return (thisResource instanceof Room);
    }

    /**
     * Returns an array of all the resources of a certain name.
     * 
     * @param resourceName the name of the Resources to be obtained.
     * @return an array of all the Resources with that name.
     */
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

    // @@author isbobby
    /**
     * Returns the number of resources of a certain name.
     * 
     * @param resourceName the name of the Resources to be counted.
     * @return the number of Resources with that name.
     */
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

    /**
     * Returns the number of resources of a certain name that are currently
     * available to be booked.
     * 
     * @param resourceName the name of the Resource in question.
     * @return the number of available Resources with that name.
     */
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
    /**
     * Returns the number of resources of a certain name that are available between
     * two given dates.
     * 
     * @param resourceName the name of the Resource in question.
     * @param dateFrom     the date from which the Resource should be available.
     * @param dateTill     the date till which the Resource should be availble.
     * @return the number of available Resources with that name.
     */
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

    // @@author aarushisingh1
    /**
     * Returns the number of resources of a certain name that are currently
     * available to be booked.
     * 
     * @param resourceName the name of the Resource in question.
     * @param date         the date that is being checked.
     * @return the number of available Resources with that name on that date.
     */
    public int getAvailableNumberOfResourceForDate(String resourceName, String date) throws ParseException {
        ArrayList<Resource> allOfResource = getAllOfResource(resourceName);
        Date checkedDate = stringToDate(date);
        int number = 0;
        for (int i = 0; i < allOfResource.size(); i++) {
            if (allOfResource.get(i).isAvailableOnDate(checkedDate)) {
                number++;
            }
        }
        return number;
    }

    /**
     * Returns the number of resources of a certain name that are currently booked.
     * 
     * @param resourceName the name of the Resource in question.
     * @return the number of booked Resources with that name.
     */
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

    // @@author aarushisingh1
    /**
     * Returns the number of resources of a certain name that are currently booked.
     * 
     * @param resourceName the name of the Resource in question.
     * @param date         the date that that is being checked
     * @return the number of booked Resources with that name on that date.
     */
    public int getBookedNumberOfResourceForDate(String resourceName, String date) throws ParseException {
        ArrayList<Resource> allOfResource = getAllOfResource(resourceName);
        Date checkedDate = stringToDate(date);
        int number = 0;
        for (int i = 0; i < allOfResource.size(); i++) {
            if (!allOfResource.get(i).isAvailableOnDate(checkedDate)) {
                number++;
            }
        }
        return number;
    }

    /**
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public int getBookedNumberOfResourceForDate(Date date) throws ParseException {
        int number = 0;
        for (int i = 0; i < resources.size(); i++) {
            if (!resources.get(i).isAvailableOnDate(date)) {
                number++;
            }
        }
        return number;
    }

    /**
     * Returns the list of reservations made by a user, given the user's ID.
     * 
     * @param userId the ID of the user whose reservations are to be obtained.
     * @return the list of reservations made by the aforementioned user.
     */
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

    /**
     * Generates a unique reservation ID for each new reservation made, regardless
     * of which resource that reservation is made for.
     * 
     * @return a unique reservation ID.
     */
    public int generateReservationId() {
        ArrayList<Integer> coveredIds = new ArrayList<Integer>();
        for (int i = 0; i < size(); i++) {
            Resource thisResource = resources.get(i);
            ReservationList thisResourceReservations = thisResource.getReservations();
            for (int j = 0; j < thisResourceReservations.size(); j++) {
                if (!coveredIds.contains(thisResourceReservations.getReservationByIndex(j).getReservationId())) {
                    coveredIds.add(thisResourceReservations.getReservationByIndex(j).getReservationId());
                }
            }
        }
        for (int k = 0; k < coveredIds.size(); k++) {
            if (!coveredIds.contains(k)) {
                return k;
            }
        }
        return coveredIds.size();
    }

    /**
     * Converts a date and time inputted by the user in String format, into a Date
     * object.
     * 
     * @param stringDate the date and time inputted by the user in String format.
     * @return a Date object representing the date and time inputted by the user.
     */
    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

    /**
     * Converts a Date object to a compact String, to be saved into a data file.
     * 
     * @param thisDate the Date object to be converted into a String.
     * @return a String representing the Date object.
     */
    public String dateToString(Date thisDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String stringDate = format.format(thisDate);
        return stringDate;
    }

    /**
     * Converts a Date object into a human-readable String, for the user's reading.
     * 
     * @param date the Date object to be converted into a String.
     * @return a human-readable String representing the Date object.
     */
    public String getDateToPrint(Date date) {
        DateFormat dayFormat = new SimpleDateFormat("d");
        int day = Integer.parseInt(dayFormat.format(date)) % 10;
        String suffix = day == 1 ? "st" : (day == 2 ? "nd" : (day == 3 ? "rd" : "th"));
        String stringDate = (new SimpleDateFormat("EEEEE, ")).format(date) + (dayFormat.format(date)) + suffix + " of "
                + (new SimpleDateFormat("MMMMM yyyy, hh:mm aaa")).format(date);
        return stringDate;
    }

}

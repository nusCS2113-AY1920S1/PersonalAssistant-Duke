package rims.command;

import java.text.ParseException;
import java.util.ArrayList;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.exception.RimsException;

/**
 * Shows the TaskList of all the currently existing Tasks in String format.
 */
public class ListCommand extends Command {
    protected String resourceName = null;
    protected String listType = null;

    /**
     * The constructor for a ListCommand, for a generic list of all Resources in the ResourceList.
     */
    public ListCommand() {
        ;
    }

    /**
     * The constructor for a ListCommand, when a detailed list of a particular Resource
     * is desired.
     * @param paramType the type of Resource desired (Item or Room)
     * @param resourceName the name of the Resource for which a list is desired.
     */
    public ListCommand(String paramType, String resourceName) {
        listType = paramType;
        this.resourceName = resourceName;
    }

    /**
     * Depending on the type of list desired, either prints out a basic list of all Resources in the ResourceList,
     * or a detailed list of an individual Resource containing all of its current and future Reservations.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     * @throws ParseException if the resource name is invalid
     * @throws RimsException for any other unexpected error
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException, RimsException {
        ui.printLine();
        if (listType == null) {
            ArrayList<String> coveredResources = new ArrayList<String>();
            ui.print("CURRENTLY AVAILABLE:");
            ui.printEmptyLine();
            for (int i = 0; i < resources.size(); i++) {
                Resource thisResource = resources.getResourceByIndex(i);
                int availableNumberOfResource = resources.getAvailableNumberOfResource(thisResource.getName());
                if (!coveredResources.contains(thisResource.getName()) && availableNumberOfResource > 0) {
                    coveredResources.add(thisResource.getName());
                    ui.print(thisResource.toString() + " (qty: " + availableNumberOfResource + ")");
                }
            }
            ui.printDash();
            ui.print("CURRENTLY BOOKED:");
            ui.printEmptyLine();
            coveredResources = new ArrayList<String>();
            for (int i = 0; i < resources.size(); i++) {
                Resource thisResource = resources.getResourceByIndex(i);
                int bookedNumberOfResource = resources.getBookedNumberOfResource(thisResource.getName());
                if (!coveredResources.contains(thisResource.getName()) && bookedNumberOfResource > 0) {
                    coveredResources.add(thisResource.getName());
                    ui.print(thisResource.toString() + " (qty: " + bookedNumberOfResource + ")");
                    ArrayList<Resource> allOfResource = resources.getAllOfResource(thisResource.getName());
                    for (int j = 0; j < allOfResource.size(); j++) {
                        if (!allOfResource.get(j).isCurrentlyAvailable()) {
                            ui.print("\t" + allOfResource.get(j).getReservations().getCurrentBooking().toString());
                        }
                    }
                }
            }
            ui.printLine();
        }

        else if (listType.equals("item")) {
            if (!resources.isItem(resourceName)) {
                throw new RimsException("There is no such item!");
            }
            ArrayList<Resource> allOfItem = resources.getAllOfResource(resourceName);
            for (int i = 0; i < allOfItem.size(); i++) {
                Resource thisResource = allOfItem.get(i);
                ReservationList thisResourceReservations = thisResource.getReservations();
                ui.printDash();
                ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
                if (!thisResourceReservations.isEmpty()) {
                    for (int j = 0; j < thisResourceReservations.size(); j++) {
                        ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                    }
                }
                else {
                    ui.print("No bookings for this resource yet!");
                }
            }
            ui.printDash();
            ui.printLine();
        }

        else if (listType.equals("room")) {
            if (!resources.isRoom(resourceName)) {
                throw new RimsException("There is no such room!");
            }
            Resource thisResource = resources.getResourceByName(resourceName);
            ReservationList thisResourceReservations = thisResource.getReservations();
            ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
            if (!thisResourceReservations.isEmpty()) {
                for (int j = 0; j < thisResourceReservations.size(); j++) {
                    ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                }
            }
            else {
                ui.print("No bookings for this resource yet!");
            }
            ui.printLine();
        }
        /*
        else if (listType.equals("date")) {

        }
        */
    }
}
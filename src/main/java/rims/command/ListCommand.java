package rims.command;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.util.converter.DateTimeStringConverter;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.exception.RimsException;

//@@author rabhijit
/**
 * Shows the TaskList of all the currently existing Tasks in String format.
 */
public class ListCommand extends Command {
    protected String resourceDetail = null;
    protected String listType = null;

    /**
     * The constructor for a ListCommand, for a generic list of all Resources in the
     * ResourceList.
     */
    public ListCommand() {
        ;
    }

    /**
     * The constructor for a ListCommand, when a detailed list of a particular
     * Resource is desired.
     *
     * @param paramType      the type of Resource desired (Item or Room)
     * @param resourceDetail the name of the Resource or the date for which a list
     *                       is desired.
     */
    public ListCommand(String paramType, String resourceDetail) {
        listType = paramType;
        this.resourceDetail = resourceDetail;
        canModifyData = false;
        commandUserInput = "list all " + paramType + "by " + resourceDetail;
    }

    /**
     * Converts a date and time inputted by the user in String format, into a Date
     * object.
     *
     * @param stringDate the date and time inputted by the user in String format.
     * @return a Date object representing the date and time inputted by the user.
     */
    public static Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

    /**
     * Depending on the type of list desired, either prints out a basic list of all
     * Resources in the ResourceList, or a detailed list of an individual Resource
     * containing all of its current and future Reservations.
     *
     * @param ui        An instance of the user interface.
     * @param storage   An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus
     *                  far.
     * @throws ParseException if the resource name is invalid
     * @throws RimsException  for any other unexpected error
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
        // @@author aarushisingh1
        else if (listType.equals("item")) {
            if (!resources.isItem(resourceDetail)) {
                throw new RimsException("There is no such item!");
            }
            ArrayList<Resource> allOfItem = resources.getAllOfResource(resourceDetail);
            for (int i = 0; i < allOfItem.size(); i++) {
                Resource thisResource = allOfItem.get(i);
                ReservationList thisResourceReservations = thisResource.getReservations();
                ui.printDash();
                ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
                if (!thisResourceReservations.isEmpty()) {
                    for (int j = 0; j < thisResourceReservations.size(); j++) {
                        ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                    }
                } else {
                    ui.print("No bookings for this resource yet!");
                }
            }
            ui.printDash();
            ui.printLine();

        }

        else if (listType.equals("room")) {

            if (!resources.isRoom(resourceDetail)) {
                throw new RimsException("There is no such room!");
            }
            Resource thisResource = resources.getResourceByName(resourceDetail);
            ReservationList thisResourceReservations = thisResource.getReservations();
            ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
            if (!thisResourceReservations.isEmpty()) {
                for (int j = 0; j < thisResourceReservations.size(); j++) {
                    ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                }
            } else {
                ui.print("No bookings for this resource yet!");
            }
            ui.printLine();

        } else if (listType.equals("date")) {

            ArrayList<String> coveredResources = new ArrayList<String>();
            ui.print("CURRENTLY AVAILABLE ON THIS DATE:");
            ui.printEmptyLine();
            String checkedDate = resourceDetail;
            Date date = resources.stringToDate(checkedDate);
            for (int i = 0; i < resources.size(); i++) {
                Resource thisResource = resources.getResourceByIndex(i);
                int availableNumberOfResource = resources.getAvailableNumberOfResourceForDate(thisResource.getName(),
                        checkedDate);
                if (!coveredResources.contains(thisResource.getName()) && availableNumberOfResource > 0) {
                    coveredResources.add(thisResource.getName());
                    ui.print(thisResource.toString() + " (qty: " + availableNumberOfResource + ")");
                }
            }
            ui.printDash();
            ui.print("CURRENTLY BOOKED ON THIS DATE:");
            ui.printEmptyLine();
            coveredResources = new ArrayList<String>();
            for (int i = 0; i < resources.size(); i++) {
                Resource thisResource = resources.getResourceByIndex(i);
                int bookedNumberOfResource = resources.getBookedNumberOfResourceForDate(thisResource.getName(),
                        checkedDate);
                if (!coveredResources.contains(thisResource.getName()) && bookedNumberOfResource > 0) {
                    coveredResources.add(thisResource.getName());
                    ui.print(thisResource.toString() + " (qty: " + bookedNumberOfResource + ")");
                    ArrayList<Resource> allOfResource = resources.getAllOfResource(thisResource.getName());
                    for (int j = 0; j < allOfResource.size(); j++) {
                        if (!allOfResource.get(j).isAvailableOnDate(date)) {
                            ui.print("\t" + allOfResource.get(j).getReservations().getCurrentBooking().toString());
                        }
                    }
                }
            }
            ui.printLine();
        }

    }

    //

    public static String[] getListForSpecificDay(Date day, ResourceList resources, Ui ui)
            throws ParseException, RimsException {
        System.out.print("here are the booked items: ");
        String[] result = new String[10];

        ArrayList<String> coveredResources = new ArrayList<String>();

        // String[] coveredResources = {};
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String strDate = dateFormat.format(day);

        for (int i = 0; i < resources.size(); i++) {
            Resource thisResource = resources.getResourceByIndex(i);
            // System.out.print(thisResource);
            result[i] = thisResource.toString();
            // int bookedNumberOfResource =
            // resources.getBookedNumberOfResourceForDate(thisResource.getName(), strDate);

            // if (!coveredResources.contains(thisResource.getName()) &&
            // bookedNumberOfResource > 0) {
            // coveredResources.add(thisResource.toString() + " (qty: " +
            // bookedNumberOfResource + ")");
            // result[i] = thisResource.toString() + " (qty: " + bookedNumberOfResource +
            // ")";

            // ui.print(thisResource.toString() + " (qty: " + bookedNumberOfResource + ")");
            // ArrayList<Resource> allOfResource =
            // resources.getAllOfResource(thisResource.getName());
            // for (int j = 0; j < allOfResource.size(); j++) {
            // if (!allOfResource.get(j).isAvailableOnDate(day)) {
            // result.add(allOfResource.get(j).getReservations().getCurrentBooking().toString());

            // }

            // }
            // }
        }
        // List<String> list = Arrays.asList(coveredResources);
        for (int i = 0; i < result.length; i++) {
            System.out.print("\n");
            System.out.print(result[i]);
        }
        return result;
    }
}

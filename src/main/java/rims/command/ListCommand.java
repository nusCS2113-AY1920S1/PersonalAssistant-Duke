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

public class ListCommand extends Command {
    protected String param = null;
    protected String listType = null;

    // for basic 'list'
    public ListCommand() {
        ;
    }

    // for list /paramType
    public ListCommand(String paramType, String param) {
        listType = paramType;
        this.param = param;
    }

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
            ui.printEmptyLine();
            ui.print("CURRENTLY BOOKED:");
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
            if (!resources.isItem(param)) {
                throw new RimsException("There is no such item!");
            }
            ArrayList<Resource> allOfItem = resources.getAllOfResource(param);
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
            if (!resources.isRoom(param)) {
                throw new RimsException("There is no such room!");
            }
            Resource thisResource = resources.getResourceByName(param);
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
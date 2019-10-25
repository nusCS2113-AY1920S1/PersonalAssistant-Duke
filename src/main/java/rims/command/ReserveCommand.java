package rims.command;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;
import rims.exception.RimsException;

public class ReserveCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected Date dateFrom;
    protected Date dateTill;
    protected String stringDateFrom = null;
    protected String stringDateTill;
    protected int userId;

    public ReserveCommand(String roomName, String stringDateTill, int userId) {
        resourceName = roomName;
        this.qty = 1;
        this.dateFrom = new Date(System.currentTimeMillis());
        this.stringDateTill = stringDateTill;
        this.userId = userId;
    }

    public ReserveCommand(String itemName, int qty, String stringDateTill, int userId) {
        resourceName = itemName;
        this.qty = qty;
        this.dateFrom = new Date(System.currentTimeMillis());
        this.stringDateTill = stringDateTill;
        this.userId = userId;
    }

    public ReserveCommand(String roomName, String stringDateFrom, String stringDateTill, int userId) {
        resourceName = roomName;
        this.qty = 1;
        this.stringDateFrom = stringDateFrom;
        this.stringDateTill = stringDateTill;
        this.userId = userId;
    }

    public ReserveCommand(String itemName, int qty, String stringDateFrom, String stringDateTill, int userId) {
        resourceName = itemName;
        this.qty = qty;
        this.stringDateFrom = stringDateFrom;
        this.stringDateTill = stringDateTill;
        this.userId = userId;
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException, ParseException {
        if (!(stringDateFrom == null)) {
            dateFrom = resources.stringToDate(stringDateFrom);
        }
        dateTill = resources.stringToDate(stringDateTill);
        ArrayList<Resource> allOfResource = resources.getAllOfResource(resourceName);
        ArrayList<Resource> bookedResources = new ArrayList<Resource>();
        int qtyBooked = 0;
        for (int j = 0; j < allOfResource.size(); j++) {
            Resource thisResource = allOfResource.get(j);
            if (thisResource.isAvailableFrom(dateFrom, dateTill)) {
                thisResource.book(resources.generateReservationId(), userId, dateFrom, dateTill);
                bookedResources.add(thisResource);
                qtyBooked++;
            }
            if (qtyBooked == qty) {
                break;
            }
        }
        if (qtyBooked != 0) {
            ui.printLine();
            ui.print("Done! I've marked these resources as loaned:");
            for (int i = 0; i < bookedResources.size(); i++) {
                ui.print(bookedResources.get(i).toString() + " (ID: " + bookedResources.get(i).getResourceId() + ")");
            }
            ui.printLine();
        }
        else {
            throw new RimsException("This item is not available between the dates you've selected!");
        }
    }
}
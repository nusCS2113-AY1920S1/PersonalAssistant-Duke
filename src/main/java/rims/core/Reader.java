package rims.core;

import java.util.ArrayList;

import rims.command.AddCommand;
import rims.command.Command;
import rims.command.DeleteCommand;
import rims.command.HomeCommand;
import rims.command.LoanCommand;
import rims.command.ReserveCommand;
import rims.command.ReturnCommand;
import rims.util.DateRange;

public class Reader implements Read {
    private String resourceName;
    private int qty;
    private int id;
    private String startDate;
    private String endDate;

    private ResourceList resources;
    private ReservationList reservations;
    private Ui ui;

    public Reader(ResourceList resources, ReservationList reservations, Ui ui) {
        this.resources = resources;
        this.reservations = reservations;
        this.ui = ui;
    }

    public Command ReadAddCommand() {
        ui.formattedPrint("Would you like to add an item or a room to the inventory?");
        String choice = ui.getInput();
        Command c = null;
        if (choice.equals("room")) {
            ui.formattedPrint("Enter the name of the room");
            String resourceName = ui.getInput();
            c = new AddCommand(resourceName);
        } else if (choice.equals("item")) {
            ui.formattedPrint("Enter the name of the item");
            String resourceName = ui.getInput();
            ui.formattedPrint("Enter the quantity of the item");
            int qty = Integer.parseInt(ui.getInput());
            c = new AddCommand(resourceName, qty);
        } else {
            c = new HomeCommand();
        }
        return c;
    }

    public Command ReadLoanCommand() {
        ui.formattedPrint("Would you like to borrow an item or book a room");
        // input validation
        String choice = ui.getInput();

        if (choice.equals("room")) {
            ui.formattedPrint("Enter Room name");
            resourceName = ui.getInput();
            // check for item existence
            // return qty here to show user the quantity available

            // check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId = ui.getInput();
            try {
                id = Integer.parseInt(tempId);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate = ui.getInput();

            Command c = new LoanCommand(resourceName, id, endDate);
            return c;
        }

        else if (choice.equals("item")) {
            ui.formattedPrint("Enter Item name (use cancel to go back)");
            resourceName = ui.getInput();
            // check for item existence
            // return qty here to show user the quantity available

            ui.formattedPrint("Enter quantity you wish to borrow");
            String tempQty = ui.getInput();

            // check for valid Integer input
            // Needs to be done: check for sufficient quantity
            try {
                qty = Integer.parseInt(tempQty);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }
            // check if quantity is valid

            // check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId = ui.getInput();
            try {
                id = Integer.parseInt(tempId);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate = ui.getInput();

            Command c = new LoanCommand(resourceName, qty, id, endDate);
            return c;
        } else {
            Command c = new HomeCommand();
            return c;
        }
    }

    /**
     * an user friendly cli to read reserve command
     * 
     * @exception someExceptions fill in later
     */
    public Command ReadReserveCommand() {
        ui.formattedPrint("Enter 'item' to reserve an item, or 'room' to book a room");
        String choice = ui.getInput();

        if (choice.equals("room")) {
            ui.formattedPrint("Enter Room name");
            this.resourceName = ui.getInput();
            int resourceId = resources.getResourceByName(this.resourceName).getResourceId();

            ui.formattedPrint("Enter your ID as a borrower");
            String tempId = ui.getInput();
            try {
                id = Integer.parseInt(tempId);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }

            ui.formattedPrint("Enter the start date in the following format: DD/MM/YYYY HHMM");
            startDate = ui.getInput();

            ui.formattedPrint("Enter the return date in the following format: DD/MM/YYYY HHMM");
            endDate = ui.getInput();

            ArrayList<DateRange> dates = new ArrayList<DateRange>();
            dates = reservations.getAllDatesByResourceId(resourceId);

            for (int i = 0; i < dates.size(); i++) {
                ui.print(dates.get(i).toString());
            }

            int currentQty = 0;
            int totalQty = resources.getResourceViaIndex(resourceId).getTotalQty();
            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.getReservationByIndex(i).getResourceId() == resourceId) {
                    currentQty += reservations.getReservationByIndex(i).getQty();
                }
            }

            ui.print("Total Quantity: " + totalQty + " Current Quantity(not accurate yet): " + currentQty);
            Command c = new ReserveCommand(resourceName, id, startDate, endDate);
            return c;
        }

        else if (choice.equals("item")) {
            ui.formattedPrint("Enter Item name (use cancel to go back)");
            this.resourceName = ui.getInput();
            int resourceId = resources.getResourceByName(this.resourceName).getResourceId();

            ui.formattedPrint(
                    "Enter the date you wish to start using the resource in the following format: DD/MM/YYYY HHMM");
            startDate = ui.getInput();

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate = ui.getInput();

            ArrayList<DateRange> dates = new ArrayList<DateRange>();
            dates = reservations.getAllDatesByResourceId(resourceId);

            for (int i = 0; i < dates.size(); i++) {
                ui.print(dates.get(i).toString());
            }            
            int currentQty = 0;
            int totalQty = resources.getResourceViaIndex(resourceId).getTotalQty();
            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.getReservationByIndex(i).getResourceId() == resourceId) {
                    currentQty += reservations.getReservationByIndex(i).getQty();
                }
            }
            ui.print("Total Quantity: " + totalQty + " Current Quantity(not accurate yet): " + currentQty);
            ui.formattedPrint("Enter quantity you wish to borrow");
            
            String tempQty = ui.getInput();
            try {
                qty = Integer.parseInt(tempQty);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }


            // check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId = ui.getInput();
            try {
                id = Integer.parseInt(tempId);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }
            Command c = new ReserveCommand(resourceName, id, startDate, endDate);
            return c;
        } else {
            Command c = new HomeCommand();
            return c;
        }
    }

    /**
     * An user friendly cli prompt to read return commands
     * 
     * @exception typelaterIamdamndone
     */
    public Command ReadReturnCommand() {
        Command c = new HomeCommand();
        ui.formattedPrint("Enter your user ID to see your reservations and bookings");

        int userId = Integer.parseInt(ui.getInput());
        ui.printLine();
        ui.printReservationArray(reservations.getReservationListByUid(userId));
        ui.printLine();

        ui.formattedPrint("Enter the corresponding reservation ID to remove/return/cancel reservation");
        int reservationId = Integer.parseInt(ui.getInput());

        c = new ReturnCommand(reservationId);
        return c;
    }

    /**
     * 
     */
    public Command ReadDeleteCommand() {
        Command c = null;
        ui.formattedPrint("Choose an item to delete by selecting its ID");
        ui.printResourceArray(resources.getResourceList());

        int resource_id = Integer.parseInt(ui.getInput());

        ui.print("You have selected the following resource:\n ");
        ui.formattedPrint(resources.getResourceByResourceId(resource_id).toString());

        // show the reservations affected
        if (reservations.getReservationListByResourceId(resource_id).size() > 0) {
            ui.ErrorPrint("The following Reservations will be also be deleted");
            ui.printReservationArray(reservations.getReservationListByResourceId(resource_id));
        }

        ui.print("Enter Y to confirm deletion, N to cancel. (All reservations will be deleted as well)");

        String choice = ui.getInput();
        if (choice.equals("y") || choice.equals("Y")) {
            c = new DeleteCommand(resource_id);
        } else {
            ui.ErrorPrint("You have canceled the deletion request");
            c = new HomeCommand();
        }
        return c;
    }
}
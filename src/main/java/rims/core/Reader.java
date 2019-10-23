package rims.core;

import java.util.ArrayList;

import rims.command.AddCommand;
import rims.command.Command;
import rims.command.DeleteCommand;
import rims.command.HomeCommand;
import rims.command.ReserveCommand;
import rims.command.ReturnCommand;
import rims.resource.ReservationList;

public class Reader implements Read {
    private String resourceName;
    private int resource_id;
    private int user_id;
    private int reservation_id;
    private String startDate;
    private String endDate;

    private ResourceList resources;
    private Ui ui;

    public Reader(ResourceList resources, Ui ui) {
        this.resources = resources;
        this.ui = ui;
    }

    public Command ReadAddCommand() {
        ui.formattedPrint("Would you like to add an item or a room to the inventory?");
        String choice = ui.getInput();
        Command c = null;
        if (choice.equals("room")) {
            ui.formattedPrint("Enter the name of the room");
            String resourceName = ui.getInput();
            c = new AddCommand(resourceName, "room");
        } else if (choice.equals("item")) {
            ui.formattedPrint("Enter the name of the item");
            String resourceName = ui.getInput();
            c = new AddCommand(resourceName, "item");
        } else {
            c = new HomeCommand();
        }
        return c;
    }

    public Command ReadReserveCommand() {
        ui.formattedPrint("Enter 'room' or 'item' to reserve a room or an item.");
        String choice = ui.getInput();
        Command c = null;
        if (choice.equals("room")) {
            c = reserveRoom();
        } else if (choice.equals("item")) {
            c = reserveItem();
        } else {//error
            c = new HomeCommand();
        }
        return c;
    }

    public Command ReadReturnCommandByUserId(){
        Command c = null;
        ui.formattedPrint("Enter your user ID to see your current reservations.");

        user_id=Integer.parseInt(ui.getInput());

        ReservationList thisReservations = resources.getReservationsByUserId(user_id);
        ui.printReservationArrayReturn(thisReservations);

        ui.formattedPrint("Enter the resource ID of the resource you wish to return");
        resource_id=Integer.parseInt(ui.getInput());
        
        ui.print("These are your reservations for the selected item");
        ui.printReservations(resource_id, resources);
        ui.formattedPrint("Select the reservation to cancel/return by entering the reservation ID");

        reservation_id=Integer.parseInt(ui.getInput());


        c = new ReturnCommand(resource_id,reservation_id);
        return c;
    }

    public Command ReadDeleteResourceCommand(){
        Command c = new HomeCommand();
        c = deleteItem();
        return c;
    }

    /**
     * This method will first print out a list of all rooms that are available. Then
     * it will ask for an resource id input, and flushes out all the reservations
     * that is currently under this resource.
     * 
     * Lastly, it will ask for a pair of dates and the user's user id.
     * @return ReserveCommand(); 
     */
    private Command reserveRoom() {
        Command c = new ReserveCommand();
        ui.printRooms(this.resources.getResourceList());
    
        resource_id = ui.getResourceId();
        ui.printReservations(resource_id, resources);

        ArrayList<String> PairOfDates = ui.getPairOfDates();
        startDate = PairOfDates.get(0);
        endDate = PairOfDates.get(1);

        user_id = ui.getUserId();
        c = new ReserveCommand(resource_id, user_id, startDate, endDate);
        return c;
    }

    private Command reserveItem() {
        Command c = new ReserveCommand();
        ui.printItem(this.resources.getResourceList());
    
        resource_id = ui.getResourceId();
        ui.printReservations(resource_id, resources);

        ArrayList<String> PairOfDates = ui.getPairOfDates();
        startDate = PairOfDates.get(0);
        endDate = PairOfDates.get(1);

        user_id = ui.getUserId();
        c = new ReserveCommand(resource_id, user_id, startDate, endDate);
        return c;
    }

    /**
     * This method flushes out all resources, then takes in a resource ID from the user and delete it.
     * @return DeleteCommand(resource_id)
     */
    public Command deleteItem(){
        Command c = new DeleteCommand();
        ui.printLine();
        ui.print("Enter a resource ID to delete the resource");
        ui.printResourceArray(resources);
        ui.printLine();

        resource_id=ui.getResourceId();

        c = new DeleteCommand(resource_id);
        return c;
    }
}
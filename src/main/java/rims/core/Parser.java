package rims.core;

import java.util.ArrayList;

import rims.command.AddCommand;
import rims.command.CloseCommand;
import rims.command.Command;
import rims.command.DeleteCommand;
import rims.command.HomeCommand;
import rims.command.ListCommand;
import rims.command.ReserveCommand;
import rims.command.ReturnCommand;
import rims.command.UnknownCommand;
import rims.resource.ReservationList;

public class Parser {
    Ui ui;
    ResourceList resources;

    public Parser(ResourceList resources, Ui ui) {
        this.resources = resources;
        this.ui = ui;
    }

    public Command parseInput(String input) {
        Command c = new HomeCommand(); // temporary default until exception handling finished
        String[] words = input.split(" ");

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        } else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        } else if (words[0].equals("reserve") && words.length == 1) {
            c = ReadReserveCommand();
        } else if ((words[0].equals("cancel") || words[0].equals("return")) && words.length == 1) {
            c = ReadReturnCommandByUserId();
        } else if (words[0].equals("add")) {
            c = ReadAddCommand();
        } else if (words[0].equals("delete")) {
            c = ReadDeleteResourceCommand();
        } else {
            c = new UnknownCommand(input);
        }
        return c;
    }

    // This section contains the extensive reading/interacting methods
    /**
     * This method prompts for multiple user inputs to initalize a new reservation
     * for a specific resource.
     * 
     * First it takes in a single user id as integer. Then it takes a string to
     * determine the type of resource and shows the list of all resources of this
     * type. Lastly, it asks for resource ID and DatePair.
     * 
     * @return ReserveCommand()
     */
    private Command ReadReserveCommand() {
        Command c = new HomeCommand();
        // asks for user id
        int user_id = ui.getIntegerFromString("Hi! Welcome to RIMS! Enter your user ID to make a reservation");

        // asks user for resource type
        String choice = ui.getInput("Enter 'room' or 'item' to reserve a room or an item.");

        if (choice.equals("room")) {
            // show all the rooms
            ui.printRooms(this.resources.getResourceList());

            // show all the resoervations belong to this resource
            int resource_id = ui.getIntegerFromString("Enter the resource ID of the resource you wish to borrow");
            ui.printReservations(resource_id, resources);

            // asks for a pair of dates, read as string first, PairOfDate.get(0) is the
            // start date
            ArrayList<String> PairOfDates = ui.getPairOfDates();
            // catch invalid date here

            // instantiate new reserve command object and return it
            c = new ReserveCommand(resource_id, user_id, PairOfDates.get(0), PairOfDates.get(1));
        } else if (choice.equals("item")) {
            // show all the items
            ui.printRooms(this.resources.getResourceList());

            // show all the resoervations belong to this resource
            int resource_id = ui.getIntegerFromString("Enter the resource ID of the resource you wish to borrow");
            ui.printReservations(resource_id, resources);

            // asks for a pair of dates, read as string first, PairOfDate.get(0) is the
            // start date
            ArrayList<String> PairOfDates = ui.getPairOfDates();
            // catch invalid date here

            // instantiate new reserve command object and return it
            c = new ReserveCommand(resource_id, user_id, PairOfDates.get(0), PairOfDates.get(1));
        } else {// error
            c = new HomeCommand();
        }
        return c;
    }

    /**
     * This method asks for user id, resource id and reservation id to remove one
     * single reservation.
     * 
     * @return ReturnCommand
     */
    public Command ReadReturnCommandByUserId() {
        Command c = new HomeCommand();

        // asks for user id
        int user_id = ui.getIntegerFromString("Enter your User ID to see all your reservations.");

        // shows the user all the reservations under him or her
        ReservationList ReservationsByThisUser = resources.getReservationsByUserId(user_id);
        ui.printReservationArrayReturn(ReservationsByThisUser);

        // asks for a resource ID to uniquely identify the resource to return
        int resource_id = ui.getIntegerFromString("Enter the resource ID of the resource you wish to return");

        ui.print("These are your reservations for the selected item");
        ui.printReservations(resource_id, resources);

        // asks for the specific reservation ID
        int reservation_id = ui
                .getIntegerFromString("Select the reservation to cancel/return by entering the reservation ID");

        c = new ReturnCommand(resource_id, reservation_id);
        return c;
    }

    /**
     * This method asks for the few key attributes and instantiate a resource object
     * 
     * @return
     */
    private Command ReadAddCommand() {
        String choice = ui.getInput("Would you like to add an item or a room to the inventory?");
        Command c = null;
        if (choice.equals("room")) {
            String resourceName = ui.getInput("Enter the name of the room");
            c = new AddCommand(resourceName, "room");
        } else if (choice.equals("item")) {
            String resourceName = ui.getInput("Enter the name of the item");
            c = new AddCommand(resourceName, "item");
        } else {
            c = new HomeCommand();
        }
        return c;
    }

    /**
     * Prompts for a single resource ID and delete it from the resource list
     * permenantly (along with all the reservations)
     */
    private Command ReadDeleteResourceCommand() {
        Command c = new DeleteCommand();
        ui.printLine();
        ui.print("Here is a list of all resources in your inventory");
        ui.printResourceArray(resources);
        ui.printLine();

        int resource_id = ui.getIntegerFromString("Choose a resource id to remove the specific resource");

        c = new DeleteCommand(resource_id);
        return c;
    }
}
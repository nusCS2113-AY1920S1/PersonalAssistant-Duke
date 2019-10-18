package rims.core;

import rims.command.Command;
import rims.command.HomeCommand;
import rims.command.LoanCommand;
import rims.command.ReserveCommand;
import rims.command.ReturnCommand;

public class Reader implements Read {
    private String item;
    private int qty;
    private int id;
    private String startDate;
    private String endDate;

    private ResourceList resources;
    private Ui ui;

    public Reader(ResourceList resources, Ui ui) {
        this.resources = resources;
        this.ui = ui;
    }

    public Command ReadLoanCommand() {
        ui.formattedPrint("Would you like to borrow an item or book a room");
        // input validation
        String choice = ui.getInput();

        if (choice.equals("room")) {
            ui.formattedPrint("Enter Room name");
            item = ui.getInput();
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

            Command c = new LoanCommand(item, id, endDate);
            return c;
        }

        else if (choice.equals("item")) {
            ui.formattedPrint("Enter Item name (use cancel to go back)");
            item = ui.getInput();
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

            Command c = new LoanCommand(item, qty, id, endDate);
            return c;
        } else {
            Command c = new HomeCommand();
            return c;
        }
    }

    public Command ReadReserveCommand() {
        ui.formattedPrint("Would you like to reserve an item or book a room");
        // input validation
        String choice = ui.getInput();

        if (choice.equals("room")) {
            ui.formattedPrint("Enter Room name");
            item = ui.getInput();
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

            ui.formattedPrint(
                    "Enter the date you wish to start using the resource in the following format: DD/MM/YYYY HHMM");
            startDate = ui.getInput();

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate = ui.getInput();

            Command c = new ReserveCommand(item, id, startDate, endDate);
            return c;
        }

        else if (choice.equals("item")) {
            ui.formattedPrint("Enter Item name (use cancel to go back)");
            item = ui.getInput();
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

            ui.formattedPrint(
                    "Enter the date you wish to start using the resource in the following format: DD/MM/YYYY HHMM");
            startDate = ui.getInput();

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate = ui.getInput();

            Command c = new ReserveCommand(item, id, startDate, endDate);
            return c;
        } else {
            Command c = new HomeCommand();
            return c;
        }
    }

    public Command ReadReturnCommand() {
        ui.formattedPrint("Enter your borrower ID");
        //get an user ID, check for valid input
        String tempId = ui.getInput();
        try {
            id = Integer.parseInt(tempId);
        } catch (NumberFormatException ex) {
            ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
            Command c = new HomeCommand();
            return c;
        }

        // check if there are items/rooms currently borrowed by this user
        if (resources.generateBookedList(id).size()>1) {
            ui.formattedPrint("These are the items or rooms that you are currently borrowing");

            //different mechanism for the array!
            ui.formattedPrint(Integer.toString(resources.size()));
            ui.printArray(resources.generateBookedList(id));
        }
        else {
            ui.ErrorPrint("You are not borrowing anything at the moment, we will redirect you to the home page");
            Command c = new HomeCommand();
            return c;
        }

        ui.formattedPrint("Would you like to return an item or a room");
        // input validation
        String choice = ui.getInput();

        if (choice.equals("item")) {
            ui.formattedPrint("Enter the item you wish to return");
            item = ui.getInput();

            ui.formattedPrint("Enter the quantity you wish to return");
            String tempqty = ui.getInput();
            try {
                qty = Integer.parseInt(tempqty);
            } catch (NumberFormatException ex) {
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }
            Command c = new ReturnCommand(item, qty, id);
            return c;

        } else if (choice.equals("room")) {
            ui.formattedPrint("Enter the room you have finished using");
            item = ui.getInput();
            Command c = new ReturnCommand(item, id);
            return c;
        } else {
            ui.ErrorPrint("Some unknow error has occured, returning you to the home page");
            Command c = new HomeCommand();
            return c;
        }
    }
}
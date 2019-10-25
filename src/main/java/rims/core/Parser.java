package rims.core;

import java.util.ArrayList;

import rims.command.Command;
import rims.command.CloseCommand;
import rims.command.ListCommand;
import rims.command.AddCommand;
import rims.command.DeleteCommand;
import rims.command.ReserveCommand;
import rims.command.ReturnCommand;
import rims.command.CalendarCommand;
import rims.exception.RimsException;
import rims.core.Ui;
import rims.core.ResourceList;
import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;

public class Parser {
    Ui ui;
    ResourceList resources;

    public Parser(Ui ui, ResourceList resources) {
        this.ui = ui;
        this.resources = resources;
    }

    public Command parseInput(String input) throws RimsException {
        Command c;
        String[] words = input.split(" ");

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        }
        else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        }
        else if (words[0].equals("cal") && words.length == 1) {
            CalendarCommand.printCal();
            c = new ListCommand();
        }
        else if (words[0].equals("list") && words.length > 1) {
            String paramType = words[1].substring(1);
            if (paramType.equals("date") || paramType.equals("room") || paramType.equals("item")) {
                String param = ui.getInput("Enter the name of the resource you'd like to view a detailed list of:");
                c = new ListCommand(paramType, param);
            }
            else {
                throw new RimsException("Invalid list parameter!");
            }
        }
        else if (words[0].equals("add")) {
            String roomOrItem = ui.getInput("Would you like to add an item or a room to the inventory?");
            if (roomOrItem.equals("room")) {
                String roomName = ui.getInput("Enter the name of the room you wish to add:");
                c = new AddCommand(roomName);
            }
            else if (roomOrItem.equals("item")) {
                String itemName = ui.getInput("Enter the name of the item you wish to add:");
                int qty = Integer.parseInt(ui.getInput("How many of this item do you wish to add?"));
                c = new AddCommand(itemName, qty);
            }
            else {
                throw new RimsException("Please choose a room or item to add to your inventory.");
            }
        }
        else if (words[0].equals("delete")) {
            String roomOrItem = ui.getInput("Would you like to delete an item or room from the inventory?");
            if (roomOrItem.equals("room")) {
                String roomName = ui.getInput("Enter the name of the room you wish to delete:");
                c = new DeleteCommand(roomName, "room");
            }
            else if (roomOrItem.equals("item")) {
                String itemName = ui.getInput("Enter the name of the item you wish to delete:");
                c = new DeleteCommand(itemName, "item");
            }
            else {
                throw new RimsException("Please choose a room or item to delete from your inventory.");
            }
        }
        else if (words[0].equals("loan")) {
            String roomOrItem = ui.getInput("Would you like to loan an item or room from the inventory?");
            ui.printLine();
            if (roomOrItem.equals("room")) {
                String roomName = ui.getInput("Enter the name of the room you wish to loan:");
                // printing out existing room bookings
                if (!resources.isRoom(roomName)) {
                    throw new RimsException("There is no such room!");
                }
                Resource thisResource = resources.getResourceByName(roomName);
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
                String dateTill = ui.getInput("Enter the date you wish to return this room:");
                int userId = Integer.parseInt(ui.getInput("Enter your user ID:"));
                c = new ReserveCommand(roomName, dateTill, userId);
            }
            else if (roomOrItem.equals("item")) {
                String itemName = ui.getInput("Enter the name of the item you wish to loan:");
                ui.printLine();
                if (!resources.isItem(itemName)) {
                    throw new RimsException("There is no such item!");
                }
                ArrayList<Resource> allOfItem = resources.getAllOfResource(itemName);
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
                int qty = Integer.parseInt(ui.getInput("Enter how many of this item you wish to borrow:"));
                String dateTill = ui.getInput("Enter the date you wish to return this item:");
                int userId = Integer.parseInt(ui.getInput("Enter your user ID:"));
                c = new ReserveCommand(itemName, qty, dateTill, userId);
            }
            else {
                throw new RimsException("Please choose a room or item to loan from your inventory.");
            }
        }
        else if (words[0].equals("reserve")) {
            String roomOrItem = ui.getInput("Would you like to reserve an item or room from the inventory?");
            ui.printLine();
            if (roomOrItem.equals("room")) {
                String roomName = ui.getInput("Enter the name of the room you wish to reserve:");
                // printing out existing room bookings
                if (!resources.isRoom(roomName)) {
                    throw new RimsException("There is no such room!");
                }
                Resource thisResource = resources.getResourceByName(roomName);
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
                String dateFrom = ui.getInput("Enter the date from which you wish to reserve this room:");
                String dateTill = ui.getInput("Enter the date you wish to return this room:");
                int userId = Integer.parseInt(ui.getInput("Enter your user ID:"));
                c = new ReserveCommand(roomName, dateFrom, dateTill, userId);
            }
            else if (roomOrItem.equals("item")) {
                String itemName = ui.getInput("Enter the name of the item you wish to reserve:");
                ui.printLine();
                if (!resources.isItem(itemName)) {
                    throw new RimsException("There is no such item!");
                }
                ArrayList<Resource> allOfItem = resources.getAllOfResource(itemName);
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
                int qty = Integer.parseInt(ui.getInput("Enter how many of this item you wish to reserve:"));
                String dateFrom = ui.getInput("Enter the date from which you wish to reserve this room:");
                String dateTill = ui.getInput("Enter the date you wish to return this item:");
                int userId = Integer.parseInt(ui.getInput("Enter your user ID:"));
                c = new ReserveCommand(itemName, qty, dateFrom, dateTill, userId);
            }
            else {
                throw new RimsException("Please choose a room or item to loan from your inventory.");
            }
        }
        else if (words[0].equals("return")) {
            int userId = Integer.parseInt(ui.getInput("Enter your user ID:"));
            ReservationList userReservations = resources.getUserBookings(userId);
            ui.printLine();
            for (int i = 0; i < userReservations.size(); i++) {
                Reservation thisReservation = userReservations.getReservationByIndex(i);
                Resource borrowedResource = resources.getResourceById(thisReservation.getResourceId());
                ui.print(borrowedResource.toString());
                ui.print("\t" + userReservations.getReservationByIndex(i).toString());
            }
            int reservationId = Integer.parseInt(ui.getInput("Enter the ID of the reservation you wish to return:"));
            c = new ReturnCommand(userId, userReservations.getReservationById(reservationId).getResourceId(), reservationId);
        }
        else {
            throw new RimsException("Please enter a recognizable command!");
        }
        return c;
    }
}
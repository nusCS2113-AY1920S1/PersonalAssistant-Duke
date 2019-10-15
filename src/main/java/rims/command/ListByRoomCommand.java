package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ListByRoomCommand extends Command {
    protected String roomName;

    public ListByRoomCommand(String roomName){
        this.roomName = roomName;
    }

    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException {
        ui.printLine();
        ui.print("Here are the resources in your list by room name:");
        ui.printArray(resources.generateListByRoom(roomName));
//        ui.printEmptyLine();
//        ui.printArray(resources.generateBookedListByDate(itemName));
        ui.printLine();
    }
}

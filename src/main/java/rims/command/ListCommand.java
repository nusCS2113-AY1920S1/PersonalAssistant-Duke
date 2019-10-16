package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ListCommand extends Command {
    protected String stringDate = null;
    protected String item = null;
    protected String room = null;
    protected String listType = null;

    public ListCommand() {
        ;
    }

    public ListCommand(String paramType, String param) {
        listType = paramType;
        if (paramType.equals("date")) {
            stringDate = param;
        }
        else if (paramType.equals("item")) {
            item = param;
        }
        else if (paramType.equals("room")) {
            room = param;
        }
    }

    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException {
        if (listType == null) {
            ui.printLine();
            ui.print("Here are the resources in your list:");
            ui.printEmptyLine();
            ui.printArray(resources.generateAvailableList());
            ui.printEmptyLine();
            ui.printArray(resources.generateBookedList());
            ui.printLine();
        }
        else if (listType.equals("date")) {
            ui.printLine();
            ui.print("Here are the resources in your list by date:");
            ui.printEmptyLine();
            ui.printArray(resources.generateAvailableListByDate(stringDate));
            ui.printEmptyLine();
            ui.printArray(resources.generateBookedListByDate(stringDate));
            ui.printLine();
        }
        else if (listType.equals("item")) {
            ui.printLine();
            ui.print("Here are the resources in your list by item name:");
            ui.printEmptyLine();
            ui.printArray(resources.generateListByItem(item));
            ui.printLine();
        }
        else if (listType.equals("room")) {
            ui.printLine();
            ui.print("Here are the resources in your list by room name:");
            ui.printArray(resources.generateListByRoom(room));
//            ui.printEmptyLine();
//            ui.printArray(resources.generateBookedListByDate(itemName));
            ui.printLine();
        }
    }
}
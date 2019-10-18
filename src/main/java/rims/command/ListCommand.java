package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

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
            ui.print("Here are the resources in your list on this date:");
            ui.printEmptyLine();
            ui.printArray(resources.generateAvailableListByDate(param));
            ui.printEmptyLine();
            ui.printArray(resources.generateBookedListByDate(param));
            ui.printLine();
        }
        else if (listType.equals("item")) {
            ui.printLine();
            ui.print("Here are the " + param + "s in your list:");
            ui.printEmptyLine();
            ui.printArray(resources.generateListByItem(param));
            ui.printLine();
        }
        else if (listType.equals("room")) {
            ui.printLine();
            ui.print("Here is the status of " + param + ":");
            ui.printArray(resources.generateListByRoom(param));
//            ui.printEmptyLine();
//            ui.printArray(resources.generateBookedListByDate(itemName));
            ui.printLine();
        }
    }
}
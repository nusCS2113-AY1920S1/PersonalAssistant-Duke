package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ListByItemCommand extends Command{
    protected String itemName;

    public ListByItemCommand(String itemName){
        this.itemName = itemName;
    }

    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException {
        ui.printLine();
        ui.print("Here are the resources in your list by item name:");
        ui.printEmptyLine();
        ui.printArray(resources.generateListByItem(itemName));
//        ui.printEmptyLine();
//        ui.printArray(resources.generateBookedListByDate(itemName));
        ui.printLine();
    }

}


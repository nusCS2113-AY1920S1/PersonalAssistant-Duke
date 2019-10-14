package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ListByDateCommand extends Command{

    protected String stringDate;

    public ListByDateCommand(String stringDate){
        this.stringDate = stringDate;
    }

    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException {
        ui.printLine();
        ui.print("Here are the resources in your list by date:");
        ui.printEmptyLine();
        ui.printArray(resources.generateAvailableListByDate(stringDate));
        ui.printEmptyLine();
        ui.printArray(resources.generateBookedListByDate(stringDate));
        ui.printLine();
    }
}

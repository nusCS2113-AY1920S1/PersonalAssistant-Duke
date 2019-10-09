package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ListCommand extends Command {
    public void execute(Ui ui, Storage storage, ResourceList resources) {
        ui.printLine();
        ui.print("Here are the resources in your list:");
        ui.printEmptyLine();
        ui.printArray(resources.generateAvailableList());
        ui.printEmptyLine();
        ui.printArray(resources.generateBookedList());
        ui.printLine();
    }
}
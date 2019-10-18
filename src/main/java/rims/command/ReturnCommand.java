package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ReturnCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected int loanId;

    public ReturnCommand(String itemName, int qty, int loanId) {
        resourceName = itemName;
        this.qty = qty;
        this.loanId = loanId;

    }
    public ReturnCommand(String roomName, int loanId) {
        resourceName = roomName;
        this.qty = 1;
        this.loanId = loanId;
    }
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        if (qty > resources.getBookedQuantityOfOrder(resourceName, loanId)) {
            // throw Exception
        }
        int actualQty = 0;
        Resource thisResource = null;
        for (int i = 0; i < qty; i++) {
            thisResource = resources.getBookedResource(resourceName, loanId);
            thisResource.markAsReturned();
        }
        if (thisResource != null) {
            ui.printLine();
            ui.print("Done! I've marked these resources as returned:");
            if (thisResource.getType() == 'I') {
                ui.print(thisResource.toString() + " (qty: " + Integer.toString(qty) + ")");
            }
            else if (thisResource.getType() == 'R') {
                ui.print(thisResource.toString());
            }
            ui.printLine();
        }
    }
}
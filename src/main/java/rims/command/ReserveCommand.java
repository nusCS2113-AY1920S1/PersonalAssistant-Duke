package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ReserveCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected int loanId;
    protected String startDate;
    protected String endDate;

    public ReserveCommand(String itemName, int qty, int loanId, String startDate, String endDate) {
        resourceName = itemName;
        this.qty = qty;
        this.loanId = loanId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ReserveCommand(String roomName, int loanId, String startDate, String endDate) {
        resourceName = roomName;
        this.qty = 1;
        this.loanId = loanId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        if (resources.getAvailableQuantity(resourceName) < qty) {
            // throw Exception
        }
        Resource thisResource = null;
        for (int i = 0; i < qty; i++) {
            thisResource = resources.getAvailableResource(resourceName);
            thisResource.markAsBooked(startDate, endDate, loanId);
        }
        if (thisResource != null) {
            ui.printLine();
            ui.print("Done! I've marked these resources as reserved:");
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
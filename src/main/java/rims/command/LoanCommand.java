package rims.command;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Resource;

public class LoanCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected int loanId;
    protected String stringDate;

    public LoanCommand(String itemName, int qty, int loanId, String stringDate) {
        resourceName = itemName;
        this.qty = qty;
        this.loanId = loanId;
        this.stringDate = stringDate;
    }

    public LoanCommand(String roomName, int loanId, String stringDate) {
        resourceName = roomName;
        this.qty = 1;
        this.loanId = loanId;
        this.stringDate = stringDate;
    }

    // public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
    //     if (resources.getAvailableQuantity(resourceName) < qty) {
    //         // throw Exception
    //     }
    //     Resource thisResource = null;
    //     for (int i = 0; i < qty; i++) {
    //         thisResource = resources.getAvailableResource(resourceName);
    //         thisResource.markAsBooked(stringDate, loanId, qty, ui);
    //     }
    //     if (thisResource != null) {

    //     }
    // }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reserves) throws Exception {
        // TODO Auto-generated method stub

    }
}
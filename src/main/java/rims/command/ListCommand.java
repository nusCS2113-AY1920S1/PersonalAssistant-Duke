package rims.command;

import java.text.ParseException;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

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

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException {
        if (listType == null) {
            ui.ErrorPrint("Resources");
            ui.printEmptyLine();
            ui.printResourceArrayWithReservations(resources.getResourceList());
            ui.printEmptyLine();            
        }
//         else if (listType.equals("date")) {
//             ui.printLine();
//             ui.print("Here are the resources in your list on this date:");
//             ui.printEmptyLine();
//             ui.printArray(resources.generateAvailableListByDate(param));
//             ui.printEmptyLine();
//             ui.printArray(resources.generateBookedListByDate(param));
//             ui.printLine();
//         }
//         else if (listType.equals("item")) {
//             ui.printLine();
//             ui.print("Here are the " + param + "s in your list:");
//             ui.printEmptyLine();
//             ui.printArray(resources.generateListByItem(param));
//             ui.printLine();
//         }
//         else if (listType.equals("room")) {
//             ui.printLine();
//             ui.print("Here is the status of " + param + ":");
//             ui.printArray(resources.generateListByRoom(param));
// //            ui.printEmptyLine();
// //            ui.printArray(resources.generateBookedListByDate(itemName));
//             ui.printLine();
//         }
    }
}
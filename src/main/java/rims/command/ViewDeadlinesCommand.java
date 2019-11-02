package rims.command;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Resource;
import rims.exception.RimsException;

/**
 * Shows a sorted list of all the upcoming/overdue deadlines.
 */
// @@author aarushisingh1
public class ViewDeadlinesCommand extends Command {
    /**
     * Prints out a sorted list of all the upcoming/overdue deadlines.
     * 
     * @param ui        An instance of the user interface.
     * @param storage   An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus
     *                  far.
     * @throws ParseException if the resource name is invalid
     * @throws RimsException  for any other unexpected error
     */
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        ui.printLine();
        ArrayList<String> coveredResources = new ArrayList<String>();
        ArrayList<Resource> sortedList = new ArrayList<Resource>();
        ui.print("CURRENTLY ACTIVE LOANS AND RESERVATIONS:");
        ui.printEmptyLine();
        for (int i = 0; i < resources.size(); i++) {
            Resource thisResource = resources.getResourceByIndex(i);
            int bookedNumberOfResource = resources.getBookedNumberOfResource(thisResource.getName());
            if (!coveredResources.contains(thisResource.getName()) && bookedNumberOfResource > 0) {
                coveredResources.add(thisResource.getName());
                ArrayList<Resource> allOfResource = resources.getAllOfResource(thisResource.getName());
                for (int j = 0; j < allOfResource.size(); j++) {
                    if (!allOfResource.get(j).isCurrentlyAvailable()) {
                        sortedList.add(allOfResource.get(j));
                    }
                }

            }
        }
        Collections.sort(sortedList, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                try {
                    return o1.getReservations().getCurrentBooking().getEndDate()
                            .compareTo(o2.getReservations().getCurrentBooking().getEndDate());
                } catch (RimsException e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });
        for (int j = 0; j < sortedList.size(); j++) {
            ui.print(sortedList.get(j).toString() + ":");
            ui.print("\t" + sortedList.get(j).getReservations().getCurrentBooking().toString());
        }
        ui.printLine();
    }
}

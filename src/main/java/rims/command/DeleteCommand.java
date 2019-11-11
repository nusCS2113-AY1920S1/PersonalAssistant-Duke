package rims.command;

import java.io.IOException;

import java.util.ArrayList;

import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;

import rims.resource.Resource;
import rims.resource.Item;
import rims.resource.Room;
import rims.resource.ReservationList;

import rims.exception.RimsException;

//@@author hin1
/**
 * Implements the deletion of a Resource from the ResourceList.
 */
public class DeleteCommand extends Command {
    protected String resourceName;
    protected String resourceType;
    protected int qty;

    /**
     * Constructor for a DeleteCommand, that takes in the name and type of the
     * Resource to be deleted.
     *
     * @param resourceName the name of the Resource to be deleted.
     * @param resourceType the type (Item or Room) of the Resource to be deleted.
     */
    public DeleteCommand(String resourceName, String resourceType) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.canModifyData = true;
        this.commandUserInput = "delete " + resourceName + " (" + resourceType + ")" + "IDs: ";
    }

    /**
     * Obtains the resource IDs of the Resources to be deleted from the user,
     * removes them from the ResourceList, and prints a message to the CLI that the
     * Resource objects have been successfully deleted.
     *
     * @param ui        An instance of the user interface.
     * @param storage   An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus
     *                  far.
     * @throws RimsException if the resource IDs specified by the user are invalid
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        storage.saveToFile(resources.getResources());
        if (resourceType.equals("room")) {
            Resource thisResource = resources.getResourceByName(resourceName);
            resources.deleteResourceByName(resourceName);
            ui.printLine();
            ui.print("The following room has been successfully deleted:");
            ui.print(thisResource.toString());
            ui.printLine();
        } else if (resourceType.equals("item")) {
            ArrayList<Resource> allOfItem = resources.getAllOfResource(resourceName);
            if (allOfItem.isEmpty()) {
                throw new RimsException("This resource does not exist in your inventory!");
            }
            ui.printLine();
            for (int i = 0; i < allOfItem.size(); i++) {
                Resource thisResource = allOfItem.get(i);
                ReservationList thisResourceReservations = thisResource.getReservations();
                ui.printDash();
                ui.print(thisResource.toString() + " (resource ID: " + thisResource.getResourceId() + ")");
                if (!thisResourceReservations.isEmpty()) {
                    for (int j = 0; j < thisResourceReservations.size(); j++) {
                        ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                    }
                } else {
                    ui.print("No bookings for this resource yet!");
                }
            }
            //@@author rabhijit
            ui.printDash();
            String idInput = ui.getInput(
                    "Type in the resource ID(s) (separated by a space for multiple IDs) "
                    + "that you wish to delete:").trim();
            if (idInput.isEmpty()) {
                throw new RimsException("Please specify the ID(s) of the resources you wish to delete!");
            }
            String[] splitIdInput = idInput.split(" ");
            ArrayList<Integer> intIdInput = new ArrayList<Integer>();
            for (int i = 0; i < splitIdInput.length; i++) {
                try {
                    intIdInput.add(Integer.parseInt(splitIdInput[i]));
                } catch (NumberFormatException e) {
                    throw new RimsException("Please enter valid integer IDs to delete resources!");
                }
            }
            ArrayList<Resource> deletedResources = new ArrayList<Resource>();
            for (int j = 0; j < intIdInput.size(); j++) {
                Resource thisResource = resources.getResourceById(intIdInput.get(j));
                if (!thisResource.getName().equals(resourceName)) {
                    throw new RimsException("Please specify an ID that belongs to the "
                            + "requested resource!");
                }
            }
            for (int k = 0; k < intIdInput.size(); k++) {
                Resource thisResource = resources.getResourceById(intIdInput.get(k));
                deletedResources.add(thisResource);
                resources.deleteResourceById(intIdInput.get(k));
            }
            qty = deletedResources.size();

            ui.printLine();
            ui.print("The following item(s) have been successfully deleted:");
            for (int k = 0; k < deletedResources.size(); k++) {
                Resource thisDeletedResource = deletedResources.get(k);
                ui.print(thisDeletedResource.toString()
                        + " (resource ID: " + thisDeletedResource.getResourceId() + ")");
            }
            ui.printLine();

            addIdsToCommandUserInput(intIdInput); //Adds information of IDs deleted in commandUserInput
        }
    }

    //@@author hin1
    private void addIdsToCommandUserInput(ArrayList<Integer> idArray) {
        for (int i : idArray) {
            commandUserInput += (i + ", ");
        }
    }
}

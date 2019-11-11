package rims.command;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.util.converter.DateTimeStringConverter;
import rims.core.ResourceList;
import rims.core.Rims;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.*;
import rims.exception.RimsException;

//author aarushisingh1
public class TagCommand extends Command{
    protected String tagName = null;
    protected String resourceName = null;

    /**
     * The constructor for a TagCommand, when a detailed list of a particular
     * tag is desired.
     *
     * @param tagName the tag type of Resource desired
     */
    public TagCommand(String tagName, String resourceName) {
        this.tagName = tagName;
        this.resourceName = resourceName;
        canModifyData = true;
        commandUserInput = "list all resources by" + tagName;
    }

    /**
     * Creates the new tag, adds objects with that tag to the TagList, and prints a message to the CLI
     * that the tag has been successfully created.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException{
        ArrayList<Resource> allOfItem = resources.getAllOfResource(resourceName);
        TagList newTagList = Rims.tags;
        for(int i = 0; i < allOfItem.size(); i++) {
            Resource thisResource = allOfItem.get(i);
            if(thisResource.getReservations().size() == 0){
                Tag thisTag = new Tag(thisResource.getResourceId(), thisResource.getName(), tagName, thisResource.getType());
                newTagList.add(thisTag);
            }else{
                Tag thisTag = new Tag(thisResource.getResourceId(), thisResource.getName(), thisResource.getReservations(), tagName, thisResource.getType());
                newTagList.add(thisTag);
            }
        }
        ui.print("I have done so successfully");
    }
}

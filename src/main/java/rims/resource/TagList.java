package rims.resource;

import rims.core.Rims;
import rims.core.Ui;
import rims.exception.RimsException;

import java.util.ArrayList;

//author aarushisingh1

public class TagList {
    protected Ui ui;
    protected ArrayList<Tag> tags;

    /**
     * Constructor for the ResourceList. Takes in an array of Resources from the
     * Storage instance and saves it.
     *
     * @param tags the array of tagged Resources, as converted from text in the
     *                  save-file by the Storage instance
     */
    public TagList(Ui ui, ArrayList<Tag> tags) throws RimsException {
        this.ui = ui;
        this.tags = tags;
    }

    /**
     * Adds a new tagged Resource to the TagList.
     *
     * @param thisTag the newly created tag.
     */
    public void add(Tag thisTag) {
        tags.add(thisTag);
    }

    /**
     * Returns the TagList itself.
     *
     * @return the array of tagged Resources.
     */
    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * Returns a tagged Resource in the array by its index number in the array.
     *
     * @param indexNo the index number of the desired Resource.
     * @return the Resource itself.
     */
    public Tag getTagByIndex(int indexNo) {
        return tags.get(indexNo);
    }

    /**
     * Returns an array of all the resources of a tag.
     *
     * @param tagName the name of the Resources to be obtained.
     * @return an array of all the Resources with that tag name.
     */
    public ArrayList<Tag> getAllOfResource(String tagName) {
        ArrayList<Tag> allOfTag = new ArrayList<Tag>();
        for (int i = 0; i < tags.size(); i++) {
            Tag thisTag = getTagByIndex(i);
            if (thisTag.getTagName().equals(tagName)) {
                allOfTag.add(thisTag);
            }
        }
        return allOfTag;
   }

   public boolean tagExists(String tagName){
       ArrayList<Tag> newTagList = Rims.tags.getAllOfResource(tagName);
          if(newTagList.size()>0){
              return true;
          }
          return false;
   }

}

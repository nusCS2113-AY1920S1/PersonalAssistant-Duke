package duke.components;

import java.util.ArrayList;

public class VerseList {

    //@@author Samuel787
    private ArrayList<Group> verseList = new ArrayList<>();

    /**
     * Adds a verse to the verselist.
     * @param group refers to the verse to be added to the verse list
     */
    public void add(Group group) {
        this.verseList.add(group);
    }

    /**
     * Finds a verse in the verselist based on the name input by the user.
     * @param name name of the verse
     * @return returns the group corresponding to the name entered by the user
     */
    public Group find(String name) {
        int size = verseList.size();
        for (int i = 0; i < size; i++) {
            if (verseList.get(i).getName().equals(name)) {
                return verseList.get(i);
            }
        }
        return null;
    }

}

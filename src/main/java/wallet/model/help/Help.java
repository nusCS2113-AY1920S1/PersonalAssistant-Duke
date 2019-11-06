//@@author Xdecosee

package wallet.model.help;

import java.util.ArrayList;

public class Help {

    private String choice;
    private ArrayList<String> sectionData;


    /**
     * Stores a help section content.
     */
    public Help(String choice, ArrayList<String> sectionData) {
        this.choice = choice;
        this.sectionData = sectionData;
    }

    /**
     * Get section header.
     *
     * @return section header.
     */
    public String getChoice() {
        return choice;
    }

    /**
     * Get section data.
     *
     * @return list of section data.
     */
    public ArrayList<String> getSectionData() {
        return sectionData;
    }


}

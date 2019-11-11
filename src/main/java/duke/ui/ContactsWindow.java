package duke.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

//@@author talesrune
/**
 * Controller for ContactsWindow. Provides the layout for the other controls.
 */
public class ContactsWindow extends AnchorPane {
    @FXML
    private TextArea taContactsList;

    /**
     * Setting up Contacts Window Interface.
     *
     * @param contactsDesc The contact details.
     */
    @FXML
    public void setContactsWindow(String contactsDesc) {
        taContactsList.setText(contactsDesc);
    }
}
//@@author
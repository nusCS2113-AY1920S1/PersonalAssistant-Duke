package optix.ui.windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Prompts user on the correct Command Input format.
 */
public class HelpWindow extends VBox {
    private static final String ADD_MENU = "To add a new show:         "
                                           + "\nadd SHOW_NAME | SEATS_BASE_PRICE | SHOW_DATE1 | SHOW_DATE2 | ...\n\n";

    private static String DELETE_MENU = "To delete shows:           "
                                        + "\ndelete SHOW_NAME | SHOW_DATE1 | SHOW_DATE2 | ...\n\n";

    private static String VIEW_MENU = "To view seats for show:    "
                                     + "\nview SHOW_NAME | SHOW_DATE\n\n";

    private static String SELL_MENU = "To sell seats for show:    "
                                      + "\nsell SHOW_NAME | SHOW_DATE | SEAT1 SEAT2 SEAT3 ...\n\n";

    private static String LIST_MENU = "To list shows:              \nlist\n\n"
                                      + "To list specific show:     \nlist SHOW_NAME\n\n"
                                      + "To list shows with date:   \nlist MONTH YEAR\n\n";

    private static String RESCHEDULE_MENU = "To reschedule show:        "
                                          + "\nreschedule SHOW_NAME | OLD_DATE | NEW_DATE\n\n";

    private static String EDIT_MENU = "To edit show name:         "
                                      + "\nedit OLD_SHOW_NAME | SHOW_DATE | NEW_SHOW_NAME\n\n";

    private static String ALIAS_MENU = "To add alias:               "
                                        + "\nadd-alias ALIAS | COMMAND\n\n"
                                        + "To remove alias:           "
                                        + "\nremove-alias ALIAS | COMMAND\n\n"
                                        + "To reset alias:            "
                                        + "\nreset-alias\n\n"
                                        + "To list alias:             "
                                        + "\nlist-alias\n\n";

    private static String PROFIT_MENU = "To view profits for a show:                   "
                                        + "\nview-profit SHOW_NAME | SHOW_DATE\n\n"
                                        + "To view monthly profits:                      "
                                        + "\nview-monthly MONTH YEAR\n\n";


    @FXML
    private Label showHelpLbl;
    @FXML
    private Label financeHelpLbl;
    @FXML
    private Label seatHelpLbl;
    @FXML
    private Label aliasHelpLbl;

    private HelpWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/HelpWindow.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showHelpLbl.setText(ADD_MENU + DELETE_MENU + LIST_MENU + RESCHEDULE_MENU + EDIT_MENU);
        seatHelpLbl.setText(SELL_MENU  + VIEW_MENU);
        financeHelpLbl.setText(PROFIT_MENU);
        aliasHelpLbl.setText(ALIAS_MENU);
    }

    public static VBox getHelpWindow() {
        return new HelpWindow();
    }
}

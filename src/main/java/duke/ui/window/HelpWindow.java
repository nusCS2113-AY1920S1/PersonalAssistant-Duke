package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.Help;
import duke.data.storage.GsonStorage;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.HelpCard;
import duke.ui.commons.UiElement;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//@@author gowgos5
/**
 * UI element for the help window.
 */
public class HelpWindow extends UiElement<Region> implements ChangeListener<String> {
    private static final String FXML = "HelpWindow.fxml";
    private static final String HELP_FILE = "data" + File.separator + "helpDetails.json";

    @FXML
    private JFXListView<HelpCard> helpListView;
    @FXML
    private AnchorPane emptyPane;

    private List<Help> helpList;
    private List<Help> contextedHelpList;

    /**
     * Constructs a help window.
     *
     * @param storage   GSON storage object.
     * @param uiContext UiContext object.
     * @throws DukeException If the data file cannot be loaded by the GSON storage object.
     */
    public HelpWindow(GsonStorage storage, UiContext uiContext) throws DukeException {
        super(FXML, null);

        initialise(storage);
        attachListenerToContext(uiContext);
        attachListenerToListView();
    }

    /**
     * Initialises the {@link #helpList} and {@link #helpListView} for the Home context.
     *
     * @param storage GSON storage object.
     * @throws DukeException If the data file cannot be loaded.
     */
    private void initialise(GsonStorage storage) throws DukeException {
        helpList = storage.loadHelpList(HELP_FILE);
        update(Context.HOME);
    }

    /**
     * Updates the {@link #contextedHelpList} and {@link #helpListView} for a given context.
     *
     * @param context The current context.
     */
    private void update(Context context) {
        contextedHelpList = new ArrayList<>();
        helpListView.getItems().clear();

        helpList.forEach(help -> {
            if (help.getContext() == context) {
                contextedHelpList.add(help);
                try {
                    helpListView.getItems().add(new HelpCard(help, false));
                } catch (DukeFatalException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Attaches a listener to the {@link UiContext} to check for changes in context.
     *
     * @param uiContext UiContext object.
     */
    private void attachListenerToContext(UiContext uiContext) {
        uiContext.addListener(event -> update((Context) event.getNewValue()));
    }

    /**
     * Attaches a listener to the {@code helpListView} to check for empty list.
     */
    private void attachListenerToListView() {
        this.helpListView.getItems().addListener((ListChangeListener<HelpCard>) change -> {
            if (this.helpListView.getItems().size() == 0) {
                this.emptyPane.setVisible(true);
            } else {
                this.emptyPane.setVisible(false);
            }
        });
    }

    /**
     * Implements a listener for the input text field of the {@link CommandWindow} to listen for text changes.
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        List<Help> filteredHelpList = new ArrayList<>();
        helpListView.getItems().clear();

        for (Help help : contextedHelpList) {
            String command = help.getCommand();

            if (newValue.contains(command)) {
                filteredHelpList.add(help);
                break;
            } else if (command.contains(newValue)) {
                filteredHelpList.add(help);
            }
        }

        final boolean isDetailsShown = (filteredHelpList.size() == 1);
        filteredHelpList.forEach(help -> {
            try {
                helpListView.getItems().add(new HelpCard(help, isDetailsShown));
            } catch (DukeFatalException e) {
                e.printStackTrace();
            }
        });
    }
}

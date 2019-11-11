package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.HelpList;

import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandIntegrationTest {
    private String output;
    private HelpList helpText;
    private ModuleContainer testContainer;
    private ArrayDeque<String> pageTrace;
    private Ui ui;

    /**
     * Initialize the set up which involves initialising the pageTrace, ui and testContainer.
     * @throws FileCreationException If there is an error when creating new files.
     * @throws DataReadWriteException If there is an error reading/writing to the files.
     * @throws CorruptedDataException If the data within the files have been corrupted.
     */
    private void initializeSetUp() throws FileCreationException, DataReadWriteException, CorruptedDataException {
        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);
        testContainer = new ModuleContainer();
    }

    /**
     * Executes the command that is provided to the Parser.
     * @param userInput The String input provided to the test.
     * @throws SpinBoxException If there are storage errors or input errors.
     */
    private void executeCommand(String userInput) throws SpinBoxException {
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(userInput);
        output = command.execute(testContainer, pageTrace, ui, false);
    }

    @Test
    public void loadHelpList_correctInputProvided_helpListSuccessfullyLoaded() throws
            SpinBoxException {
        initializeSetUp();

        String fullHelpListInput = "help";
        executeCommand(fullHelpListInput);
        assertEquals(output, helpText.helpOnly);

        String viewHelpListInput = "help / view";
        executeCommand(viewHelpListInput);
        assertEquals(output, helpText.view);

        String addHelpListInput = "help / add";
        executeCommand(addHelpListInput);
        assertEquals(output, helpText.add);

        String removeHelpListInput = "help / remove";
        executeCommand(removeHelpListInput);
        assertEquals(output, helpText.remove);

        String removeMultipleHelpListInput = "help / remove-*";
        executeCommand(removeMultipleHelpListInput);
        assertEquals(output, helpText.removeMultiple);

        String setDateHelpListInput = "help / set-date";
        executeCommand(setDateHelpListInput);
        assertEquals(output, helpText.setDate);

        String setNameHelpListInput = "help / set-name";
        executeCommand(setNameHelpListInput);
        assertEquals(output, helpText.setName);

        String updateHelpListInput = "help / update";
        executeCommand(updateHelpListInput);
        assertEquals(output, helpText.update);

        String updateMultipleHelpListInput = "help / update-*";
        executeCommand(updateMultipleHelpListInput);
        assertEquals(output, helpText.updateMultiple);

        String exportHelpListInput = "help / export";
        executeCommand(exportHelpListInput);
        assertEquals(output, helpText.export);

        String scoreHelpListInput = "help / score";
        executeCommand(scoreHelpListInput);
        assertEquals(output, helpText.score);

        String populateHelpListInput = "help / populate";
        executeCommand(populateHelpListInput);
        assertEquals(output, helpText.populate);
    }

    @Test
    public void loadHelpList_invalidInputProvided_exceptionThrown() throws
            SpinBoxException {
        initializeSetUp();

        try {
            String fullHelpListInput = "help / random";
            executeCommand(fullHelpListInput);
        } catch (InputException e) {
            assertEquals("Invalid Input\n\nThe command specified is not a valid command. Type 'help'"
                    + " to view the full list of commands available.", e.getMessage());
        }
    }
}
package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.HelpList;

import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandIntegrationTest {
    private String output;
    private HelpList helpText;
    private ModuleContainer testContainer;
    private ArrayDeque<String> pageTrace;
    private Command command;
    private Ui ui;

    @Test
    public void loadHelpList_correctInputProvided_helpListSuccessfullyLoaded() throws
            SpinBoxException {
        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);
        testContainer = new ModuleContainer();

        String fullHelpListInput = "help";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(fullHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.helpOnly);

        String viewHelpListInput = "help / view";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(viewHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.view);

        String addHelpListInput = "help / add";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(addHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.add);

        String removeHelpListInput = "help / remove";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(removeHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.remove);

        String removeMultipleHelpListInput = "help / remove-*";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(removeMultipleHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.removeMultiple);

        String setDateHelpListInput = "help / set-date";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setDateHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.setDate);

        String setNameHelpListInput = "help / set-name";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.setName);

        String updateHelpListInput = "help / update";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(updateHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.update);

        String updateMultipleHelpListInput = "help / update-*";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(updateMultipleHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.updateMultiple);

        String exportHelpListInput = "help / export";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(exportHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.export);

        String scoreHelpListInput = "help / score";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(scoreHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.score);

        String populateHelpListInput = "help / populate";
        helpText = new HelpList();
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(populateHelpListInput);
        output = command.execute(testContainer, pageTrace, ui, false);

        assertEquals(output, helpText.populate);
    }

    @Test
    public void loadHelpList_invalidInputProvided_exceptionThrown() throws
            SpinBoxException {
        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);
        testContainer = new ModuleContainer();

        try {
            String fullHelpListInput = "help / random";
            helpText = new HelpList();
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(fullHelpListInput);
            output = command.execute(testContainer, pageTrace, ui, false);
        } catch (InputException e) {
            assertEquals("Invalid Input\n\nThe command specified is not a valid command. Type 'help'"
                    + " to view the full list of commands available.", e.getMessage());
        }
    }
}

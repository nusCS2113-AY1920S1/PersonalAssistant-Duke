//@@author LongLeCE

package planner.logic.command;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.parser.Parser;
import planner.main.InputTest;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public class CommandTest extends InputTest {

    User user;
    HashMap<String, ModuleInfoDetailed> modDetail;
    PlannerUi ui;
    Storage storage;
    JsonWrapper json;
    ModuleCommand command;
    Parser parser;

    public CommandTest() throws ModException {
        init();
    }

    private void init() throws ModException {
        ui = new PlannerUi();
        ui.setOutput(outContent, errContent);
        storage = new Storage();
        json = new JsonWrapper();
        modDetail = json.getModuleDetailedMap(true, storage);
        User.setPath("data/test/userProfile.json");
        user = User.loadProfile(modDetail, ui, storage, json);
        resetAll();
        parser = new Parser();
    }

    /**
     * Execute command.
     * @param input to be parsed into command
     */
    public void execute(String input) {
        ByteArrayInputStream inputStream = getInputStream(input);
        ui.setInput(inputStream);
        for (input = ui.readInput(inputStream);
             input != null;
             input = ui.readInput(inputStream)) {
            try {
                command = parser.parseCommand(input);
                if (command != null) {
                    command.call(modDetail, ui, storage, json, user);
                }
            } catch (ModException ex) {
                ex.printStackTrace();
            }
        }
    }

    private ByteArrayInputStream getInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    /**
     * Get output.
     * @param reset whether to reset output stream
     * @return output
     */
    public String getOut(boolean reset) {
        String outputString = outContent.toString();
        if (reset) {
            outContent.reset();
        }
        return standardize(outputString);
    }

    public String getOut() {
        return getOut(true);
    }

    /**
     * Get error.
     * @param reset whether to reset error stream
     * @return error
     */
    public String getErr(boolean reset) {
        String outputString = errContent.toString();
        if (reset) {
            errContent.reset();
        }
        return standardize(outputString);
    }

    public String getErr() {
        return getErr(true);
    }

    private String standardize(String s) {
        return s.replaceAll("\r\n", "\n");
    }

    /**
     * Reset all data for clean test.
     */
    public void resetAll() { // For clean test
        user.clear();
        outContent.reset();
        errContent.reset();
    }
}

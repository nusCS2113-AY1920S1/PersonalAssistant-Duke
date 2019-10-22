package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.util.ArrayList;

/**
 * Represents a Command to print a help manual.
 */
public class HelpCommand extends Command {
    private String keyword;
    private static final int COMMANDS_BEGIN = 8;

    /**
     * Constructor for individual Help Command.
     *
     * @param keyword to indicate keyword that help is needed for.
     */
    public HelpCommand(String keyword) {
        super();
        this.keyword = keyword;
    }

    /**
     * Prints a help manual.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        ArrayList<String> commands = storage.readManual();
        if (keyword.isEmpty()) {
            ui.printHelpCommands();
        } else {
            String description = individualQuery(keyword, commands);
            ui.printHelpCommand(description);
        }
    }

    /**
     * Get instruction for individual Help Command.
     *
     * @param keyword keyword where individual instruction is needed.
     * @return description      use of individual instruction.
     * @throws OofException if keyword is invalid.
     */
    private String individualQuery(String keyword, ArrayList<String> commands) throws OofException {
        String description = null;
        for (int i = COMMANDS_BEGIN; i < commands.size(); i++) {
            if (isMatchCommand(keyword, commands.get(i))) {
                description = commands.get(i);
                break;
            }
        }
        if (description == null) {
            throw new OofException("Invalid keyword!");
        }
        return description;
    }

    /**
     * Check if keyword and command String values match.
     *
     * @param keyword given by user.
     * @param command stored in manual.txt.
     * @return boolean True if String values match.
     */
    private boolean isMatchCommand(String keyword, String command) {
        int keywordEnd = command.indexOf("  ");
        String instruction = null;
        if (isKeyword(keywordEnd)) {
            instruction = command.substring(0, keywordEnd).toUpperCase();
            keyword = keyword.toUpperCase();
        }
        return keyword.equals(instruction);
    }

    /**
     * Check if keyword exists.
     *
     * @param index supposed end of keyword
     * @return boolean True if end of keyword is more than zero (ie exists).
     */
    private boolean isKeyword(int index) {
        return index > 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

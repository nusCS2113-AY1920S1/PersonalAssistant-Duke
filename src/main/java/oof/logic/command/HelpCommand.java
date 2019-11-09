package oof.logic.command;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.commons.exceptions.command.OofManualNotFoundException;
import oof.model.university.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to print a help manual.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
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
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if help manual is missing or if user input contains invalid arguments.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        try {
            ArrayList<String> commands = storageManager.readManual();
            if (keyword.isEmpty()) {
                ui.printHelpCommands();
            } else {
                String description = individualQuery(keyword, commands);
                ui.printHelpCommand(description);
            }
        } catch (FileNotFoundException e) {
            throw new OofManualNotFoundException("Manual Unavailable!");
        }
    }

    /**
     * Get instruction for individual Help Command.
     *
     * @param keyword keyword where individual instruction is needed.
     * @return description      use of individual instruction.
     * @throws InvalidArgumentException if user input contains invalid arguments.
     */
    private String individualQuery(String keyword, ArrayList<String> commands) throws InvalidArgumentException {
        String description = null;
        for (int i = COMMANDS_BEGIN; i < commands.size(); i++) {
            if (isMatchCommand(keyword, commands.get(i))) {
                description = commands.get(i);
                break;
            }
        }
        if (description == null) {
            throw new InvalidArgumentException("Invalid keyword!");
        }
        return description;
    }

    /**
     * Check if keyword and command String values match.
     *
     * @param keyword given by user.
     * @param command stored in manual.txt.
     * @return True if String values match.
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
     * @return True if end of keyword is more than zero (i.e. exists).
     */
    private boolean isKeyword(int index) {
        return index > 0;
    }
}

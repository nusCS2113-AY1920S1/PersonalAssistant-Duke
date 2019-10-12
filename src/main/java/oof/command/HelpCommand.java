package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.util.ArrayList;

public class HelpCommand extends Command {
    private String keyword;
    private static final int COMMANDS_BEGIN = 8;

    /**
     * Constructor for individual Help Command.
     * @param keyword           to indicate keyword that help is needed for.
     */
    public HelpCommand(String keyword) {
        super();
        this.keyword = keyword;
    }

    /**
     * Get instruction for individual Help Command.
     * @param keyword           keyword where individual instruction is needed.
     * @return description      use of individual instruction
     */
    private String individualQuery(String keyword, ArrayList<String> commands) {
        String description = null;
        for (int i = COMMANDS_BEGIN; i < commands.size(); i++) {
            int keywordEnd = commands.get(i).indexOf("  ");
            if (keywordEnd > 0) {
                String command = commands.get(i).substring(0, keywordEnd).toUpperCase();
                keyword = keyword.toUpperCase();
                if (keyword.equals(command)) {
                    description = commands.get(i);
                    break;
                }
            }
        }
        return description; // return individual instruction
    }

    /**
     * Invokes other Command subclasses based on the input given by the user.
     * @param tasks   Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OofException {
        ArrayList<String> commands = storage.readManual();
        if (keyword.isEmpty()) {
            ui.printHelpCommands();
        } else {
            String instruction = individualQuery(keyword, commands);
            ui.printHelpCommand(instruction);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

package logic.command;

import common.DukeException;
import model.Model;

public class FindCommand extends Command {

    private static final String SUCCESS_MSSAGE = "The tasks below are with keyword: ";
    private static final String NOT_FOUND_MESSAGE  = "No such task with keyword: ";
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    //@@author yuyanglin28

    /**
     * This method is to get tasks' name or description with a certain keyword
     * @param model Model interface
     * @return tasks list with the certain keyword,
     * if just name contains that word, just show the name, status, deadline
     * if description contains that word, show all the things above and also description
     */
    @Override
    public CommandOutput execute(Model model) {
        String tasks = model.getTasksByKeyword(keyword);
        if (tasks.equals("")) {
            return new CommandOutput(NOT_FOUND_MESSAGE + keyword);
        } else {
            return new CommandOutput(SUCCESS_MSSAGE + keyword + tasks);
        }


    }
}

package logic.command;

import common.DukeException;
import model.Model;

public class FindCommand extends Command {

    private static final String SUCCESS_MSSAGE = "The tasks below are with keyword: ";
    private static final String NOT_FOUND_MESSAGE  = "no such task with keyword: ";
    private static final String FAIL_MSSAGE = "fail to do find command";
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        try {
            String tasks = model.getTasksByKeyword(keyword);
            System.out.println(keyword);
            if (tasks.equals("")) {
                return new CommandOutput(NOT_FOUND_MESSAGE + keyword);
            } else {
                return new CommandOutput(SUCCESS_MSSAGE + keyword + tasks);
            }
        } catch (Exception e) {
            throw new DukeException(FAIL_MSSAGE);
        }

    }
}

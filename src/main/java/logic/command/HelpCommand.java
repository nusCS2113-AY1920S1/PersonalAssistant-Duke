package logic.command;

import model.Model;


public class HelpCommand extends Command {

    /**
     * execute help command
     *
     * @param model no used
     * @return a string pass to commandOutput, which will be shown on window
     */
    public CommandOutput execute(Model model) {
        String output =
                "Available Commands\n"
                        + "1. basic add: add {task/member} [NAME]\n"
                        + "2. find: find [KEYWOED]\n"
                        + "3. done: done [INDEX]\n"
                        + "4. delete: delete {task/member} [INDEX]...\n"
                        + "5. link: link [INDEX] ... /to [MEMBER_NAME] ...\n"
                        + "6. unlink: unlink [INDEX] ... /from [MEMBER_NAME] ... \n"
                        + "7. list: list {tasks/members}"
                        + "8. edit: edit {task/member} {{time/des}/{bio/email/phone}} INDEX /to [change content]\n"
                        + "9. schedule: schedule {team/member} {all/todo} ([MEMBER_NAME])\n"
                        + "10. bye: bye\n";

        return new CommandOutput(output);
    }
}

package gazeeebo.commands.studyassist;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class studyassistCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException, DukeException, ParseException {
        System.out.println("Welcome to Study Assist!");
        while(!ui.fullCommand.equals("esc")) {
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("list")) {
                new studyPlannerCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            }
            ui.readCommand();
        }
        System.out.println("Back to the main page!");
    }
    @Override
    public boolean isExit() {
        return false;
    }
}

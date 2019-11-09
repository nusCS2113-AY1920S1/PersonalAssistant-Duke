package leduc.command;

import leduc.ui.Ui;
import leduc.exception.DukeException;
import leduc.exception.EmptyArgumentException;
import leduc.storage.Storage;
import leduc.task.TaskList;

/**
 * Represent the language command.
 * Allow to change the language of the software
 */
public class LanguageCommand extends Command {

    private static String languageShortcut = "language";

    /**
     * Constructor of Command.
     * @param userInput String which represent the input string of the user.
     */
    public LanguageCommand(String userInput) {
        super(userInput);
    }

    /**
     * Allow the user to change the language of the software
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws DukeException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        String userSubstring;
        if(isCalledByShortcut){
            userSubstring = userInput.substring(LanguageCommand.languageShortcut.length()).trim();
        }
        else {
            userSubstring = userInput.substring(8).trim();
        }
        if(userSubstring.isBlank()){
            throw new EmptyArgumentException();
        }
        if(userSubstring.equals("fr")){
            storage.setLanguage("fr");
            ui.showLanguage("French");
        }
        else if(userSubstring.equals("en")){
            storage.setLanguage("en");
            ui.showLanguage("English");
        }
        else{
            storage.setLanguage("en");
            ui.showErrorLanguage();
            ui.showLanguage("English");
        }
        storage.saveConfig();
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getLanguageShortcut() {
        return languageShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param languageShortcut the new shortcut
     */
    public static void setLanguageShortcut(String languageShortcut) {
        LanguageCommand.languageShortcut = languageShortcut;
    }
}

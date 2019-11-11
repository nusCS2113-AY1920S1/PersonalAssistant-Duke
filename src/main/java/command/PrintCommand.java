package command;

import degree.DegreeManager;
import javafx.HelpFX;
import ui.UI;
import task.TaskList;
import list.DegreeList;
import storage.Storage;
import exception.DukeException;

import java.util.List;
import java.util.Map;

/**
 * PrintCommand Class extends the abstract Command class.
 * Called when printing items in tasks
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class PrintCommand extends Command {
    private String command;
    private String input;
    public PrintCommand(String command) {
        this.command = command;
    }

    public PrintCommand(String command, String input)
    {
        this.command = command;
        this.input = input;
    }
//    protected PrintCommand() {
//    }

    /**
     * overwrites default execute to print task in tasks.
     *
     * @param tasks   TasksList Object being used currently
     * @param ui      UI in charge of printing messages
     * @param storage Storage in charge of loading and saving files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @param degreesManager
     * @throws DukeException DukeException thrown when unable to execute
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) throws DukeException {
        if(this.command.matches("tasks")) {
            tasks.print();
        }
        else if(this.command.matches("choices")) {
            lists.print();
        }
        else if(this.command.matches("detail")) {
            degreesManager.print(input);
        }
        else if(this.command.matches("compare")) {
            degreesManager.compare(input);
        }
        else if(this.command.matches("keywords")){
            System.out.println("<Keyword> : <Alias>, <Alias> ...");
            Map<String, List<String>> aliases = degreesManager.getKeywords();
            for(Map.Entry<String, List<String>> entry: aliases.entrySet()) {
                String key = entry.getKey();
                List<String> commonAliases = entry.getValue();
                StringBuilder myAliases = new StringBuilder();
                for(String name: commonAliases) {
                    if(!name.equalsIgnoreCase(key))
                        myAliases.append(name).append(", ");
                }
                myAliases.setLength(myAliases.length() - 2);
                System.out.println(key + ": " + myAliases.toString());
            }
        }
    }
}
package duke.commands;

import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import duke.Storage;
import duke.TaskList;
import duke.items.tasks.Task;
import duke.items.tasks.FileTask;
import duke.Ui;

import java.util.ArrayList;
import java.util.List;

public class FileCommand extends Command {
    private String action;
    private String fileName;
    private int index;

    /**
     * Constructor for file command, either adding, marking as downloaded, or viewing.
     * @param action the type of file action, either "add", "done", or "view".
     * @param fullCommand input user typed in this string.
     * @throws InputException input errors.
     */
    public FileCommand(String action, String fullCommand) throws InputException {
        String[] components = fullCommand.split(" ");
        this.action = action;
        switch (action) {
        case "add":
            this.fileName = fullCommand.replaceFirst("file add ", "");
            break;
        case "delete":
        case "done":
            try {
                this.index = Integer.parseInt(components[2]) - 1;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new InputException("Please provide an index. Eg. 'file done 5' or 'file delete 3'");
            }
            break;
        case "view":
            break;
        default:
            throw new InputException("Please specify the type of file command.\n"
                    + "file add <filename>, file done <index>, or file view."
            );
        }

        if (action.equals("add") && fileName.equals("")) {
            throw new InputException("☹ OOPS!!! The filename cannot be empty.");
        }

        this.setFileCommand(true);
    }

    /**
     * Either add the file into fileTaskList, mark a file as downloaded, or view all the files.
     * @param fileTaskList fileTaskList instance.
     * @param fileStorage fileStorage instance.
     * @param ui Ui instance.
     * @throws DukeException invalid index or storage error.
     */
    @Override
    public String execute(TaskList fileTaskList, Storage fileStorage, Ui ui) throws DukeException {
        List<String> formattedOutput = new ArrayList<>();
        List<Task> files;

        switch (action) {
        case "add":
            Task added = fileTaskList.addTask(new FileTask(fileName));
            formattedOutput.add("Got it. I've added this file:");
            formattedOutput.add(added.toString());
            break;
        case "done":
            try {
                Task downloaded = fileTaskList.markDone(index);
                fileStorage.setData(fileTaskList.getTasks());
                formattedOutput.add("Nice! I've marked this file as downloaded:");
                formattedOutput.add(downloaded.toString());
            } catch (IndexOutOfBoundsException e) {
                throw new InputException("Invalid index entered. Type 'file view' to see your files.");
            }
            break;
        case "delete":
            try {
                Task removed = fileTaskList.removeTask(index);
                files = fileTaskList.getTasks();
                fileStorage.setData(fileTaskList.getTasks());
                formattedOutput.add("Noted. I've removed this file:\n" + removed.toString());
                formattedOutput.add("You currently have " + files.size()
                        + ((files.size() == 1) ? " task in the list." : " tasks in the list."));
            } catch (IndexOutOfBoundsException e) {
                throw new InputException("Invalid index entered. Type 'file view' to see your files.");
            }
            break;
        default:
            files = fileTaskList.getTasks();
            formattedOutput.add("Here are the files in your list:");
            for (int i = 0; i < fileTaskList.getTasks().size(); i++) {
                formattedOutput.add(Integer.toString(i + 1) + ". " + files.get(i).toString());
            }
        }

        fileStorage.setData(fileTaskList.getTasks());
        return ui.showFormatted(formattedOutput);
    }


}

package leduc.command;

import leduc.Ui;
import leduc.exception.DukeException;
import leduc.storage.Storage;
import leduc.task.TaskList;

import java.util.ArrayList;

public class ShortcutCommand extends Command {

    public ShortcutCommand(String user){
        super(user);
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ArrayList<String> listShortcut = new ArrayList<>();
        String newShortcut="";
        do{
            ui.display("The precedent shortcut for bye is " + ByeCommand.getByeShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        ByeCommand.setByeShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for list is " + ListCommand.getListShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        ListCommand.setListShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for help is " + HelpCommand.getHelpShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        HelpCommand.setHelpShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for done is " + DoneCommand.getDoneShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        DoneCommand.setDoneShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for find is " + FindCommand.getFindShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        FindCommand.setFindShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for delete is " + DeleteCommand.getDeleteShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        DeleteCommand.setDeleteShortcut(newShortcut);
        listShortcut.add(newShortcut);


        do{
            ui.display("The precedent shortcut for deadline is " + DeadlineCommand.getDeadlineShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        DeadlineCommand.setDeadlineShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for event is " + EventCommand.getEventShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        EventCommand.setEventShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for todo is " + TodoCommand.getTodoShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        TodoCommand.setTodoShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for edit is " + EditCommand.getEditShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        EditCommand.setEditShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for postpone is " + PostponeCommand.getPostponeShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        PostponeCommand.setPostponeShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for snooze is " + SnoozeCommand.getSnoozeShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        SnoozeCommand.setSnoozeShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for reschedule is " + RescheduleCommand.getRescheduleShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        RescheduleCommand.setRescheduleShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for remind is " + RemindCommand.getRemindShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        RemindCommand.setRemindShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for sort is " + SortCommand.getSortShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        SortCommand.setSortShortcut(newShortcut);
        listShortcut.add(newShortcut);

        do{
            ui.display("The precedent shortcut for setWelcome is " + SetWelcomeCommand.getSetWelcomeShortcut() +" please enter new shortcut");
            newShortcut = ui.readCommand();
        } while(listShortcut.contains(newShortcut));
        SetWelcomeCommand.setSetWelcomeShortcut(newShortcut);
        listShortcut.add(newShortcut);

        ui.display("All shortcut has been set");



    }
}

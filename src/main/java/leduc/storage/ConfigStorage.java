package leduc.storage;

import leduc.command.*;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigStorage {
    private File file;

    public ConfigStorage(String file) throws FileException, MeaninglessException {
        this.file = new File(file);
        try {
            if(this.file.createNewFile()){ //if file exist, return false
                saveConfig();
            }
            else {
                loadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() throws FileException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            try{
                fileWriter.write("bye:" + ByeCommand.getByeShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(ByeCommand.getByeShortcut());
                fileWriter.write("list:" + ListCommand.getListShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(ListCommand.getListShortcut());
                fileWriter.write("help:" + HelpCommand.getHelpShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(HelpCommand.getHelpShortcut());
                fileWriter.write("done:" + DoneCommand.getDoneShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(DoneCommand.getDoneShortcut());
                fileWriter.write("find:" + FindCommand.getFindShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(FindCommand.getFindShortcut());
                fileWriter.write("delete:" + DeleteCommand.getDeleteShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(DeleteCommand.getDeleteShortcut());
                fileWriter.write("deadline:" + DeadlineCommand.getDeadlineShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(DeadlineCommand.getDeadlineShortcut());
                fileWriter.write("event:" + EventCommand.getEventShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(EventCommand.getEventShortcut());
                fileWriter.write("todo:" + TodoCommand.getTodoShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(TodoCommand.getTodoShortcut());
                fileWriter.write("edit:" + EditCommand.getEditShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(EditCommand.getEditShortcut());
                fileWriter.write("postpone:" + PostponeCommand.getPostponeShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(PostponeCommand.getPostponeShortcut());
                fileWriter.write("snooze:" + SnoozeCommand.getSnoozeShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(SnoozeCommand.getSnoozeShortcut());
                fileWriter.write("reschedule:" + RescheduleCommand.getRescheduleShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(RescheduleCommand.getRescheduleShortcut());
                fileWriter.write("remind:" + RemindCommand.getRemindShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(RemindCommand.getRemindShortcut());
                fileWriter.write("sort:" + SortCommand.getSortShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(SortCommand.getSortShortcut());
                fileWriter.write("setwelcome:" + SetWelcomeCommand.getSetWelcomeShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(SetWelcomeCommand.getSetWelcomeShortcut());
            }finally {
                fileWriter.close();
            }
        }catch(IOException e){
            e.printStackTrace();
            throw new FileException();
        }
    }

    public void loadConfig() throws FileException, MeaninglessException {
        Scanner sc = null;
        try {
            sc = new Scanner(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileException();
        }
        while(sc.hasNext()){
            String commandShortcut = sc.nextLine();
            String[] commandShortcutSplit = commandShortcut.split(":");
            ShortcutCommand.setOneShortcut(commandShortcutSplit[0].trim(), commandShortcutSplit[1].trim());
        }

    }
}

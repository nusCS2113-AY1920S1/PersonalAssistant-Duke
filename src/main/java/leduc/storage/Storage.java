package leduc.storage;

import leduc.Date;
import leduc.command.*;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.task.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Represents a leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private File file;
    private File configFile;

    /**
     * Constructor of leduc.storage.Storage
     * @param file String representing the path of the file
     * @param configFile String representing the path of the file storing the shortcut
     */
    public Storage(String file, String configFile) throws FileException, MeaninglessException {
        this.file = new File(file);
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.configFile = new File(configFile);
        try {
            if(this.configFile.createNewFile()){ //if file exist, return false
                saveConfig();
            }
            else {
                loadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read the file and write all the task to an array of task.
     * if the file is empty, the array is empty too
     * @return an array of task
     * @throws FileException thrown when there is a reading error of the file
     */
    public List<Task> load() throws FileException { // load the initial data file
        Scanner sc = null;
        try {
            sc = new Scanner(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileException();
        }
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] tokens = line.split("//");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            int priority = -1 ;
            switch (tokens[0]){
                case "T" :
                    if (tokens.length ==3 ){ // priority not specified
                        tasks.add(new TodoTask(tokens[2],tokens[1].trim()));
                    }
                    else {
                        priority =  Integer.parseInt(tokens[3].trim());
                        if (priority < 0 || priority > 9) { // the priority is set by default to 5
                            tasks.add(new TodoTask(tokens[2], tokens[1].trim()));
                        }
                        else {
                            tasks.add(new TodoTask(tokens[2], tokens[1].trim(),priority));
                        }
                    }
                    break;
                case "D" :
                    if (tokens.length ==4 ){ // priority not specified
                        tasks.add(new DeadlinesTask(tokens[2],tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter))));
                    }
                    else {
                        priority =  Integer.parseInt(tokens[4].trim());
                        if (priority < 0 || priority > 9) { // the priority is set by default to 5
                            tasks.add(new DeadlinesTask(tokens[2],tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter))));
                        }
                        else {
                            tasks.add(new DeadlinesTask(tokens[2],tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter)),priority));
                        }
                    }
                    break;
                case "E":
                    if (tokens.length ==5 ){ // priority not specified
                        tasks.add(new EventsTask(tokens[2], tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter)), new Date(LocalDateTime.parse(tokens[4], formatter))));
                    }
                    else {
                        priority =  Integer.parseInt(tokens[5].trim());
                        if (priority < 0 || priority > 9) { // the priority is set by default to 5
                            tasks.add(new EventsTask(tokens[2], tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter)), new Date(LocalDateTime.parse(tokens[4], formatter))));
                        }
                        else {
                            tasks.add(new EventsTask(tokens[2], tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter)), new Date(LocalDateTime.parse(tokens[4], formatter)),priority));
                        }
                    }
                    break;
            }
        }
        return tasks;
    }

    /**
     * write all task to the files
     * @param tasks all the tasks that have to be written to the file
     * @throws FileException thrown when there is writing problem to the files
     */
    public void save(ArrayList<Task> tasks) throws FileException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            try {

                for (Task task : tasks){
                    if (task.isTodo()) {
                        fileWriter.write("T//"+ task.getMark() +"//" + task.getTask() + "//" + task.getPriority() + "\n");
                    } else if (task.isDeadline()) {
                        fileWriter.write("D//"+ task.getMark() +"//" + task.getTask() + "//" + ((DeadlinesTask) task).getDeadlines().toString()+ "//" + task.getPriority() +"\n");
                    } else if (task.isEvent()) {
                        fileWriter.write("E//"+ task.getMark() +"//" + task.getTask() + "//" + ((EventsTask) task).getDateFirst().toString() + "//" + ((EventsTask) task).getDateSecond().toString() + "//" + task.getPriority() + "\n");
                    }
                }
            } finally {
                fileWriter.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
            throw new FileException();
        }
    }

    public void saveConfig() throws FileException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.configFile);
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
                fileWriter.write("prioritize:" + PrioritizeCommand.getPrioritizeShortcut() + "\n");
                ShortcutCommand.getSetShortcut().add(PrioritizeCommand.getPrioritizeShortcut());
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
            sc = new Scanner(this.configFile);
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

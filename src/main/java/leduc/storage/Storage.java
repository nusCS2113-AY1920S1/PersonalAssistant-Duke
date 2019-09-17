package leduc.storage;

import leduc.Parser;
import leduc.Ui;
import leduc.exception.NonExistentDateException;
import leduc.task.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private String filePath;
    private WriteFile appendWrite;

    /**
     * Constructor of leduc.storage.Storage
     * @param filePath String representing the path of the file
     */
    public Storage(String filePath){
        this.filePath = filePath;
    }

    /**
     * Returns the tasks list initialized with the data in the data file.
     * @param parser leduc.Parser which deals with making sense of the user command.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @return the tasks list initialized with the date in the data file.
     * @throws IOException Exception caught when the error occurred during the reading of the data file.
     * @throws NonExistentDateException Exception caught when the date of an event or deadline task present in the data file does not exist.
     */
    public List<Task> load(Parser parser, Ui ui) throws IOException, NonExistentDateException { // load the initial data file
        ReadFile rFile = new ReadFile(this.filePath,ui);// reader for initialization of tasks list
        BufferedReader bufferedReader = rFile.getBufferedReader();
        List<Task> tasks = new ArrayList<>();
        String readLine = bufferedReader.readLine();
        String[] line;
        String mark = "";
        while (readLine != null && !readLine.isBlank()) {
            line = readLine.split("//");
            mark = line[2];
            switch (line[1]) {
                case "[T]":
                    tasks.add(new TodoTask(line[3], mark));
                    break;
                case "[D]":
                    tasks.add(new DeadlinesTask(line[3], mark, parser.stringToDate(line[4].substring(4).trim(),ui)));
                    break;
                case "[E]":
                    tasks.add(
                            new EventsTask(line[3], mark, parser.stringToDate(line[4].substring(4),ui), parser.stringToDate(line[5],ui)));
            }
            readLine = bufferedReader.readLine();
        }
        rFile.freeBufferedReader();
        return tasks;
    }

    /**
     * Getter of the path of the file.
     * @return String representing the path of the file.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Getter with returns the writer of file.
     * @return the writer of file.
     */
    public WriteFile getAppendWrite() {
        return appendWrite;
    }

    /**
     * Create a new writer of file for the fields of leduc.storage.Storage class.
     * @param file String representing the path of the file.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public void getNewAppendWrite(String file, Ui ui){
        this.appendWrite = new WriteFile(file,true,ui);
    }

    /**
     * Returns a String representing the data of the data file without the task which need to be removed.
     * @param removedTask task to remove from the tasks list and data file.
     * @param index the position of the removed task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param tasksSize size of the tasks list after removed the task.
     * @return a String representing the data of the data file without the task which need to be removed.
     */
    public String getDeleteTaskString(Task removedTask, int index , Ui ui, int tasksSize){
        String text="" , line ="", oldLine =(index+1)+"//"+removedTask.getTag() ,
                newLine ="";
        ReadFile readFile = new ReadFile(this.filePath,ui);// reader to read before change the data file
        BufferedReader bufferedR = readFile.getBufferedReader();
        try{
            for (int i = 0 ; i< tasksSize+1 ; i++){ // one task have been just removed
                line = bufferedR.readLine();
                if (!line.contains(oldLine)){
                    if (i > index ) { // we should minus 1 to each line after the line removed
                        line = line.replace( (i+1) +"//", (i)+"//"  ) + "\n";
                        text += line ;
                    }
                    else{
                        text+= line +"\n";
                    }
                }
            }
        }
        catch(IOException e){
            ui.display("\t IOException: \n\t\t error when reading the whole file");
        }
        readFile.freeBufferedReader(); //close the reader
        return text;
    }

    /**
     * Returns a String representing the data of the data file with the changement of the task which need to be snoozed.
     * @param snoozedTask the deadline task to be snoozed
     * @param index the position of the snoozed task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param tasksSize size of the tasks list after removed the task.
     * @return a String representing the data of the data file with the changement of the task which need to be snoozed.
     */
    public String getSnoozeTaskString(DeadlinesTask snoozedTask, int index , Ui ui, int tasksSize){
        String text="" , line ="", oldLine =(index+1)+"//"+snoozedTask.getTag();
        ReadFile readFile = new ReadFile(this.filePath,ui);// reader to read before change the data file
        BufferedReader bufferedR = readFile.getBufferedReader();
        try{
            for (int i = 0 ; i< tasksSize ; i++){ // one task have been just removed
                line = bufferedR.readLine();
                if (!line.contains(oldLine)){
                        text+= line +"\n";
                }
                else{
                    line = line.replace( line, (index +1) + "//" + snoozedTask.getTag() + "//" +
                            snoozedTask.getMark() + "//" + snoozedTask.getTask() + "//" + " by:"
                            +snoozedTask.getDeadlines()) + "\n";
                    text += line ;
                }
            }
        }
        catch(IOException e){
            ui.display("\t IOException: \n\t\t error when reading the whole file");
        }
        readFile.freeBufferedReader(); //close the reader
        return text;
    }

    /**
     * Returns a String representing the data of the data file after mark done to the specific task.
     * @param tasks tasks list.
     * @param index the position of the removed task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @return a String representing the data of the data file after mark done to the specific task.
     */
    public String getDoneString(TaskList tasks , int index, Ui ui){ //returns String by replacing the done task
        Task task = tasks.get(index);
        String text="" , line, oldLine =(index+1)+"//"+task.getTag()+"//"+"[✗]" ,
                newLine =(index+1)+"//"+task.getTag()+"//"+task.getMark();
        ReadFile readFile = new ReadFile(this.filePath,ui);// reader to read before change the data file
        BufferedReader bufferedR = readFile.getBufferedReader();
        try{
            while ((line = bufferedR.readLine()) != null) {
                if (line.contains(oldLine)){
                    line = line.replace(oldLine,newLine);
                }
                text += line + "\n";
            }
        }
        catch(IOException e){
            ui.display("\t IOException: \n\t\t error when reading the whole file");
        }
        readFile.freeBufferedReader(); //close the reader
        return text;
    }

    /**
     * Allows to rewrite the whole file ( write the String text to the file).
     * @param text String representing the text to write on the file.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public void rewriteFile(String text, Ui ui){
        WriteFile rwFile = new WriteFile(this.filePath,false,ui);
        try{
            rwFile.write(text);
        }
        catch (IOException e){
            ui.display("\t IOException: \n\t\t error when writing the whole file");
        }
        rwFile.freeBufferedWriter();//free the writer
    }
}

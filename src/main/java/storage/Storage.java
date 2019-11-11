package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.AlphaNUSException;
import task.TaskList;
import project.Fund;
import project.Project;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

//@@author leowyh
/**
 * Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String basefilepath = System.getProperty("user.dir");
    private static String projectsfilepath = basefilepath + "/localdata/BackupProjects.json";
    private static String commandlistfilepath = basefilepath +  "/localdata/Backuphistory.json";
    private static String fundfilepath =  basefilepath + "/localdata/BackupFund.json";
    private static String tasklistfilepath =  basefilepath + "/localdata/TaskList.json";
    private static String undoListFilePath = basefilepath +  "/localdata/undo.json";
    private static String redoListFilePath = basefilepath +  "/localdata/redo.json";
    private static String undofundfilepath = basefilepath + "localdata/undoFund.json";
    private static String redofundfilepath = basefilepath + "localdata/redoFund.json";
    private static String currentprojectfilepath = basefilepath + "/localdata/CurrentProject.json";
    private static String dictFilePath = basefilepath + "/localdata/dict.json";
    private static String backuphistoryfilepath = "Backuphistory.json";
    private static String backupfundfilepath = "BackupFund.json";
    private static String backupprojectsfilepath = "BackupProjects.json";
    private static String backupTaskListfilepath = "BackupTaskList.json";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Writes current projectmap in ProjectManager to local storage.
     * @param dict LinkedHashMap of projects.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToDictFile(Set<String> dict) throws AlphaNUSException {
        String toWriteStr = gson.toJson(dict);
        try {
            File file = new File(dictFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + dictFilePath);
        }
    }

    /**
     * Writes current projectmap in ProjectManager to local storage.
     * @param projectmap LinkedHashMap of projects.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToProjectsFile(LinkedHashMap<String, Project> projectmap) throws AlphaNUSException {
        String toWriteStr = gson.toJson(projectmap);
        try {
            File file = new File(projectsfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + projectsfilepath);
        }
    }

    /**
     * Writes current project in ProjectManager to local storage.
     * @param currentprojectname Current project before exiting the application.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeTocurrentprojectnameFile(String currentprojectname) throws AlphaNUSException {
        String toWriteStr = gson.toJson(currentprojectname);
        try {
            File file = new File(currentprojectfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + currentprojectfilepath);
        }
    }

    /**
     * Writes current Fund to local storage.
     * @param fund Fund object containing fund details.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToFundFile(Fund fund) throws AlphaNUSException {
        String toWriteStr = gson.toJson(fund);
        try {
            File file = new File(fundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + fundfilepath);
        }
    }

    //@@author lijiayu980606
    /**
     * Writes current tasklist to local storage.
     * @param taskList tasklist object containing fund details.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToTaskListFile(TaskList taskList) throws AlphaNUSException {
        String toWriteStr = gson.toJson(taskList);
        try {
            File file = new File(tasklistfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + tasklistfilepath);
        }
    }

    //@@author E0373902
    /**
     * writes the fund present, before the current command was executed, to local storage.
     * @param fund TODO
     * @throws AlphaNUSException TODO
     */
    public void writeToundoFundFile(Fund fund) throws AlphaNUSException {
        String toWriteStr = gson.toJson(fund);
        try {
            File file = new File(undofundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + undofundfilepath);
        }
    }

    /**
     * Writes the fund present, after the current command is executed, to the local storage.
     * @param fund TODO
     * @throws AlphaNUSException TODO
     */
    public void writeToredoFundFile(Fund fund) throws AlphaNUSException {
        String toWriteStr = gson.toJson(fund);
        try {
            File file = new File(redofundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + redofundfilepath);
        }
    }

    /**
     * Writes the projectmap, before the current command is executed, to local storage.
     * @param projectmap LinkedHashMap of projects.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToUndoFile(LinkedHashMap<String, Project> projectmap) throws AlphaNUSException {
        String toWriteStr = gson.toJson(projectmap);
        try {
            File file = new File(undoListFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + undoListFilePath);
        }
    }

    //@@author E0373902
    /**
     * Writes the projectmap, after current command is executed, to local storage.
     * @param projectmap LinkedHashMap of projects.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToRedoFile(LinkedHashMap<String, Project> projectmap) throws AlphaNUSException {
        String toWriteStr = gson.toJson(projectmap);
        try {
            File file = new File(redoListFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + redoListFilePath);
        }
    }

    //@@author leowyh
    /**
     * Read HashMap of projects from local storage and returns it.
     * @return HashMap of Project objects stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public Set<String> readFromDictFile() throws AlphaNUSException {
        Type dictType = new TypeToken<Set<String>>(){}.getType();
        Set<String> dict;
        try {
            File file = new File(dictFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            dict = gson.fromJson(bufferedReader, dictType);
            bufferedReader.close();
            if (dict == null) {
                dict = new HashSet<>();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return dict;
    }

    /**
     * Read HashMap of projects from local storage and returns it.
     * @return HashMap of Project objects stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public LinkedHashMap<String, Project> readFromProjectsFile() throws AlphaNUSException {
        Type projectmaptype = new TypeToken<LinkedHashMap<String, Project>>(){}.getType();
        LinkedHashMap<String, Project> projectmap;
        try {
            File file = new File(projectsfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            projectmap = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
            if (projectmap == null) {
                projectmap = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return projectmap;
    }

    /**
     * Read Current Project from local storage and returns it.
     * @return Current Project object stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public String readFromcurrentprojectnameFile() throws AlphaNUSException {
        Type projectmaptype = new TypeToken<String>(){}.getType();
        String currentprojectname;
        try {
            File file = new File(currentprojectfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            currentprojectname = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return currentprojectname;
    }

    /**
     * Read Fund from local storage and return it.
     * @return Fund stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public Fund readFromFundFile() throws AlphaNUSException {
        Type fundtype = new TypeToken<Fund>(){}.getType();
        Fund fund;
        try {
            File file = new File(fundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            fund = gson.fromJson(bufferedReader, fundtype);
            bufferedReader.close();
            if (fund == null) {
                fund = new Fund();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return fund;
    }

    //@@author lijiayu980606
    /**
     * Read Fund from local storage and return it.
     * @return Fund stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public TaskList readFromTaskListFile() throws AlphaNUSException {
        Type tasklistType = new TypeToken<TaskList>(){}.getType();
        TaskList tasklist;
        try {
            File file = new File(tasklistfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            tasklist = gson.fromJson(bufferedReader, tasklistType);
            bufferedReader.close();
            if (tasklist == null) {
                tasklist = new TaskList();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return tasklist;
    }

    //@@author E0373902
    /**
     * Reads fund from undo file for fund from local storage and returns it.
     * @return TODO
     * @throws AlphaNUSException TODO
     */
    public Fund readFromundoFundFile() throws AlphaNUSException {
        Type fundtype = new TypeToken<Fund>(){}.getType();
        Fund fund;
        try {
            File file = new File(undofundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            fund = gson.fromJson(bufferedReader, fundtype);
            bufferedReader.close();
            if (fund == null) {
                fund = new Fund();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return fund;
    }

    /**
     * Reads fund from redo file for fund from local storage and returns it.
     * @return TODO
     * @throws AlphaNUSException TODO
     */
    public Fund readFromredoFundFile() throws AlphaNUSException {
        Type fundtype = new TypeToken<Fund>(){}.getType();
        Fund fund;
        try {
            File file = new File(redofundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            fund = gson.fromJson(bufferedReader, fundtype);
            bufferedReader.close();
            if (fund == null) {
                fund = new Fund();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return fund;
    }

    /**
     * Read HashMap of projects in the undo file from local storage and returns it.
     * @return HashMap of Project objects stored in the undo file in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */

    public LinkedHashMap<String, Project> readFromUndoFile() throws AlphaNUSException {
        Type projectmaptype = new TypeToken<LinkedHashMap<String, Project>>(){}.getType();
        LinkedHashMap<String, Project> projectmap;
        try {
            File file = new File(undoListFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            projectmap = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return projectmap;
    }

    //@@author E0373902
    /**
     * Read HashMap of projects in the redo file from local storage and returns it.
     * @return HashMap of Project objects stored in the redo file in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */

    public LinkedHashMap<String, Project> readFromRedoFile() throws AlphaNUSException {
        Type projectmaptype = new TypeToken<LinkedHashMap<String, Project>>(){}.getType();
        LinkedHashMap<String, Project> projectmap;
        try {
            File file = new File(redoListFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            projectmap = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return projectmap;
    }

    /**
     * Saves the tasklist of the user as an ArrayList containing the task object.
     * @param str TODO
     */
    public static void remove(String str){
        //TODO
    }

    /**
     * Writes the input command entered by the user to the local storage.
     * @param command input command entered by the user
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToCommandsFile(String command) throws AlphaNUSException {
        String toWriteStr = gson.toJson(command);
        try {
            File file = new File(commandlistfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(toWriteStr);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + commandlistfilepath);
        }
    }

    /**
     * Reads array list of input commands from local storage and returns it.
     * @return ArrayList of input commands stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public ArrayList<String> readFromCommandsFile() throws AlphaNUSException {
        //Type commandListtype = new TypeToken<ArrayList<String>>(){}.getType();
        String line = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            File file = new File(commandlistfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return list;
    }

    //@@author leowyh
    /**
     * Reads array list of input commands from local storage and returns it.
     * @return ArrayList of input commands stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public ArrayList<String> readFromBackupCommandsFile() throws AlphaNUSException {
        String line = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            InputStream in = getClass().getResourceAsStream(backuphistoryfilepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return list;
    }

    /**
     * Reads array list of input commands from local storage and returns it.
     * @return ArrayList of input commands stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public Fund readFromBackupFundFile() throws AlphaNUSException {
        Type fundtype = new TypeToken<Fund>(){}.getType();
        Fund fund;
        try {
            InputStream in = getClass().getResourceAsStream(backupfundfilepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            fund = gson.fromJson(bufferedReader, fundtype);
            bufferedReader.close();
            if (fund == null) {
                fund = new Fund();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return fund;
    }

    //@@author lijiayu980606
    /**
     * Reads array list of tasks from local storage and returns it.
     * @return Tasklist stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public TaskList readFromBackupTaskListFile() throws AlphaNUSException {
        Type tasklistType = new TypeToken<TaskList>(){}.getType();
        TaskList tasklist;
        try {
            InputStream in = getClass().getResourceAsStream(backupTaskListfilepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            tasklist = gson.fromJson(bufferedReader, tasklistType);
            bufferedReader.close();
            if (tasklist == null) {
                tasklist = new TaskList();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return tasklist;
    }

    //@@author leowyh
    /**
     * Read HashMap of projects from local storage and returns it.
     * @return HashMap of Project objects stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public LinkedHashMap<String, Project> readFromBackupProjectsFile() throws AlphaNUSException {
        Type projectmaptype = new TypeToken<LinkedHashMap<String, Project>>(){}.getType();
        LinkedHashMap<String, Project> projectmap;
        try {
            InputStream in = getClass().getResourceAsStream(backupprojectsfilepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            projectmap = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
            if (projectmap == null) {
                projectmap = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return projectmap;
    }
}
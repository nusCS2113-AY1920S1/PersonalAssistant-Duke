package util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controllers.ConsoleInputController;
import models.project.Project;
import util.log.DukeLogger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonConverter {
    private String projectFilePath = System.getProperty("user.dir") + "/savedProjects.json";

    /**
     * Method that is responsible for saving Projects Data by using GSON library to convert to a human editable JSON
     * file.
     */
    public void saveProjectsData(ArrayList<Project> allProjects) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            DukeLogger.logDebug(ConsoleInputController.class, "Saving to file.");
            FileWriter fileWriter = new FileWriter(projectFilePath);
            gson.toJson(allProjects, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            DukeLogger.logDebug(ConsoleInputController.class, "File saved.");
        } catch (IOException err) {
            DukeLogger.logError(ConsoleInputController.class, "savedProjects file is not found.");
        }
    }

    /**
     * Method responsible for loading Projects Data from hard coded directory where savedProjects.json file is located
     */
    public ArrayList<Project> loadProjectsData() {
        Gson gson = new Gson();
        ArrayList<Project> allProjects;
        try (FileReader fileReader = new FileReader(projectFilePath)) {
            DukeLogger.logDebug(ConsoleInputController.class, "Loading saved file.");
            allProjects = gson.fromJson(fileReader, new TypeToken<ArrayList<Project>>(){}.getType());
            if (allProjects == null) {
                return new ArrayList<>();
            }
            DukeLogger.logDebug(ConsoleInputController.class, "Saved file loaded.");
        } catch (IOException err) {
            DukeLogger.logError(ConsoleInputController.class, "Saved file not loaded");
            return new ArrayList<>();
        }
        return allProjects;
    }
}

package util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controllers.ConsoleInputController;
import exceptions.DukeException;
import models.project.Project;
import util.log.DukeLogger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonConverter {
    private String userDirectory = System.getProperty("user.dir") + "/savedProjects.json";

    /**
     * Method that is responsible for saving Projects Data by using GSON library to convert to a human editable JSON
     * file.
     */
    public void saveProject(Project project) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            DukeLogger.logDebug(JsonConverter.class, "Saving to file.");
            FileWriter fileWriter = new FileWriter(userDirectory + "/" +
                                                    project.getName() + ".json");
            gson.toJson(project, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            DukeLogger.logDebug(JsonConverter.class, "File saved.");
        } catch (IOException err) {
            DukeLogger.logError(JsonConverter.class, "Save file is not found or not created");
        }
    }

    public void deleteProject(Project project) throws DukeException {
        String selectedProjectJson = userDirectory + "/" + project.getName() + ".json";
        File file = new File(selectedProjectJson);
        if (!file.delete()) {
            throw new DukeException("JSON of project not found");
        }
    }

    /**
     * Method responsible for loading Projects Data from hard coded directory where savedProjects.json file is located
     */
    public ArrayList<Project> loadAllProjectsData() {
        Gson gson = new Gson();
        ArrayList<Project> allProjects;
        try (FileReader fileReader = new FileReader(userDirectory)) {
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

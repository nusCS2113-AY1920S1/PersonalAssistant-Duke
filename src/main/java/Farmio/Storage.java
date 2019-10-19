package Farmio;

import Exceptions.FarmioFatalException;
import FrontEnd.AsciiColours;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Storage {

    private static String appDir;

    public Storage() {
        appDir = System.getProperty("user.dir");
    }

    public boolean getSaveExist() {
        return new File(appDir.concat("\\data\\save.json")).exists();
    }

    /**
     * Finds and return previously saved game data.
     *
     * @return Last saved farmer object.
     * @throws ParseException "save.json" does not contain json data.
     * @throws IOException    "save.json" does not exist.
     */
    public JSONObject loadFarmer() throws ParseException, IOException {
        Reader reader = new FileReader(appDir.concat("\\data\\save.json"));
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }

    /**
     * Stores farmer object into "save.json".
     *
     * @param farmer Farmio.Farmer object to be stored in to save file.
     * @throws IOException Fail to save farmer object into save file.
     */
    public static boolean storeFarmer(Farmer farmer) {
        FileWriter file;
        try {
            file = new FileWriter(appDir.concat("\\data\\save.json"));
            file.write(farmer.toJSON().toJSONString());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getAsciiArt(String name) throws FarmioFatalException {
        String path = "asciiArt/" + name + ".txt";
        StringBuilder art = new StringBuilder();
        FileReader fileReader;
        try {
            fileReader = new FileReader(getResourceFile(path));
        } catch (FileNotFoundException e) {
            throw new FarmioFatalException(formatFatalMessage(path));
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) break;
            } catch (IOException e) {
                throw new FarmioFatalException(formatFatalMessage(path));
            }
            art.append(line).append("\n");
        }
        return art.toString();
    }

    public ArrayList<String> loadFrame(String path, int frameId, int frameWidth, int frameHeight) throws FarmioFatalException {
        path = "asciiArt/" + path + "/frame" + frameId + ".txt";
        FileReader fileReader;
        try {
            fileReader = new FileReader(getResourceFile(path));
        } catch (FileNotFoundException | FarmioFatalException e) {
            throw new FarmioFatalException(formatFatalMessage(path));
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        ArrayList<String> frame = new ArrayList<>();
        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) break;
            } catch (IOException e) {
                throw new FarmioFatalException(formatFatalMessage(path));
            }
            if (line.length() <= frameWidth) {
                line = String.format("%-" + frameWidth + "s", line);
            } else {
                line = String.format("%" + frameWidth + "s", "");
            }
            frame.add(
                    "|" +
                    AsciiColours.BACKGROUND_WHITE +
                    AsciiColours.BLACK +
                    line +
                    AsciiColours.SANE +
                    "|"
            );
        }
        if (frame.size() < frameHeight) {
            for (int i = frame.size(); i < frameHeight; i++) {
                frame.add(
                        "|" +
                        AsciiColours.BACKGROUND_WHITE +
                        AsciiColours.BLACK +
                        String.format("%" + frameWidth + "s", "") +
                        AsciiColours.SANE +
                        "|"
                );
            }
        }
        return frame;
    }

    public JSONObject getLevel(int level) throws FarmioFatalException {
        String path = "levels/" + level + ".json";
        Reader reader;
        try {
            reader = new FileReader(getResourceFile(path));
        } catch (FileNotFoundException e) {
            throw new FarmioFatalException(formatFatalMessage(path));
        }
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new FarmioFatalException(formatFatalMessage(path));
        }
    }

    private File getResourceFile(String path) throws FarmioFatalException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new FarmioFatalException(formatFatalMessage(path));
        }
        return new File(resource.getFile());
    }

    private String formatFatalMessage(String path) {
        return "\"" + path + "\" not found!";
    }
}

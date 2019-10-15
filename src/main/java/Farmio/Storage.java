package Farmio;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class Storage {

    private static String appDir;

    public Storage() {
        appDir = System.getProperty("user.dir");
    }

    public boolean getFarmerExist() {
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
    public static void storeFarmer(Farmer farmer) throws IOException {
        FileWriter file = new FileWriter(appDir.concat("\\data\\save.json"));
        file.write(farmer.toJSON().toJSONString());
    }

    public String getAsciiArt(String name) throws IOException {
        StringBuilder art = new StringBuilder();
        FileReader fileReader = new FileReader(getResourceFile("asciiArt/" + name + ".txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            art.append(line).append("\n");
        }
        return art.toString();
    }

    public JSONObject getLevel(int level) throws IOException, ParseException {
        Reader reader = new FileReader(getResourceFile("levels/" + level + ".json"));
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }

    private File getResourceFile(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new IOException("Game is corrupted!");
        }
        return new File(resource.getFile());
    }
}

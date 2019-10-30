package farmio;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import frontend.AsciiColours;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Storage {

    private String appDir;
    private String jsonName = "save.json";
    private JSONObject jsonFarmer = null;

    public Storage() {
        appDir = System.getProperty("user.dir");
        if (System.getProperty("os.name").startsWith("Windows")) {
            jsonName = "\\" + jsonName;
            AsciiColours.inActivate();
        } else {
            jsonName = "/" + jsonName;
        }
    }

    public boolean getSaveExist() {
        System.out.println(new File(appDir.concat(jsonName)).toString());
        return new File(appDir.concat(jsonName)).exists();
    }

    /**
     * Finds and return previously saved game data.
     *
     * @return Last saved farmer object.
     * @throws ParseException "save.json" does not contain json data.
     * @throws IOException    "save.json" does not exist.
     */
    public JSONObject loadFarmer() throws FarmioException {
        Reader reader = null;
        try {
            reader = new FileReader(appDir.concat(jsonName));
        } catch (FileNotFoundException e) {
            throw new FarmioException("Game save not found!");
        }
        JSONParser parser = new JSONParser();
        try {
            jsonFarmer = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new FarmioException("Gave save corrupted!");
        }
        return jsonFarmer;
    }

    public JSONObject loadFarmerBackup() throws FarmioException {
        if(jsonFarmer == null){
            throw new FarmioException("Recovery failed!");
        }
        recoverFarmer();
        return jsonFarmer;
    }

    /**
     * Stores farmer object into "save.json".
     *
     * @param farmer Farmio.Farmer object to be stored in to save file.
     * @throws IOException Fail to save farmer object into save file.
     */
    public boolean storeFarmer(Farmer farmer) {
        FileWriter file;
        try {
            file = new FileWriter(appDir.concat(jsonName));
            jsonFarmer = farmer.toJson();
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean storeFarmerPartial(Farmer farmer) {
        if(jsonFarmer == null){
            return false;
        }
        FileWriter file;
        try {
            file = new FileWriter(appDir.concat(jsonName));
            jsonFarmer = farmer.updateJSON(jsonFarmer);
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean recoverFarmer() {
        FileWriter file;
        try {
            file = new FileWriter(appDir.concat(jsonName));
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public ArrayList<String> loadFrame(String path, int frameId, int frameWidth, int frameHeight) throws FarmioFatalException {
        path = "asciiArt/" + path + "/frame" + frameId + ".txt";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResourceStream(path)));
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

    public JSONObject getLevel(double level) throws FarmioFatalException {
        String path = "levels/" + level + ".json";
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new InputStreamReader(getResourceStream(path)));
        } catch (IOException | ParseException e) {
            throw new FarmioFatalException(formatFatalMessage(path));
        }
    }

    private InputStream getResourceStream(String path) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }

    private String formatFatalMessage(String path) {
        return "\"" + path + "\" not found!";
    }
}

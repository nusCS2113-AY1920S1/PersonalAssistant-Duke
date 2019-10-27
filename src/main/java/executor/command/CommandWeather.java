package executor.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import executor.task.TaskList;
import ui.Wallet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class CommandWeather extends Command {
    private String link = "https://www.metaweather.com/api/location/1062617/";
    private HashMap<String, HashMap<String, String>> fullWeatherData = new HashMap<>();
   // HashMap<String, String> innerMap = new HashMap<String, String>();

    public CommandWeather() {

    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {

    }


    private String consultWeatherApi() {
        try {
            URL url = new URL(this.link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String completeJson = "";
            while (null != (line = reader.readLine())) {
                completeJson += line;
            }
            return completeJson;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void storeWeatherDataFromJson(String json){
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("consolidated_weather");
        for (int i = 0; i < arr.size(); i++) {
            String weatherStateName = arr.get(i).getAsJsonObject().get("weather_state_name").getAsString();
            \

    }
}

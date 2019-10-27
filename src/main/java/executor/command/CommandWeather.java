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
    private HashMap<String, HashMap<String, String>> fullWeatherData;

    public CommandWeather(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.WEATHER;
        this.description = "Command that displays weather for now, tomorrow or later";
        setFullWeatherData(storeWeatherDataFromJson(consultWeatherApi()));

    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {

    }

    private String getWhichWeatherDataUserWants(String userInput){

    }


    private String consultWeatherApi() {
        try {
            String link = "https://www.metaweather.com/api/location/1062617/";
            URL url = new URL(link);
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

    private HashMap<String, HashMap<String, String>> storeWeatherDataFromJson(String json) {
        HashMap<String, HashMap<String, String>> weatherData = new HashMap<>();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("consolidated_weather");
        for (int i = 0; i < arr.size(); i++) {
            String weatherStateName = arr.get(i).getAsJsonObject().get("weather_state_name").getAsString();
            String min_temp = arr.get(i).getAsJsonObject().get("min_temp").getAsString();
            String max_temp = arr.get(i).getAsJsonObject().get("max_temp").getAsString();
            String the_temp = arr.get(i).getAsJsonObject().get("the_temp").getAsString();
            HashMap<String, String> innerMap = new HashMap<>();
            innerMap.put("min_temp", min_temp);
            innerMap.put("max_temp", max_temp);
            innerMap.put("the_temp", the_temp);
            innerMap.put("weatherStateName", weatherStateName);
            weatherData.put(String.valueOf(i),innerMap);
        }
        return weatherData;
    }

    public void setFullWeatherData(HashMap<String, HashMap<String, String>> fullWeatherData) {
        this.fullWeatherData = fullWeatherData;
    }
}

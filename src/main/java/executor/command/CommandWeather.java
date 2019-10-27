package executor.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommandWeather extends Command {
    private HashMap<String, HashMap<String, String>> fullWeatherData;

    public CommandWeather(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.WEATHER;
        this.description = "Command that displays weather for now, tomorrow or later";
        setFullWeatherData(storeWeatherDataFromJson());

    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        String status = getWhichWeatherDataUserWants(this.userInput);
        printWeatherDataOutput(getLengthOfArrayToPrint(status));
    }

    private String getWhichWeatherDataUserWants(String userInput){
        try {
            String queryFor = Parser.parseForFlag("for", userInput);
            return queryFor;
        } catch (Exception e){
            return "later";
        }
    }

    private int getLengthOfArrayToPrint(String status){

        if(status.equals("now")){
            return 1;
        } else if(status.equals("tomorrow")){
            return 2;

        } else if(status.equals("later")){
            this.fullWeatherData.size();
        }
        return 1;
    }

    private void printWeatherDataOutput(int size){
        for (int i = 0; i < size; i++){
            Iterator it = this.fullWeatherData.get(i).entrySet().iterator();
            Ui.dukeSays("Duke$$$ has found the following weather details as per your request : \n");
            while (it.hasNext()){
                Map.Entry mapElement = (Map.Entry)it.next();
                System.out.println(mapElement.getKey() + " : " + mapElement.getValue() + "\n");
            }
        }
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

    private HashMap<String, HashMap<String, String>> storeWeatherDataFromJson() {
        String json = consultWeatherApi();
        HashMap<String, HashMap<String, String>> weatherData = new HashMap<>();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("consolidated_weather");
        for (int i = 0; i < arr.size(); i++) {
            String weatherStateName = arr.get(i).getAsJsonObject().get("weather_state_name").getAsString();
            String minTemp = arr.get(i).getAsJsonObject().get("min_temp").getAsString();
            String maxTemp = arr.get(i).getAsJsonObject().get("max_temp").getAsString();
            String theTemp = arr.get(i).getAsJsonObject().get("the_temp").getAsString();
            String applicableDate = arr.get(i).getAsJsonObject().get("applicable_date").getAsString();
            HashMap<String, String> innerMap = new HashMap<>();
            innerMap.put("applicable_date", applicableDate);
            innerMap.put("min_temp", minTemp);
            innerMap.put("max_temp", maxTemp);
            innerMap.put("the_temp", theTemp);
            innerMap.put("weatherStateName", weatherStateName);
            weatherData.put(String.valueOf(i),innerMap);
        }
        return weatherData;
    }

    public void setFullWeatherData(HashMap<String, HashMap<String, String>> fullWeatherData) {
        this.fullWeatherData = fullWeatherData;
    }
}

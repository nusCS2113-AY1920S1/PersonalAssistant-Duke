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
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandWeather extends Command {
    private LinkedHashMap<String, LinkedHashMap<String, String>> fullWeatherData;

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
        printWeatherDataOutput();

    }

    private String getWhichWeatherDataUserWants(String userInput){
        try {
            return Parser.parseForFlag("for", userInput);
        } catch (Exception e){
            Ui.dukeSays("Please enter in the following format : \n"
                    + "1. weather /for now \n"
                    + "2. weather /for later \n"
                    + "3. weather /for tomorrow \n");
            return "later";
        }
    }

    private int getLengthOfArrayToPrint(String status){
        switch (status) {
            case "now":
                return 1;
            case "tomorrow":
                return 2;
            case "later":
                return this.fullWeatherData.size();
        }
        return 1;
    }


    private void printWeatherDataOutput(){
        int size = getLengthOfArrayToPrint(getWhichWeatherDataUserWants(this.userInput));
        Ui.dukeSays("Duke$$$ has found the following weather forecast as requested :");
        for(Map.Entry<String, LinkedHashMap<String, String>> weather : this.fullWeatherData.entrySet()) {
            String weatherKey = weather.getKey();
            if(Integer.parseInt(weatherKey) < size) {
                System.out.println("\n");
                for (Map.Entry<String, String> weatherEntry : weather.getValue().entrySet()) {
                    String field = weatherEntry.getKey();
                    String value = weatherEntry.getValue();
                    System.out.println(field + " : " + value);
                }
            }
        }
        Ui.printSeparator();
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

    private LinkedHashMap<String, LinkedHashMap<String, String>> storeWeatherDataFromJson() {
        String json = consultWeatherApi();
        LinkedHashMap<String, LinkedHashMap<String, String>> weatherData = new LinkedHashMap<>();
        if(json!= null) {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray arr = jsonObject.getAsJsonArray("consolidated_weather");
            for (int i = 0; i < arr.size(); i++) {
                String weatherStateName = arr.get(i).getAsJsonObject().get("weather_state_name").getAsString();
                String minTemp = arr.get(i).getAsJsonObject().get("min_temp").getAsString();
                String maxTemp = arr.get(i).getAsJsonObject().get("max_temp").getAsString();
                String theTemp = arr.get(i).getAsJsonObject().get("the_temp").getAsString();
                String applicableDate = arr.get(i).getAsJsonObject().get("applicable_date").getAsString();
                LinkedHashMap<String, String> innerMap = new LinkedHashMap<>();
                innerMap.put("Forecast Date", applicableDate);
                innerMap.put("Minimum Temperature in Degrees Celsius", minTemp);
                innerMap.put("Maximum Temperature in Degrees Celsius", maxTemp);
                innerMap.put("Current Temperature in Degrees Celsius", theTemp);
                innerMap.put("State Of Weather", weatherStateName);
                weatherData.put(String.valueOf(i), innerMap);
            }
        }
        return weatherData;
    }

    public void setFullWeatherData(LinkedHashMap<String, LinkedHashMap<String, String>> fullWeatherData) {
        this.fullWeatherData = fullWeatherData;
    }
}

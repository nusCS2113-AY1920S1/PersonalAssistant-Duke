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

    /**
     * CommandWeather displays weather information based on user request.
     * @param userInput String is the user entered Input from CLI
     */
    public CommandWeather(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.WEATHER;
        this.description = "Command that displays weather for now, tomorrow or later \n"
                + "FORMAT : \n";
        setFullWeatherData(storeWeatherDataFromJson());
    }
    
    @Override
    public void execute(Wallet wallet) {
        printWeatherDataOutput();

    }

    @Override
    public void execute(TaskList taskList) {

    }


    /**
     * getWhichWeatherDataUserWants parses to see until when user wants weather forecast.
     * @param userInput String is the user entered Input from CLI.
     * @return this function returns a String based on user's choice
     */
    private String getWhichWeatherDataUserWants(String userInput) {
        try {
            return Parser.parseForFlag("until", userInput);
        } catch (Exception e) {
            Ui.dukeSays("Please enter in the following format : \n"
                    + "1. weather /until now \n"
                    + "2. weather /until later \n"
                    + "3. weather /until tomorrow \n");
            return "later";
        }
    }

    /**
     * getLengthOfHashMapToPrint decides until which point are we to report weather.
     * @param status String is the time until user requests for weather
     * @return function returns the length of HashMap we need to print
     */
    private int getLengthOfHashMapToPrint(String status) {
        switch (status) {
        case "tomorrow":
            return 2;
        case "later":
            return this.fullWeatherData.size();
        case "now":
        default:
            return 1;
        }
    }

    /**
     * printWeatherDataOutput loops through the HashMap of HashMap to get weather data.
     */
    private void printWeatherDataOutput() {
        int size = getLengthOfHashMapToPrint(getWhichWeatherDataUserWants(this.userInput));
        Ui.dukeSays("Duke$$$ has found the following weather forecast as requested :");
        for (Map.Entry<String, LinkedHashMap<String, String>> weather : this.fullWeatherData.entrySet()) {
            String weatherKey = weather.getKey();
            if (Integer.parseInt(weatherKey) < size) {
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

    /**
     * consultWeatherApi fetches data from the api in json.
     * @return a String of the json data is returned
     */
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
            //ex.printStackTrace();
            return null;
        }
    }

    /**
     * storeWeatherDataFromJson loops through the string json and gets needed data.
     * @return a map of maps is returned containing the weather forecast we need by date
     */
    private LinkedHashMap<String, LinkedHashMap<String, String>> storeWeatherDataFromJson() {
        String json = consultWeatherApi();
        LinkedHashMap<String, LinkedHashMap<String, String>> weatherData = new LinkedHashMap<>();
        if (json != null) {
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

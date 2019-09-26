package optix.core;

import optix.util.ShowMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Storage {
    private File filePath;
    private LocalDate today;

    public Storage(File filePath) {
        today = LocalDate.now();

        this.filePath = filePath;
        try {
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Unable to create file.\n");
        }
    }

    public ShowMap load() {
        ShowMap shows = new ShowMap();
        try {
            FileReader rd = new FileReader(filePath);
            BufferedReader br = new BufferedReader(rd);

            String message;
            int counter = 0;

            while ((message = br.readLine()) != null) {
                String[] arrStr = message.split(" \\| ");

                if (arrStr[0].toLowerCase().equals("s")) {
                    LocalDate date = localDate(arrStr[1]);
                    String showName = arrStr[2].trim();
                    double cost = Double.parseDouble(arrStr[3]);
                    double revenue = Double.parseDouble(arrStr[4]);

                    Show show = new Show(showName, cost, revenue);
                    shows.put(date, show);
                }

                counter++;
            }

            br.close();
            rd.close();

        } catch (IOException e) {
            System.out.println("Unable to load file.\n");
        }


        return shows;
    }

    public void write(ShowMap shows) {
        try {
            filePath.delete();
            filePath.createNewFile();
            FileWriter wr = new FileWriter(filePath, true);

            for (Map.Entry<LocalDate, Show> entry : shows.entrySet()) {
                Show show = entry.getValue();
                LocalDate date = entry.getKey();

                wr.write(String.format("S | %s | %s", date, show.writeToFile()));
            }
            wr.close();
        } catch (IOException e) {
            System.out.println("Unable to write to file.");
        }
    }

    private LocalDate localDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //Convert string to localdate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public LocalDate getToday() {
        return today;
    }
}

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

            while ((message = br.readLine()) != null) {
                String[] arrStr = message.split(" \\| ");

                if (arrStr[0].toLowerCase().equals("s")) {
                    LocalDate date = localDate(arrStr[1]);
                    String showName = arrStr[2].trim();
                    double cost = Double.parseDouble(arrStr[3]);
                    double revenue = Double.parseDouble(arrStr[4]);
                    double seatBasePrice = Double.parseDouble(arrStr[5]);

                    if (date.compareTo(today) <= 0) {
                        continue;
                    }

                    Theatre theatre = new Theatre(showName, cost, revenue, seatBasePrice);
                    theatre = loadSeat(br, theatre);

                    shows.put(date, theatre);
                }
            }

            br.close();
            rd.close();

        } catch (IOException e) {
            System.out.println("Unable to load file.\n");
        }
        return shows;
    }

    private Theatre loadSeat(BufferedReader br, Theatre theatre) throws IOException {
        String message;
        while ((message = br.readLine()) != null && !message.equals("next")) {
            String[] arrStr = message.split("\\|");
            String buyerName = arrStr[0].trim();
            int row = Integer.parseInt(arrStr[1].trim());
            int col = Integer.parseInt(arrStr[2].trim());

            theatre.setSeat(buyerName, row, col);
        }

        return theatre;
    }

    public void write(ShowMap shows) {
        try {
            filePath.delete();
            filePath.createNewFile();
            FileWriter wr = new FileWriter(filePath, true);

            for (Map.Entry<LocalDate, Theatre> entry : shows.entrySet()) {
                Theatre theatre = entry.getValue();
                LocalDate date = entry.getKey();

                wr.write(String.format("S | %s | %s", date, theatre.writeToFile()));

                writeSeats(wr, theatre);
            }
            wr.close();
        } catch (IOException e) {
            System.out.println("Unable to write to file.");
        }
    }

    private void writeSeats(FileWriter wr, Theatre theatre) throws IOException {
        Seat[][] seats = theatre.getSeats();

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isBooked()) {
                    wr.write(String.format("%s | %d | %d\n", seats[i][j].getName(), i, j));
                }
            }
        }
        wr.write("next\n");
    }


    private LocalDate localDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //Convert string to localdate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    /**
     * Get today's date as LocalDate object.
     */
    public LocalDate getToday() {
        return today;
    }
}

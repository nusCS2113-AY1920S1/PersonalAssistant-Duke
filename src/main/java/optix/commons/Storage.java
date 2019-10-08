package optix.commons;

import optix.commons.model.Seat;
import optix.commons.model.Show;
import optix.commons.model.ShowHistoryMap;
import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Storage {
    private File archiveFilePath;
    private File showMapFilePath;
    private LocalDate today;

    public Storage(File filePath) {
        today = LocalDate.now();

        this.showMapFilePath = new File(filePath + "\\optix.txt");
        this.archiveFilePath = new File(filePath + "\\archive.txt");
        try {
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!showMapFilePath.exists()) {
                showMapFilePath.createNewFile();
            }
            if (!archiveFilePath.exists()) {
                archiveFilePath.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Unable to create file.\n");
        }
    }

    public void loadShows(ShowMap shows, ShowHistoryMap showsHistory) {
        try {
            FileReader rd = new FileReader(showMapFilePath);
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
                        Show show = new Show(showName, revenue);
                        showsHistory.put(date, show);
                        continue;
                    }

                    Theatre theatre = new Theatre(showName, cost, revenue, seatBasePrice);
                    loadSeat(br, theatre);

                    shows.put(date, theatre);
                }
            }

            br.close();
            rd.close();

        } catch (IOException e) {
            System.out.println("Unable to load file.\n");
        }
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

    public void loadArchive(ShowHistoryMap showsHistory) {
        try {
            FileReader rd = new FileReader(archiveFilePath);
            BufferedReader br = new BufferedReader(rd);

            String message;

            while ((message = br.readLine()) != null) {
                String[] arrStr = message.split(" \\| ");

                LocalDate date = localDate(arrStr[0]);
                String showName = arrStr[1].trim();
                double revenue = Double.parseDouble(arrStr[2]);

                Show show = new Show(showName, revenue);

                showsHistory.put(date, show);
            }

            br.close();
            rd.close();

        } catch (IOException e) {
            System.out.println("Unable to load file.\n");
        }
    }

    public void write(ShowMap shows) {
        try {
            showMapFilePath.delete();
            showMapFilePath.createNewFile();
            FileWriter wr = new FileWriter(showMapFilePath, true);

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

    public void writeArchive(ShowHistoryMap showsHistory) {
        try {
            archiveFilePath.delete();
            archiveFilePath.createNewFile();
            FileWriter wr = new FileWriter(archiveFilePath, true);

            for (Map.Entry<LocalDate, Show> entry : showsHistory.entrySet()) {
                Show show = entry.getValue();
                LocalDate date = entry.getKey();

                wr.write(String.format("%s | %s | %s", date, show.getShowName(), show.getProfit()));
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

    /**
     * Get today's date as LocalDate object.
     */
    public LocalDate getToday() {
        return today;
    }
}

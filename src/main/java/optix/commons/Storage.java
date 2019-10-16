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

    /**
     * Initialise a new storage object.
     *
     * @param filePath path to the save file.
     */
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

    /**
     * Load the data from the save file into model.
     */
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
                    double revenue = Double.parseDouble(arrStr[3]);
                    double seatBasePrice = Double.parseDouble(arrStr[4]);

                    if (date.compareTo(today) <= 0) {
                        Show show = new Show(showName, revenue);
                        showsHistory.put(date, show);
                        continue;
                    }

                    Theatre theatre = new Theatre(showName, revenue, seatBasePrice);
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

    /**
     * Load the seats from the save file.
     *
     * @param br      buffered reader
     * @param theatre the theatre to populate
     * @return the populated theatre
     * @throws IOException when buffered reader has problems with readLine().
     */
    private Theatre loadSeat(BufferedReader br, Theatre theatre) throws IOException {
        String message;
        while ((message = br.readLine()) != null && !message.equals("next")) {
            String[] arrStr = message.split("\\|");
            int row = Integer.parseInt(arrStr[0].trim());
            int col = Integer.parseInt(arrStr[1].trim());

            theatre.setSeat(row, col);
        }

        return theatre;
    }

    /**
     * Load information from archive.
     *
     * @param showsHistory Map of shows.
     */
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

    /**
     * write to the save file.
     * Deletes the old file and writes a new file.
     *
     * @param shows ShowMap of shows.
     */
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
                    wr.write(String.format("%d | %d\n", i, j));
                }
            }
        }
        wr.write("next\n");
    }

    /**
     * Place shows that have passed into an archive.
     *
     * @param showsHistory Map of shows.
     */
    public void writeArchive(ShowHistoryMap showsHistory) {
        try {
            archiveFilePath.delete();
            archiveFilePath.createNewFile();
            FileWriter wr = new FileWriter(archiveFilePath, true);

            for (Map.Entry<LocalDate, Show> entry : showsHistory.entrySet()) {
                Show show = entry.getValue();
                LocalDate date = entry.getKey();

                wr.write(String.format("%s | %s | %s\n", date, show.getShowName(), show.getProfit()));
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

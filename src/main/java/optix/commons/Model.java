package optix.commons;

import optix.commons.model.Show;
import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.Map;

public class Model {
    private ShowMap showsHistory = new ShowMap();
    private ShowMap shows = new ShowMap();
    private ShowMap showsGui;
    private OptixDateFormatter formatter = new OptixDateFormatter();

    /**
     * The Optix model.
     * @param storage the object which handles data from the save file.
     */
    public Model(Storage storage) {
        storage.loadShows(shows, showsHistory);
        storage.loadArchive(showsHistory);
        storage.writeArchive(showsHistory);
        showsGui = this.getShows();
    }

    public ShowMap getShows() {
        return shows;
    }

    public ShowMap getShowsHistory() {
        return showsHistory;
    }

    public ShowMap getShowsGui() {
        return showsGui;
    }

    public void setShowsGui(ShowMap showsGui) {
        this.showsGui = showsGui;
    }

    public boolean hasSameName(LocalDate key, String showName) {
        return shows.get(key).hasSameName(showName);
    }

    public boolean containsKey(LocalDate key) {
        return shows.containsKey(key);
    }

    //// Commands that deals with Shows

    public void addShow(String showName, LocalDate showDate, double seatBasePrice) {
        shows.addShow(showName, showDate, seatBasePrice);
        this.setShowsGui(shows);
    }

    public void editShowName(LocalDate showDate, String showName) {
        shows.editShowName(showDate, showName);
        this.setShowsGui(shows);
    }

    public void postponeShow(LocalDate oldDate, LocalDate newDate) {
        shows.postponeShow(oldDate, newDate);
        this.setShowsGui(shows);
    }

    public String listShow() {
        this.setShowsGui(shows);
        return shows.listShow();
    }

    /**
     * Get the list of show dates for the show in query.
     * @param showName The name of the show.
     * @return String message for the list of dates for the show in query.
     */
    public String listShow(String showName) {
        this.setShowsGui(shows.listShow(showName));
        StringBuilder message = new StringBuilder();
        int counter = 1;
        for (Map.Entry<LocalDate, Theatre> entry : showsGui.entrySet()) {
            String date = new OptixDateFormatter().toStringDate(entry.getKey());
            message.append(String.format("%d. %s\n", counter, date));
            counter++;
        }
        return message.toString();
    }

    /**
     * Get the list of show for the month in query.
     * @param startOfMonth The first day of month in query.
     * @param endOfMonth The first day of the following month for the month in query.
     * @return String message for the list of shows that are scheduled for the month in query.
     */
    public String listShow(LocalDate startOfMonth, LocalDate endOfMonth) {
        this.setShowsGui(shows.listShow(startOfMonth, endOfMonth));
        StringBuilder message = new StringBuilder();
        int counter = 1;
        for (Map.Entry<LocalDate, Theatre> entry : showsGui.entrySet()) {
            String date = new OptixDateFormatter().toStringDate(entry.getKey());
            String showName = entry.getValue().getShowName();
            message.append(String.format("%d. %s (on: %s)\n", counter, showName, date));
            counter++;
        }
        return message.toString();
    }

    /**
     * Calculates the earnings for a certain month from the Optix file.
     * @param mth The month in numerical form.
     * @param yr The year.
     * @param showsQuery shows, showsHistory or both. Contains all the shows from these ShowMaps
     * @return A message String that contains the profit to show to the user.
     */
    public String findMonthly(int mth, int yr, ShowMap... showsQuery) {
        StringBuilder message = new StringBuilder();
        double profit = 0;
        double projectedProfit = 0.0;

        int searchMonth;
        int searchYear;
        ShowMap showsWanted = new ShowMap();
        String monthYear = formatter.intToMonth(mth) + " " + yr;
        for (int i = 0; i < showsQuery.length; i++) { //maximum 2
            for (Map.Entry<LocalDate, Theatre> entry : showsQuery[i].entrySet()) {
                searchMonth = entry.getKey().getMonthValue();
                searchYear = entry.getKey().getYear();
                if (searchMonth == mth && searchYear == yr) { //show matches query date
                    showsWanted.put(entry.getKey(), entry.getValue());
                    if (i == 1) { // if the query is the current month
                        projectedProfit += entry.getValue().getProfit();
                    } else {
                        profit += entry.getValue().getProfit();
                    }
                }
            }
        }

        if (profit == 0) {
            if (showsWanted.isEmpty()) {
                message.append(String.format("☹ OOPS!!! There are no shows in %1$s.\n", monthYear));
            } else {
                message.append(String.format("None of the seats for the shows in %1$s has been sold yet!\n",
                        monthYear));
            }
        } else {
            if (showsQuery.length == 2) { // query is for current month
                message.append(String.format("The current earnings for %1$s is $%2$.2f.\n", monthYear, profit));
                if (projectedProfit > 0) {
                    message.append(String.format("The projected earnings for the rest of the month is $%1$.2f.\n",
                            projectedProfit));
                }
            } else { //either from ShowMap or ShowHistoryMap
                if (showsQuery[0].equals(shows)) {
                    message.append(String.format("The projected earnings for %1$s is $%2$.2f.\n", monthYear,
                            profit));
                } else {
                    message.append(String.format("The earnings for %1$s is $%2$.2f.\n", monthYear, profit));
                }
            }
        }
        return message.toString();
    }

    public void deleteShow(LocalDate showDate) {
        shows.deleteShow(showDate);
        this.setShowsGui(shows);
    }

    //// Commands that deals with Seats.

    public String viewSeats(LocalDate localDate) {
        return shows.viewSeats(localDate);
    }

    public String sellSeats(LocalDate localDate, String... seats) {
        return shows.sellSeats(localDate, seats);
    }

    public String reassignSeat(LocalDate showlocalDate, String oldSeat, String newSeat) {
        return shows.reassignSeat(showlocalDate, oldSeat, newSeat);
    }

}

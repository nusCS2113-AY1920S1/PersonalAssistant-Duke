package optix.commons;

import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.Map;

public class Model {
    private ShowMap showsHistory = new ShowMap();
    private ShowMap shows = new ShowMap();
    private ShowMap showsGui;

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

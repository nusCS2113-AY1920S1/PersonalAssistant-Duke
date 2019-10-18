package optix.commons;

import optix.commons.model.ShowMap;

import java.time.LocalDate;

public class Model {
    private ShowMap showsHistory = new ShowMap();
    private ShowMap shows = new ShowMap();

    /**
     * The Optix model.
     * @param storage the object which handles data from the save file.
     */
    public Model(Storage storage) {
        storage.loadShows(shows, showsHistory);
        storage.loadArchive(showsHistory);
        storage.writeArchive(showsHistory);
    }

    public ShowMap getShows() {
        return shows;
    }

    public ShowMap getShowsHistory() {
        return showsHistory;
    }

    public void setShows(ShowMap shows) {
        this.shows = shows;
    }

    public void setShowsHistory(ShowMap showsHistory) {
        this.showsHistory = showsHistory;
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
    }

    public void editShowName(LocalDate showDate, String showName) {
        shows.editShowName(showDate, showName);
    }

    public void postponeShow(LocalDate oldDate, LocalDate newDate) {
        shows.postponeShow(oldDate, newDate);
    }

    public String listShow() {
        return shows.listShow();
    }

    public String listShow(String showName) {
        return shows.listShow(showName);
    }

    public String listShow(LocalDate startOfMonth, LocalDate endOfMonth) {
        return shows.listShow(startOfMonth, endOfMonth);
    }

    public void deleteShow(LocalDate showDate) {
        shows.deleteShow(showDate);
    }

    public String deleteShow(String[] showNames) {
        return shows.deleteShow(showNames);
    }

    //// Commands that deals with Seats.

    public String viewSeats(LocalDate localDate) {
        return shows.viewSeats(localDate);
    }

    public String sellSeats(LocalDate localDate, String... seats) {
        return shows.sellSeats(localDate, seats);
    }
}

package optix.commons;

import optix.commons.model.ShowHistoryMap;
import optix.commons.model.ShowMap;

import java.time.LocalDate;

public class Model {
    private ShowHistoryMap showsHistory = new ShowHistoryMap();
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

    public ShowHistoryMap getShowsHistory() {
        return showsHistory;
    }

    public void setShows(ShowMap shows) {
        this.shows = shows;
    }

    public void setShowsHistory(ShowHistoryMap showsHistory) {
        this.showsHistory = showsHistory;
    }


    public boolean containsKey(LocalDate key) {
        return shows.containsKey(key);
    }

    public void addShow(String showName, LocalDate showDate, double seatBasePrice) {
        shows.addShow(showName, showDate, seatBasePrice);
    }

}

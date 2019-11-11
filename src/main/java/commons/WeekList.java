package commons;

import javafx.collections.ObservableList;
import javafx.scene.text.Text;

public class WeekList {
    private ObservableList<Text> monList;
    private ObservableList<Text> tueList;
    private ObservableList<Text> wedList;
    private ObservableList<Text> thuList;
    private ObservableList<Text> friList;
    private ObservableList<Text> satList;
    private ObservableList<Text> sunList;

    public WeekList() {
    }

    /**
     * This method constructs WeekList class.
     * @param monList The list of events on monday
     * @param tueList The list of events on tuesday
     * @param wedList The list of events on wednesday
     * @param thuList The list of events on thursday
     * @param friList The list of events on friday
     * @param satList The list of events on saturday
     * @param sunList The list of events on sunday
     */
    public WeekList(ObservableList<Text> monList, ObservableList<Text> tueList, ObservableList<Text> wedList,
                    ObservableList<Text> thuList, ObservableList<Text> friList, ObservableList<Text> satList,
                    ObservableList<Text> sunList) {
        this.monList = monList;
        this.tueList = tueList;
        this.wedList = wedList;
        this.thuList = thuList;
        this.friList = friList;
        this.satList = satList;
        this.sunList = sunList;
    }

    public ObservableList<Text> getMonList() {
        return monList;
    }

    public ObservableList<Text> getTueList() {
        return tueList;
    }

    public ObservableList<Text> getWedList() {
        return wedList;
    }

    public ObservableList<Text> getThuList() {
        return thuList;
    }

    public ObservableList<Text> getFriList() {
        return friList;
    }

    public ObservableList<Text> getSatList() {
        return satList;
    }

    public ObservableList<Text> getSunList() {
        return sunList;
    }
}


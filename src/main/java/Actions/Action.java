package Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserInterfaces.Ui;

public abstract class Action {
    Ui ui;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;

    public Action() {

    }

    public Action(Ui ui, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public abstract int execute();

    ;
}

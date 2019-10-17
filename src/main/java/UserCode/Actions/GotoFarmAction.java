package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Ui;

public class GotoFarmAction extends Action {

    public GotoFarmAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.gotoFarm;
    }

    public void execute(Ui ui) {

    }
}

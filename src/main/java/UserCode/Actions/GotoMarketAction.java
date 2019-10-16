<<<<<<< HEAD
package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
    }
    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().plantSeeds();
            new Simulate(ui, "GotoMarketSimulation", 3).simulate(super.farmio);
        } catch (Exception e){
            e.getMessage();
        }
    }
}
=======
package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
    }
    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().plantSeeds();
            new Simulate(ui, "GotoMarketSimulation", 3).simulate(super.farmio);
        } catch (Exception e){
            e.getMessage();
        }
    }
}
>>>>>>> ed4e35485dbfed07c4d7337b51acfafacc82b2ac

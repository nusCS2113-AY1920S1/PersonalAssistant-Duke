import FarmioExceptions.FarmioException;
import UserInterfaces.Ui;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public abstract class Menu {
//    private final String ART_NAME = "menu";
//    public show(Ui ui, Storage storage){
//        try{
//            ui.show(storage.getAsciiArt(ART_NAME));
//        } catch (IOException | FarmioException e) {
//            ui.showWarning(ART_NAME.substring(0, 1).toUpperCase() + ART_NAME.substring(1) + " ascii art missing!");
//        }
//        ui.showMenu(storage.getFarmerExist());
//        //TODO: convert swtich into parser
//        while(true){
//            switch(ui.getInput().toLowerCase()){
//                case "load save":
//                    try {
//                        this.farmer = new Farmer(ui, storage.loadFarmer());
//                        return;
//                    } catch (FarmioException | ParseException e) {
//                        ui.showWarning("Game save is corrupted!");
//                        ui.showInfo("Farmio starting a new game.");
//                    } catch (IOException e) {
//                        ui.showWarning("No game save detected!");
//                        ui.showInfo("Farmio starting a new game.");
//                    }
//                case "new game":
//                    this.farmer = new Farmer();
//                    return;
//                case "quit":
//                    System.exit(0);
//                default:
//                    ui.showWarning("Unknown command.");
//            }
//        }
//    }
}

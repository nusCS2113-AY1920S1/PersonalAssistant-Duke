package Optix.commands;

import optix.Ui;
import optix.commands.shows.AddCommand;
import optix.commands.ViewSeatsCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ViewSeatsCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);
@Test
void execute() {
	// add a dummy show
	AddCommand addDummyShow = new AddCommand("Dummy Show","5/5/2020",2000,20);
	addDummyShow.execute(shows,ui,storage);
	ViewSeatsCommand testCommand = new ViewSeatsCommand("Dummy Show","5/5/2020");
	testCommand.execute(shows, ui, storage);
	String expected1 =
	"__________________________________________________________________________________\n"
	+"Here is the layout of the theatre for Dummy Show on 5/5/2020: \n"
    +"                |STAGE|           \n"
	+"  [\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718]\n"
	+"  [\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718]\n"
	+"  [\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718]\n"
	+"  [\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718]\n"
	+"  [\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718]\n"
	+"  [\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718][\u2718]\n"

	+"\nTier 1 Seats: 20\n"
	+"Tier 2 Seats: 20\n"
	+"Tier 3 Seats: 20\n"
	+"__________________________________________________________________________________\n";
	assertEquals(expected1,ui.showLine());

	// view a show that does not exist
	ViewSeatsCommand viewNonExistentShow = new ViewSeatsCommand("non existent show","5/5/2020");
	viewNonExistentShow.execute(shows,ui,storage);
	String expected2 =
			"__________________________________________________________________________________\n"
			+"☹ OOPS!!! Sorry the show <non existent show> cannot be found.\n"
			+"__________________________________________________________________________________\n";
	assertEquals(expected2, ui.showLine());
}

}

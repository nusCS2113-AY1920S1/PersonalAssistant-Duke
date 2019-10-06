package Optix.commands;

import optix.commands.shows.AddCommand;
import optix.Ui;
import optix.commands.seats.SellSeatCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SellSeatCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);
	@Test
	void execute() {
		filePath.deleteOnExit();
		AddCommand addTestShow1 = new AddCommand("Test Show 1","5/5/2020", 2000, 20);
		addTestShow1.execute(shows, ui, storage);
		// sell an available seat
		SellSeatCommand testCommand1 = new SellSeatCommand("Test Show 1","5/5/2020","John","A1");
		testCommand1.execute(shows,ui,storage);
		String expected1 = "__________________________________________________________________________________\n"
				+ "You have successfully purchased the following seats: \n"
				+ "[A1]\n"
				+ "The total cost of the ticket is $30.00\n"
				+ "__________________________________________________________________________________\n";
		assertEquals(expected1,ui.showLine());

		// sell a seat that is taken
	//	SellSeatCommand testCommand2 = new SellSeatCommand("Test Show 1", "5/5/2020","A1");
		testCommand1.execute(shows,ui,storage);
		String expected2 = "__________________________________________________________________________________\n"
				+ "☹ OOPS!!! All of the seats [A1] are unavailable\n"
				+ "__________________________________________________________________________________\n";
		assertEquals(expected2,ui.showLine());
		// sell a seat that does not exist
//		SellSeatCommand testCommand2 = new SellSeatCommand("Test Show 1", "5/5/2020","John","%1");
//		testCommand2.execute(shows,ui,storage);
//		String expected3 = "__________________________________________________________________________________\n"
//				+ "You have successfully purchased the following seats: \n"
//				+ "\n"
//				+ "The total cost of the ticket is $00.00\n"
//				+ "The following seats are unavailable: \n"
//				+ "[A1]\n"
//				+ "__________________________________________________________________________________\n";
//		assertEquals(expected3,ui.showLine());
	}
}
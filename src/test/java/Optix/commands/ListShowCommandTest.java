package Optix.commands;

import optix.Ui;
import optix.commands.shows.AddCommand;
import optix.commands.shows.ListShowCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
class ListShowCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);


	@Test
	void execute() {
		// try looking for a show that does not exist
		ListShowCommand testCommand1 = new ListShowCommand("non existent show.");
		testCommand1.execute(shows,ui,storage);
		String expected1 = "__________________________________________________________________________________\n" +
				"☹ OOPS!!! The show cannot be found.\n" +
				"__________________________________________________________________________________\n";
		assertEquals(expected1, ui.showLine());

		// insert dummy show
		AddCommand insertDummyShow1 = new AddCommand("Dummy Show","5/5/2020",2000,20);
		insertDummyShow1.execute(shows,ui,storage);
		AddCommand insertDummyShow2 = new AddCommand("Dummy Show","6/5/2020",2000,20);
		insertDummyShow2.execute(shows,ui,storage);
		// attempt to view dummy show.
		ListShowCommand testCommand2 = new ListShowCommand("Dummy Show");
		testCommand2.execute(shows,ui,storage);
		String expected2 = "__________________________________________________________________________________\n" +
				"The show Dummy Show is showing on the following following dates: \n" +
				"1. 2020-05-05\n" +
				"2. 2020-05-06\n" +
				"__________________________________________________________________________________\n";
		assertEquals(expected2, ui.showLine());
		filePath.deleteOnExit();
	}
}
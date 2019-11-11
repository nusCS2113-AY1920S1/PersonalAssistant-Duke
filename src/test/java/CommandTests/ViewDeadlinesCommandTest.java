package CommandTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
//
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

import java.text.ParseException;
import java.util.*;
import rims.exception.RimsException;
import rims.resource.*;

public class ViewDeadlinesCommandTest {
    //private ViewDeadlinesCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;

    @BeforeAll
    private static void init() throws RimsException, ParseException {
        ui = new Ui();
        ArrayList<Resource> newList = new ArrayList<Resource>();
        //ArrayList<Reservation> reserveListItem = new ArrayList<>();
        ReservationList reserveList = new ReservationList();
        Reservation itemReservation = new Reservation(1, 1, 5, "20/12/2019 0900", "21/12/2019 1000");
        reserveList.add(itemReservation);
        Item newItem = new Item(1, "ball", reserveList);
        //ReservationList reserveListRoom = new ReservationList();
        Reservation roomReservation = new Reservation(2, 2, 5, "20/12/2019 0900", "20/12/2019 0930");
        reserveList.add(roomReservation);
        Room newRoom = new Room(2, "mpsh", reserveList);
        newList.add(newItem);
        newList.add(newRoom);
        listUnderTest = new ResourceList(ui, newList);
    }
    
    /**
     * This test creates a resource list, and tries to list resources by date ,which
     * should not invoke a RimsException.
     * 
     * @throws RimsException
     */
    @Test
    void PrintByDateTest() throws RimsException {
        ;
    }

}

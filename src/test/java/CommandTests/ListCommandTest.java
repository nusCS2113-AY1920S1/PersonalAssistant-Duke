// package CommandTests;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeAll;
// import rims.command.ListCommand;
// import rims.command.ReserveCommand;
// import rims.core.ResourceList;
// import rims.core.Storage;
// import rims.core.Ui;

// import java.text.ParseException;
// import java.util.*;
// import rims.core.*;
// import rims.exception.RimsException;
// import rims.resource.*;

// import static org.junit.jupiter.api.Assertions.*;

// public class ListCommandTest {
//     private ListCommand commandUnderTest;
//     private static Ui ui;
//     private static Storage storage;
//     private static ResourceList listUnderTest;

//     @BeforeAll
//     private static void init() throws RimsException, ParseException {
//         ui = new Ui();
//         ArrayList<Resource> newList = new ArrayList<Resource>();
//         //ArrayList<Reservation> reserveListItem = new ArrayList<>();
//         ReservationList reserveList = new ReservationList();
//         Reservation itemReservation = new Reservation(1, 1, 5, "20/12/2019 0900", "21/12/2019 1000");
//         reserveList.add(itemReservation);
//         Item newItem = new Item(1, "ball", reserveList);
//         //ReservationList reserveListRoom = new ReservationList();
//         Reservation roomReservation = new Reservation(2, 2, 5, "20/12/2019 0800", "20/12/2019 0830");
//         reserveList.add(roomReservation);
//         Room newRoom = new Room(2, "mpsh", reserveList);
//         newList.add(newItem);
//         newList.add(newRoom);
//         listUnderTest = new ResourceList(ui, newList);
//     }

//     /**
//      * This test creates a resource list, and tries to list resources by item
//      * ,which should not invoke a RimsException.
//      */
//     @Test
//      void PrintByItemTest() {

//         commandUnderTest = new ListCommand("item", "ball");
//         Exception e = assertThrows(RimsException.class, () -> {
//             commandUnderTest.execute(ui, storage, listUnderTest);
//         });

//         assertEquals("[I] ball (ID:1)" + "\t" + "[1] borrowed by user: 5 from Friday 20th December 2019, 09:00 AM till Saturday, 21st December 2019, 10:00 AM", e.getMessage());
//         System.out.print("Test 1: passed");
//     }
//     /**
//      * This test creates a resource list, and tries to list resources by room
//      * ,which should not invoke a RimsException.
//      */
//     @Test
//     void PrintByRoomTest() {

//         commandUnderTest = new ListCommand("room", "mpsh");
//         Exception e = assertThrows(RimsException.class, () -> {
//             commandUnderTest.execute(ui, storage, listUnderTest);
//         });

//         assertEquals("[R] mpsh (ID:2)" + "\t" + "[2] borrowed by user: 5 from Friday 20th December 2019, 08:00 AM till Friday, 20th December 2019, 08:30 AM", e.getMessage());
//         System.out.print("Test 2: passed");
//     }
//     /**
//      * This test creates a resource list, and tries to list resources by date
//      * ,which should not invoke a RimsException.
//      */
//     @Test
//     void PrintByDateTest() {

//         commandUnderTest = new ListCommand("date", "20/12/2019 0820");
//         Exception e = assertThrows(RimsException.class, () -> {
//             commandUnderTest.execute(ui, storage, listUnderTest);
//         });

//         assertEquals("[R] mpsh (qty: 1)" + "\t" + "[2] borrowed by user: 5 from Friday 20th December 2019, 08:00 AM till Friday, 20th December 2019, 08:30 AM", e.getMessage());
//         System.out.print("Test 3: passed");
//     }
// }

package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetBusRouteCommandTest {

    @Test
    void getBusRouteTest() throws DukeException {
        ModelStub model = new ModelStub();
        String expected = "Here is the bus route:\n" + "45009 BT PANJANG INT\n" + "44259 Blk 183\n"
                + "44149 Opp Phoenix Stn\n" + "44271 Bet Blks 13/14\n" + "44281 Blk 8\n" + "44291 Opp Cck Polyclinic\n"
                + "44391 Aft Blk 202\n" + "44531 Opp Choa Chu Kang Stn\n" + "44461 Blk 352\n" + "44521 Blk 280\n"
                + "44511 Blk 289\n" + "44689 Sunshine Pl\n" + "44079 Opp Blk 464\n" + "30089 Aft Hai Inn See Tp\n"
                + "30099 Opp Chengtai Nursery\n" + "30059 Opp Track 14\n" + "30049 Home Team Academy\n"
                + "30039 Army Logistics Base\n" + "30029 Keat Hong Camp\n" + "30019 Opp Tengah Air Base\n"
                + "31199 B25 Old Choa Chu Kang Rd\n" + "31179 Aft Jln Berseri\n" + "31169 Teen Challenge\n"
                + "31159 Bef Lim Chu Kang Rd\n" + "31061 Opp Lim Chu Kang Camp II\n"
                + "31071 Aft Chinese Cemy Path 11\n" + "31081 Opp HQ 5th SIB Camp I\n"
                + "34101 Aft Old Lim Chu Kang Rd\n" + "32101 Aft Murai Farmway\n" + "32111 Aft LP 93\n"
                + "32031 Opp Ama Keng Rd\n" + "32041 Opp LP 160\n" + "32051 Aft Track 11\n" + "32061 Opp LP 173\n"
                + "32071 Aft Track 13\n" + "32081 Sg Gedong Camp\n" + "33011 B19 Lim Chu Kang Rd\n"
                + "33021 Opp Neo Tiew Rd\n" + "33031 Aft Jln Bahtera\n" + "33041 Opp Lim Chu Kang Lane 3\n"
                + "33051 Bef Lim Chu Kang Lane 3A\n" + "34011 Opp Lim Chu Kang Lane 4\n"
                + "34021 Opp Lim Chu Kang Lane 5\n" + "34031 Aft Lim Chu Kang Lane 6\n" + "34041 B03 Lim Chu Kang Rd\n"
                + "34051 Aft Lim Chu Kang Lane 8\n" + "34009 Police Coast Guard\n" + "34059 Yishun Dairy Farm\n"
                + "34049 Aft Lim Chu Kang Lane 8\n" + "34039 Bef Lim Chu Kang Lane 6\n"
                + "34029 Bef Lim Chu Kang Lane 5\n" + "34019 Aft Lim Chu Kang Lane 4\n"
                + "33059 Aft Lim Chu Kang Lane 3A\n" + "33049 Aft Lim Chu Kang Lane 3\n" + "33039 Opp Jln Bahtera\n"
                + "33029 Aft Neo Tiew Rd\n" + "33019 B20 Lim Chu Kang Rd\n" + "32089 Aft Sg Gedong Camp\n"
                + "32079 Opp Track 13\n" + "32069 B26 Lim Chu Kang Rd\n" + "32059 Opp Track 11\n" + "32049 Aft LP 161\n"
                + "32039 Aft Ama Keng Rd\n" + "32119 Aft LP 96\n" + "32109 Bef Lor Serambi\n"
                + "34109 Aft Lim Chu Kang Rd\n" + "31089 HQ 5th SIB Camp I\n" + "31079 Opp Chinese Cemy Path 11\n"
                + "31069 Bef Old Choa Chu Kang Rd\n" + "31151 Aft Lim Chu Kang Rd\n" + "31161 Opp Teen Challenge\n"
                + "31171 Opp Jln Berseri\n" + "31191 B26 Old Choa Chu Kang Rd\n" + "30011 Tengah Air Base\n"
                + "30021 Opp Keat Hong Camp\n" + "30031 Opp Army Logistics Base\n" + "30041 Opp Home Team Academy\n"
                + "30051 Aft Track 14\n" + "30091 Chengtai Nursery\n" + "30081 Opp Hai Inn See Tp\n"
                + "44071 Blk 464\n" + "44519 Blk 402\n" + "44529 Blk 410\n" + "44469 Blk 414\n"
                + "44539 Lot 1/Choa Chu Kang Stn\n" + "44399 Opp Blk 210\n" + "44299 Cck Polyclinic\n"
                + "44289 Blk 6\n" + "44279 Blk 157\n" + "44141 Phoenix Stn\n" + "44251 Bt Panjang Stn/Blk 604\n"
                + "45009 BT PANJANG INT\n";


        GetBusRouteCommand command = new GetBusRouteCommand("975");
        assertEquals(expected, command.execute(model).getMessage());

        String expectedError = "I'm sorry, but nothing was found...\n";

        //non-existant bus stop
        GetBusRouteCommand command2 = new GetBusRouteCommand("0");
        assertEquals(expectedError, command2.execute(model).getMessage());

        //negative number bus stop
        GetBusRouteCommand command3 = new GetBusRouteCommand("-1");
        assertEquals(expectedError, command3.execute(model).getMessage());

        GetBusRouteCommand command4 = new GetBusRouteCommand("-2");
        assertEquals(expectedError, command4.execute(model).getMessage());

        //test for string
        GetBusRouteCommand command5 = new GetBusRouteCommand("test");
        assertThrows(AssertionError.class, () -> {
            command5.execute(model).getMessage();
        });
    }
}

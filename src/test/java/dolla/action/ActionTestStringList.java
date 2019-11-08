package dolla.action;

public interface ActionTestStringList {
    String MODE_DEBT = "debt";
    String MODE_ENTRY = "entry";
    String MODE_LIMIT = "limit";

    String OWE = "owe";
    String BORROW = "borrow";

    String DEBT_TEST1 = "[owe] [tata] [$20.0] [food] [/due 01/01/2019] {Tag: }";
    String DEBT_TEST2 = "[borrow] [yuyu] [$40.0] [drink] [/due 02/01/2019] {Tag: }";
    String DEBT_TEST3 = "[owe] [mama] [$30.0] [bill] [/due 03/01/2019] {Tag: }";
    String DEBT_TEST4 = "[borrow] [baba] [$60.0] [dinner] [/due 04/01/2019] {Tag: }";

    String DEBT_TEST5 = "[borrow] [aikpeng] [$126.0] [bushi] [/due 01/01/2011] {Tag: }";
    String DEBT_TEST6 = "[owe] [yetong] [$245.0] [sushi] [/due 02/01/2012] {Tag: }";
    String DEBT_TEST7 = "[owe] [kexin] [$91.0] [disco] [/due 03/01/2013] {Tag: }";
    String DEBT_TEST8 = "[owe] [xiaoxue] [$1151.0] [wine] [/due 04/01/2014] {Tag: }";
    String DEBT_TEST9 = "[borrow] [bahaba] [$521.0] [rebina] [/due 04/01/2015] {Tag: }";

    String TEST_INPUT1 = "add expense 200 buy food /on 12/12/2019";
    String TEST_INPUT2 = "add income 300 tution /on 21/12/2019";
    String TEST_INPUT3 = "add expense 6 gongcha /on 11/11/2019";
    String TEST_INPUT4 = "add income 40  sell pen /on 1/10/2019";


}

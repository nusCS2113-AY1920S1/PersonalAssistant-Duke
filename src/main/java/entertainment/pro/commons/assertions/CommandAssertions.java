package entertainment.pro.commons.assertions;

import java.util.ArrayList;

/**
 * Command assertions management class.
 */
public class CommandAssertions {

    /**
     * Asserts that the String is all lower case.
     *
     * @param str input string to be checked
     * @return true is string is all lower
     */
    public static boolean assertIsLowerString(String str) {
        char[] charArray = str.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            //if any character is in upper case, return false
            if (Character.isUpperCase(charArray[i])) {
                return false;
            }
        }

        return true;
    }


    /**
     * Asserts that the String Array is all lower case.
     *
     * @param strArr input string array to be checked
     * @return true is string array is all lower
     */
    public static boolean assertIsLowerStringArr(String [] strArr) {
        System.out.print("STRING ARRAY");
        System.out.print(strArr.toString());

        char[] charArray = strArr.toString().toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            //if any character is in upper case, return false
            if (Character.isUpperCase(charArray[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Asserts that the String Arraylist is all lower case.
     *
     * @param strArrL input string array list to be checked
     * @return true is string array is all lower
     */
    public static boolean assertIsLowerStringList(ArrayList<String> strArrL) {
        System.out.print("STRING ARRAYLIST");
        System.out.print(strArrL.toString());
        char[] charArray = strArrL.toString().toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            //if any character is in upper case, return false
            if (Character.isUpperCase(charArray[i])) {
                return false;
            }
        }

        return true;
    }
}

package Javadoc_Exercise;

/** An un-instantiable (effectively static) class of utility functions for use in various projects.
 * @author Louis Doherty @ The Software Institute
 * @since JDK 21.0.2
 * @version Feb 2024
 */
public class Utils {

    private Utils(){}

    /** A function to determine whether a given number is prime or not in O(n) time.
     * <b>Please note negative numbers cannot be prime.</b>
     * @param toCheck The number to determine the primeness of.
     * @return True if toCheck is prime, false otherwise.
     */
    public static boolean isPrime(int toCheck) {
        if(toCheck < 2) {
            return false;
        }

        for(int i = 2; i < toCheck/2; i++) {
            if(toCheck%i == 0) {
                return false;
            }
        }

        return true;
    }

    public static String reverse(String toReverse) {
        StringBuilder returnString = new StringBuilder();

        for(int i = toReverse.length()-1; i >= 0; i--) {
            returnString.append(toReverse.charAt(i));
        }

        return returnString.toString();
    }

    public static int binarySearch(int[] arr, int desiredElement) {
        int startIndex = 0;
        int endIndex = arr.length-1;
        int middle;

        while(true) {
            middle = startIndex+((endIndex-startIndex)/2);

            if(arr[middle] == desiredElement) {
                return middle;
            } else if(startIndex == endIndex) {
                return -1;
            } else {
                if(arr[middle] < desiredElement) {
                    startIndex = middle+1;
                } else {
                    endIndex = middle-1;
                }
            }
        }
    }

    /** An in-place implementation of bubble sort, an O(n^2) sorting algorithm. Sorts into ascending order.
     * @param toSort An array of integer values to be sorted.
     */
    //https://www.hackerearth.com/practice/algorithms/sorting/bubble-sort/visualize/
    //Implementation

    /** A function to determine the nth element of the fibonnaci-sequence.
     * @param toFind The number in the sequence we want to find (e.g. the 5th element - not the index).
     * @return The number at that position in the sequence.
     */
    //https://en.wikipedia.org/wiki/Fibonacci_sequence
    //Implementation

}

import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
  private static final int ASCII_ZERO = 48;
  private static String num_toString;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    BigInteger inputNumber = scanner.nextBigInteger();
    scanner.close();
    
    BigInteger result = nextLargestInteger(inputNumber);
    System.out.println(result);
  }

  /**
   * Finds the next largest integer to the input integer by rearanging the digits of it.
   *
   * @return An integer representing the next largest integer to the input integer. 
   *         If it is not possible to rearrange the digits for a larger integer, 
   *         then the input integer is returned.
   */
  public static BigInteger nextLargestInteger(BigInteger inputNumber) {
    num_toString = inputNumber.toString();

    int index_leftSwap = findDigit_leastPlaceValue_andNumericValue_lessThanRightNeigbour();
    if (index_leftSwap < 0) {
      return inputNumber;
    }

    int index_rightSwap = find_minDiffernce_toRigtDigits(index_leftSwap);
    BigInteger result = new BigInteger(rearrangeNumber(index_leftSwap, index_rightSwap));

    return result;
  }

  /**
   * Finds the digit with least 'place value', which 'numeric value' is also less than 
   * the 'numeric value' of its direct neighbour to the right.
   *
   * Example: 12187465 
   *         '4' => digit with least 'place value' and 'numeric value' less than 
   *         the 'numeric value' of its direct neighbour to the right.
   *
   * @return A non-negative integer, representing the index of the found digit.
   *         If not such digit is found, it returns '-1';
   */
  private static int findDigit_leastPlaceValue_andNumericValue_lessThanRightNeigbour() {
    int rightIndex = num_toString.length() - 1;
    int leftIndex = rightIndex - 1;

    while (leftIndex >= 0) {
      if (num_toString.charAt(leftIndex) < num_toString.charAt(rightIndex)) {
        break;
      }
      leftIndex--;
      rightIndex--;
    }
    return leftIndex;
  }

  /**
   * Having established the left swap digit, find the most suitable right swap digit.
   *
   * The most suitable swap digit to the right is this digit, where: 
   * 'left swap digit' < 'digit to the right' 
   *  and 
   * 'digit to the right' - 'left swap digit' is minimum.
   *
   * Example: 112651 => leftSwap: 2, (6-2)=4, (5-2)=3, rightSwap: 5.
   *
   * @return An integer, representing the index of the most suitable swap digit to the right.
   */
  private static int find_minDiffernce_toRigtDigits(int index_leftSwap) {
    int index_rightSwap = index_leftSwap + 1;
    int index_minDifference = index_leftSwap;
    int minDifference = Integer.MAX_VALUE;
    char leftSwap = num_toString.charAt(index_leftSwap);

    while (index_rightSwap < num_toString.length()) {
      char rightSwap = num_toString.charAt(index_rightSwap);
      if (rightSwap > leftSwap && (rightSwap - leftSwap) < minDifference) {
        minDifference = rightSwap - leftSwap;
        index_minDifference = index_rightSwap;
      }
      index_rightSwap++;
    }

    return index_minDifference;
  }

  /**
   * Interchanges the places of the left and right swap digits.
   * Then sorts in increasing order all the digits after the swapped digit to the left.
   *
   * Example: 112651 => leftSwap: 2, rightSwap: 5. 
   *                    after swap: 115621, after sort: 115126
   *
   * @return A string, representing the next largest integer.
   */
  private static String rearrangeNumber(int index_leftSwap, int index_rightSwap) {

    StringBuilder result = new StringBuilder(num_toString);
    result.setCharAt(index_leftSwap, num_toString.charAt(index_rightSwap));
    result.setCharAt(index_rightSwap, num_toString.charAt(index_leftSwap));

    /**
    * Stores number of occurences of all digits after the swapped digit to the left.
    */
    int[] digits = new int[10];
    for (int i = index_leftSwap + 1; i < num_toString.length(); i++) {
      digits[result.codePointAt(i) - ASCII_ZERO]++;
    }

    /**
    * Appends to result all the digits after after the swapped digit to the left, 
    * in increasingn order.
    */
    int index_sorted = index_leftSwap + 1;
    for (int i = 0; i < 10; i++) {

      char ch = (char) (ASCII_ZERO + i);
      while (digits[i] > 0) {
        result.setCharAt(index_sorted, ch);
        index_sorted++;
        digits[i]--;
      }
    }

    return result.toString();
  }
}

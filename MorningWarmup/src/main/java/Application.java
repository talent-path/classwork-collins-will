import java.lang.reflect.Array;
import java.util.*;

public class Application {

    public static void main(String[] args) {
/*        System.out.println(middleOfThree(1,2,3));
        System.out.println(middleOfThree(1,3,2));
        System.out.println(middleOfThree(2,1,3));
        System.out.println(middleOfThree(2,3,1));
        System.out.println(middleOfThree(3,1,2));
        System.out.println(middleOfThree(3,2,1));

        fizzBuzz();

        System.out.println(canBalance(new int[]{1,2,3,4,4,3,2,1}));
        System.out.println(canBalance(new int[]{2,5,8,5}));
        System.out.println(canBalance(new int[]{2,8,1,5,5}));

        System.out.println();

        System.out.println(noTriples(new int[]{5,5,5}));
        System.out.println(noTriples(new int[]{1,2,2,3}));
        System.out.println(noTriples(new int[]{5,2,5,4,4,3}));

        int[] sum = addBigNum(new int[]{7,8,9}, new int[]{5,5,5});
        for (int i = 0; i < sum.length; i++) {
            System.out.println(sum[i]);
        }
        // {2,4,5,1}

        int[] prod = multiplyBigNum(new int[]{7,8,9}, new int[]{5,5,5});
        for (int i = 0; i < prod.length; i++) {
            System.out.println(prod[i]);
        }
        // {5,8,7,7,4,5}

        System.out.println(maxMirror(new int[] {1,2,3,8,9,3,2,1})); // 3
        System.out.println(maxMirror(new int[] {1,2,1,4})); // 3
        System.out.println(maxMirror(new int[] {7,1,2,9,7,2,1})); // 2
        System.out.println(maxMirror(new int[] {1,2,3,2,1}));

        String[] allNames = {"Bob","Bobby","Robert","Roberto","Alice","Alicia"};
        Map<String, List<String>> groupedNames = groupByFirstTwoLetters(allNames);
        System.out.println(groupedNames.toString());

        groupedNames = groupByFirstNLetters(allNames, 4);
        System.out.println(groupedNames.toString());

        findLongestCollatzInRange(1000000);

        System.out.println(digitReverse(123));
        System.out.println(digitReverse(2001));
        System.out.println(digitReverse(2000));

        System.out.println(isPerfect(6));
        System.out.println(isPerfect(28));
        System.out.println(isPerfect(8));*/

        char[][] board = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        solveSudoku(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int middleOfThree(int a, int b, int c) {
        int midNum = a;

        if ((midNum > b && midNum > c) || (midNum < b && midNum < c)) {
            midNum = b;
        }

        if ((midNum > a && midNum > c) || (midNum < a && midNum < c)) {
            midNum = c;
        }

        return midNum;
    }

    public static int greatestOfThree(int a, int b, int c) {
        int out = a;

        if (b > out) {
            out = b;
        }

        if (c > out) {
            out = c;
        }

        return out;
    }

    public static void fizzBuzz() {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 != 0 && i % 5 != 0 && i % 7 != 0) {
                System.out.println(i);
            } else {
                if (i % 3 == 0) {
                    System.out.print("fizz");
                }
                if (i % 5 == 0) {
                    System.out.print("buzz");
                }
                if (i % 7 == 0) {
                    System.out.print("bang");
                }
                System.out.print("\n");
            }
        }
    }

    // Warmup 1/15/21
    public static boolean canBalance(int[] nums) {
        // return true if we can split array so that earlier numbers equal later half
        int totalSum = Aggregate.sum(nums);
        if (totalSum % 2 != 0) {
            return false;
        }

        int leftSum = 0;
        int halfSum = totalSum / 2;
        for (int i = 0; i < nums.length - 1; i++) {
            leftSum += nums[i];
            if (leftSum == halfSum) {
                return true;
            }
        }

        return false;
    }

    public static boolean noTriples(int[] arr) {
        // return true if same digit not repeated three times in a row
        if (arr.length < 3) {
            return true;
        }

        for (int i = 0; i < arr.length - 2; i++) {
            if (arr[i] == arr[i+1] && arr[i] == arr[i+2]) {
                return false;
            }
        }
        return true;
    }

    // Warmup 1/19/21

    // give two arrays of size 100 each representing a 100 digit number
    //      (each element of the input array will have a value between 0 and 9)
    // return the 101-element "sum" of these two numbers
    //      (in the output array, the digits should also be between 0 and 9)
    // The digit at index 0 is the one's place, index 1 is the 10's, etc.
    public static int[] addBigNum(int[] left, int[] right) {
        int[] output = new int[left.length + 1];
        int carry = 0;

        for (int i = 0; i < left.length; i++) {
            int digitSum = left[i] + right[i] + carry;
            carry = digitSum / 10;
            output[i] = digitSum % 10;
        }

        output[left.length] = carry;
        return output;
    }

    public static int[] multiplyBigNum(int[] left, int[] right) {
        // determine number of digits (size of output array)
        int[] output = new int[left.length + right.length];
        int count = 0;

        for (int i = 0; i < left.length; i++) {
            int[] tempArr = new int[left.length + right.length];
            int carry = 0;

            for (int j = 0; j < right.length; j++) {
                int digitProd = left[i] * right[j] + carry;
                carry = digitProd / 10;
                tempArr[j + count] = digitProd % 10;
            }
            tempArr[right.length + count] = carry;
            int[] tempSum = addBigNum(output, tempArr);
            // take only significant digits
            boolean sigFig = false;
            for (int j = tempSum.length - 1; j >= 0; j--) {
                if (tempSum[j] != 0 || (tempSum[j] == 0 && sigFig)) {
                    sigFig = true;
                    output[j] = tempSum[j];
                }
            }
            count++;
        }

        return output;
    }

    public static int[] addSmallNum(int[] left, int[] right) {
        throw new UnsupportedOperationException();
    }

    // Warmup 1/20/21

    public static int maxMirror(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int output = 1;

        // length of subarray
        for (int i = 2; i <= arr.length; i++) {
            // starting index of first subarray
            for (int j = 0; j + i <= arr.length && i > output; j++) {
                // starting index of second subarray
                for (int k = i % 2 == 0 ? j+1 : j; k + i <= arr.length && i > output; k++) {
                    boolean matching = true;

                    for (int l = 0; l < i; l++) {
                        if (arr[j+l] != arr[k+i-1-l]) {
                            matching = false;
                        }
                    }

                    if (matching && i > output) {
                        output = i;
                    }
                }
            }
        }
        return output;
    }

    // Warmup 1/21/21

    public static Map<String, List<String>> groupByFirstTwoLetters(String[] toGroup) {
        return groupByFirstNLetters(toGroup, 2);
    }

    public static Map<String, List<String>> groupByFirstNLetters(String[] toGroup, int numLetters) {
        Map<String, List<String>> outMap = new HashMap<>();

        for (String str : toGroup) {
            String firstN = numLetters > str.length() ? str.toUpperCase() : str.substring(0, numLetters).toUpperCase();
            if (!outMap.keySet().contains(firstN)) {
                outMap.put(firstN, new ArrayList<>());
            }
            outMap.get(firstN).add(str);
        }

        return outMap;
    }

    // Warmup 1/22/21
    // Project Euler Problem 14

    public static long collatzChainLength(long start) {
        // base case
        if (start == 1) {
            return 1;
        } else if (start % 2 == 0) {
            return 1 + collatzChainLength(start / 2);
        } else {
            return 1 + collatzChainLength(3 * start + 1);
        }
    }

    public static void findLongestCollatzInRange(long exclusiveMax) {
        long longestLength = 0;
        long longestStart = 0;
        for (long i = 1; i < exclusiveMax; i++) {
            long length = collatzChainLength(i);
            if (length > longestLength) {
                longestLength = length;
                longestStart = i;
            }
        }

        System.out.println("Longest Collatz chain under " + exclusiveMax +  " starts at "
                + longestStart + " with a length of " + longestLength);
    }

    // Warmup 1/25/21

    // 123   ->  321
    // 2001  ->  1002
    // 2000  ->  2
    public static int digitReverse(int toFlip) {
        int flip = toFlip;
        int out = 0;

        // check number of digits
        int numDigits = 1;
        boolean more = true;

        while (more) {
            if (Math.floor(toFlip / Math.pow(10, numDigits)) >= 1) {
                numDigits++;
            } else {
                more = false;
            }
        }

        // use modulo math to get digits out
        for (int i = 0; i < numDigits; i++) {
            int currentDigit = (int) Math.floor(flip / Math.pow(10, numDigits - 1 - i));
            out += currentDigit * Math.pow(10, i);
            flip -= currentDigit * Math.pow(10, numDigits - 1 - i);
        }
        return out;
    }

    // Warmup 1/26/21

    // a perfect number is one where the sum of ALL factors
    // adds up to 2x the number
    // 6: 1 + 2 + 3 + 6 = 12
    // 28: 1 + 2 + 4 + 7 + 14 + 28 = 56
    public static boolean isPerfect(int num) {
        int factorSum = 0;
        factorSum += 1 + num;

        for (int i = 2; i * i < num; i++) {
            if (num % i == 0) {
                factorSum += i;

                if (num / i != i) {
                    factorSum += (num / i);
                }
            }
        }

        return factorSum / num == 2;
    }

    // Warmup 1/27/21

    public static boolean isValidSudoku(char[][] board) {
        List<Character> checkedChars = new ArrayList<>();

        // check rows
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                if (checkedChars.contains(board[i][j])) {
                    return false;
                }

                if (board[i][j] != '.') {
                    checkedChars.add(board[i][j]);
                }
            }
            checkedChars.clear();
        }

        // check columns
        for (int j = 0; j < 9; j++) {

            for (int i = 0; i < 9; i++) {

                if (checkedChars.contains(board[i][j])) {
                    return false;
                }

                if (board[i][j] != '.') {
                    checkedChars.add(board[i][j]);
                }
            }
            checkedChars.clear();
        }

        // check boxes
        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {

                for (int i2 = i; i2 < i + 3; i2++) {
                    for (int j2 = j; j2 < j + 3; j2++) {

                        if (checkedChars.contains(board[i2][j2])) {
                            return false;
                        }

                        if (board[i2][j2] != '.') {
                            checkedChars.add(board[i2][j2]);
                        }
                    }
                }
                checkedChars.clear();
            }
        }

        return true;
    }

    public static boolean isValidSudoku(char[][] board, int row, int col) {
        List<Character> checkedChars = new ArrayList<>();

        for (int j = 0; j < 9; j++) {
            if (checkedChars.contains(board[row][j])) {
                return false;
            }
            if (board[row][j] != '.') {
                checkedChars.add(board[row][j]);
            }
        }
        checkedChars.clear();

        for (int i = 0; i < 9; i++) {
            if (checkedChars.contains(board[i][col])) {
                return false;
            }
            if (board[i][col] != '.') {
                checkedChars.add(board[i][col]);
            }
        }
        checkedChars.clear();

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (checkedChars.contains(board[i][j])) {
                    return false;
                }
                if (board[i][j] != '.') {
                    checkedChars.add(board[i][j]);
                }
            }
        }

        return true;
    }

    public static void solveSudoku(char[][] board) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (int k = 1; k <= 9; k++) {
                        char current = (char) (k + 48);
                        board[i][j] = current;

                        if (isValidSudoku(board, i, j)) {
                            if (isFullSudoku(board)) {
                                return;
                            } else {
                                solveSudoku(board);
                                if (isFullSudoku(board)) {
                                    return;
                                }
                            }
                        }
                    }
                    board[i][j] = '.';
                    return;
                }
            }
        }
    }

    public static boolean isFullSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
}


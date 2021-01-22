import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        System.out.println(sumMultiplesOf3And5(1000));

        System.out.println(sumEvenFibonacciNumbers(4000000));

        System.out.println(largestPrimeFactor(600851475143L));

        System.out.println(largestPalindromeProduct());

        System.out.println(smallestMultiple(10));
    }

    // Problem 1
    public static int sumMultiplesOf3And5(int exMax) {
        int out = 0;
        for (int i = 1; i < exMax; i++) {
            if (i % 3 == 0) {
                out += i;
            }
            if (i % 5 == 0 && i % 3 != 0) {
                out += i;
            }
        }
        return out;
    }

    // Problem 2

    public static int fibonacci(int index) {
        // base cases
        if (index == 0) {
            return 1;
        } else if (index == 1) {
            return 2;
        } else {
            return fibonacci(index - 1) + fibonacci(index - 2);
        }
    }

    public static int sumEvenFibonacciNumbers(int exMax) {
        int out = 0;
        int fiboNum = fibonacci(0);
        int i = 0;

        while (fiboNum < exMax) {
            if (fiboNum % 2 == 0) {
                out += fiboNum;
            }
            i++;
            fiboNum = fibonacci(i);
        }

        return out;
    }

    // Problem 3

    public static long largestPrimeFactor(long num) {
        long out = -1;

        while (num % 2 == 0) {
            out = 2;
            num /= 2;
        }

        for (long i = 3; i < Math.sqrt((double) num); i+=2) {
            if (num % i == 0) {
                out = i;
                num /= i;
            }
        }
        if (num > 2) {
            return num;
        } else {
            return out;
        }
    }

    // Problem 4

    public static boolean isPalindrome(String str) {
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                return false;
            }
        }

        return true;
    }

    public static int largestPalindromeProduct() {
        int out = 0;
        for (int i = 100; i < 1000; i++) {
            for (int j = i; j < 1000; j++) {
                int prod = i * j;

                if (prod > out) {
                    String strProd = Integer.toString(prod);
                    if (isPalindrome(strProd)) {
                        out = prod;
                    }
                }
            }
        }

        return out;
    }
}

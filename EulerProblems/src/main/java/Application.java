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

        System.out.println(smallestMultiple(20));

        System.out.println(sumSquareDiff(100));
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

    // Problem 5

    public static List<Integer> generatePrimes(int max) {
        List<Integer> out = new ArrayList<>();
        out.add(2);

        for (int i = 3; i <= max; i+=2) {
            int j = 0;
            boolean isPrime = true;

            while (out.get(j) * out.get(j) <= i) {
                if (i % out.get(j) == 0) {
                    isPrime = false;
                }
                j++;
            }

            if (isPrime) {
                out.add(i);
            }
        }

        return out;
    }

    public static int smallestMultiple(int max) {
        int out = 1;
        List<Integer> primes = generatePrimes(max);
        Map<Integer, List<Integer>> factorCounts = new HashMap<>();

        for (int i = 2; i <= max; i++) {
            int num = i;
            List<Integer> count = new ArrayList<>();

            while (num > 1) {
                for (int prime : primes) {
                    if (num % prime == 0) {
                        count.add(prime);
                        num /= prime;
                    }
                }
            }

            factorCounts.put(i, count);
        }

        for (int prime : primes) {
            boolean keepGoing = true;
            while (keepGoing) {
                boolean primeUsed = false;

                for (int key : factorCounts.keySet()) {
                    if (factorCounts.get(key).contains(prime)) {
                        factorCounts.get(key).remove((Object) prime);
                        primeUsed = true;
                    }
                }
                if (primeUsed) {
                    out *= prime;
                }
                keepGoing = primeUsed;
            }
        }

        return out;
    }

    // Problem 6

    public static int sumSquareDiff(int max) {
        int sumOfSquares = 0;

        for (int i = 1; i <= max; i++) {
            sumOfSquares += (i * i);
        }

        int squareOfSums = 0;

        for (int i = 1; i <= max; i++) {
            squareOfSums += i;
        }

        squareOfSums *= squareOfSums;

        return squareOfSums - sumOfSquares;
    }
}

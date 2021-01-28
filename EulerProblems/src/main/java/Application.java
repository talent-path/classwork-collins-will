import java.util.*;

public class Application {

    public static void main(String[] args) {
        System.out.println(sumMultiplesOf3And5(1000));

        System.out.println(sumEvenFibonacciNumbers(4000000));

        System.out.println(largestPrimeFactor(600851475143L));

        System.out.println(largestPalindromeProduct());

        System.out.println(smallestMultiple(20));

        System.out.println(sumSquareDiff(100));

        System.out.println(nthPrime(10001));

        String digitString = "73167176531330624919225119674426574742355349194934" +
                "96983520312774506326239578318016984801869478851843" +
                "85861560789112949495459501737958331952853208805511" +
                "12540698747158523863050715693290963295227443043557" +
                "66896648950445244523161731856403098711121722383113" +
                "62229893423380308135336276614282806444486645238749" +
                "30358907296290491560440772390713810515859307960866" +
                "70172427121883998797908792274921901699720888093776" +
                "65727333001053367881220235421809751254540594752243" +
                "52584907711670556013604839586446706324415722155397" +
                "53697817977846174064955149290862569321978468622482" +
                "83972241375657056057490261407972968652414535100474" +
                "82166370484403199890008895243450658541227588666881" +
                "16427171479924442928230863465674813919123162824586" +
                "17866458359124566529476545682848912883142607690042" +
                "24219022671055626321111109370544217506941658960408" +
                "07198403850962455444362981230987879927244284909188" +
                "84580156166097919133875499200524063689912560717606" +
                "05886116467109405077541002256983155200055935729725" +
                "71636269561882670428252483600823257530420752963450";
        List<Integer> digitList = new ArrayList<>();

        for (int i = 0; i < digitString.length(); i++) {
            digitList.add((int) digitString.charAt(i) - 48);
        }
        System.out.println(largestSeriesProduct(digitList));

        System.out.println(pythagoreanTriplet(1000));

        System.out.println(primeSum(2000000));
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

    // Problem 7

    public static boolean isPrime(int n) {

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int nthPrime(int n) {
        if (n == 1) {
            return 2;
        }

        int curr = 3;
        int count = 2;

        while (count < n) {
            curr += 2;
            if (isPrime(curr)) {
                count++;
            }
        }

        return curr;
    }

    // Problem 8

    public static long largestSeriesProduct(List<Integer> list) {

        long out = 1;

        for (int i = 0; i < list.size() - 13; i++) {
            List<Integer> currentSubList = list.subList(i, i+13);

            if (currentSubList.contains(0)) {
                i += currentSubList.indexOf(0);
            } else {
                long prod = 1;
                for (int num : currentSubList) {
                    prod *= num;
                }
                if (prod > out) {
                    out = prod;
                }
            }
        }

        return out;
    }

    public static long pythagoreanTriplet(int maxSum) {
        for (int a = 1; a < maxSum; a++) {
            for (int b = a; b < maxSum - a; b++) {
                int c = maxSum - a - b;
                if (a * a + b * b == c * c) {
                    return a * b * c;
                }
            }
        }

        return 0;
    }

    // Problem 10

    public static long primeSum(int maxPrime) {
        if (maxPrime < 2) {
            return 0;
        }

        if (maxPrime == 2) {
            return 2;
        }

        long out = 2;

        for (int i = 3; i < maxPrime; i+=2) {
            if (isPrime(i)) {
                out+=i;
            }
        }

        return out;
    }
}

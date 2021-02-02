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

        int[][] array = {{8,2,22,97,38,15,0,40,0,75,4,5,7,78,52,12,50,77,91,8},
                {49,49,99,40,17,81,18,57,60,87,17,40,98,43,69,48,4,56,62,0},
                {81,49,31,73,55,79,14,29,93,71,40,67,53,88,30,3,49,13,36,65},
                {52,70,95,23,4,60,11,42,69,24,68,56,1,32,56,71,37,2,36,91},
                {22,31,16,71,51,67,63,89,41,92,36,54,22,40,40,28,66,33,13,80},
                {24,47,32,60,99,3,45,2,44,75,33,53,78,36,84,20,35,17,12,50},
                {32,98,81,28,64,23,67,10,26,38,40,67,59,54,70,66,18,38,64,70},
                {67,26,20,68,2,62,12,20,95,63,94,39,63,8,40,91,66,49,94,21},
                {24,55,58,5,66,73,99,26,97,17,78,78,96,83,14,88,34,89,63,72},
                {21,36,23,9,75,0,76,44,20,45,35,14,0,61,33,97,34,31,33,95},
                {78,17,53,28,22,75,31,67,15,94,3,80,4,62,16,14,9,53,56,92},
                {16,39,5,42,96,35,31,47,55,58,88,24,0,17,54,24,36,29,85,57},
                {86,56,0,48,35,71,89,7,5,44,44,37,44,60,21,58,51,54,17,58},
                {19,80,81,68,5,94,47,69,28,73,92,13,86,52,17,77,4,89,55,40},
                {4,52,8,83,97,35,99,16,7,97,57,32,16,26,26,79,33,27,98,66},
                {88,36,68,87,57,62,20,72,3,46,33,67,46,55,12,32,63,93,53,69},
                {4,42,16,73,38,25,39,11,24,94,72,18,8,46,29,32,40,62,76,36},
                {20,69,36,41,72,30,23,88,34,62,99,69,82,67,59,85,74,4,36,16},
                {20,73,35,29,78,31,90,1,74,31,49,71,48,86,81,16,23,57,5,54},
                {1,70,54,71,83,51,54,69,16,92,33,48,61,43,52,1,89,19,67,48}};

        System.out.println(highestAdjacentProduct(array));

        System.out.println(firstTriangularNumWithOverNDivisor(500));
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

    // Problem 11

    public static long highestAdjacentProduct(int[][] arr) {
        long out = 0;

        // rows
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 20; j++) {
                long curr = arr[i][j] * arr[i+1][j] * arr[i+2][j] * arr[i+3][j];
                if (curr > out) {
                    out = curr;
                }
            }
        }

        // columns
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 17; j++) {
                long curr = arr[i][j] * arr[i][j+1] * arr[i][j+2] * arr[i][j+3];
                if (curr > out) {
                    out = curr;
                }
            }
        }

        // diagonals
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                long curr = arr[i][j] * arr[i+1][j+1] * arr[i+2][j+2] * arr[i+3][j+3];
                if (curr > out) {
                    out = curr;
                }
            }
        }

        for (int i = 3; i < 20; i++) {
            for (int j = 0; j < 17; j++) {
                long curr = arr[i][j] * arr[i-1][j+1] * arr[i-2][j+2] * arr[i-3][j+3];
                if (curr > out) {
                    out = curr;
                }
            }
        }

        return out;
    }

    // Problem 12

    public static int firstTriangularNumWithOverNDivisor(int num) {
        int current = 0;
        int count = 1;
        List<Integer> divisors = new ArrayList<>();

        while (divisors.size() <= num) {
            divisors.clear();
            current += count;

            for (int i = 1; i * i <= current; i++) {
                if (current % i == 0) {
                    divisors.add(i);
                    if (i * i != 0) {
                        divisors.add(current / i);
                    }
                }
            }

            count++;
        }

        return current;
    }
}

public class Application {

    public static void main(String[] args) {
/*        System.out.println(middleOfThree(1,2,3));
        System.out.println(middleOfThree(1,3,2));
        System.out.println(middleOfThree(2,1,3));
        System.out.println(middleOfThree(2,3,1));
        System.out.println(middleOfThree(3,1,2));
        System.out.println(middleOfThree(3,2,1));

        fizzBuzz();*/

        System.out.println(canBalance(new int[]{1,2,3,4,4,3,2,1}));
        System.out.println(canBalance(new int[]{2,5,8,5}));
        System.out.println(canBalance(new int[]{2,8,1,5,5}));

        System.out.println();

        System.out.println(noTriples(new int[]{5,5,5}));
        System.out.println(noTriples(new int[]{1,2,2,3}));
        System.out.println(noTriples(new int[]{5,2,5,4,4,3}));
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
}

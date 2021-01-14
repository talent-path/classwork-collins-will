public class Application {

    public static void main(String[] args) {
        System.out.println(middleOfThree(1,2,3));
        System.out.println(middleOfThree(1,3,2));
        System.out.println(middleOfThree(2,1,3));
        System.out.println(middleOfThree(2,3,1));
        System.out.println(middleOfThree(3,1,2));
        System.out.println(middleOfThree(3,2,1));

        fizzBuzz();

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
}

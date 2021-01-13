import java.util.Random;

public class Application {

    public static void main(String[] args) {
        int num = Console.readInt("Please enter a number: ", 0, 10);
        System.out.println(num);

        double dub = Console.readDouble("Please enter another number (decimals okay) : ", 0, 10);
        System.out.println(dub);
    }
}

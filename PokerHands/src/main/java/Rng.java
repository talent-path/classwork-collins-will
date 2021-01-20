import java.util.Random;

public class Rng {

    static Random rng = new Random();

    public static int nextInt( int incMin, int incMax ){

        return incMin + rng.nextInt(incMax - incMin + 1);

    }


}

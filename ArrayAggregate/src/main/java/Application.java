public class Application {
    public static void main(String[] args) {
        int[] testArray = {82, 95, 71, 6, 34};

        System.out.println(Aggregate.min(testArray));
        System.out.println(Aggregate.max(testArray));
        System.out.println(Aggregate.sum(testArray));
        System.out.println(Aggregate.average(testArray));
        System.out.println(Aggregate.stdDev(testArray));
    }
}

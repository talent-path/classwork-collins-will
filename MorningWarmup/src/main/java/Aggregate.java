public class Aggregate {

    public static int min(int[] arr) {
        int out = Integer.MAX_VALUE;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < out) {
                out = arr[i];
            }
        }

        return out;
    }

    public static int max(int[] arr) {
        int out = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > out) {
                out = arr[i];
            }
        }

        return out;
    }

    public static int sum(int[] arr) {
        int out = 0;

        for (int i = 0; i < arr.length; i++) {
            out += arr[i];
        }

        return out;
    }

    public static double average(int[] arr) {
        double length = arr.length;
        return sum(arr) / length;
    }

    public static double stdDev(int[] arr) {
        double average = average(arr);
        double diffSum = 0;

        for (int i = 0; i < arr.length; i++) {
            diffSum += Math.pow((arr[i] - average), 2);
        }

        double diffMean = diffSum / arr.length;
        return Math.sqrt(diffMean);
    }
}

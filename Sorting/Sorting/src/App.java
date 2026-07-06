import java.util.Arrays;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        Integer x[] = new Random().ints(10000).boxed().toArray(Integer[]::new);
        Integer test1[] = x.clone();
        int test2[] = Arrays.stream(x).mapToInt(Integer::intValue).toArray();
        IntroSort s = new IntroSort();

        double time2 = System.nanoTime();
        QuickSort.ascquicksort(test2);
        System.out.println((System.nanoTime() - time2) / 1_000_000);

        double time = System.nanoTime();
        s.sort(test2);
        System.out.println((System.nanoTime() - time) / 1_000_000);

    }
}

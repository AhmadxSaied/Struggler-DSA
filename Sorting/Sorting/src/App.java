import java.util.Arrays;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        Integer x[] = new Random().ints(10000).sorted().boxed().toArray(Integer[]::new);
        Integer test1[] = x.clone();
        int test2[] = Arrays.stream(x).mapToInt(Integer::intValue).toArray();
        TimSort<Integer> s = new TimSort<>();

        double time2 = System.nanoTime();
        QuickSort.ascquicksort(test2);
        System.out.println((System.nanoTime() - time2) / 1_000_000);

        double time = System.nanoTime();
        s.sort(test1);
        System.out.println((System.nanoTime() - time) / 1_000_000);

    }
}

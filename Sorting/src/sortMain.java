import java.util.Random;

public class sortMain {
    public static void main(String[] args) throws Exception {
        int x[] = new Random().ints(10,0,10000).toArray();
        x = radixSort.sort(x);   
      }
}

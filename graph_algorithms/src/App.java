
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<List<Integer>> G = new ArrayList<>();
        G.add(new ArrayList<>(Arrays.asList(1)));
        G.add(new ArrayList<>(Arrays.asList()));
        System.out.println(GraphAlgorithms.Topological_sort_bfs(G));

        
        int graph[][] = new int[][] { { 0, 2, 0, 6, 0 },
                                      { 2, 0, 3, 8, 5 },
                                      { 0, 3, 0, 0, 7 },
                                      { 6, 8, 0, 0, 9 },
                                      { 0, 5, 7, 9, 0 } };
        GraphAlgorithms.MST_PRIM(graph);
    }
}

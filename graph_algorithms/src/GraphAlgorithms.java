
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class GraphAlgorithms {
    
    public static void dfs(List<List<Integer>> G){
        if(G.isEmpty()) return;
        boolean[] visited = new boolean[G.size()];
        Arrays.fill(visited, false);
        for(int i=0;i<G.size();i++){
            if(!visited[i]){
                dfs(i,G,visited);
                System.out.println();
            }
        }
    }

    private static void dfs(Integer currentNode,List<List<Integer>> G,boolean [] visited){
        if(visited[currentNode]) return;
        visited[currentNode] = true;
        System.out.print(currentNode);
        for(int adjecent_node : G.get(currentNode)){
            if(!visited[adjecent_node]){
                System.out.print(" --> ");
                dfs(adjecent_node,G,visited);
            }
        }
    }
    // for all connected_components
    public static List<List<Integer>> bfs(List<List<Integer>> G){
        List<List<Integer>> res = new ArrayList<>();
        if(G.isEmpty()) return res;
        boolean[] visited = new boolean[G.size()];
        Queue<Integer> q = new LinkedList<>();
        
        List<Integer> connected_component = new ArrayList<>();
        for(int i=0;i<G.size();i++){

            connected_component.clear();

            if(!visited[i]){
                q.add(i);
                visited[i] = true;
            }
            while(!q.isEmpty()){
                Integer node = q.poll();
                connected_component.add(node);
                
                
                for(Integer adjecent_node : G.get(node)){
                    if(!visited[adjecent_node]){
                        visited[adjecent_node] = true;
                        q.add(adjecent_node);
                    }
                }
            }
            if(!connected_component.isEmpty())
                res.add(new ArrayList<>(connected_component));
        }
        return res;
}

    public static boolean Topological_sort(List<List<Integer>> G){
        int[] Indegree = new int[G.size()];
        // this is to determine which vertices has indegree of 0 to start with them
        for(int i=0; i<G.size();i++){
            for(int adjacent_vertix : G.get(i)){
                Indegree[adjacent_vertix]++ ;
            }
        }
        List<Integer> Zero_indegree_vetrices = new ArrayList<>();
        for (int i = 0; i < G.size(); i++) {
            if(Indegree[i] == 0) Zero_indegree_vetrices.add(i);
        }

        int[] visited = new int[G.size()];
        Arrays.fill(visited, -1);
        // if false is returned in the if this means we have a cycle & G must be Directed acyclic graph DAG
        for(int i =0 ; i<Zero_indegree_vetrices.size(); i++){
            int node = Zero_indegree_vetrices.get(i);
            if(!Topological_sort_dfs(node,G,visited,Indegree,Zero_indegree_vetrices)) return false;
                
        }
        int count = 0;
        for(int d : Indegree){
            if(d==0) count++;
        }
        return count == G.size();
    }
    private static boolean Topological_sort_dfs(Integer current_node,List<List<Integer>> G,int[] visited,
        int[] Indegree,List<Integer> Zero_Indegree_vertices){
        if(visited[current_node] == 0) return false; // This indicates there is a cycle
        if(visited[current_node] == 1) return true;
        
        visited[current_node] = 0; // currently visiting

        for(int i : G.get(current_node)){  
                Indegree[i]--;
                if(Indegree[i] == 0) {
                    Zero_Indegree_vertices.add(i);
                }
                if(!Topological_sort_dfs(i,G,visited,Indegree,Zero_Indegree_vertices)) return false;
            
        }
        visited[current_node] = 1;
        return true;
    }
    // Kahn's Algorithm
    public static boolean Topological_sort_bfs(List<List<Integer>> G){
        int[] Indegree = new int[G.size()];

        for(int i=0; i<G.size();i++){
            for(int adjacent_vertix : G.get(i)){
                Indegree[adjacent_vertix]++ ;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i = 0 ;i<Indegree.length ; i++){
            if(Indegree[i] == 0) q.add(i);
        }

        int count = 0;
        while(!q.isEmpty()){
            int node = q.poll();
            count ++;
            for(int adjecent_node : G.get(node)){
                Indegree[adjecent_node]--;
                if(Indegree[adjecent_node] == 0) q.add(adjecent_node);
            }
        }
        return count == G.size();
    }

    public static void MST_PRIM(int[][] weights){
        int V = weights.length;
        int[] vertices_keys = new int[V];
        boolean[] inside_queue = new boolean[V];
        int[] parent = new int[V];
        for(int i = 0;i<vertices_keys.length;i++){
            vertices_keys[i] = Integer.MAX_VALUE;
            inside_queue[i] = true;
        }

        vertices_keys[0] = 0;
        parent[0] = -1;


        PriorityQueue<Map.Entry<Integer,Integer>> q = new PriorityQueue<>(
            (a,b) -> a.getValue() - b.getValue()
        );
        for(int i=0;i< vertices_keys.length;i++){
            q.add(Map.entry(i, vertices_keys[i]));
        }
        int count = 0;
        while(!q.isEmpty() && count < V-1){
            count++;
            Map.Entry<Integer,Integer> min_entry = q.poll();
            int min_node = min_entry.getKey();
            inside_queue[min_node] = false;

            for(int j =0;j < V;j++){
                if(inside_queue[j] && weights[min_node][j] !=0 && vertices_keys[j] > weights[min_node][j]){
                    q.remove(Map.entry(j, vertices_keys[j]));
                    vertices_keys[j] = weights[min_node][j];
                    q.add(Map.entry(j, vertices_keys[j]));
                    parent[j] = min_node;
                }
            }
        }
        printMST(parent, weights);
    }
    private static void printMST(int[] parent, int[][] weights){

        for(int i=1;i< parent.length ; i++){
            System.out.println("Edge \tWeight");
            System.out.println(parent[i] + "-" + i +"\t"+weights[parent[i]][i]);
        }
    }
    
}

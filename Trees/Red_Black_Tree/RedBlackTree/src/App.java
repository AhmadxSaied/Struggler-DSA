
import implementation.RedBlackTree;

public class App {
    public static void main(String[] args) throws Exception {
        Integer [] nums = new Integer[] {10,5,20,4,2,6,7};
        RedBlackTree<Integer,Integer> rbt = new RedBlackTree<>();
        for(Integer i : nums){
            rbt.insert(i, i);
            System.out.println(rbt.inorderTraversal());
        }
        System.out.println(rbt);
    }
}

package implementation;

import java.util.ArrayList;
import java.util.List;

public class BTree<K extends Comparable<? super K>,V> {
    private int size;
    private int minimum_degree;
    private BTreeNode root;
    private class BTreeNode {
        int keys_count;
        boolean leaf = true;
        List<K> Keys;
        List<BTreeNode> children;
        BTreeNode parent;
        public BTreeNode(){
            this.Keys = new ArrayList<>(minimum_degree*2 -1);
            this.children = new ArrayList<>(minimum_degree * 2);
        }
    }
    public BTree(){
        BTreeNode node = new BTreeNode();
        node.leaf = true;
        node.keys_count = 0;
        node.parent = null;
        this.root = node;
    }

    public Integer search(K key){
        return search(root,key);
    }
    private Integer search(BTreeNode node,K key){
        int i=0;
        int count = node.keys_count;
        // we search lineary in the node until we encounter
        // a key greater than that we are searching for
        // if it is what we are searching for we return its index
        // else we check if we are leaf then such key doesnt exist
        // else we check in the ith child 
        // ith child is the subtree containing keys smaller than
        // that made us take the branching desicion
        while(i < count && key.compareTo(node.Keys.get(i)) > 1){
            i++;
        }
        if(i < count && key.compareTo(node.Keys.get(i))==0)
            return i;
        else if(node.leaf) return null;

        else 
            return search(node.children.get(i),key);
    }

    private void split(BTreeNode node , int ithChild){
        BTreeNode new_node = new BTreeNode(); // the new node created that will hold the right half of the 
        // node to be splitted
        BTreeNode temp = node.children.get(ithChild); // the one i want to split
        new_node.keys_count = minimum_degree-1;

        // we take the right part of the child to be splitted and insert it in the new node
                        //target
                        //   |
                        //   v
        // y=  | 1 | 2 | 3 | 4 | 5 | 6 | 7 |  t = 4
                        // z=  |   |   |   |        
        //       0   1   2   3   4   5   6   
        // as you can see we want to put 5 , 6 and 7 it the z keys 
        // so we can say z[i] = y[i + t] i = 0 , 1 , 2 : i < t-1
        for(int i=0;i < (minimum_degree-1);i++){
            new_node.Keys.add(i,temp.Keys.get(i + minimum_degree));
            temp.Keys.remove(i+minimum_degree);
        }
        // if the child to be splitted is a leaf there is no meaning to copying children as there are no children
        if(!temp.leaf){
            // y=  | 1 | 2 | 3 | 4 |   |   |   |  t = 4
            //    c0  c1  c2  c3  c4  c5  c6   c7
                            // z=  | 5 | 6 | 7 |        
            //       0   1   2   3   4   5   6   
            // here we adjust the children and put them at z
            // we can say z.c[i] = y.c[i + t] i = 0 , 1 , 2 ,3 : i < t
            for(int i=0;i<minimum_degree;i++){
                new_node.children.add(i,temp.children.get((i+minimum_degree)));
                temp.children.remove(i+minimum_degree);
            }
        }
        temp.keys_count = minimum_degree -1;
        //    c0   c1  y  c3  c4 
        // x=  | a | x | f | g |   |   |   |
        //                 i 
        // y=  | 1 | 2 | 3 | 4 |   |   |   |  t = 4      
        //       0   1   2   3   4   5   6   
            //     c0 c1   y      c3  c4 
//result    // x=  | a | x | f | g |   |   |
        
        // we need to make space for the new child that will take place
        for(int i = node.keys_count ; i >=ithChild+1;i--){
            node.children.add(i+1, node.children.get(i));
            node.children.remove(i);
        }
 
            //       0   1   2   3   4   5   6   
            //     c0 c1  y    z   c3  c4 
//result    // x=  | a | x |   | f | g |   |   |


        node.children.add(ithChild+1, new_node);
        for(int i = node.keys_count -1 ; i >=ithChild;i--){
            node.Keys.add(i+1, node.Keys.get(i));
            node.Keys.remove(i);
        }
        //     0   1   2   3   4   5   6   
        //     c0 c1  y   z   c3  c4 
//result    // x=  | a | x | 4 | f | g |   |   |
    // y=  | 1 | 2 | 3 |   | 5 | 6 | 7 | = z
        node.Keys.add(ithChild, temp.Keys.get(minimum_degree-1));
        node.Keys.remove(minimum_degree -1);
        node.keys_count++;
    }


    public void insert(K key){
        BTreeNode temproot = this.root;
        if(temproot.keys_count == 2 * minimum_degree - 1){
            BTreeNode noderoot = new BTreeNode();
            this.root = noderoot;
            noderoot.leaf = false;
            noderoot.keys_count = 0;

            noderoot.children.add(0, temproot);
            split(noderoot, 0);
            insert_not_full(noderoot,key);
        }
        else insert_not_full(temproot,key);
    }   

    private void insert_not_full(BTreeNode node,K key){
        int i = node.keys_count;
        if(node.leaf){
            while(i >= 0 && key.compareTo(node.Keys.get(i))<0){
                node.Keys.add(i+1, node.Keys.get(i));
                i --;
            }
            node.Keys.add(i, key);
            node.keys_count++;
        }else{
            while(i >= 0 && key.compareTo(node.Keys.get(i))<0){
                i--;
            }
            i++;
            if(node.children.get(i).keys_count == 2 * minimum_degree -1){
                split(node, i);
                if(key.compareTo(node.Keys.get(i)) > 0)
                    i++;
            }
            insert_not_full(node.children.get(i), key);
        }
    }

    private K predecessor(BTreeNode node,int index){
        if(index == 0 || node.leaf){
            return null;
        }
        BTreeNode searchchild = node.children.get(index-1);
        K foundkey = searchchild.Keys.get(searchchild.keys_count-1);
        if(searchchild.leaf == true){
            searchchild.Keys.remove(searchchild.keys_count-1);
            searchchild.keys_count--;
            return foundkey;
        }
        while(!searchchild.leaf){
            searchchild = searchchild.children.get(searchchild.keys_count);
        }
        foundkey = searchchild.Keys.get(searchchild.keys_count-1);
        searchchild.Keys.remove(searchchild.keys_count-1);
        return foundkey;   
    }

    private K succecessor(BTreeNode node,int index){
        if(index == 0 || node.leaf){
            return null;
        }
        BTreeNode searchchild = node.children.get(index+1);
        K foundkey = searchchild.Keys.get(0);
        if(searchchild.leaf == true){
            searchchild.Keys.remove(0);
            searchchild.keys_count--;
            return foundkey;
        }
        while(!searchchild.leaf){
            searchchild = searchchild.children.get(0);
        }
        foundkey = searchchild.Keys.get(0);
        return foundkey;   
    }

    public void delete(K key){

    }

    private void delete(BTreeNode node ,K key){
        int i = node.keys_count;
        if(node.leaf){
            int j=0;
            while(j < i && key.compareTo(node.Keys.get(j))!=0){
                j++;
            }
            if(key.compareTo(node.Keys.get(j))==0){
                node.Keys.remove(j);
                while(j < i){
                    node.Keys.add(j,node.Keys.get(j+1));
                    j++;
                }
            }
        }
        int j = 0;
        while(j < i && key.compareTo(node.Keys.get(i)) > 0){
            j++;
        }
        if(key.compareTo(node.Keys.get(j)) == 0) {// we found it
                //case a
                if(j !=0 && node.children.get(j-1).keys_count >= minimum_degree){
                    node.Keys.add(i, predecessor(node, i));
                }
                else if(j != 2 * minimum_degree -1 && node.children.get(j+1).keys_count >= minimum_degree){
                    node.Keys.add(j,succecessor(node, j));
                    return;
                }
                else{
                    merge(node.children.get(j-1), node.children.get(j+1), node.Keys.get(j));
                    node.Keys.remove(i);
                    while(j < node.keys_count-1){
                        node.Keys.add(i, node.Keys.get(i+1));
                    }
                    delete(node.children.get(i), key);
                    return;
                }
            }
        if(node.children.get(j).keys_count >= minimum_degree){
            delete(node.children.get(j), key);
            
        }else{
            if(node.children.get(i+1) != null){
                
            }
        }
    
}
    private void merge(BTreeNode nodeleft,BTreeNode noderight,K median){
        nodeleft.Keys.add(minimum_degree-1, median);
        for(int i = minimum_degree;i < (2 *minimum_degree -1);i++){
            nodeleft.Keys.add(i, noderight.Keys.get(i-minimum_degree));
        }
        for(int i = minimum_degree -1;i <= (2 *minimum_degree -1);i++){
            nodeleft.children.add(i, noderight.children.get(i-minimum_degree));
        }
        nodeleft.keys_count += noderight.keys_count+1;
    }
}

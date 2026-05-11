package implementation;

import java.util.ArrayList;
import java.util.List;

public class BTree<K extends Comparable<? super K>,V> {
    private int size;
    private final  int minimum_degree;
    private BTreeNode root;
    private class BTreeNode {
        int keys_count;
        boolean leaf = true;
        List<K> Keys;
        List<BTreeNode> children;
        public BTreeNode(){
            this.Keys = new ArrayList<>(minimum_degree*2 -1);
            this.children = new ArrayList<>(minimum_degree * 2);
        }
    }
    public BTree(int minimum_degree){
        this.minimum_degree = minimum_degree;
        BTreeNode node = new BTreeNode();
        node.leaf = true;
        node.keys_count = 0;
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
        while(i < count && key.compareTo(node.Keys.get(i)) > 0){
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
        new_node.leaf = temp.leaf;
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
            new_node.Keys.add(temp.Keys.remove(minimum_degree));
            
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
                new_node.children.add(temp.children.remove(minimum_degree));
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
        node.children.add(null);
        for(int i = node.keys_count ; i >=ithChild+1;i--){
            node.children.set(i+1, node.children.get(i));
        }
 
            //       0   1   2   3   4   5   6   
            //     c0 c1  y    z   c3  c4 
//result    // x=  | a | x |   | f | g |   |   |


        node.children.set(ithChild+1, new_node);

        node.Keys.add(null);
        for(int i = node.keys_count -1 ; i >=ithChild;i--){
            node.Keys.set(i+1, node.Keys.get(i));
        }
        //     0   1   2   3   4   5   6   
        //     c0 c1  y   z   c3  c4 
//result    // x=  | a | x | 4 | f | g |   |   |
    // y=  | 1 | 2 | 3 |   | 5 | 6 | 7 | = z
        node.Keys.set(ithChild, temp.Keys.remove(minimum_degree-1));
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
        int i = node.keys_count-1;
        if(node.leaf){
            while(i >= 0 && key.compareTo(node.Keys.get(i))<0){
                i --;
            }
            this.size++;
            node.Keys.add(i+1, key);
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
        BTreeNode searchchild = node.children.get(index);

        while(!searchchild.leaf){
            searchchild = searchchild.children.get(searchchild.keys_count);
        }

        return searchchild.Keys.get(searchchild.keys_count-1);   
    }

    private K succecessor(BTreeNode node,int index){

        BTreeNode searchchild = node.children.get(index+1);

        while(!searchchild.leaf){
            searchchild = searchchild.children.get(0);
        }
        return searchchild.Keys.get(0); 
    }

    public void delete(K key){
        delete(root,key);
        if(this.root.keys_count == 0 && !root.leaf){
            root = root.children.get(0);
        }
    }

    private void delete(BTreeNode node ,K key){
        if(node.leaf){
            int j=0;
            while(j < node.keys_count && key.compareTo(node.Keys.get(j))!=0){
                j++;
            }
            if(j < node.keys_count && key.compareTo(node.Keys.get(j))==0){
                node.Keys.remove(j);
                node.keys_count--;
            }
            this.size--;
            return;
        }
        int j = 0;
        while(j < node.keys_count && key.compareTo(node.Keys.get(j)) > 0){
            j++;
        }
        if(j< node.keys_count && key.compareTo(node.Keys.get(j)) == 0) {// we found it
                BTreeNode leftchild = node.children.get(j);
                BTreeNode rightchild = node.children.get(j+1);
                //case 2a
                if(leftchild.keys_count >= minimum_degree){
                    K pred = predecessor(node, j);
                    node.Keys.set(j,pred);
                    delete(leftchild,pred);
                }
                // case 2b
                else if(rightchild.keys_count >= minimum_degree){
                    K succ = succecessor(node, j);
                    node.Keys.set(j,succ);
                    delete(rightchild,succ);
                }
                else{
                    // case 2c
                    merge(node, j);
                    delete(leftchild, key);
                    
                }
                return;
            }
        BTreeNode child = node.children.get(j);
        if(child.keys_count < minimum_degree){
            // case:3a  borrow from left sibling
            if(j>0 && node.children.get(j-1).keys_count >= minimum_degree){
                borrowFromPrev(node,j);
            }
            else if( j < node.keys_count && node.children.get(j+1).keys_count >= minimum_degree){
                borrowFromNext(node,j);
            }else{
                if(j < node.keys_count){
                    // merge right
                    merge(node, j);
                }else{
                    merge(node, j-1);
                    // child was merged
                    child = node.children.get(j-1);
                }
            }
        }
        delete(child,key);
   
}
    private void merge(BTreeNode parent,int index){
        BTreeNode leftchild = parent.children.get(index);
        BTreeNode rightchild = parent.children.get(index+1);
        K median = parent.Keys.remove(index);

        leftchild.Keys.add(median);

        for(int i = 0;i < rightchild.keys_count ;i++){
            leftchild.Keys.add(rightchild.Keys.get(i));
        }
        if(!leftchild.leaf){
        for(int i = 0;i <= rightchild.keys_count ;i++){
            leftchild.children.add(rightchild.children.get(i));
        }
    }
        parent.children.remove(index+1);    

        leftchild.keys_count += rightchild.keys_count+1;

        parent.keys_count--;
    }

    private void borrowFromNext(BTreeNode node,int index){
        BTreeNode rightchild = node.children.get(index+1);
        BTreeNode leftchild = node.children.get(index);

        leftchild.Keys.add(node.Keys.get(index));
        
        if(!leftchild.leaf){
            leftchild.children.add(rightchild.children.remove(0));
        }

        node.Keys.set(index,rightchild.Keys.remove(0));

        leftchild.keys_count++;
        rightchild.keys_count--;
    }

    private void borrowFromPrev(BTreeNode node,int index){
        BTreeNode rightchild = node.children.get(index);
        BTreeNode leftchild = node.children.get(index-1);

        rightchild.Keys.add(0,node.Keys.get(index-1));
        
        if(!rightchild.leaf){
            rightchild.children.add(0,leftchild.children.remove(leftchild.keys_count));
        }

        node.Keys.set(index-1,leftchild.Keys.remove(leftchild.keys_count-1));

        rightchild.keys_count++;
        leftchild.keys_count--;
    }
}

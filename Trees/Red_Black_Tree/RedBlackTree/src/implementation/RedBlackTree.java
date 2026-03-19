package implementation;

public class RedBlackTree<K extends Comparable<K>,V>{
    private int size;
    private Node<K,V> root=null;

    private class  Node<K extends Comparable<? super K>,V> implements Comparable<Node<K,V>>{

        private K key;
        private V value;
        private boolean black;
        private boolean isLeftChild;
        private Node<K,V> leftChild;
        private Node<K,V> rightChild;
        private Node<K,V> parent;

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            this.black=false;
            this.leftChild=this.rightChild=this.parent=null;
            this.isLeftChild=false;
        }

        @Override
        public int compareTo(Node<K, V> node) {
            return this.key.compareTo(node.key);
        }

    }
    public void insert(K key,V value){
        Node<K,V> new_node = new Node<>(key, value);
        if(this.root==null){
            this.root = new_node;
            this.root.black=true;
            return;
        }

        Node<K,V> node = this.root;
        while(node!=null){
            if(node.compareTo(new_node)>0){//the new node is smaller
                if(node.leftChild == null){
                    node.leftChild = new_node;
                    new_node.parent=  node;
                    break;
                }
                node = node.leftChild;
            } else{
                if(node.rightChild==null){
                    node.rightChild = new_node;
                    new_node.parent = node;
                    break;
            }
            node = node.rightChild;
        }
    }
    
        // fixTree(new_node);

}
private  void rotate(Node<K,V> node){
    if(node.isLeftChild){
        if(node.parent.isLeftChild){
            // we do right rotate
            if(node.parent.parent.rightChild==null||node.parent.parent.rightChild.black){
                rightrotate(node.parent.parent);
                //fix color
            }
                colorfix(node.parent);
            return;
        }
        if(node.parent.parent.rightChild==null||node.parent.parent.rightChild.black){
            rightleftrotate(node.parent.parent);
        }
        
        colorfix(node);
        return;
    }
    if(!node.parent.isLeftChild){
        if(node.parent.parent.leftChild==null || node.parent.parent.leftChild.black){
            leftrotate(node.parent.parent);
        }
        colorfix(node.parent.parent);
        return;
    }
    if(node.parent.parent.leftChild==null || node.parent.parent.leftChild.black){
        leftrightrotate(node.parent.parent);
    }
    colorfix(node.parent.parent);
}   

private void rightrotate(Node<K,V> node){
    // node is the grandparent
    Node<K,V> temp = node.leftChild; // parent
    
    // we want the child right subtree to be the parent left subtree
    node.leftChild = temp.rightChild;

    if(node.leftChild!=null){
        // we were a right child now we are left
        node.leftChild.isLeftChild=true;
        node.leftChild.parent = node;
    }

    // if the grandparent is the root then the temp is the new root
    if(node.parent==null){
        this.root = temp;
        temp.parent = null;
    }else{
    temp.parent = node.parent;
    if(node.isLeftChild){
        node.parent.leftChild = temp;
        temp.isLeftChild = true;
    }else{
        node.parent.rightChild = temp;
        temp.isLeftChild = false;
    }
}
    node.parent = temp;
    temp.rightChild = node;
    node.isLeftChild = false;
}
private void leftrotate(Node<K,V> node){

    //node is the grandparent

    Node<K,V> temp = node.rightChild; // parent node of recently inserted


    node.rightChild = temp.leftChild; // child left subtree is the parent new right subtree

    if(node.rightChild!=null){ // if there is a leftsubtree existing
        node.rightChild.parent = node;
        node.rightChild.isLeftChild=false;
    }

    if(node.parent == null){ //we are at the root
        this.root = temp;
        temp.parent = null;
    }else{
        if(node.isLeftChild){
            node.parent.leftChild =temp;
            temp.isLeftChild=true;
        }else{
            node.parent.rightChild = temp;
            temp.isLeftChild = false;
        }
        temp.parent = node.parent;
    }


    node.parent = temp;
    temp.leftChild = node;
    node.isLeftChild= true;
    
}
private void rightleftrotate(Node<K,V> node){
    rightrotate(node.parent);
    leftrotate(node);
}
private void leftrightrotate(Node<K,V> node){
    leftrotate(node.parent);
    rightrotate(node);
}
private void colorfix(Node<K,V> node){
    node.black = false;
    if(node.leftChild!=null){
        node.leftChild.black=true;
    }
    if(node.rightChild!=null){
        node.rightChild.black=true;
    }
}
}
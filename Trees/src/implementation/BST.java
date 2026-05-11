package implementation;
public class BST<K extends Comparable<? super K>,V> {
    int size;
    private Node root;
    private class Node implements Comparable<Node>{
        K key;
        V value;
        Node parent;
        Node leftChild;
        Node rightChild;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.leftChild =null;
            this.rightChild =null;
            this.parent = null;
        }
        @Override
        public int compareTo(Node node) {
             return this.key.compareTo(node.key);
        }
    }

public void insert(K key, V value){
    Node newNode = new Node(key, value);

    Node x = this.root;

    if( x == null){
        this.root = newNode;
        size++;
        return;
    }

    while(x != null){

        if(x.compareTo(newNode)>0){
            if (x.leftChild !=null)
                x = x.leftChild;
            else{
                newNode.parent = x;
                x.leftChild = newNode;
                size++;
                return;
            }

        }
        else if(x.compareTo(newNode)<0){
            if(x.rightChild != null)
                x = x.rightChild;
            else{
                newNode.parent = x;
                x.rightChild = newNode;
                size++;
                return;
            }
        }else{
            x.value = newNode.value;
            return;
        }
    }
}
public V delete(K key){
    Node deleted = search_node(key);
    Node pluckNode;

    if( deleted==null ) return null;

    if(deleted.leftChild == null){
        transplant(deleted, deleted.rightChild); 
    }
    else if(deleted.rightChild == null){
        transplant(deleted, deleted.leftChild);
    }else{
        pluckNode = getMin( deleted.rightChild );

        if(pluckNode.parent != deleted){
            transplant(pluckNode,pluckNode.rightChild);
            
            pluckNode.rightChild = deleted.rightChild;

            if(pluckNode.rightChild != null)
                pluckNode.rightChild.parent = pluckNode;
        }
        transplant(deleted, pluckNode);
        pluckNode.leftChild = deleted.leftChild;
        
        if(pluckNode.leftChild != null)
            pluckNode.leftChild.parent = pluckNode;
    }
    size--;
    return  deleted.value;
}
private void transplant(Node node, Node replaced){
    if(node.parent == null){
        this.root = replaced;
        return;
    }
    else if(node == node.parent.leftChild){
        
        node.parent.leftChild = replaced;
        return;
    }
    else{
        node.parent.rightChild = replaced;
    }
    if(replaced != null){
        replaced.parent = node.parent;
    }
}
private  Node successor(Node node){
    Node x = node;

    if(x.rightChild != null) {return getMin(x.rightChild);}

    Node y = x.parent;

    while(y != null && x == y.rightChild) {
        x = y;
        y = y.parent;
    }
    return y;
}
private  Node predecessor(Node node){
    Node x = node;

    if(x.leftChild != null) return getMax(x.leftChild);

    Node y = x.parent;

    while(y != null && x == y.leftChild){
        x = y;
        y = y.parent;
    }
    return y;
}
private Node search_node(K key){
    Node x = this.root;

    if(x==null) return null;

    while(x!=null){
        if(x.key.compareTo(key)==0){ return x;}
        else if(x.key.compareTo(key) > 0) {x = x.leftChild;}
        else x = x.rightChild;
        }
        return null;
}
public V search(K key){
    Node x = this.root;
    if(x == null){
        return null;
    }
    while(x!=null){
    if(x.key.compareTo(key)==0){ return x.value;}
    else if(x.key.compareTo(key) > 0) {x = x.leftChild;}
    else x = x.rightChild;
    }
    return null;
} 
private Node getMin(Node node){
    Node x = node;
    if(x == null) return x;
    while(x.leftChild !=null) x = x.leftChild;
    return x;
}
private Node getMax(Node node){
    Node x = node;
    if(x == null) return x;
    while(x.rightChild !=null) x = x.rightChild;
    return x;
}
}

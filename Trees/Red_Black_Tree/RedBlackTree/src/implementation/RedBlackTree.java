package implementation;

public class RedBlackTree<K extends Comparable<K>, V> {
    private int size;
    private  Node root;
    private  final  Node NIL;

    public RedBlackTree() {
        NIL = new Node(null,null);
        NIL.black = true;
        NIL.leftChild = NIL.rightChild = NIL.parent =NIL;
        root = NIL;
        size=0;
    }

    private  class Node implements Comparable<Node> {

        private K key;
        private V value;
        private boolean black;
        private  Node leftChild;
        private  Node rightChild;
        private  Node parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.black = false;
            this.leftChild = NIL;
            this.rightChild = NIL;
            this.parent = NIL;
        }

        @Override
        public int compareTo(Node node) {
            return this.key.compareTo(node.key);
        }

        @Override
        public String toString() {
        return key + (black ? "[B]" : "[R]");
        }   
    }

    public void insert(K key, V value) {
        Node new_node = new Node(key, value);
        if (this.root == NIL) {
            this.root = new_node;
            this.root.black = true;
            size++;
            return;
        }

        Node node = this.root;
        while (node != NIL) {
            if (node.compareTo(new_node) > 0) {// the new node is smaller
                if (node.leftChild == NIL) {
                    node.leftChild = new_node;
                    new_node.parent = node;
                    break;
                }
                node = node.leftChild;
            } else if (node.compareTo(new_node) < 0) {
                if (node.rightChild == NIL) {
                    node.rightChild = new_node;
                    new_node.parent = node;
                    break;
                }
                node = node.rightChild;
            } else {
                node.value = value;
                return;
            }
        }
        size++;
        if(new_node.parent.parent ==NIL) return;
        insertionFix(new_node);
        root.black = true;
    }

    private void insertionFix(Node Issue_node) {

        Node node = Issue_node;

        while (node != root && !node.parent.black) {

                if (node.parent == node.parent.parent.leftChild) {

                    Node uncle = node.parent.parent.rightChild;

                    if (!uncle.black) {

                        node.parent.parent.black = false;
                        node.parent.black = true;
                        uncle.black = true;

                        node = node.parent.parent;
                    }

                    // we do right rotate
                    // uncle == null || uncle.blac
                    else {
                        if(node == node.parent.rightChild){
                            node = node.parent;
                            leftrotate(node);
                        }

                        node.parent.black = true;
                        node.parent.parent.black = false;
                        rightrotate(node.parent.parent);
                        break;
                    }

                } 
             else {

                    Node uncle = node.parent.parent.leftChild;

                    if ( !uncle.black) {

                        node.parent.black = true;
                        uncle.black = true;
                        node.parent.parent.black = false;

                        node = node.parent.parent;
                    }

                    // uncle == null || uncle.black

                    else {
                        if(node == node.parent.leftChild){
                            node = node.parent;
                            rightrotate(node);
                        }
                        node.parent.black = true;
                        node.parent.parent.black = false;
                        leftrotate(node.parent.parent);
                        break;
                    }

            }
        }
        root.black = true;
    }

    private void rightrotate(Node node) {
        // node is the grandparent
        Node temp = node.leftChild; // parent

        // we want the child right subtree to be the parent left subtree
        node.leftChild = temp.rightChild;

        if (node.leftChild != NIL) {
            // we were a right child now we are left

            node.leftChild.parent = node;
        }

        // if the grandparent is the root then the temp is the new root
        temp.parent = node.parent;
        if (node.parent == NIL) {
            this.root = temp;
        } else {
            
            if (node == node.parent.leftChild) {
                node.parent.leftChild = temp;

            } else {
                node.parent.rightChild = temp;

            }
        }
        node.parent = temp;
        temp.rightChild = node;

    }

    private void leftrotate(Node node) {

        // node is the grandparent

        Node temp = node.rightChild; // parent node of recently inserted

        node.rightChild = temp.leftChild; // child left subtree is the parent new right subtree

        if (node.rightChild != NIL) { // if there is a leftsubtree existing
            node.rightChild.parent = node;

        }

        temp.parent = node.parent;
        if (node.parent == NIL) { // we are at the root
            this.root = temp;
        } else {
            if (node == node.parent.leftChild) {
                node.parent.leftChild = temp;

            } else {
                node.parent.rightChild = temp;

            }
            
        }

        node.parent = temp;
        temp.leftChild = node;


    }
    public int height() {
        Node node = root;

        return node == NIL ? 0 : height(node) - 1;
    }

    private int height(Node node) {
        if (node == NIL)
            return 0;

        return Math.max(height(node.leftChild) + 1, height(node.rightChild) + 1);
    }

    public V Search(K key) {
        Node node = Search_node(key);
        return node == NIL ? null : node.value;
    }

    private Node Search_node(K key) {
        Node node = this.root;

        while (node != NIL) {
            if (node.key.compareTo(key) == 0) {
                return node;
            } else if (node.key.compareTo(key) > 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return NIL;
    }

    public boolean Delete(K key) {
        Node found_node = Search_node(key);

        if (found_node == NIL)
            return false;

        // we keep track of the node that will be switched with the deleted if node has
        // two children
        // if the node has one the plucked out will be the deleted node itself no
        // switching

        Node plucked_out = found_node;

        // we keep track of the node that we will do our fixing from
        Node fixup_point;

        // capture switching node for fixup condition original color
        boolean trouble_color = plucked_out.black;

        // 3 cases are present

        // case 1 i have no left child

        if (plucked_out.leftChild == NIL) {

            fixup_point = found_node.rightChild;

            // transplant here switches the nodes right subtree with it

            Transplant(found_node, found_node.rightChild);
            // no point in adjusting the left pointer as it is null

        } else if (plucked_out.rightChild == NIL) {

            fixup_point = found_node.leftChild;

            // transplant here switches the nodes left subtree with it

            Transplant(found_node, found_node.leftChild);

            // no point in adjusting the right pointer as it is null

        } else { // if we have 2 children

            // we get the successor of the deleted node

            plucked_out = getMin(found_node.rightChild); // we will remove the successor from the tree

            // we record the color of the successor as it is the one removed so it might
            // cause trouble
            trouble_color = plucked_out.black;

            fixup_point = plucked_out.rightChild; // we start fixing from the removed right
            // note that it is guarrented that the successor has no left

            // if plucked out is directly the right child of deleted we correct pointers

            if (plucked_out.parent == found_node) { // im right child of node to be deleted
                    fixup_point.parent = plucked_out; 
                
            } else {

                // if the successor has a right subtree we put it inplace of the plucked out
                // node
                Transplant(plucked_out, plucked_out.rightChild);

                // the plucked out node that will be switched need to have right pointer
                // adjusted before transplant
                plucked_out.rightChild = found_node.rightChild;
                plucked_out.rightChild.parent = plucked_out;

            }
            // transplant maked the plcuked node that is connected with the deleted right
            // subtree take the deleted node position
            Transplant(found_node, plucked_out);

            // we adjust pointers with left subtree
            plucked_out.leftChild = found_node.leftChild;
            plucked_out.leftChild.parent = plucked_out;

            plucked_out.black = found_node.black;

        }

        if (trouble_color){
            
            deleteFix(fixup_point);
            
        }
        size--;
        return true;

    }

    // transplat moves an entire subtree to its position after deletion
    private void Transplant(Node u, Node v) {
        if (u.parent == NIL) {
            this.root = v;
        }
        else if (u.parent.leftChild == u) {
            u.parent.leftChild = v;
        }
        else if (u.parent.rightChild == u) {

            u.parent.rightChild = v;
        }

                v.parent = u.parent;
    }

    private Node getMin(Node rootofsearch) {
        Node y = rootofsearch;
        while (y.leftChild != NIL) {
            y = y.leftChild;
        }
        return y;
    }

    private void deleteFix(Node node) {
        Node x = node;

        // we have 4 cases
        // Angry == red color && calm == black color

        // case 1
        // that i have an angry sibling
        // i will tell me father to calm him down
        // my father will then become angry and rotates by my side

        // case 2
        // that my sibling is calm and his children are calm
        // i tell my father to check on him and my sibling becomed angry
        // and the problem is now to be dealt with by father

        // case 3
        // my sibling is calm and his child that is far from me is calm
        // i tell my sibling to go check on his other child
        // the other child becomed calm
        // my sibling becomes angry at me and rotated away from me

        // case 4
        // my sibling is calm and his child that is far from me is angry
        // i tell him to calm him down so he does so and becomed calm
        // sibling switch moods with my father and my father becomes calm
        // and my parent rotates at me

        // case 1 leads to -> case 2 or 3
        // case 3 -> leads to 4

        while (x != this.root && x.black) {
            if (x == x.parent.leftChild) {

                Node my_sibling = x.parent.rightChild;

                // case 1 my sibling is angry
                if (!my_sibling.black) {

                    // sibling calms down
                    my_sibling.black = true;

                    // father gets angry and rotates at me
                    x.parent.black = false;
                    leftrotate(x.parent);

                    my_sibling = x.parent.rightChild;
                }

                // my sibling is calm and so are his children
                if (blackChildren(my_sibling)) {

                    
                        // my father gets angry so me try to resolve from him
                    my_sibling.black = false;
                    x = x.parent;

                }
                // my sibling far child is calm
                else {
                    if (my_sibling.rightChild.black) {

                    
                    // the other child gets calm
                    my_sibling.leftChild.black = true;

                    // the father gets angry and rolls away from me
                    my_sibling.black = false;
                    rightrotate(my_sibling);

                    my_sibling = x.parent.rightChild;
                }  // the far child is angry

                    // my sibling mood become like my father
                    my_sibling.black = x.parent.black;

                
                        // his son becomes calm
                    my_sibling.rightChild.black = true; 

                    // my father calm down
                    x.parent.black = true;

                    // my father rotates towards me
                    leftrotate(x.parent);
                    x = this.root;
                
            }

            } else {
                // im a right sibling

                Node my_sibling = x.parent.leftChild;

                if (!my_sibling.black) {

                    my_sibling.black = true;

                    x.parent.black = false;
                    rightrotate(x.parent);

                    my_sibling = x.parent.leftChild;

                } 
                if (blackChildren(my_sibling)) {

                    
                   my_sibling.black = false;

                    x = x.parent;

                } else{ 
                    if (my_sibling.leftChild.black) {

                    
                    my_sibling.rightChild.black = true;

                    my_sibling.black = false;
                    leftrotate(my_sibling);

                    my_sibling = x.parent.leftChild;
                } 

                    my_sibling.black = x.parent.black;
                    x.parent.black = true;
                    my_sibling.leftChild.black = true;

                    
                    rightrotate(x.parent);

                    x = this.root;
                
            }
        }
        }
        if(x!=NIL)  x.black = true;
        this.root.black =true;
    }

    private boolean blackChildren(Node node) {
        return (node.leftChild == NIL || node.leftChild.black)
                && (node.rightChild == NIL || node.rightChild.black);
    }
}
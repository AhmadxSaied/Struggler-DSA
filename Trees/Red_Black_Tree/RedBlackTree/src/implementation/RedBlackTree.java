package implementation;

public class RedBlackTree<K extends Comparable<K>, V> {
    private int size;
    private Node<K, V> root = null;

    private class Node<K extends Comparable<? super K>, V> implements Comparable<Node<K, V>> {

        private K key;
        private V value;
        private boolean black;
        private boolean isLeftChild;
        private Node<K, V> leftChild;
        private Node<K, V> rightChild;
        private Node<K, V> parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.black = false;
            this.leftChild = this.rightChild = this.parent = null;
            this.isLeftChild = false;
        }

        @Override
        public int compareTo(Node<K, V> node) {
            return this.key.compareTo(node.key);
        }

    }

    public void insert(K key, V value) {
        Node<K, V> new_node = new Node<>(key, value);
        if (this.root == null) {
            this.root = new_node;
            this.root.black = true;
            size++;
            return;
        }

        Node<K, V> node = this.root;
        while (node != null) {
            if (node.compareTo(new_node) > 0) {// the new node is smaller
                if (node.leftChild == null) {
                    node.leftChild = new_node;
                    new_node.parent = node;
                    new_node.isLeftChild = true;
                    break;
                }
                node = node.leftChild;
            } else if (node.compareTo(new_node) < 0) {
                if (node.rightChild == null) {
                    node.rightChild = new_node;
                    new_node.parent = node;
                    new_node.isLeftChild = false;
                    break;
                }
                node = node.rightChild;
            } else {
                node.value = value;
                return;
            }
        }
        size++;
        insertionFix(new_node);
        root.black = true;
    }

    private void insertionFix(Node<K, V> Issue_node) {

        Node<K, V> node = Issue_node;

        while (node != root && !node.parent.black) {


            if (node.isLeftChild) {

                if (node.parent.isLeftChild) {

                    Node<K, V> uncle = node.parent.parent.rightChild;

                    if (uncle != null && !uncle.black) {

                        node.parent.parent.black = false;
                        node.parent.black = true;
                        uncle.black = true;

                        node = node.parent.parent;
                    }

                    // we do right rotate
                    // uncle == null || uncle.blac
                    else {

                        node.parent.black = true;
                        node.parent.parent.black = false;

                        rightrotate(node.parent.parent);
                        break;
                    }

                } else {

                    Node<K, V> uncle = node.parent.parent.leftChild;

                    if (uncle != null && !uncle.black) {

                        node.parent.parent.black = false;
                        node.parent.black = true;
                        uncle.black = true;

                        node = node.parent.parent;

                    } else {

                        node.parent.black = false;
                        node.parent.parent.black = false;
                        node.black = true;
                        rightleftrotate(node);
                        break;
                    }
                }


            } else {
                if (!node.parent.isLeftChild) {

                    Node<K, V> uncle = node.parent.parent.leftChild;

                    if (uncle != null && !uncle.black) {

                        node.parent.black = true;
                        uncle.black = true;
                        node.parent.parent.black = false;

                        node = node.parent.parent;
                    }

                    // uncle == null || uncle.black

                    else {
                        node.parent.black = true;
                        node.parent.parent.black = false;
                        leftrotate(node.parent.parent);
                        break;
                    }

                } else {
                    Node<K, V> uncle = node.parent.parent.rightChild;

                    if (uncle != null && !uncle.black) {

                        node.parent.black = true;
                        uncle.black = true;
                        node.parent.parent.black = false;

                        node = node.parent.parent;
                    } else {

                        node.parent.black = false;
                        node.parent.parent.black = false;
                        node.black = true;
                        leftrightrotate(node);
                        break;
                    }
                }
            }
        }
        root.black = true;
    }

    private void rightrotate(Node<K, V> node) {
        // node is the grandparent
        Node<K, V> temp = node.leftChild; // parent

        // we want the child right subtree to be the parent left subtree
        node.leftChild = temp.rightChild;

        if (node.leftChild != null) {
            // we were a right child now we are left
            node.leftChild.isLeftChild = true;
            node.leftChild.parent = node;
        }

        // if the grandparent is the root then the temp is the new root
        if (node.parent == null) {
            this.root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (node.isLeftChild) {
                node.parent.leftChild = temp;
                temp.isLeftChild = true;
            } else {
                node.parent.rightChild = temp;
                temp.isLeftChild = false;
            }
        }
        node.parent = temp;
        temp.rightChild = node;
        node.isLeftChild = false;
    }

    private void leftrotate(Node<K, V> node) {

        // node is the grandparent

        Node<K, V> temp = node.rightChild; // parent node of recently inserted

        node.rightChild = temp.leftChild; // child left subtree is the parent new right subtree

        if (node.rightChild != null) { // if there is a leftsubtree existing
            node.rightChild.parent = node;
            node.rightChild.isLeftChild = false;
        }

        if (node.parent == null) { // we are at the root
            this.root = temp;
            temp.parent = null;
        } else {
            if (node.isLeftChild) {
                node.parent.leftChild = temp;
                temp.isLeftChild = true;
            } else {
                node.parent.rightChild = temp;
                temp.isLeftChild = false;
            }
            temp.parent = node.parent;
        }

        node.parent = temp;
        temp.leftChild = node;
        node.isLeftChild = true;

    }

    private void rightleftrotate(Node<K, V> node) {

        Node<K, V> parent = node.parent;
        Node<K, V> grand = parent.parent;

        rightrotate(parent);
        leftrotate(grand);

    }

    private void leftrightrotate(Node<K, V> node) {

        Node<K, V> parent = node.parent;
        Node<K, V> grand = parent.parent;

        leftrotate(parent);
        rightrotate(grand);

    }

    public int height() {
        Node<K, V> node = root;

        return node == null ? 0 : height(node) - 1;
    }

    private int height(Node<K, V> node) {
        if (node == null)
            return 0;

        return Math.max(height(node.leftChild) + 1, height(node.rightChild) + 1);
    }

    public V Search(K key) {
        Node<K, V> node = Search_node(key);
        return node == null ? null : node.value;
    }

    private Node<K, V> Search_node(K key) {
        Node<K, V> node = this.root;

        while (node != null) {
            if (node.key.compareTo(key) == 0) {
                return node;
            } else if (node.key.compareTo(key) > 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return null;
    }

    public boolean Delete(K key) {
        Node<K, V> found_node = Search_node(key);

        if (found_node == null)
            return false;

        // we keep track of the node that will be switched with the deleted if node has
        // two children
        // if the node has one the plucked out will be the deleted node itself no
        // switching

        Node<K, V> plucked_out = found_node;

        // we keep track of the node that we will do our fixing from
        Node<K, V> fixup_point;

        // capture switching node for fixup condition original color
        boolean trouble_color = plucked_out.black;

        // 3 cases are present

        // case 1 i have no left child

        if (plucked_out.leftChild == null) {

            fixup_point = found_node.rightChild;

            // transplant here switches the nodes right subtree with it

            Transplant(found_node, found_node.rightChild);
            // no point in adjusting the left pointer as it is null

        } else if (plucked_out.rightChild == null) {

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
                if (fixup_point != null){
                    fixup_point.parent = plucked_out; 
                }
            } else {

                // if the successor has a right subtree we put it inplace of the plucked out
                // node
                Transplant(plucked_out, plucked_out.rightChild);

                // the plucked out node that will be switched need to have right pointer
                // adjusted before transplant
                plucked_out.rightChild = found_node.rightChild;
                plucked_out.rightChild.parent = plucked_out;
                plucked_out.rightChild.isLeftChild = false;
            }
            // transplant maked the plcuked node that is connected with the deleted right
            // subtree take the deleted node position
            Transplant(found_node, plucked_out);

            // we adjust pointers with left subtree
            plucked_out.leftChild = found_node.leftChild;
            plucked_out.leftChild.parent = plucked_out;
            plucked_out.leftChild.isLeftChild = true;
            plucked_out.black = found_node.black;

        }

        if (trouble_color){
            
            deleteFix(fixup_point);
            
            this.root.black = true;
        }

        return true;

    }

    // transplat moves an entire subtree to its position after deletion
    private void Transplant(Node<K, V> u, Node<K, V> v) {
        if (u.parent == null) {
            this.root = v;
            if(v!=null) v.parent = null;
            return;
        }
        if (u.parent.leftChild == u) {
            u.parent.leftChild = v;
            if(v!=null)v.isLeftChild=true;
        }
        if (u.parent.rightChild == u) {

            u.parent.rightChild = v;
            if(v!=null)v.isLeftChild=false;
        }
        if (v != null)
            v.parent = u.parent;
    }

    private Node<K, V> getMin(Node<K, V> rootofsearch) {
        Node<K, V> y = rootofsearch;
        while (y.leftChild != null) {
            y = y.leftChild;
        }
        return y;
    }

    private void deleteFix(Node<K, V> node) {
        Node<K, V> x = node;

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
        // i tell him to calm him down so he does so and becomed
        // sibling switch moods with my father and my father becomes calm
        // and my parent rotates at me

        while (x != null && x != this.root && x.black) {
            if (x.isLeftChild) {

                Node<K, V> my_sibling = x.parent.rightChild;

                // case 1 my sibling is angry
                if (my_sibling!=null && !my_sibling.black) {

                    // sibling calms down
                    my_sibling.black = true;

                    // father gets angry and rotates at me
                    x.parent.black = false;
                    leftrotate(x.parent);

                    my_sibling = x.parent.rightChild;
                }

                // my sibling is calm and so are his children
                else if (my_sibling == null ||blackChildren(my_sibling)) {

                    if(my_sibling!=null)
                        // my father gets angry so me try to resolve from him
                        my_sibling.black = false;
                    x = x.parent;

                }
                // my sibling far child is calm
                else if (my_sibling.rightChild == null ||my_sibling.rightChild.black) {

                    if(my_sibling.leftChild != null)
                        // the other child gets calm
                        my_sibling.leftChild.black = true;

                    // the father gets angry and rolls away from me
                    my_sibling.black = false;
                    rightrotate(my_sibling);

                    my_sibling = x.parent.rightChild;
                } else { // the far child is angry

                    // my sibling mood become like my father
                    my_sibling.black = x.parent.black;

                    if(my_sibling.rightChild != null)
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

                Node<K, V> my_sibling = x.parent.leftChild;

                if (my_sibling!=null && !my_sibling.black) {

                    my_sibling.black = true;

                    x.parent.black = false;
                    rightrotate(x.parent);

                    my_sibling = x.parent.leftChild;

                } else if (my_sibling == null ||blackChildren(my_sibling)) {

                    if(my_sibling!=null)
                        my_sibling.black = false;

                    x = x.parent;

                } else if (my_sibling.leftChild ==null || my_sibling.leftChild.black) {

                    if(my_sibling.rightChild != null)
                        my_sibling.rightChild.black = true;

                    my_sibling.black = false;
                    leftrotate(my_sibling);

                    my_sibling = x.parent.leftChild;
                } else {

                    my_sibling.black = x.parent.black;
                    x.parent.black = true;

                    if(my_sibling.leftChild != null)
                        my_sibling.leftChild.black = true;

                    
                    rightrotate(x.parent);

                    x = this.root;
                }
            }
        }
        this.root.black =true;
    }

    private boolean blackChildren(Node<K, V> node) {
        return (node.leftChild == null || node.leftChild.black)
                && (node.rightChild == null || node.rightChild.black);
    }
}
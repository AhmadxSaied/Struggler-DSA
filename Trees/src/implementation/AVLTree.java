package implementation;

public class AVLTree<K extends Comparable<? super K>, V> {
    /*
     * The avl node has one extra attributes which is the height
     * the rest of the attributes are the normal BST attributes
     */
    private class AVLTreeNode implements Comparable<AVLTreeNode> {
        private final K key;
        private V value;
        private int height;
        private AVLTreeNode leftChild;
        private AVLTreeNode rightChild;
        private AVLTreeNode parent;

        public AVLTreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 0;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
        }

        @Override
        public int compareTo(AVLTreeNode other) {
            return this.key.compareTo(other.key);
        }

    }

    private int size;

    private AVLTreeNode root;

    /*
     * The constructor doesnt need any extra features we initializing the tree root
     * using the insertion method
     */
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    /*
     * The first part of the insertion is the normal as the BST we take a key and a
     * value and search for the place to insert the key
     */
    public boolean insert(K key, V value) {
        AVLTreeNode tempItr = this.root;
        if (tempItr == null) {
            this.root = new AVLTreeNode(key, value);
            this.size++;
            return true;
        }
        AVLTreeNode insertionPoint = this.root;
        while (tempItr != null) {
            insertionPoint = tempItr;
            if (tempItr.key.compareTo(key) > 0) {
                tempItr = tempItr.leftChild;
            } else if (tempItr.key.compareTo(key) < 0) {
                tempItr = tempItr.rightChild;
            } else {
                tempItr.value = value;
                return false;
            }
        }
        AVLTreeNode newNode = new AVLTreeNode(key, value);
        if (insertionPoint.key.compareTo(key) > 0) {
            insertionPoint.leftChild = newNode;
        } else {
            insertionPoint.rightChild = newNode;
        }
        /*
         * we need to update the height of the parent we just added to ensure it is
         * correct
         */
        insertionPoint.height = Math.max(height(insertionPoint.leftChild), height(insertionPoint.rightChild)) + 1;
        newNode.parent = insertionPoint;
        /*
         * after we insert and correct the parent connection with the newly added node
         * we check for any inbalances to fix
         */
        fixUp(newNode);
        this.size++;
        return true;
    }

    /*
     * The first part of the delete is also similar to the BST deletion the only
     * difference is in the deletion
     */
    public V delete(K key, V Value) {
        AVLTreeNode tempItr = this.root;

        if (tempItr == null) {
            return null;
        }
        while (tempItr != null) {
            if (tempItr.key.compareTo(key) > 0) {
                tempItr = tempItr.leftChild;
            } else if (tempItr.key.compareTo(key) < 0) {
                tempItr = tempItr.rightChild;
            } else {
                break;
            }
        }
        /*
         * here is the part were me find the node to be deleted
         */
        if (tempItr != null) {
            /*
             * we check if the node to bedeleted has 0 , 1 or two chicldren
             * 
             * in the case were we have one or no children we make sure that we replace the
             * node with one of its one of its children or with null if the node is a leaf
             */
            if (tempItr.leftChild == null) {

                transplant(tempItr, tempItr.rightChild);
                fixUp(tempItr.parent);

            } else if (tempItr.rightChild == null) {

                transplant(tempItr, tempItr.leftChild);
                fixUp(tempItr.parent);

            } else {
                /*
                 * in the case of the node having two children we can replace it with its
                 * inorder successor or its inorder predecessor i went with the inorderSuccessor
                 * aka the smallest in the right subtree
                 */
                AVLTreeNode inorderSuccessor = inorderSuccessor(tempItr);
                /*
                 * here we check that the insuccessor is not the immediaty right child
                 * if it is not the immediate we replace the successor with the right child and
                 * we adjust the right pointer of the successor
                 */
                if (inorderSuccessor != tempItr.rightChild) {
                    transplant(inorderSuccessor, inorderSuccessor.rightChild);

                    inorderSuccessor.rightChild = tempItr.rightChild;

                    /*
                     * here is to ensure we are not accessing a null object
                     */
                    if (inorderSuccessor.rightChild != null)
                        inorderSuccessor.rightChild.parent = inorderSuccessor;
                }
                /*
                 * here we transplalnt the node to be deleted with the inordersuccor aka
                 * adjusting the parent connection
                 */
                transplant(tempItr, inorderSuccessor);
                /*
                 * we adjust the left pointer to be that of the deleted node
                 */
                inorderSuccessor.leftChild = tempItr.leftChild;

                if (inorderSuccessor.leftChild != null)
                    inorderSuccessor.leftChild.parent = inorderSuccessor;

                /*
                 * we ensure the height of the successor is updated and then we try to fixup any
                 * inconsistencies
                 */
                inorderSuccessor.height = Math.max(height(inorderSuccessor.leftChild),
                        height(inorderSuccessor.rightChild)) + 1;
                fixUp(inorderSuccessor);

            }
            this.size--;
            return tempItr.value;
        }

        return null;

    }

    public V search(K key) {
        AVLTreeNode tempItr = this.root;

        while (tempItr != null) {
            if (tempItr.key.compareTo(key) > 0) {
                tempItr = tempItr.leftChild;
            } else if (tempItr.key.compareTo(key) < 0) {
                tempItr = tempItr.rightChild;
            } else {
                return tempItr.value;
            }
        }
        return null;
    }

    /*
     * This is done so that we dont reference a null object
     */
    private int height(AVLTreeNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /*
     * this function returns the inordersuccessor aka the smallest element in the
     * right subtree
     */
    private AVLTreeNode inorderSuccessor(AVLTreeNode node) {
        if (node == null)
            return null;

        AVLTreeNode temp = node.rightChild;
        while (temp.leftChild != null) {
            temp = temp.leftChild;
        }
        return temp;
    }

    /*
     * The transplant function handles the parent connection and ensures that it
     * corrects it and handles the root edge case
     */
    private void transplant(AVLTreeNode target, AVLTreeNode replacement) {
        if (target.parent == null) {
            this.root = replacement;
        } else if (target == target.parent.leftChild) {
            target.parent.leftChild = replacement;
        } else if (target == target.parent.rightChild) {
            target.parent.rightChild = replacement;
        }

        if (replacement != null) {
            replacement.parent = target.parent;
        }
    }

    /* @formatter:off
    * Right/Left rotate are made to ensure the we keep the balance of the tree
   * The right rotate:
   *                  X                                Y 
   *                 /                                / \
   *                Y             =====>             Z   X
   *               / \                                  /
   *              Z   V                                V
   *                                          
    * The left rotate:
    *                  X                                   Y 
    *                    \                                / \
    *                     Y          =====>              X   Z
    *                    / \                              \
    *                   V    Z                             V
    */ 
   // @formatter:on
    private void rightRotate(AVLTreeNode node) {
        /*
         * Since we rotate right we need a reference to the leftchild
         * we first make the leftchild right child the leftchild of the node to be
         * repeated
         * we correct the connection between the parent of the node to be rotated that
         * why we used transplant
         */
        AVLTreeNode leftChild = node.leftChild;
        node.leftChild = leftChild.rightChild;
        transplant(node, leftChild);

        if (node.leftChild != null)
            node.leftChild.parent = node;
        /*
         * we then ensure that the leftchilds new right child is the original node to be
         * rotated
         */
        leftChild.rightChild = node;
        leftChild.rightChild.parent = leftChild;
        /*
         * here we adjust the height of the node that is rotated and its previous
         * leftchild that took its place
         */
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;

        leftChild.height = Math.max(height(leftChild.leftChild), height(leftChild.rightChild)) + 1;
    }

    /*
     * here we do the same as the right rotate the difference that we switch with
     * teh children
     */
    private void leftRotate(AVLTreeNode node) {
        AVLTreeNode rightChild = node.rightChild;
        node.rightChild = rightChild.leftChild;
        transplant(node, rightChild);

        if (node.rightChild != null)
            node.rightChild.parent = node;

        rightChild.leftChild = node;
        rightChild.leftChild.parent = rightChild;

        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;

        rightChild.height = Math.max(height(rightChild.leftChild), height(rightChild.rightChild)) + 1;
    }

    /*
     * this is to calculate the balancing factor that we will use in the fixup
     */
    private int balancingFactor(AVLTreeNode node) {
        if (node == null)
            return 0;
        return (height(node.rightChild) - height(node.leftChild));
    }

    /*
     * this is the function that is the backbone of keeping the tree strictly
     * balanced
     */
    private void fixUp(AVLTreeNode targetNode) {
        AVLTreeNode node = targetNode;
        while (node != null) {
            int balanceFactor = balancingFactor(node);

            if (balanceFactor >= 2) { // right branch is deeper than left thereforer leftrotation

                AVLTreeNode rightChild = node.rightChild;
                int childFactor = balancingFactor(rightChild);
                /*
                 * if the child balance factor is smaller than zero this means the child of the
                 * right child is the left one thus we are in the triangle case and we need to
                 * perform a right rotate before the normal left that we will execute
                 */
                if (childFactor < 0) {
                    rightRotate(rightChild);
                }
                leftRotate(node);
                /*
                 * the same goes here but we switch the rotations
                 */
            } else if (balanceFactor <= -2) { // left branch is deeper than right thereforer rightrotation
                AVLTreeNode leftChild = node.leftChild;
                int childFactor = balancingFactor(leftChild);

                if (childFactor > 0) {
                    leftRotate(leftChild);
                }
                rightRotate(node);
            }
            /*
             * we update the height of the node
             */
            node = node.parent;
        }
    }
}

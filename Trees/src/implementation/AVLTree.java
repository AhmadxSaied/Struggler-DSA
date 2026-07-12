package implementation;

public class AVLTree<K extends Comparable<? super K>, V> {
    private int size;
    private AVLTreeNode root;

    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    private class AVLTreeNode implements Comparable<AVLTreeNode> {
        private K key;
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

    public boolean insert(K key, V value) {
        AVLTreeNode tempItr = this.root;
        if (tempItr == null) {
            this.root = new AVLTreeNode(key, value);
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

        if (insertionPoint.key.compareTo(key) > 0) {
            insertionPoint.leftChild = new AVLTreeNode(key, value);
        } else {
            insertionPoint.rightChild = new AVLTreeNode(key, value);
        }
        fix_insertion();
        return true;
    }

    public V delete(K key, V Value) {
        AVLTreeNode tempItr = this.root;

        if (tempItr == null) {
            return false;
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
        if (tempItr != null) {
            if (tempItr.leftChild == null) {

                transplant(tempItr, tempItr.rightChild);

            } else if (tempItr.rightChild == null) {

                transplant(tempItr, tempItr.leftChild);
            } else {
                AVLTreeNode inorderSuccessor = inorderSuccessor(tempItr);

                if (inorderSuccessor != tempItr.rightChild) {
                    transplant(inorderSuccessor, inorderSuccessor.rightChild);

                    inorderSuccessor.rightChild = tempItr.rightChild;

                    if (inorderSuccessor.rightChild != null)
                        inorderSuccessor.rightChild.parent = inorderSuccessor;
                }

                transplant(tempItr, inorderSuccessor);

                inorderSuccessor.leftChild = tempItr.leftChild;

                if (inorderSuccessor.leftChild != null)
                    inorderSuccessor.leftChild.parent = inorderSuccessor;

                this.size--;

            }
            return tempItr.value;
        }

        return null;

    }

    private AVLTreeNode inorderSuccessor(AVLTreeNode node) {
        if (node == null)
            return null;

        AVLTreeNode temp = node.rightChild;
        while (temp.leftChild != null) {
            temp = temp.leftChild;
        }
        return temp;
    }

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
}

package implementation;

public class AVLTree<K extends Comparable<? super K>, V> {
    private int size;
    private AVLTreeNode root;

    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    private int height(AVLTreeNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

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
        AVLTreeNode newNode = new AVLTreeNode(key, value);
        if (insertionPoint.key.compareTo(key) > 0) {
            insertionPoint.leftChild = newNode;
        } else {
            insertionPoint.rightChild = newNode;
        }
        insertionPoint.height = Math.max(height(insertionPoint.leftChild), height(insertionPoint.rightChild));
        newNode.parent = insertionPoint;
        fixUp(newNode);
        return true;
    }

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

                inorderSuccessor.height = Math.max(height(inorderSuccessor.leftChild),
                        height(inorderSuccessor.rightChild));
                fixUp(inorderSuccessor);
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

    private void rightRotate(AVLTreeNode node) {
        AVLTreeNode leftChild = node.leftChild;
        node.leftChild = leftChild.rightChild;
        transplant(node, leftChild);

        if (node.leftChild != null)
            node.leftChild.parent = node;

        leftChild.rightChild = node;
        leftChild.rightChild.parent = leftChild;

        node.height = Math.max(height(node.leftChild), height(node.rightChild));

        leftChild.height = Math.max(height(leftChild.leftChild), height(leftChild.rightChild));
    }

    private void leftRotate(AVLTreeNode node) {
        AVLTreeNode rightChild = node.rightChild;
        node.rightChild = rightChild.leftChild;
        transplant(node, rightChild);

        if (node.rightChild != null)
            node.rightChild.parent = node;

        rightChild.leftChild = node;
        rightChild.leftChild.parent = node;

        node.height = Math.max(height(node.leftChild), height(node.rightChild));

        rightChild.height = Math.max(height(rightChild.leftChild), height(rightChild.rightChild));
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

    private int balancingFactor(AVLTreeNode node) {
        if (node == null)
            return 0;
        return (height(node.rightChild) - height(node.leftChild));
    }

    private void heightUpdate(AVLTreeNode node) {
        while (node != null) {
            AVLTreeNode rightChild = node.rightChild;
            AVLTreeNode leftChild = node.leftChild;

            int leftHeight = leftChild == null ? 0 : leftChild.height + 1;
            int rightHeight = rightChild == null ? 0 : rightChild.height + 1;

            node.height = Math.max(leftHeight, rightHeight);
            node = node.parent;
        }
    }

    private void fixUp(AVLTreeNode targetNode) {
        AVLTreeNode node = targetNode;
        while (node != root) {
            int balanceFactor = balancingFactor(node);

            if (balanceFactor >= 2) { // right branch is deeper than left thereforer leftrotation

                AVLTreeNode rightChild = node.rightChild;
                int childFactor = balancingFactor(rightChild);

                if (childFactor < 0) {
                    rightRotate(rightChild);
                }
                leftRotate(node);

            } else if (balanceFactor <= -2) {
                AVLTreeNode leftChild = node.leftChild;
                int childFactor = balancingFactor(leftChild);

                if (childFactor > 0) {
                    leftRotate(leftChild);
                }
                leftRotate(node);
            }
            node.height = Math.max(height(node.leftChild), height(node.rightChild));
            node = node.parent;
        }
    }
}

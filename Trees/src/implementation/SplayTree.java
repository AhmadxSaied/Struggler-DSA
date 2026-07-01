package implementation;

public class SplayTree<K extends Comparable<? super K>, V> {
    private SplayTreeNode root;
    private int size;

    public SplayTree() {
        this.root = null;
        this.size = 0;
    }

    private class SplayTreeNode implements Comparable<SplayTreeNode> {
        private final K key;
        private V value;
        private SplayTreeNode leftchild;
        private SplayTreeNode rightchild;
        private SplayTreeNode parent;

        public SplayTreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.parent = null;
            this.leftchild = null;
            this.rightchild = null;
        }

        @Override
        public int compareTo(SplayTreeNode other) {
            return this.key.compareTo(other.key);
        }

    }

    public V search(K key) {
        SplayTreeNode iterNodeP = null;
        SplayTreeNode iterNode = this.root;
        if (iterNode == null)
            return null;

        while (iterNode != null) {
            if (iterNode.key.compareTo(key) > 0) {
                iterNodeP = iterNode;
                iterNode = iterNode.leftchild;
            } else if (iterNode.key.compareTo(key) < 0) {
                iterNodeP = iterNode;
                iterNode = iterNode.rightchild;
            } else
                break;
        }

        if (iterNode == null) {
            splay(iterNodeP);
            return null;
        }

        splay(iterNode);
        return iterNode.value;
    }

    public boolean insert(K key, V value) {
        if (this.root == null) {
            this.root = new SplayTreeNode(key, value);
            this.size++;
            return true;
        }

        SplayTreeNode iterNodeP = null;
        SplayTreeNode iterNode = this.root;

        while (iterNode != null) {
            if (iterNode.key.compareTo(key) > 0) {
                iterNodeP = iterNode;
                iterNode = iterNode.leftchild;
            } else if (iterNode.key.compareTo(key) < 0) {
                iterNodeP = iterNode;
                iterNode = iterNode.rightchild;
            } else {
                iterNode.value = value;
                splay(iterNode);
                return false;
            }
        }
        SplayTreeNode newNode = new SplayTreeNode(key, value);
        if (iterNodeP.key.compareTo(key) > 0)
            iterNodeP.leftchild = newNode;
        else
            iterNodeP.rightchild = newNode;

        newNode.parent = iterNodeP;
        splay(newNode);
        this.size++;
        return true;
    }

    public boolean delete(K key) {
        if (this.root == null)
            return false;

        SplayTreeNode deletedNodeP = null;
        SplayTreeNode deletedNode = this.root;

        while (deletedNode != null) {
            if (deletedNode.key.compareTo(key) > 0) {
                deletedNodeP = deletedNode;
                deletedNode = deletedNodeP.leftchild;
            } else if (deletedNode.key.compareTo(key) < 0) {
                deletedNodeP = deletedNode;
                deletedNode = deletedNode.rightchild;
            } else
                break;
        }
        SplayTreeNode replacement = null;
        if (deletedNode != null) {
            if (deletedNode.leftchild == null) {
                transplant(deletedNode, deletedNode.rightchild);
                replacement = deletedNode.rightchild;
            }

            else if (deletedNode.rightchild == null) {
                transplant(deletedNode, deletedNode.leftchild);
                replacement = deletedNode.leftchild;
            }

            else {
                replacement = inorderSuccessor(deletedNode);

                if (replacement != deletedNode.rightchild) {
                    transplant(replacement, replacement.rightchild);
                    replacement.rightchild = deletedNode.rightchild;
                    replacement.rightchild.parent = replacement;
                }
                transplant(deletedNode, replacement);
                replacement.leftchild = deletedNode.leftchild;
                replacement.leftchild.parent = replacement;

            }
            this.size--;
        }
        if (replacement == null)
            splay(deletedNodeP);
        else
            splay(replacement);
        return deletedNode != null;

    }

    private void transplant(SplayTreeNode node, SplayTreeNode replacement) {
        SplayTreeNode parent = node.parent;

        if (parent == null)
            this.root = replacement;

        else if (node == parent.leftchild)
            parent.leftchild = replacement;
        else if (node == parent.rightchild)
            parent.rightchild = replacement;

        if (replacement != null)
            replacement.parent = parent;
    }

    private SplayTreeNode inorderSuccessor(SplayTreeNode node) {
        SplayTreeNode iterNode = node.rightchild;

        while (iterNode.leftchild != null) {
            iterNode = iterNode.leftchild;
        }
        return iterNode;
    }

    private void rightRotate(SplayTreeNode node) {
        SplayTreeNode leftchild = node.leftchild;

        transplant(node, leftchild);

        node.parent = leftchild;

        node.leftchild = leftchild.rightchild;

        if (node.leftchild != null)
            node.leftchild.parent = node;

        leftchild.rightchild = node;
        leftchild.rightchild.parent = leftchild;
    }

    private void leftRotate(SplayTreeNode node) {
        SplayTreeNode rightchild = node.rightchild;

        transplant(node, rightchild);

        node.parent = rightchild;
        node.rightchild = rightchild.leftchild;

        if (node.rightchild != null)
            node.rightchild.parent = node;

        rightchild.leftchild = node;
        rightchild.leftchild.parent = rightchild;
    }

    private void splay(SplayTreeNode node) {
        if (node == null)
            return;
        while (node != this.root) {
            SplayTreeNode parent = node.parent;
            SplayTreeNode grandparent = node.parent.parent;

            // you are a child of a root
            if (grandparent == null) {
                zig(node, parent);
            } else {
                if (parent == grandparent.leftchild) {

                    // case 2 im right child thus we perform zig zag
                    if (node == parent.rightchild) {
                        zigzag(node, parent, grandparent);
                    } else {
                        zigzig(node, parent, grandparent);
                    }

                } else {
                    if (node == parent.leftchild) {
                        zigzag(node, parent, grandparent);
                    } else {
                        zigzig(node, parent, grandparent);
                    }
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    private void zig(SplayTreeNode node, SplayTreeNode parent) {
        if (node == parent.leftchild)
            rightRotate(parent);
        else
            leftRotate(parent);
    }

    private void zigzig(SplayTreeNode node, SplayTreeNode parent, SplayTreeNode grandparent) {
        zig(parent, grandparent);
        zig(node, parent);
    }

    private void zigzag(SplayTreeNode node, SplayTreeNode parent, SplayTreeNode grandparent) {
        zig(node, parent);
        zig(node, grandparent);
    }
}

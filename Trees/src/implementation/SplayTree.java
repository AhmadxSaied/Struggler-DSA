package implementation;

import com.sun.source.tree.Tree;

public class SplayTree<K extends Comparable<? super K>, V> {
    private SplayTreeNode root;
    private int size;

    public SplayTree() {
        this.root = null;
        this.size = 0;
    }

    private class SplayTreeNode implements Comparable<SplayTreeNode> {
        private K key;
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
        SplayTreeNode iterNode = this.root;
        if (iterNode == null)
            return null;

        while (iterNode != null) {
            if (iterNode.key.compareTo(key) > 0) {
                iterNode = iterNode.leftchild;
            } else if (iterNode.key.compareTo(key) < 0) {
                iterNode = iterNode.rightchild;
            } else
                break;
        }

        if (iterNode == null)
            return null;

        splay(iterNode);
        return iterNode.value;
    }

    public boolean insert(K key, V value) {
        if (this.root == null) {
            this.root = new SplayTreeNode(key, value);
            return true;
        }

        SplayTreeNode iterNodeP, iterNode = this.root;

        while (iterNode != null) {
            if (iterNode.key.compareTo(key) > 0) {
                iterNodeP = iterNode;
                iterNode = iterNode.leftchild;
            } else if (iterNode.key.compareTo(key) < 0) {
                iterNodeP = iterNode;
                iterNode = iterNode.rightchild;
            } else
                iterNode.value = value;
            splay(iterNode);
            return false;
        }
        SplayTreeNode newNode = new SplayTreeNode(key, value);
        if (iterNodeP.key.compareTo(key) > 0)
            iterNodeP.leftchild = newNode;
        else
            iterNodeP.rightchild = newNode;

        newNode.parent = iterNodeP;
        splay(newNode);

        return true;
    }

    public boolean delete(K key) {
        if (this.root == null)
            return false;

        SplayTreeNode deletedNodeP, deletedNode = this.root;
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

        if (deletedNode != null) {
            if (deletedNode.leftchild == null)
                transplant(deletedNode, deletedNode.rightchild);

            if (deletedNode.rightchild == null)
                transplant(deletedNode, deletedNode.leftchild);

            else {
                SplayTreeNode replacement = inorderSuccessor(deletedNode);

                if (replacement != deletedNode.rightchild) {
                    transplant(replacement, replacement.rightchild);
                    replacement.rightchild = deletedNode.rightchild;
                    replacement.rightchild.parent = replacement;
                }
                transplant(deletedNode, replacement);
                replacement.leftchild = deletedNode.leftchild;
                replacement.leftchild.parent = replacement;
            }

        }
        splay(deletedNodeP);
        return false;

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
}

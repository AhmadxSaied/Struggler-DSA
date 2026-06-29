import java.util.Random;

public class SkipList {

    private SkipListNode head;
    private SkipListNode tail;
    private int height = -1;
    private final Random coin = new Random();

    private class SkipListNode {
        public SkipListNode left;
        public SkipListNode right;
        public SkipListNode up;
        public SkipListNode down;
        public Integer val;

        public SkipListNode(Integer val) {
            this.left = null;
            this.right = null;
            this.up = null;
            this.down = null;
        }

    }

    public SkipList() {
        this.head = new SkipListNode(Integer.MIN_VALUE);
        this.tail = new SkipListNode(Integer.MAX_VALUE);

        this.head.right = this.tail;
        this.tail.left = this.head;
        this.height = 0;
    }

    public boolean insert(Integer val) {
        SkipListNode newNode = new SkipListNode(val);

        SkipListNode tempIterator = this.head;

        int current_level = this.height;

        while (current_level != 0) {

            while (tempIterator.right != null && tempIterator.right.val > val) {
                tempIterator = tempIterator.right;
            }
            current_level--;
            tempIterator = tempIterator.down;
        }
        while (tempIterator.right != null && tempIterator.right.val > val) {
            tempIterator = tempIterator.right;
        }
        tempIterator.right.left = newNode;
        tempIterator.right = newNode;

        newNode.left = tempIterator;
        newNode.right = tempIterator.right.left;

        boolean head_or_tail = coin.nextBoolean();
        int promoted_levels = 0;
        while (head_or_tail) {
            newNode = promote(newNode, promoted_levels);
            head_or_tail = coin.nextBoolean();
        }
        return true;
    }

    private SkipListNode left_bridge(SkipListNode node) {
        SkipListNode temp = node;
        while (temp.left != null && temp.up == null) {
            temp = temp.left;
        }
        temp = temp.up;
        return temp.up;

    }

    private SkipListNode promote(SkipListNode newBridge, int current_level) {
        SkipListNode newBridgeClone = new SkipListNode(newBridge.val);

        if (current_level == height) {
            get_new_level();

            this.tail.left = newBridgeClone;
            newBridgeClone.right = this.tail;

            this.head.right = newBridgeClone;
            newBridgeClone.left = this.head;

            newBridgeClone.down = newBridge;
            newBridge.up = newBridgeClone;
            return newBridgeClone;

        }

        SkipListNode leftBridge = left_bridge(newBridge);

        leftBridge.right.left = newBridgeClone;
        newBridgeClone.right = leftBridge.right;

        newBridgeClone.left = leftBridge;
        leftBridge.right = newBridgeClone;

        newBridgeClone.down = newBridge;
        newBridge.up = newBridgeClone;

        return newBridgeClone;

    }

    private void get_new_level() {
        SkipListNode newHead = new SkipListNode(Integer.MIN_VALUE);
        SkipListNode newTail = new SkipListNode(Integer.MIN_VALUE);

        this.head.up = newHead;
        newHead.down = this.head;

        this.tail.up = newTail;
        newTail.down = this.tail;

        newHead.right = newTail;
        newTail.left = newHead;

        this.head = newHead;
        this.tail = newTail;
    }
}

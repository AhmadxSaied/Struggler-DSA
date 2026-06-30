import java.util.Random;

public class SkipList {

    private SkipListNode head;
    private SkipListNode tail;
    private int height = -1;
    private final Random coin = new Random();

    private class SkipListNode {
        /*
         * Here we need a node with four pointer as the skiplist works
         * as a train service were we have regular train that stops at each
         * station while there is express trains that stop at less stations making
         * leaping from a express level to another level cheaper than stopping at each
         * stationwe aim to achieve (log n) levels for time complexity of O(n) in all
         * our operations with high probability.
         */
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
            this.val = val;
        }

    }

    public SkipList() {
        /*
         * we typically have sentinal nodes of -oo and +oo to ensure our operations
         * operate well.
         */
        this.head = new SkipListNode(Integer.MIN_VALUE);
        this.tail = new SkipListNode(Integer.MAX_VALUE);

        this.head.right = this.tail;
        this.tail.left = this.head;
        this.height = 0;
    }

    public boolean insert(Integer val) {
        /*
         * our insertion process will be that at each till we reach an element greater
         * than or equal the value
         * we repeat the process at each level till we reach the bottom level aka level
         * 0
         */
        SkipListNode newNode = new SkipListNode(val);

        SkipListNode tempIterator = this.head;

        int current_level = this.height;

        while (current_level != 0) {
            /*
             * here we need to check we didnt the end of list
             * we really dont need the first condition but for extra protection
             */
            while (tempIterator.right != null && tempIterator.right.val < val) {
                tempIterator = tempIterator.right;
            }
            current_level--;
            tempIterator = tempIterator.down;
        }
        while (tempIterator.right != null && tempIterator.right.val < val) {
            tempIterator = tempIterator.right;
        }

        /*
         * here we ensure that the new node is correclty connected from the left and the
         * right
         */

        tempIterator.right.left = newNode;
        newNode.right = tempIterator.right;

        tempIterator.right = newNode;
        newNode.left = tempIterator;

        /*
         * this is the probabilistic part we continue as long as we land on head aka
         * (true)
         */
        boolean head_or_tail = coin.nextBoolean();
        int promoted_levels = 0;
        while (head_or_tail) {
            newNode = promote(newNode, promoted_levels);
            head_or_tail = coin.nextBoolean();
            promoted_levels++;
        }
        return true;
    }

    private SkipListNode left_bridge(SkipListNode node) {
        /*
         * here we need to continue going to the left until we get the node that
         * connects the bridge
         */
        SkipListNode temp = node;
        while (temp.left != null && temp.up == null) {
            temp = temp.left;
        }
        temp = temp.up;
        return temp;

    }

    private SkipListNode promote(SkipListNode newBridge, int current_level) {
        /*
         * here we take in the node on the lower level and make a clone of it
         */
        SkipListNode newBridgeClone = new SkipListNode(newBridge.val);

        /*
         * here is to add an entire new level so we need to ensure to add a new head and
         * tail and then make the connection to them.
         */
        if (current_level == height) {
            get_new_level();

            this.tail.left = newBridgeClone;
            newBridgeClone.right = this.tail;

            this.head.right = newBridgeClone;
            newBridgeClone.left = this.head;

            // here is to ensure that the bridge is connected
            newBridgeClone.down = newBridge;
            newBridge.up = newBridgeClone;
            return newBridgeClone;

        }
        /*
         * here we get the element on the above level and ensure that the pointers of
         * the bridge and the node on its right is correctly connected to the new node
         */
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
        /*
         * here in order to add a new level we make a new head and tail with -oo and +oo
         * we connect the new sentinal nodes with the older sentinal nodes
         */
        SkipListNode newHead = new SkipListNode(Integer.MIN_VALUE);
        SkipListNode newTail = new SkipListNode(Integer.MAX_VALUE);

        this.head.up = newHead;
        newHead.down = this.head;

        this.tail.up = newTail;
        newTail.down = this.tail;

        newHead.right = newTail;
        newTail.left = newHead;

        this.head = newHead;
        this.tail = newTail;
        this.height++;
    }

    public boolean search(Integer val) {
        SkipListNode target = inner_search(val);
        return target != null;
    }

    private SkipListNode inner_search(Integer val) {
        /*
         * here we do the same as insertion but we check if we found our target node
         * and we return the node that we found this will be used in the normal search
         * and in the delete
         */
        SkipListNode tempIterator = this.head;

        int currentlevel = this.height;
        while (currentlevel != 0) {

            while (tempIterator.val < val)
                tempIterator = tempIterator.right;

            if (tempIterator.val.equals(val))
                return tempIterator;
            currentlevel--;
            tempIterator = tempIterator.down;
        }
        while (tempIterator.val < val)
            tempIterator = tempIterator.right;

        if (tempIterator.val.equals(val))
            return tempIterator;
        else
            return null;
    }

    public boolean delete(Integer val) {
        /*
         * here we find the target node and return false if not found
         * we then ensure that if we encounter a bridge node we delete all the elements
         * in the down levels abd we correct the connections on the left and right of
         * the node
         */
        SkipListNode target = inner_search(val);

        if (target == null)
            return false;

        while (target != null) {

            target.right.left = target.left;

            target.left.right = target.right;

            target.left = null;
            target.right = null;

            target = target.down;
        }
        return true;
    }

}

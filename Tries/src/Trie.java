
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trie {

    private final TrieNode root;

    private class TrieNode {
        private final char data;
        private int wordEnding;
        private int immediateChildren;
        private final List<TrieNode> children;

        public TrieNode(char data) {
            this.data = data;
            this.wordEnding = 0;
            this.immediateChildren = 0;
            this.children = new ArrayList<>(Collections.nCopies(26, null));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TrieNode{");
            sb.append("data=").append(data);
            sb.append(", wordEnding=").append(wordEnding);
            sb.append(", immediateChildren=").append(immediateChildren);
            sb.append(", children=").append(children);
            sb.append('}');
            return sb.toString();
        }
    }

    public Trie() {
        this.root = new TrieNode('.');
    }

    public void insert(String word) {
        int index = 0;
        this.insert(this.root, word, index);
    }

    private int insert(TrieNode node, String word, int index) {
        if (index == word.length())
            return 1;
        int desired_char = word.charAt(index) - 'a';
        TrieNode temp = node.children.get(desired_char);

        int wordEnding;
        boolean added_new = false;
        if (temp == null) {
            temp = new TrieNode(word.charAt(index));

            node.children.set(desired_char, temp);
            wordEnding = this.insert(temp, word, index + 1);
            added_new = true;
        } else {
            wordEnding = this.insert(temp, word, index + 1);
        }

        temp.wordEnding += wordEnding;
        if (added_new) {
            node.immediateChildren++;
        }
        return 0;
    }

    public boolean search(String pattern) {
        int index = 0;
        return this.search(this.root, pattern, index);
    }

    private boolean search(TrieNode node, String pattern, int index) {
        int desired_char = pattern.charAt(index) - 'a';

        if (index == pattern.length() - 1) {
            TrieNode finalNode = node.children.get(desired_char);
            return finalNode != null && finalNode.wordEnding > 0;
        }

        TrieNode possibleChar = node.children.get(desired_char);

        if (possibleChar == null)
            return false;

        return search(possibleChar, pattern, index + 1);
    }

    public void delete(String word) {
        int index = 0;
        this.delete(this.root, word, index);
    }

    private boolean delete(TrieNode node, String word, int index) {
        int desired_char = word.charAt(index) - 'a';

        if (index == word.length() - 1) {
            TrieNode finalNode = node.children.get(desired_char);
            return finalNode != null && finalNode.wordEnding == 1 && finalNode.immediateChildren == 0;
        }

        TrieNode possibleChar = node.children.get(desired_char);

        if (possibleChar == null)
            return false;

        boolean deleteTrial = this.delete(possibleChar, word, index + 1);

        if (deleteTrial) {
            possibleChar.children.set(index, null);
            possibleChar.immediateChildren--;
        }
        return possibleChar.immediateChildren == 0 && possibleChar.wordEnding <= 1;
    }
}

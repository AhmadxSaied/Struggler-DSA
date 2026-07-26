public class App {
    public static void main(String[] args) throws Exception {
        Trie t = new Trie();

        t.insert("cat");
        t.insert("cap");
        t.insert("car");
        t.insert("dog");
        t.insert("door");
        t.insert("do");

        System.out.println((t.search("do") ? "Deleted" : "Not Deleted"));
        System.out.println((t.search("dock") ? "Deleted" : "Not Deleted"));

    }
}

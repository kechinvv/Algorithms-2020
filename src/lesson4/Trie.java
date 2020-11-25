package lesson4;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private Node root = new Node();
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    private static class Node {
        Map<Character, Node> children = new LinkedHashMap<>();
    }

    public class Pair {
        Character val;
        Node node;

        Pair(Character v, Node n) {
            val = v;
            node = n;
        }

        public Pair() {

        }
    }

    public class TrieIterator implements Iterator<String> {
        String cur;
        ArrayDeque<String> deque = new ArrayDeque();

        private TrieIterator() {
            if (root != null) fill(root, "");
        }

        public void fill(Node node, String word) {
            for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
                if (entry.getKey().equals((char) 0)) deque.push(word);
                else fill(entry.getValue(), word + entry.getKey());
            }
        }

        @Override
        public boolean hasNext() {
            return !deque.isEmpty();
        }
        //сложность: O(1) Ресурсоемкость: O(1)

        @Override
        public String next() {
            cur = deque.removeLast();
            return cur;
        }
        //сложность: O(1) Ресурсоемкость: O(1)

        @Override
        public void remove() {
            if (cur!= null) {
                Trie.this.remove(cur);
                cur=null;
            }
            else throw new IllegalStateException();
        }
        //сложность: O(n) Ресурсоемкость: O(n)
    }
}
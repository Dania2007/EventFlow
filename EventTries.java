package eventflow.algorithms;

import java.util.*;

public class EventTries {
    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        boolean end;
    }

    private final Node root = new Node();

    public void insert(String word) {
        Node current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            current = current.children.computeIfAbsent(
                ch, k -> new Node()
            );
        }
        current.end = true;
    }

    public List<String> autocomplete(String prefix) {
        List<String> results = new ArrayList<>();
        Node current = root;
        String normalized = prefix.toLowerCase();

        for (char ch : normalized.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return results;
        }

        collect(current, new StringBuilder(normalized), results);
        return results;
    }

    private void collect(Node node, StringBuilder word,
                         List<String> results) {
        if (node.end) results.add(word.toString());

        for (Map.Entry<Character, Node> entry
                : node.children.entrySet()) {
            word.append(entry.getKey());
            collect(entry.getValue(), word, results);
            word.deleteCharAt(word.length() - 1);
        }
    }
}
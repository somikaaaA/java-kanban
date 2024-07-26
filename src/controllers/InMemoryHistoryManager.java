package controllers;

import model.Task;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;
    private Map<Integer, Node> nodeMap = new HashMap<>();

    public static class Node {
        private Task task;
        private Node prev;
        private Node next;

        protected Node(Task task) {
            this.task = task;
        }
    }

    @Override
    public void add(Task task) {
        Node node = new Node(task);
        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
        }
        linkLast(node);
        nodeMap.put(task.getId(), node);
    }

    private void linkLast(Node node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else { // Удаляемый узел является головой списка
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else { // Удаляемый узел является хвостом списка
            tail = node.prev;
        }
    }

    @Override
    public void remove(int id) {
        Node node = nodeMap.get(id);
        if (node != null) {
            removeNode(node);
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }
}

package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Task;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> history = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    private static class Node<T> {
        Task task;
        Node<Task> prev;
        Node<Task> next;

        public Node(Task task) {
            this.task = task;
            this.next = null;
        }

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        if (history.containsKey(task.getId())) {
            removeNode(history.remove(task.getId()));
        }
        linkLast(task);
        history.put(task.getId(), tail);
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    @Override
    public void remove(int id) {
        final Node node = history.remove(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }

    private void linkLast(Task task) {
        final Node<Task> node = new Node<>(tail, task, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }
}
package controllers;

import model.Task;

public class Node {
    Task task;
    Node prev;
    Node next;

    public Node(Task task) {
        this.task = task;
    }
}

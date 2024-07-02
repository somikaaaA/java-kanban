package model;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.id = -1; // Инициализируем с некорректным значением, чтобы проверить корректность генерации ID
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Status: " + status.name();
    }
}
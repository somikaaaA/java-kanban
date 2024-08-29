//package com.example.taskmanager;

import model.Task;
import model.Epic;
import model.Subtask;
import controllers.FileBackedTaskManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
//        File tempFile = File.createTempFile("temp", ".csv");
//        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Добро пожаловать в менеджер задач!");
//
//        while (true) {
//            System.out.println("\nВыберите действие:");
//            System.out.println("1. Создать задачу");
//            System.out.println("2. Создать эпик");
//            System.out.println("3. Создать подзадачу");
//            System.out.println("4. Сохранить изменения");
//            System.out.println("5. Загрузить задачи из файла");
//            System.out.println("6. Просмотр всех задач");
//            System.out.println("7. Выход");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Обработка переноса строки после ввода числа
//
//            switch (choice) {
//                case 1 -> createTask(scanner, manager);
//                case 2 -> createEpic(scanner, manager);
//                case 3 -> createSubtask(scanner, manager);
//                case 4 -> saveChanges(manager);
//                case 5 -> loadTasksFromFile(manager);
//                case 6 -> printAllTasks(manager);
//                case 7 -> {
//                    break;
//                }
//                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
//            }
//        }
//    }
//
//    private static void createTask(Scanner scanner, FileBackedTaskManager manager) {
//        System.out.print("Введите название задачи: ");
//        String name = scanner.nextLine();
//        System.out.print("Введите описание задачи: ");
//        String description = scanner.nextLine();
//        System.out.print("Введите продолжительность в минутах: ");
//        long durationMinutes = scanner.nextLong();
//        scanner.nextLine(); // Обработка переноса строки после ввода числа
//        System.out.print("Введите время начала (формат HH:mm): ");
//        String startTimeStr = scanner.nextLine();
//        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
//
//        Task task = new Task(name, description, Duration.ofMinutes(durationMinutes), startTime);
//        manager.addNewTask(task);
//        System.out.println("Задача создана успешно!");
//    }
//
//    private static void createEpic(Scanner scanner, FileBackedTaskManager manager) {
//        System.out.print("Введите название эпика: ");
//        String name = scanner.nextLine();
//        System.out.print("Введите описание эпика: ");
//        String description = scanner.nextLine();
//        System.out.print("Введите ID подзадачи для добавления в эпик (или нажмите Enter для завершения): ");
//        int subtaskId = scanner.nextInt();
//        scanner.nextLine(); // Обработка переноса строки после ввода числа
//
//        Epic epic = new Epic(name, description, Arrays.asList(subtaskId));
//        manager.addNewEpic(epic);
//        System.out.println("Эпик создан успешно!");
//    }
//
//    private static void createSubtask(Scanner scanner, FileBackedTaskManager manager) {
//        System.out.print("Введите название подзадачи: ");
//        String name = scanner.nextLine();
//        System.out.print("Введите описание подзадачи: ");
//        String description = scanner.nextLine();
//        System.out.print("Введите продолжительность в минутах: ");
//        long durationMinutes = scanner.nextLong();
//        scanner.nextLine(); // Обработка переноса строки после ввода числа
//        System.out.print("Введите время начала (формат HH:mm): ");
//        String startTimeStr = scanner.nextLine();
//        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
//        System.out.print("Введите ID эпика: ");
//        int epicId = scanner.nextInt();
//        scanner.nextLine(); // Обработка переноса строки после ввода числа
//
//        Subtask subtask = new Subtask(name, description, Duration.ofMinutes(durationMinutes), startTime, epicId);
//        manager.addNewSubtask(subtask);
//        System.out.println("Подзадача создана успешно!");
//    }
//
//    private static void saveChanges(FileBackedTaskManager manager) throws IOException {
//        manager.save();
//        System.out.println("Изменения сохранены в файле.");
//    }
//
//    private static void loadTasksFromFile(FileBackedTaskManager manager) throws IOException {
//        manager.loadFromFile();
//        System.out.println("Задачи загружены из файла.");
//    }
//
//    private static void printAllTasks(FileBackedTaskManager manager) {
//        System.out.println("Все задачи:");
//        for (Object task : manager.getAllTasks()) {
//            System.out.println(task);
//        }
    }
}

package ru.catwarden.sltest;

import java.util.List;
import java.util.Scanner;

public class Console {
    private Scanner scanner;
    private Controller controller;

    public Console(Scanner scanner, Controller controller) {
        this.scanner = scanner;
        this.controller = controller;

    }

    public void showUi(){
        boolean running = true;

        while(running) {
            printSeparator();
            System.out.println("Введите команду:");
            System.out.println("1. Показать текущие и ближайшие ДР");
            System.out.println("2. Показать все ДР");
            System.out.println("3. Добавить новый ДР");
            System.out.println("4. Удалить ДР");
            System.out.println("5. Изменить ДР");
            System.out.println("0. Выйти");

            String cmd = scanner.nextLine();

            switch (cmd){
                case "1":
                    showCurrentBirthdays();
                    showUpcomingBirthdays();
                    break;
                case "2":
                    showAllBirthdays();
                    break;
                case "0":
                    running = false;
                    break;

            }
        }
    }


    public void showCurrentBirthdays(){

        System.out.println("Список текущих ДР:");
    }

    public void showUpcomingBirthdays(){

        System.out.println("Список ближайших ДР:");
    }
    public void showAllBirthdays(){
        List<Birthday> list = controller.getAllBirthdayList();

        printSeparator();
        System.out.println("Список всех ДР:");
        for(Birthday day:list){
            System.out.print(day.getName() + " ");
            System.out.println(day.getDate());
        }
    }

    public void printSeparator(){
        System.out.println("________________");
    }

    public void printIntro(){
        System.out.println("Поздравлятор 3000");
    }
}

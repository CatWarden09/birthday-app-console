package ru.catwarden.sltest;

import java.util.ArrayList;
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

        while(true) {
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
                case "3":
                    setNewBirthday();
                    break;
                case "4":
                    deleteBirthday();
                    break;
                case "5":
                    editBirthday();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Некорректная команда!");

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
        List<BirthdayWithIndex> list = controller.getAllBirthdayList();

        printSeparator();
        System.out.println("Список всех ДР:");
        for(BirthdayWithIndex birthday :list){
            System.out.print(birthday.getIndex() + "." + " ");
            System.out.print(birthday.getName() + " ");
            System.out.println(birthday.getDate());
        }
    }

    public void setNewBirthday(){


        System.out.println("Укажите имя");
        String name = scanner.nextLine();
        List<String> list = validateUserInput();

        String year = list.get(0);
        String month = list.get(1);
        String day = list.get(2);

        controller.setNewBirthday(name, year, month, day);
        System.out.println("ДР добавлен!");
    }

    public void deleteBirthday(){
        while (true) {
            printSeparator();
            System.out.println("Выберите ДР для удаления:");
            List<BirthdayWithIndex> list = controller.getAllBirthdayList();

            for (BirthdayWithIndex birthday : list) {
                System.out.print(birthday.getIndex() + "." + " ");
                System.out.print(birthday.getName() + " ");
                System.out.println(birthday.getDate());
            }


            int index = scanner.nextInt();
            scanner.nextLine();
            int index_checked = controller.getBirthdayId(index, list);
            if (index_checked == -1) {
                System.out.println("Указан неверный номер ДР!");
            } else {
                System.out.println("Вы уверены, что хотите удалить ДР?");
                System.out.println("1. Удалить");
                System.out.println("2. Отмена");
                String cmd = scanner.nextLine();
                switch (cmd){
                    case "1":
                        controller.deleteBirthday(index_checked);
                        System.out.println("ДР удалён!");
                        break;
                    case "2":
                        break;
                    default:
                        System.out.println("Некорректная команда!");
                        continue;
                } break;

            }
        }
    }

    public void editBirthday() {
        while (true) {
            printSeparator();
            System.out.println("Выберите ДР для редактирования:");
            List<BirthdayWithIndex> list = controller.getAllBirthdayList();

            for (BirthdayWithIndex birthday : list) {
                System.out.print(birthday.getIndex() + "." + " ");
                System.out.print(birthday.getName() + " ");
                System.out.println(birthday.getDate());
            }

            int index = scanner.nextInt();
            scanner.nextLine();
            int index_checked = controller.getBirthdayId(index, list);
            if (index_checked == -1) {
                System.out.println("Указан неверный номер ДР!");
            } else{
                List<String> date_list = validateUserInput();

                String year = date_list.get(0);
                String month = date_list.get(1);
                String day = date_list.get(2);

                controller.editBirthday(index_checked, year, month, day);
            break;
            }
        }
    }

    public List<String> validateUserInput() {
        List<String> list = new ArrayList();
        while (true) {
            String year;
            System.out.println("Укажите год рождения в формате YYYY");
            while (true) {
                year = scanner.nextLine();

                if (!year.chars().allMatch(Character::isDigit)) {
                    System.out.println("Обнаружены некорректные символы!");
                    continue;
                } else if (year.length() != 4) {
                    System.out.println("Некорректный формат года!");
                    continue;

                }
                int y = Integer.parseInt(year);

                if (y > 2025 || y < 1920) {
                    System.out.println("Некорректный год!");
                } else {
                    break;
                }
            }

            // TODO add the leap year checking (29 days in February)
            // check java.time.Year
            String month;
            System.out.println("Укажите месяц рождения в формате MM");
            while (true) {
                month = scanner.nextLine();

                if (!month.chars().allMatch(Character::isDigit)) {
                    System.out.println("Обнаружены некорректные символы!");
                    continue;
                } else if (month.length() != 2) {
                    System.out.println("Некорректный формат месяца!");
                    continue;

                }
                int m = Integer.parseInt(month);

                if (m > 12 || m < 1) {
                    System.out.println("Некорректный месяц!");

                } else {
                    break;
                }
            }

            String day;
            System.out.println("Укажите день рождения в формате DD");
            while (true) {
                day = scanner.nextLine();

                if (!day.chars().allMatch(Character::isDigit)) {
                    System.out.println("Обнаружены некорректные символы!");
                    continue;
                } else if (day.length() != 2) {
                    System.out.println("Некорректный формат дня!");
                    continue;
                }

                int d = Integer.parseInt(day);

                // separate check for February
                if ((month.equals("02") && (d > 28 || d < 1)) || (!month.equals("02") && (d > 31 || d < 1))) {
                    System.out.println("Некорректный день!");
                    continue;
                }
                break;
            }
            list.add(year);
            list.add(month);
            list.add(day);
            return list;
        }
    }

    public void printSeparator(){
        System.out.println("________________");
    }

    public void printIntro(){
        System.out.println("Поздравлятор 3000");
    }
}


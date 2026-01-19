package ru.catwarden.sltest;
import java.util.Scanner;
// TODO add birthdays sorting, add skipped birthdays display
public class App {

    public static void main( String[] args ){

        Scanner sc = new Scanner(System.in);


        Config config = new Config("config.properties");
        Database db = new Database(config);
        Controller controller = new Controller(db);

        Console console = new Console(sc, controller);

        // console state on program launch
        console.printSeparator();
        console.printIntro();
        console.showCurrentBirthdays();
        console.showUpcomingBirthdays();
        console.showUi();

    }
}

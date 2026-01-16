package ru.catwarden.sltest;

import java.util.List;

public class Controller {
    private Database db;

    public Controller(Database db){
        this.db = db;
    }

    public List<Birthday> getAllBirthdayList(){
        return db.getAllBirthdays();
    }
}

package ru.catwarden.sltest;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Database db;

    public Controller(Database db){
        this.db = db;
    }

    public List<BirthdayWithIndex> getAllBirthdayList(){
        List<BirthdayWithIndex> list_parsed= new ArrayList<>();

        List<Birthday> list = db.getAllBirthdays();

        for(int i =0; i<list.size();i++){
            list_parsed.add(new BirthdayWithIndex(
                    list.get(i).getId(),
                    i+1,
                    list.get(i).getName(),
                    list.get(i).getDate()
                    )
            );
        }
        return list_parsed;
    }

    public void setNewBirthday(String name, String year, String month, String day){


        Date date_parsed = Date.valueOf(year + "-" + month + "-" + day);

        Birthday birthday = new Birthday(name, date_parsed);

        db.setNewBirthday(birthday.getName(), birthday.getDate());

    }

    public int getBirthdayId(int index, List<BirthdayWithIndex> list){
        int id = -1;
        for(BirthdayWithIndex birthday:list){
            if(birthday.getId() == index){
                id = index;
            }
        }
        return id;
    }
}

package ru.catwarden.sltest;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Database db;

    public Controller(Database db){
        this.db = db;
    }

    public List<BirthdayWithIndex> getAllBirthdayList(){
        List<BirthdayWithIndex> list_parsed = new ArrayList<>();

        List<Birthday> list = db.getAllBirthdays();

        for(int i =0; i<list.size();i++){
            BirthdayWithIndex birthday = new BirthdayWithIndex();
            birthday.setId(list.get(i).getId());
            birthday.setIndex(i+1);
            birthday.setName(list.get(i).getName());
            birthday.setDate(list.get(i).getDate());

            list_parsed.add(birthday);
        }
        return list_parsed;
    }

    public void setNewBirthday(String name, String year, String month, String day){
        Date date_parsed = Date.valueOf(year + "-" + month + "-" + day);

        db.setNewBirthday(name, date_parsed);

    }

    public int getBirthdayId(int index, List<BirthdayWithIndex> list){
        int id = -1;
        for(BirthdayWithIndex birthday:list){
            if(birthday.getIndex() == index){
                id = birthday.getId();
            }
        }
        return id;
    }

    public void deleteBirthday(int id){
        db.deleteBirthday(id);
    }

    public void editBirthday(int id, String year, String month, String day){
        Date date_parsed = Date.valueOf(year + "-" + month + "-" + day);

        db.editBirthday(id, date_parsed);

    }

    public List<BirthdayWithIndex> getTodayBirthdays(){
        LocalDate current_date = LocalDate.now();

        int current_month = current_date.getMonthValue();
        int current_day = current_date.getDayOfMonth();
        int current_year = current_date.getYear();

        List<BirthdayWithIndex> list_parsed= new ArrayList<>();

        List<Birthday> list = db.getTodayBirthdays(current_month, current_day);

        for(int i = 0; i<list.size();i++){
            BirthdayWithIndex birthday = new BirthdayWithIndex();
            birthday.setIndex(i+1);
            birthday.setName(list.get(i).getName());
            birthday.setDate(list.get(i).getDate());

            int year_parsed = list.get(i).getDate().toLocalDate().getYear();
            birthday.setAge(current_year-year_parsed);
            System.out.println(birthday.getAge());

            list_parsed.add(birthday);
        }
    return list_parsed;
    }
}

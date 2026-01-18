package ru.catwarden.sltest;


import java.sql.Date;
import java.time.LocalDate;
import java.time.MonthDay;
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

    public void setNewBirthday(String name, Date date){

        db.setNewBirthday(name, date);

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

    public void editBirthday(int id, Date date, String name){
        db.editBirthday(id, date, name);

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

            int year_parsed = list.get(i).getDate().toLocalDate().getYear();
            birthday.setAge(current_year-year_parsed);

            list_parsed.add(birthday);
        }
    return list_parsed;
    }

    public List<BirthdayWithIndex> getUpcomingBirthdays(){
        List<Birthday> list = db.getAllBirthdays();
        List<BirthdayWithIndex> parsed_list = new ArrayList<>();

        LocalDate current_date = LocalDate.now();
        LocalDate end_date = current_date.plusDays(7);

        int index = 1;

        for(Birthday birthday:list){
            MonthDay birthday_month_day = MonthDay.from(birthday.getDate().toLocalDate());

            // get the birthday date with the current year (get month and day, then assign a year)
            LocalDate current_birthday_date = birthday_month_day.atYear(current_date.getYear());

            // if the birthday already was in this year, assign the next year
            if (current_birthday_date.isBefore(current_date)) {
                current_birthday_date = birthday_month_day.atYear(current_date.getYear() + 1);
            }

            // check if the birthday is inside the dates range and create a Birthday object
            if (!current_birthday_date.isBefore(current_date) && !current_birthday_date.isAfter(end_date)) {
                BirthdayWithIndex birthday_parsed = new BirthdayWithIndex();
                birthday_parsed.setIndex(index);
                birthday_parsed.setName(birthday.getName());
                birthday_parsed.setDate(java.sql.Date.valueOf(current_birthday_date));

                parsed_list.add(birthday_parsed);
                ++index;
            }
        }
        return parsed_list;
    }

}

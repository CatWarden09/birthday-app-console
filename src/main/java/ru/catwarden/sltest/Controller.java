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

            int year_parsed = list.get(i).getDate().toLocalDate().getYear();
            birthday.setAge(current_year-year_parsed);
            System.out.println(birthday.getAge());

            list_parsed.add(birthday);
        }
    return list_parsed;
    }

    public List<BirthdayWithIndex> getUpcomingBirthdays(){
        // get all the birthdays to avoid complicated SQL-queries and parse the date right in this method
        List<Birthday> list = db.getAllBirthdays();
        List<BirthdayWithIndex> parsed_list = new ArrayList<>();

        LocalDate current_date = LocalDate.now();
        LocalDate end_date = current_date.plusDays(8); // add 8 days because later check does not include the end day itself

        int current_year = current_date.getYear();
        int next_year = end_date.getYear();

        // format the SQL-data from the full date to day and month and filter the upcoming birthdays
        // this loop checks if the birthday is within the current year or in the next year if the 7-day gap goes into it
        for(Birthday birthday:list){
            int index = 1; // index is needed here for the birthday order in the console output since the indexes in list will not represent the actual birthday indexes (the loop may skip unsuitable Birthdays)
            MonthDay birthday_date = MonthDay.from(birthday.getDate().toLocalDate());
            MonthDay birthday_start_date = MonthDay.from(current_date);
            MonthDay birthday_end_date = MonthDay.from(end_date);

            // check if the end date is in current year
            if (birthday_start_date.isBefore(birthday_end_date)) {
                if (birthday_date.isAfter(birthday_start_date) && birthday_date.isBefore(birthday_end_date)) {
                    BirthdayWithIndex birthday_parsed = new BirthdayWithIndex();
                    birthday_parsed.setIndex(index);
                    birthday_parsed.setName(birthday.getName());

                    // get the current year birthday date and pass it to the BirthdayWithIndex object for the console output
                    LocalDate birthday_in_current_year = LocalDate.of(current_year,birthday.getDate().toLocalDate().getMonthValue(), birthday.getDate().toLocalDate().getDayOfMonth());
                    birthday_parsed.setDate(java.sql.Date.valueOf(birthday_in_current_year));

                    parsed_list.add(birthday_parsed);
                }
            } else{
                if(birthday_date.isAfter(birthday_start_date) || birthday_date.isBefore(birthday_end_date)){
                    BirthdayWithIndex birthday_parsed = new BirthdayWithIndex();
                    birthday_parsed.setIndex(index);
                    birthday_parsed.setName(birthday.getName());

                    // get the next year birthday date and pass it to the BirthdayWithIndex object for the console output
                    LocalDate birthday_in_next_year = LocalDate.of(next_year,birthday.getDate().toLocalDate().getMonthValue(), birthday.getDate().toLocalDate().getDayOfMonth());
                    birthday_parsed.setDate(java.sql.Date.valueOf(birthday_in_next_year));

                    parsed_list.add(birthday_parsed);

                    parsed_list.add(birthday_parsed);
                }

            }
        }
        return parsed_list;
    }

}

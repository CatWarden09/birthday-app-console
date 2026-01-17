package ru.catwarden.sltest;

import java.sql.Date;

// class for fetching the data from the DB
public class Birthday {
    private int id;
    private String name;
    private Date date;

    public Birthday(String name, Date date){
        this.name = name;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public Date getDate() {
        return date;
    }
}

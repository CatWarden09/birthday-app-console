package ru.catwarden.sltest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Config config;
    private String dburl;
    private String dbuser;
    private String dbpassword;

    public Database(Config config) {
        this.config = config;
        this.dburl = config.getDatabaseUrl();
        this.dbuser = config.getDatabaseUser();
        this.dbpassword = config.getDatabasePassword();

    }

    public Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(this.dburl, this.dbuser, this.dbpassword);

    }

    public List<Birthday> getAllBirthdays() {
        String query = "SELECT id, name, birthday FROM birthday";
        List<Birthday> list = new ArrayList<Birthday>();

        try(Connection conn = connectToDatabase();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();) {

            while(rs.next()){
                Birthday birthday = new Birthday(rs.getString("name"),rs.getDate("birthday"));
                birthday.setId(rs.getInt("id"));
                list.add(birthday);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    public void setNewBirthday(String name, Date date){
        String query = "INSERT into birthday (name,birthday) VALUES(?,?)";


        try(Connection conn = connectToDatabase();
        PreparedStatement statement = conn.prepareStatement(query);
        ) { statement.setString(1, name);
            statement.setDate(2, date);
            statement.executeUpdate();

        } catch (SQLException exception){
            exception.printStackTrace();
        }


    }
}

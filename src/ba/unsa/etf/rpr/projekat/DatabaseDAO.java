package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DatabaseDAO {
    private Connection conn;
    private PreparedStatement ps, findUserStatement, findAdminStatement, findMaxIdStatement, addNewUserStatement,
                              allUsersUsernameStatement, allAdminsUsernameStatement;
    private PreparedStatement deleteUserFromBase, updateUserStatement;

    private static DatabaseDAO instance = null;

    //Constructor
    private DatabaseDAO() throws SQLException {
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/restaurant.db";
        conn = DriverManager.getConnection(url);
        try{
            ps = conn.prepareStatement("SELECT * FROM users");
        }catch(SQLException e){
            createBase();
          try {
              ps = conn.prepareStatement("SELECT * FROM users");
          }catch(SQLException ee){
              System.out.println("Unable to select anything from users table or invalid database");
          }
        }
        try {
            findUserStatement = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            findAdminStatement=conn.prepareStatement("SELECT * FROM admins WHERE username=? AND password=?");
            findMaxIdStatement=conn.prepareStatement("SELECT MAX(id)+1 FROM users");
            addNewUserStatement=conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?)");
            allUsersUsernameStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            allAdminsUsernameStatement = conn.prepareStatement("SELECT * FROM admins WHERE username=?");
            deleteUserFromBase = conn.prepareStatement("DELETE FROM users WHERE id=?");
            updateUserStatement = conn.prepareStatement("UPDATE users SET name=?, surname=?, username=?, email=?, password=?, gender=? WHERE id=?");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //getInstance for singleton pattern (1x connection on database)
    public static DatabaseDAO getInstance() throws SQLException {
        if(instance == null) instance = new DatabaseDAO();
        return instance;
    }
    public static void removeInstance() throws SQLException {
        if(instance==null) return;
        instance.conn.close();
        instance = null;
    }

    //getUser by username and password
    public User getUser(String username, String password){
        User usr = null;
        if(username.isEmpty() || password.isEmpty()) return null;
        try {
            findUserStatement.setString(1, username);
            findUserStatement.setString(2, password);
            ResultSet rs = findUserStatement.executeQuery();
            if(rs.next())
                usr = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return usr;
    }


    //getAdministrator by username and password
    public Administrator getAdministrator(String username, String password){
        Administrator admin = null;
        if(username.isEmpty() || password.isEmpty()) return null;
        try {
            findAdminStatement.setString(1, username);
            findAdminStatement.setString(2, password);
            ResultSet rs = findAdminStatement.executeQuery();
            if(rs.next())
                admin = new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    //createBase by default
    public void createBase(){
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("users.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka... nastavljam sa praznom bazom");
        }
    }


    //return all users by username
    public ArrayList<User> returnAllUsers(String username){
        ArrayList<User> users = new ArrayList<>();
        try {
            allUsersUsernameStatement.setString(1, username);
            ResultSet rs = allUsersUsernameStatement.executeQuery();
            while(rs.next())
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //return all administrators by username
    public ArrayList<Administrator> returnAllAdmins(String username){
        ArrayList<Administrator> admins = new ArrayList<>();
        try {
            allAdminsUsernameStatement.setString(1, username);
            ResultSet rs = allAdminsUsernameStatement.executeQuery();
            while(rs.next())
                admins.add(new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }


    //add new user in database
    public User addNewUser(String name, String surname, String email, String username, String password, String selectedToggle) {
        int id= 1;
        try {
            ResultSet rs = findMaxIdStatement.executeQuery();
            if(rs.next()) id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            addNewUserStatement.setInt(1, id);
            addNewUserStatement.setString(2, name);
            addNewUserStatement.setString(3, surname);
            addNewUserStatement.setString(4, email);
            addNewUserStatement.setString(5, username);
            addNewUserStatement.setString(6, password);
            addNewUserStatement.setString(7, selectedToggle);
            addNewUserStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User usr = new User(id, name, surname, email, username, password, selectedToggle);
        return usr;
    }


    public ObservableList<User> returnAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                 users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return FXCollections.observableList(users);
    }




    public void deleteUser(int id){
        try {
            deleteUserFromBase.setInt(1, id);
            deleteUserFromBase.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editUser(String name, String surname, String email, String username, String password, String pol, int id) {
        //UPDATE users SET name=?, surname=?, username=?, email=?, password=?, gender=? WHERE id=?"
        try {
            updateUserStatement.setString(1, name);
            updateUserStatement.setString(2, surname);
            updateUserStatement.setString(3, username);
            updateUserStatement.setString(4, email);
            updateUserStatement.setString(5, password);
            updateUserStatement.setString(6, pol);
            updateUserStatement.setInt(7, id);
            updateUserStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class DatabaseDAO {
    private Connection conn;
    private PreparedStatement ps, findUserStatement, findAdminStatement, findMaxIdStatement, addNewUserStatement,
            allUsersUsernameStatement, allAdminsUsernameStatement;
    private PreparedStatement deleteUserFromBase, updateUserStatement;
    private PreparedStatement returnAllReservationsStatement, deleteReservationFromBaseStm, returnReservationsByDateStm, findMaxIdReservationStm,
           insertReservationStm, editReservationStatement;
    private PreparedStatement returnAllMenuItemsStatement, returnMenuItemsByNameStm, findMaxMenuItemIdStm, insertMenuItemStm, deleteMenuItemStatement,
            editMenuItemStm;
    private static DatabaseDAO instance = null;

    //Constructor
    private DatabaseDAO() throws SQLException {
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/restaurant.db";
        conn = DriverManager.getConnection(url);
        try {
            ps = conn.prepareStatement("SELECT * FROM users");
        } catch (SQLException e) {
            createBase();
            try {
                ps = conn.prepareStatement("SELECT * FROM users");
            } catch (SQLException ee) {
                System.out.println("Unable to select anything from users table or invalid database");
            }
        }
        try {
            findUserStatement = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            findAdminStatement = conn.prepareStatement("SELECT * FROM admins WHERE username=? AND password=?");
            findMaxIdStatement = conn.prepareStatement("SELECT MAX(id)+1 FROM users");
            addNewUserStatement = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?)");
            allUsersUsernameStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            allAdminsUsernameStatement = conn.prepareStatement("SELECT * FROM admins WHERE username=?");
            deleteUserFromBase = conn.prepareStatement("DELETE FROM users WHERE id=?");
            updateUserStatement = conn.prepareStatement("UPDATE users SET name=?, surname=?, username=?, email=?, password=?, gender=? WHERE id=?");

            returnAllReservationsStatement = conn.prepareStatement("SELECT * FROM reservations");
            returnAllMenuItemsStatement = conn.prepareStatement("SELECT * FROM menuitem");
            deleteReservationFromBaseStm=conn.prepareStatement("DELETE FROM reservations WHERE id=?");
            returnReservationsByDateStm=conn.prepareStatement("SELECT * FROM reservations WHERE date=? AND time=?");
            findMaxIdReservationStm=conn.prepareStatement("SELECT MAX(id)+1 FROM reservations");
            insertReservationStm=conn.prepareStatement("INSERT INTO reservations VALUES(?,?,?,?)");
            editReservationStatement=conn.prepareStatement("UPDATE reservations SET date=?, time=?, guests=? WHERE id=?");
            findMaxMenuItemIdStm=conn.prepareStatement("SELECT MAX(id)+1 FROM menuitem");

            returnMenuItemsByNameStm = conn.prepareStatement("SELECT * FROM menuitem WHERE name=?");
            insertMenuItemStm = conn.prepareStatement("INSERT INTO menuitem VALUES(?,?,?,?,?)");
            deleteMenuItemStatement =conn.prepareStatement("DELETE FROM menuitem WHERE id=?");
            editMenuItemStm=conn.prepareStatement("UPDATE menuitem SET name=?, price=?, vegan=?, vegetarian=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //getInstance for singleton pattern (1x connection on database)
    public static DatabaseDAO getInstance() throws SQLException {
        if (instance == null) instance = new DatabaseDAO();
        return instance;
    }

    public static void removeInstance() throws SQLException {
        if (instance == null) return;
        instance.conn.close();
        instance = null;
    }

    //getUser by username and password
    public User getUser(String username, String password) {
        User usr = null;
        if (username.isEmpty() || password.isEmpty()) return null;
        try {
            findUserStatement.setString(1, username);
            findUserStatement.setString(2, password);
            ResultSet rs = findUserStatement.executeQuery();
            if (rs.next())
                usr = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usr;
    }


    //getAdministrator by username and password
    public Administrator getAdministrator(String username, String password) {
        Administrator admin = null;
        if (username.isEmpty() || password.isEmpty()) return null;
        try {
            findAdminStatement.setString(1, username);
            findAdminStatement.setString(2, password);
            ResultSet rs = findAdminStatement.executeQuery();
            if (rs.next())
                admin = new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    //createBase by default
    public void createBase() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("users.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
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
    public ArrayList<User> returnAllUsers(String username) {
        ArrayList<User> users = new ArrayList<>();
        try {
            allUsersUsernameStatement.setString(1, username);
            ResultSet rs = allUsersUsernameStatement.executeQuery();
            while (rs.next())
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //return all administrators by username
    public ArrayList<Administrator> returnAllAdmins(String username) {
        ArrayList<Administrator> admins = new ArrayList<>();
        try {
            allAdminsUsernameStatement.setString(1, username);
            ResultSet rs = allAdminsUsernameStatement.executeQuery();
            while (rs.next())
                admins.add(new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }


    //add new user in database
    public User addNewUser(String name, String surname, String email, String username, String password, String selectedToggle) {
        int id = 1;
        try {
            ResultSet rs = findMaxIdStatement.executeQuery();
            if (rs.next()) id = rs.getInt(1);
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
            if(selectedToggle.equals("Muško") || selectedToggle.equals("Male") || selectedToggle.equals("Männlich"))
                 addNewUserStatement.setString(7, "Male");
            else addNewUserStatement.setString(7, "Female");
            addNewUserStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User usr = new User(id, name, surname, email, username, password, selectedToggle);
        return usr;
    }


    public ObservableList<User> returnAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableList(users);
    }


    public void deleteUser(int id) {
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

    public ObservableList<Reservation> returnAllReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            ResultSet rs = returnAllReservationsStatement.executeQuery();
            while (rs.next()) {
                  String date = rs.getString(2);
                  DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ld = LocalDate.parse(date, df);
                    reservations.add(new Reservation(rs.getInt(1), ld, rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableList(reservations);
    }


    public ObservableList<MenuItem> returnAllMenuItems() {
        ArrayList<MenuItem> menuItems=new ArrayList<>();
        try{
            ResultSet rs = returnAllMenuItemsStatement.executeQuery();
            while (rs.next())
            menuItems.add(new MenuItem(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return FXCollections.observableList(menuItems);
    }

    public void deleteReservation(int id) {
        try {
            deleteReservationFromBaseStm.setInt(1, id);
            deleteReservationFromBaseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<Reservation> returnAllReservations(LocalDate date, String time) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            returnReservationsByDateStm.setString(1, date.toString());
            returnReservationsByDateStm.setString(2, time);
            ResultSet rs = returnReservationsByDateStm.executeQuery();
            while(rs.next()) {
                String date2 = rs.getString(2);
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ld = LocalDate.parse(date2, df);
                reservations.add(new Reservation(rs.getInt(1), ld, rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  return  reservations;
    }

    public void addNewReservation(LocalDate date, String time, int guests) {
        int id = 1;
        try {
            ResultSet rs = findMaxIdReservationStm.executeQuery();
            if (rs.next()) id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String datum = df.format(date);
            insertReservationStm.setInt(1, id);
            insertReservationStm.setString(2, datum);
            insertReservationStm.setString(3, time);
            insertReservationStm.setInt(4, guests);
            insertReservationStm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void editReservation(LocalDate date, String time, int guests, int id) {
        try {
            editReservationStatement.setString(1, String.valueOf(date));
            editReservationStatement.setString(2, time);
            editReservationStatement.setInt(3, guests);
            editReservationStatement.setInt(4, id);
            editReservationStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MenuItem> returnAllMenuItems(String name) {
        int id = 1;
        try {
            ResultSet rs = findMaxMenuItemIdStm.executeQuery();
            if (rs.next()) id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<MenuItem> menuis = new ArrayList<>();
        try {
            returnMenuItemsByNameStm.setString(1, name);
            ResultSet rs = returnMenuItemsByNameStm.executeQuery();
            while(rs.next())
                menuis.add(new MenuItem(id, rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuis;
    }

    public void addNewMenuItem(String name, double price, boolean vegan, boolean vegetarian){
        int id = 1;
        try {
            ResultSet rs = findMaxMenuItemIdStm.executeQuery();
            if (rs.next()) id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            insertMenuItemStm.setInt(1,id);
            insertMenuItemStm.setString(2, name);
            insertMenuItemStm.setDouble(3, price);
            if(vegan)
            insertMenuItemStm.setString(4, "yes");
            else insertMenuItemStm.setString(4, "no");
            if(vegetarian)
                insertMenuItemStm.setString(5, "yes");
            else insertMenuItemStm.setString(5, "no");
            insertMenuItemStm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(int id) {
        try {
            deleteMenuItemStatement.setInt(1, id);
            deleteMenuItemStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editMenuitem(String name, String price, boolean vegan, boolean vegetarian, int id) {
        try {
            editMenuItemStm.setString(1, name);
            editMenuItemStm.setDouble(2, Double.parseDouble(price));
            if(vegan) editMenuItemStm.setString(3, "yes");
            else editMenuItemStm.setString(3, "no");
            if(vegetarian) editMenuItemStm.setString(4, "yes");
            else editMenuItemStm.setString(4, "no");
            editMenuItemStm.setInt(5, id);
            editMenuItemStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
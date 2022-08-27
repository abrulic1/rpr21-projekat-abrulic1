package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class DatabaseDAO {
    private Connection conn;
    private PreparedStatement ps;
    private static DatabaseDAO instance = null;

    private DatabaseDAO() throws SQLException {
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/restaurant.db";
        conn = DriverManager.getConnection(url);
        try{
            ps = conn.prepareStatement("SELECT * FROM users");
        }catch(SQLException e){
            createBase();
            ps = conn.prepareStatement("SELECT * FROM users");
        }

    }

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


    public static DatabaseDAO getInstance() throws SQLException {
        if(instance == null) instance = new DatabaseDAO();
        return instance;
    }

    public static void removeInstance() throws SQLException {
        if(instance==null) return;
        instance.conn.close();
        instance = null;
    }


    public ArrayList<User> returnAllUsers(){
        ArrayList<User> users = new ArrayList<>();

        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}

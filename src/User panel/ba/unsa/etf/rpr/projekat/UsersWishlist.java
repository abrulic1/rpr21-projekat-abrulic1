package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;
import java.util.TreeSet;

public class UsersWishlist {
    private int id;
    private int userId;
    private TreeSet<String> meals;
    private double total;

    public UsersWishlist() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UsersWishlist(int id, int userId, TreeSet<String> meals, double total) {
        this.id = id;
        this.userId = userId;
        this.meals = meals;
        this.total = total;
    }

    public TreeSet<String> getMeals() {
        return meals;
    }

    public void setMeals(TreeSet<String> meals) {
        this.meals = meals;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

package ba.unsa.etf.rpr.projekat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class UsersWishlist {
    private int id;
    private int userId;
    private String menuitem;
    private double price;

    public UsersWishlist() {
    }

    public UsersWishlist(int id, int userId, String menuitem, double price) {
        this.id = id;
        this.userId = userId;
        this.menuitem = menuitem;
        this.price = price;
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

    public String getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(String menuitem) {
        this.menuitem = menuitem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
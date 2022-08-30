package ba.unsa.etf.rpr.projekat;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private boolean vegan, vegetarian;

    public MenuItem(int id, String name, double price, boolean vegan, boolean vegetarian) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.vegan = vegan;
        this.vegetarian = vegetarian;
    }

    public MenuItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}

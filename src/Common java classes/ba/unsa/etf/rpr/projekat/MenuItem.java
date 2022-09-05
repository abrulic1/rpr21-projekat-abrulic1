package ba.unsa.etf.rpr.projekat;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String vegan, vegetarian;

    public MenuItem(int id, String name, double price, String vegan, String vegetarian) {
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

    public String getVegan() {
        return vegan;
    }

    public void setVegan(String vegan) {
        this.vegan = vegan;
    }

    public String getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(String vegetarian) {
        this.vegetarian = vegetarian;
    }

    @Override
    public String toString(){
        return name + ", "+price +" dollars";
    }

}

package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseDAOTest {
    private DatabaseDAO dao;

    {
        try {
            dao = DatabaseDAO.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        dao.createBase();
    }
    @Test
    void getUser() {
        User neki = dao.getUser("ana", "ana");
        assertEquals(2, neki.getId());
    }

    @Test
    void getUser2() {
        var lista = dao.returnAllUsers();
        ArrayList<User> useri = lista.stream().filter(f->f.getUsername().equals("ana")).collect(Collectors.toCollection(ArrayList::new));
       boolean tacno = true;
        if(useri.size()>1) tacno = false;
        assertTrue(tacno);
    }

    @Test
    void getAdministrator() {
        Administrator neki = dao.getAdministrator("admin", "admin");
        assertEquals(1, neki.getId());
    }

    @Test
    void returnAllUsers() {
        assertEquals(2, dao.returnAllUsers().size());
    }

    @Test
    void returnAllAdmins() {
        assertEquals(1, dao.returnAllAdmins("admin").size());
    }

    @Test
    void addNewMenuItem() {
        var list = dao.returnAllMenuItems();
        MenuItem mi = dao.addNewMenuItem("name", 5, true, false);
        list.add(mi);
       ArrayList<String> priceLarger = list.stream().filter(f->f.getPrice()>10).map(menuItem -> menuItem.getName()).collect(Collectors.toCollection(ArrayList::new));
        boolean cntins=false;
       for(String s : priceLarger)
            if(s.equals("Vegan Pasta")) cntins=true;
        assertTrue(cntins);
        //Vegan Pasta's price is 25.0
    }

    @Test
    void deleteMenuItem() {
    }

    @Test
    void returnAllVeganMenuItems() {
       var list =  dao.returnAllVeganMenuItems();
       ArrayList<String> l= list.stream().map(f->f.getName()).collect(Collectors.toCollection(ArrayList::new));
       boolean cntins = false;
       if(l.contains("Lentil bolognese")) cntins=true;
       assertTrue(cntins);
       assertEquals(3, list.size());
    }

}
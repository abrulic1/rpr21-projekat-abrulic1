package ba.unsa.etf.rpr.projekat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Reservation {
    private int id;
    private LocalDate date;
    private String time;
    private int numberOfGuests;
    private int userId;
    private String guest_name, guest_surname;

    public Reservation(int id, LocalDate date, String time, int numberOfGuests, int userId, String guest_name, String guest_surname) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.userId = userId;
        this.guest_name = guest_name;
        this.guest_surname = guest_surname;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_surname() {
        return guest_surname;
    }

    public void setGuest_surname(String guest_surname) {
        this.guest_surname = guest_surname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

}

package ba.unsa.etf.rpr.projekat;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int id;
    private LocalDate date;
    private String time;
    private int numberOfGuests;

    public Reservation(int id, LocalDate date, String time, int numberOfGuests) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
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

package ba.unsa.etf.rpr.projekat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private int id;
    private String code;
    private Date time;
    private int numberOfGuests;

    public Reservation(int id, String code, Date time, int numberOfGuests) {
        this.id = id;
        this.code = code;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}

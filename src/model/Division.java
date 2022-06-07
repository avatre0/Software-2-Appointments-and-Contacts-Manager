package model;

import java.time.LocalDateTime;

public class Division {
    private int id;
    private String division;
    private int countryID;

    /**
     * Constructor
     * @param id
     * @param division
     * @param countryID
     */
    public Division(int id, String division, int countryID) {
        this.id = id;
        this.division = division;
        this.countryID = countryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}

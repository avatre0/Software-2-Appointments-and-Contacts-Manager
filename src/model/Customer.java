package model;

import java.time.LocalDateTime;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;
    private int divisionID;

    /**
     * Constructor
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @param country
     * @param divisionID
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String division, String country, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
        this.divisionID = divisionID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDivision(){ return division; }

    public void setDivision(String division){ this.division = division; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}

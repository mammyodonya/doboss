package com.univerity.chartsturt.doby.Model;

public class DobieDetails {
    String firstName,lastname,location,phone_number;

    public DobieDetails(String firstName, String lastname, String location, String phone_number) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.location = location;
        this.phone_number = phone_number;
    }

    public DobieDetails() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}

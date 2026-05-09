package org.example.model;

import java.util.UUID;

public class TemporaryUser {
    private String name;
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String company;
    private String address1;
    private String address2;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobile;
    private String birthDay;
    private String birthMonth;
    private String birthYear;

    private TemporaryUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // method - create random user
    public static TemporaryUser createRandom() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String name = "Random_" + uniqueId;
        String email = "random_" + uniqueId + "@test.com";

        return new TemporaryUser(name, email);
    }

    // A SEPARATE method for filling in the remaining fields
    public TemporaryUser accountFields() {
        this.password = "Test123!";
        this.firstName = "John";
        this.lastName = "Doe";
        this.company = "Test Company";
        this.address1 = "123 Main Street";
        this.address2 = "Apt 4B";
        this.country = "United States";
        this.state = "California";
        this.city = "Los Angeles";
        this.zipcode = "90210";
        this.mobile = "1234567890";
        this.birthDay = "10";
        this.birthMonth = "May";
        this.birthYear = "1990";
        return this;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }

    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCompany() { return company; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getZipcode() { return zipcode; }
    public String getMobile() { return mobile; }
    public String getBirthDay() { return birthDay; }
    public String getBirthMonth() { return birthMonth; }
    public String getBirthYear() { return birthYear; }
}
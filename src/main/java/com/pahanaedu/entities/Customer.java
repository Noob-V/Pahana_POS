// src/main/java/com/pahanaedu/entities/Customer.java
package com.pahanaedu.entities;

import java.time.LocalDateTime;

public class Customer {
    private Integer id;
    private String accountNumber;
    private String name;
    private String email;
    private String contactNumber;
    private String address;
    private String city;
    private String postalCode;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Customer() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    // Constructors
    public Customer(String accountNumber, String name, String email) {
        this();
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    // Add phone setter for compatibility
    public void setPhone(String phone) { this.contactNumber = phone; }
    public String getPhone() { return this.contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // Add city getter and setter
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    // Add postal code getter and setter
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Utility methods
    public String getDisplayName() {
        return name + " (" + accountNumber + ")";
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", active=" + active +
                '}';
    }
}
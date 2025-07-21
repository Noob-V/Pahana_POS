package com.pahanaedu.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Customer {
    private String accountNumber;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Customer() {
        this.active = true;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Date getCreatedDateAsDate() {
        return createdDate != null ?
                Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public Date getUpdatedDateAsDate() {
        return updatedDate != null ?
                Date.from(updatedDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
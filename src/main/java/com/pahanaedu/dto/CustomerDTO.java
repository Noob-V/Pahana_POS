package com.pahanaedu.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CustomerDTO {
    private String accountNumber;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String phoneNumber;
    private boolean active;
    private int unitsConsumed;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Existing getters and setters...
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getUnitsConsumed() { return unitsConsumed; }
    public void setUnitsConsumed(int unitsConsumed) { this.unitsConsumed = unitsConsumed; }

    // Add these new methods:
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

    public Date getCreatedDateAsDate() {
        return createdDate != null ?
                Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public Date getUpdatedDateAsDate() {
        return updatedDate != null ?
                Date.from(updatedDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
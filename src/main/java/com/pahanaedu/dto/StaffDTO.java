package com.pahanaedu.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class StaffDTO {
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastLogin;

    public StaffDTO() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public Date getCreatedDateAsDate() {
        return createdDate != null ?
                Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
    public Date getLastLoginAsDate() {
        return lastLogin != null ?
                Date.from(lastLogin.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
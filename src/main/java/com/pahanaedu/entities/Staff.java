package com.pahanaedu.entities;

public class Staff extends User {

    public Staff() {
        super();
        this.setRole("STAFF");
    }

    public Staff(String username, String passwordHash, String fullName, String email) {
        super(username, passwordHash, fullName, email, "STAFF");
    }
}
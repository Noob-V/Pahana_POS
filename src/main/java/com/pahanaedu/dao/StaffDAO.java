package com.pahanaedu.dao;

import com.pahanaedu.entities.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffDAO {
    List<Staff> findAllStaff();
    Optional<Staff> findStaffByUsername(String username);
    Optional<Staff> findStaffById(Integer id);
    Staff saveStaff(Staff staff);
    Staff updateStaff(Staff staff);
    boolean deleteStaff(String username);
}
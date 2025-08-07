package com.pahanaedu.services.impl;

import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.dao.impl.StaffDAOImpl;
import com.pahanaedu.dto.StaffDTO;
import com.pahanaedu.entities.Staff;
import com.pahanaedu.mapper.StaffMapper;
import com.pahanaedu.services.StaffService;
import com.pahanaedu.utils.PasswordUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StaffServiceImpl implements StaffService {
    private final StaffDAO staffDAO;

    public StaffServiceImpl() {
        this.staffDAO = new StaffDAOImpl();
    }

    @Override
    public List<StaffDTO> findAllStaff() {
        return staffDAO.findAllStaff()
                .stream()
                .map(StaffMapper::toDTO) // Remove casting since it's already Staff
                .collect(Collectors.toList());
    }

    @Override
    public StaffDTO findStaffByUsername(String username) {
        Optional<Staff> staff = staffDAO.findStaffByUsername(username); // Changed from Optional<User>
        return staff.map(StaffMapper::toDTO).orElse(null); // Remove casting
    }

    @Override
    public StaffDTO findStaffById(Integer id) {
        Optional<Staff> staff = staffDAO.findStaffById(id); // Changed from Optional<User>
        return staff.map(StaffMapper::toDTO).orElse(null); // Remove casting
    }

    @Override
    public StaffDTO saveStaff(StaffDTO staffDTO) {
        validateStaffDTO(staffDTO);

        if (staffDAO.findStaffByUsername(staffDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Staff staff = StaffMapper.toEntity(staffDTO);
        staff.setPasswordHash(PasswordUtils.hashPassword(staffDTO.getPassword()));
        staff.setRole("STAFF");

        Staff savedStaff = staffDAO.saveStaff(staff); // Changed from User
        return StaffMapper.toDTO(savedStaff); // Remove casting
    }

    @Override
    public StaffDTO updateStaff(StaffDTO staffDTO) {
        validateStaffDTO(staffDTO);

        Staff staff = StaffMapper.toEntity(staffDTO);
        Staff updatedStaff = staffDAO.updateStaff(staff);
        return StaffMapper.toDTO(updatedStaff);
    }

    @Override
    public boolean deleteStaff(String username) {
        return staffDAO.deleteStaff(username);
    }

    private void validateStaffDTO(StaffDTO staffDTO) {
        if (staffDTO.getUsername() == null || staffDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (staffDTO.getFullName() == null || staffDTO.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (staffDTO.getEmail() == null || !staffDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
    }
}
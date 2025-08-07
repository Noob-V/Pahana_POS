package com.pahanaedu.services;

import com.pahanaedu.dto.StaffDTO;
import java.util.List;

public interface StaffService {
    List<StaffDTO> findAllStaff();
    StaffDTO findStaffByUsername(String username);
    StaffDTO findStaffById(Integer id);
    StaffDTO saveStaff(StaffDTO staffDTO);
    StaffDTO updateStaff(StaffDTO staffDTO);
    boolean deleteStaff(String username);
}
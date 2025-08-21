package com.pahanaedu.controllers;

import com.pahanaedu.dto.StaffDTO;
import com.pahanaedu.entities.User;
import com.pahanaedu.services.StaffService;
import com.pahanaedu.services.impl.StaffServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StaffController", urlPatterns = {"/staff", "/staff/*"})
public class StaffController extends HttpServlet {
    private StaffService staffService;

    @Override
    public void init() throws ServletException {
        staffService = new StaffServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            listStaff(request, response);
        } else if (pathInfo.equals("/add")) {
            showAddForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo.equals("/delete")) {
            deleteStaff(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/add")) {
            addStaff(request, response);
        } else if (pathInfo.equals("/edit")) {
            updateStaff(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<StaffDTO> staffList = staffService.findAllStaff();
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("/WEB-INF/views/staff/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/staff/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username != null) {
            StaffDTO staff = staffService.findStaffByUsername(username);
            if (staff != null) {
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/WEB-INF/views/staff/edit.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/staff?error=Staff not found");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/staff");
        }
    }

    private void addStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setUsername(request.getParameter("username"));
            staffDTO.setFullName(request.getParameter("fullName"));
            staffDTO.setEmail(request.getParameter("email"));
            staffDTO.setPassword(request.getParameter("password"));
            staffDTO.setActive("true".equals(request.getParameter("active")));

            staffService.saveStaff(staffDTO);
            response.sendRedirect(request.getContextPath() + "/staff?message=staff-added");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/staff/add.jsp").forward(request, response);
        }
    }

    private void updateStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setId(Integer.parseInt(request.getParameter("id")));
            staffDTO.setUsername(request.getParameter("username"));
            staffDTO.setFullName(request.getParameter("fullName"));
            staffDTO.setEmail(request.getParameter("email"));
            staffDTO.setActive("true".equals(request.getParameter("active")));

            staffService.updateStaff(staffDTO);
            response.sendRedirect(request.getContextPath() + "/staff?message=staff-updated");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            String username = request.getParameter("username");
            StaffDTO staff = staffService.findStaffByUsername(username);
            request.setAttribute("staff", staff);
            request.getRequestDispatcher("/WEB-INF/views/staff/edit.jsp").forward(request, response);
        }
    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username != null) {
            try {
                staffService.deleteStaff(username);
                response.sendRedirect(request.getContextPath() + "/staff?message=staff-deleted");
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/staff?error=" + e.getMessage());
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/staff");
        }
    }
}
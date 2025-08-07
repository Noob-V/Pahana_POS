package com.pahanaedu.patterns.factory;

import com.pahanaedu.dao.CustomerDAO;

public class CustomerDAOProduct implements DAOProduct<CustomerDAO> {
    private final CustomerDAO customerDAO;

    public CustomerDAOProduct(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public CustomerDAO getDAO() {
        return customerDAO;
    }
}
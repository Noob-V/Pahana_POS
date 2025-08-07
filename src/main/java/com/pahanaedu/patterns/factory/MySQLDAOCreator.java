package com.pahanaedu.patterns.factory;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.impl.CustomerDAOImpl;
import com.pahanaedu.dao.impl.BillDAOImpl;

public class MySQLDAOCreator<T> extends DAOCreator<T> {
    @Override
    @SuppressWarnings("unchecked")
    public DAOProduct<T> createDAO(String type) {
        switch (type) {
            case "customer":
                return (DAOProduct<T>) new CustomerDAOProduct(new CustomerDAOImpl());
            case "bill":
                return (DAOProduct<T>) new BillDAOProduct(new BillDAOImpl());
            default:
                throw new IllegalArgumentException("Unknown DAO type: " + type);
        }
    }
}
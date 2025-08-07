package com.pahanaedu.patterns.factory;

import com.pahanaedu.dao.BillDAO;

public class BillDAOProduct implements DAOProduct<BillDAO> {
    private final BillDAO billDAO;

    public BillDAOProduct(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    @Override
    public BillDAO getDAO() {
        return billDAO;
    }
}
package com.pahanaedu.patterns.factory;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.patterns.builder.CustomerBuilder;

public class CustomerFactory {
    private final DAOCreator<CustomerDAO> daoCreator;

    public CustomerFactory(DAOCreator<CustomerDAO> daoCreator) {
        this.daoCreator = daoCreator;
    }

    public Customer createSampleCustomer(String name, String phone, String email) {
        CustomerDAO customerDAO = daoCreator.createDAO("customer").getDAO();

        return new CustomerBuilder()
                .name(name)
                .phoneNumber(phone)
                .email(email)
                .address("Sample Address")
                .active(true)
                .withAutoGeneration(customerDAO)
                .build();
    }

    public Customer createCustomerFromRequest(String accountNumber, String name,
                                              String address, String phone, String email) {
        CustomerDAO customerDAO = daoCreator.createDAO("customer").getDAO();

        CustomerBuilder builder = new CustomerBuilder()
                .name(name)
                .address(address)
                .phoneNumber(phone)
                .email(email)
                .active(true);

        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            builder.accountNumber(accountNumber);
        }

        return builder.withAutoGeneration(customerDAO).buildWithValidation();
    }

    public Customer createPremiumCustomer(String name, String phone, String email, String address) {
        CustomerDAO customerDAO = daoCreator.createDAO("customer").getDAO();

        return new CustomerBuilder()
                .accountNumber("PREM" + customerDAO.generateNextAccountNumber())
                .name(name)
                .phoneNumber(phone)
                .email(email)
                .address(address)
                .active(true)
                .build();
    }
}
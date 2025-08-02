package com.pahanaedu.patterns.factory;

import com.pahanaedu.dao.*;
import com.pahanaedu.dao.impl.*;
import com.pahanaedu.mappers.*;
import com.pahanaedu.services.*;
import com.pahanaedu.services.impl.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final Map<Class<?>, Object> services = new HashMap<>();
    private static final Map<Class<?>, Object> daos = new HashMap<>();
    private static final Map<Class<?>, Object> mappers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getDAO(Class<T> daoClass) {
        if (!daos.containsKey(daoClass)) {
            if (daoClass == BookDAO.class) {
                daos.put(daoClass, new BookDAOImpl());
            } else if (daoClass == CustomerDAO.class) {
                daos.put(daoClass, new CustomerDAOImpl());
            } else if (daoClass == BillDAO.class) {
                daos.put(daoClass, new BillDAOImpl());
            } else if (daoClass == CategoryDAO.class) {
                daos.put(daoClass, new CategoryDAOImpl());
            } else if (daoClass == UserDAO.class) {
                daos.put(daoClass, new UserDAOImpl());
            } else if (daoClass == StaffDAO.class) {
                daos.put(daoClass, new StaffDAOImpl());
            }
        }
        return (T) daos.get(daoClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getMapper(Class<T> mapperClass) {
        if (!mappers.containsKey(mapperClass)) {
            if (mapperClass == BookMapper.class) {
                mappers.put(mapperClass, new BookMapper());
            } else if (mapperClass == CustomerMapper.class) {
                mappers.put(mapperClass, new CustomerMapper());
            } else if (mapperClass == BillMapper.class) {
                mappers.put(mapperClass, new BillMapper());
            }
        }
        return (T) mappers.get(mapperClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> serviceClass) {
        if (!services.containsKey(serviceClass)) {
            if (serviceClass == BookService.class) {
                BookDAO dao = getDAO(BookDAO.class);
                BookMapper mapper = getMapper(BookMapper.class);
                services.put(serviceClass, new BookServiceImpl(dao, mapper));
            } else if (serviceClass == CustomerService.class) {
                CustomerDAO dao = getDAO(CustomerDAO.class);
                CustomerMapper mapper = getMapper(CustomerMapper.class);
                services.put(serviceClass, new CustomerServiceImpl(dao, mapper));
            } else if (serviceClass == BillService.class) {
                BillDAO dao = getDAO(BillDAO.class);
                services.put(serviceClass, new BillServiceImpl(dao));
            } else if (serviceClass == CategoryService.class) {
                services.put(serviceClass, new CategoryServiceImpl(getDAO(CategoryDAO.class)));
            } else if (serviceClass == StaffService.class) {
                services.put(serviceClass, new StaffServiceImpl(getDAO(StaffDAO.class)));
            } else if (serviceClass == UserService.class) {
                services.put(serviceClass, new UserServiceImpl(getDAO(UserDAO.class)));
            }
        }
        return (T) services.get(serviceClass);
    }
}
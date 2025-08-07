package com.pahanaedu.patterns.factory;

public interface DAOProduct<T> {
    T getDAO();
}
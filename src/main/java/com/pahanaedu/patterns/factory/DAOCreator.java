package com.pahanaedu.patterns.factory;

public abstract class DAOCreator<T> {
    public abstract DAOProduct<T> createDAO(String type);
}
package com.expeditors.musicaltrack.services;

import java.util.List;

public interface ServiceBase<T> {
    List<T> getAll();
    T getById(int id);
    T insert(T model);
    boolean update(int id, T model);
    boolean delete(int id);
}

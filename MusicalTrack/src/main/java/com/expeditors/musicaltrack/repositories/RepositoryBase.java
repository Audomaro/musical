package com.expeditors.musicaltrack.repositories;

import java.util.List;
import java.util.function.Predicate;

public interface RepositoryBase<T> {
    List<T> getAll();
    T getById(int id);
    T insert(T model);
    boolean update(int id, T model);
    boolean delete(int id);
    List<T> getBy(Predicate<T> predicate);
}

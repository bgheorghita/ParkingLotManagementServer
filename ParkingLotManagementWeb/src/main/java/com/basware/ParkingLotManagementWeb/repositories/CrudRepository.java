package com.basware.ParkingLotManagementWeb.repositories;

import java.util.List;

public interface CrudRepository<T> {
    boolean save(T t);
    long deleteAll(Class<? extends T> cls);
    boolean delete(T t);
    List<? extends T> findAll(Class<? extends T> cls);
}

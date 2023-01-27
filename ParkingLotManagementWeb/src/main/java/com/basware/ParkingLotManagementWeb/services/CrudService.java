package com.basware.ParkingLotManagementWeb.services;

import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrudService<T> {
    Optional<T> save(T t);
    long deleteAll();
    long deleteByFieldValues(Map<String, BsonValue> fieldValuesMap, boolean multi);
    boolean deleteById(ObjectId objectId);
    List<T> findAllByFieldValues(Map<String, BsonValue> fieldValuesMap);
    List<T> findAll();
    Optional<T> findById(ObjectId objectId);
}

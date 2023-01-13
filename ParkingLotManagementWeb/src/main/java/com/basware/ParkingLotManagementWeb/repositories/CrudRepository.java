package com.basware.ParkingLotManagementWeb.repositories;

import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrudRepository<T> {
    boolean save(T t);
    long deleteAll();
    long deleteByFieldValues(Map<String, BsonValue> fieldValueMap, boolean multi);
    boolean deleteById(ObjectId objectId);
    List<T> findAllByFieldValues(Map<String, BsonValue> fieldValueMap);
    List<T> findAll();
    Optional<T> findById(ObjectId objectId);
}
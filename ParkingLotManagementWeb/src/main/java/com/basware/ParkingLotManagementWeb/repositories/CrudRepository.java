package com.basware.ParkingLotManagementWeb.repositories;

import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T t) throws TooManyRequestsException, SaveException;
    boolean save(ObjectId objectId, T t);
    long deleteAll();
    long deleteByFieldValues(Map<String, BsonValue> fieldValueMap, boolean multi);
    boolean deleteById(ObjectId objectId);
    List<T> findAllByFieldValues(Map<String, BsonValue> fieldValueMap);
    List<T> findAllByNotMatchValue(String field, String value);
    List<T> findAll();
    Optional<T> findById(ObjectId objectId);
}
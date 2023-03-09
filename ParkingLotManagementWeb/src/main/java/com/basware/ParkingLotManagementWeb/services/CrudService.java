package com.basware.ParkingLotManagementWeb.services;

import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrudService<T> {
    T save(T t) throws TooManyRequestsException, SaveException;
    long deleteAll();
    long deleteByFieldValues(Map<String, BsonValue> fieldValuesMap, boolean multi);
    boolean deleteById(ObjectId objectId);
    List<T> findAllByFieldValues(Map<String, BsonValue> fieldValuesMap);
    List<T> findAllByNotMatchValue(String field, String value);
    List<T> findAll();
    Optional<T> findById(ObjectId objectId);
}

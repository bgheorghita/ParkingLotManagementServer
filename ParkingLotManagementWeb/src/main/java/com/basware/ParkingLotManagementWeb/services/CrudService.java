package com.basware.ParkingLotManagementWeb.services;

import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface CrudService<T> {
    boolean save(T t);
    long deleteAll();
    long deleteByFieldValues(Map<String, BsonValue> fieldValuesMap, boolean multi);
    boolean deleteById(ObjectId objectId);
    List<T> findAllByFieldValues(Map<String, BsonValue> fieldValuesMap);
    List<T> findAll();
    T findById(ObjectId objectId) throws ResourceNotFoundException;
}

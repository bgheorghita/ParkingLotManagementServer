package com.basware.ParkingLotManagementWeb.services;

import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;
import org.bson.BsonValue;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrudServiceImpl<T> implements CrudService<T>{
    @Autowired
    protected CrudRepository<T> crudRepository;

    @Override
    public T save(T t) throws TooManyRequestsException, SaveException {
        return crudRepository.save(t);
    }

    @Override
    public long deleteAll() {
        return crudRepository.deleteAll();
    }

    @Override
    public long deleteByFieldValues(Map<String, BsonValue> fieldValuesMap, boolean multi) {
        return crudRepository.deleteByFieldValues(fieldValuesMap, multi);
    }

    @Override
    public boolean deleteById(ObjectId objectId) {
        return crudRepository.deleteById(objectId);
    }

    @Override
    public List<T> findAllByFieldValues(Map<String, BsonValue> fieldValuesMap) {
        return crudRepository.findAllByFieldValues(fieldValuesMap);
    }

    @Override
    public List<T> findAllByNotMatchValue(String field, String value) {
        return crudRepository.findAllByNotMatchValue(field, value);
    }

    @Override
    public List<T> findAll() {
        return crudRepository.findAll();
    }

    @Override
    public Optional<T> findById(ObjectId objectId) {
        return crudRepository.findById(objectId);
    }
}

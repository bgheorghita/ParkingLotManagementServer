package com.basware.ParkingLotManagementWeb.services;

import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class CrudServiceImpl<T> implements CrudService<T>{
    protected final CrudRepository<T> crudRepository;

    public CrudServiceImpl(CrudRepository<T> crudRepository){
        this.crudRepository = crudRepository;
    }

    @Override
    public boolean save(T t) {
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
    public List<T> findAll() {
        return crudRepository.findAll();
    }

    @Override
    public T findById(ObjectId objectId) throws ResourceNotFoundException {
        return crudRepository.findById(objectId).orElseThrow(ResourceNotFoundException::new);
    }
}

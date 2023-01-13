package com.basware.ParkingLotManagementWeb.repositories;

import com.mongodb.MongoWriteException;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.internal.MorphiaCursor;
import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrudRepositoryImpl<T> implements CrudRepository<T> {

    protected final Datastore datastore;
    private final Class<T> classType;

    public CrudRepositoryImpl(Class<T> cls, Datastore datastore) {
        this.classType = cls;
        this.datastore = datastore;
    }

    @Override
    public boolean save(T t) {
        try {
            datastore.save(t);
            return true;
        } catch (MongoWriteException e) {
            return false;
        }
    }

    @Override
    public long deleteAll() {
        return deleteByFieldValues(Map.of(), true);
    }

    @Override
    public long deleteByFieldValues(Map<String, BsonValue> fieldValueMap, boolean multi) {
        Query<T> query = datastore.find(classType);
        applyFilters(query, fieldValueMap);
        return query.delete(new DeleteOptions().multi(multi)).getDeletedCount();
    }

    @Override
    public boolean deleteById(ObjectId objectId) {
        return deleteByFieldValues(Map.of("_id", new BsonObjectId(objectId)), false) == 1;
    }

    @Override
    public List<T> findAllByFieldValues(Map<String, BsonValue> fieldValueMap) {
        Query<T> query = datastore.find(classType);
        applyFilters(query, fieldValueMap);
        List<T> resultList;

        try(MorphiaCursor<T> iterator = query.iterator()){
            resultList = iterator.toList();
        }

        return resultList;
    }

    @Override
    public List<T> findAll() {
        return findAllByFieldValues(Map.of());
    }

    @Override
    public Optional<T> findById(ObjectId objectId) {
        List<T> list = findAllByFieldValues(Map.of("_id", new BsonObjectId(objectId)));
        return list.size() > 0 ? Optional.of(list.get(0)) : Optional.empty();
    }

    private void applyFilters(Query<T> query, Map<String, BsonValue> fieldValueMap){
        fieldValueMap.forEach((field, value) -> query.filter(Filters.and(Filters.eq(field, value))));
    }
}
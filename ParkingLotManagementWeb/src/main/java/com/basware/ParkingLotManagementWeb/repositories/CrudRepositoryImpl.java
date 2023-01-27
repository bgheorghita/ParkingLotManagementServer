package com.basware.ParkingLotManagementWeb.repositories;

import com.mongodb.MongoWriteException;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.internal.MorphiaCursor;
import org.bson.BsonInt64;
import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CrudRepositoryImpl<T> implements CrudRepository<T> {

    protected final Datastore datastore;
    private final Class<T> classType;

    public CrudRepositoryImpl(Class<T> cls, Datastore datastore) {
        this.classType = cls;
        this.datastore = datastore;
    }

    @Override
    public Optional<T> save(T t) {
        try {
            T obj = datastore.save(t);
            return Optional.of(obj);
        } catch (MongoWriteException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean save(ObjectId objectId, T t) {
        return false;
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
    public boolean deleteById(Long id) {
        return deleteByFieldValues(Map.of("_id", new BsonInt64(id)), false) == 1;
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
        Filter[] filters = new Filter[fieldValueMap.size()];
        AtomicInteger i = new AtomicInteger(0);

        fieldValueMap.forEach((field, value) -> {
            filters[i.get()] = Filters.eq(field, value);
            i.getAndIncrement();
        });

        query.filter(Filters.and(filters));
    }
}
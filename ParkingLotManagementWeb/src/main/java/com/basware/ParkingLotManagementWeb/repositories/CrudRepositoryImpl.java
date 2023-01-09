package com.basware.ParkingLotManagementWeb.repositories;

import com.mongodb.MongoWriteException;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.internal.MorphiaCursor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CrudRepositoryImpl<T> implements CrudRepository<T>{

    private final Datastore datastore;

    public CrudRepositoryImpl(Datastore datastore){
        this.datastore = datastore;
    }

    @Override
    public boolean save(T t) {
        try{
            return datastore.save(t) != null;
        } catch (MongoWriteException e){
            return false;
        }
    }

    @Override
    public long deleteAll(Class<? extends T> cls) {
        return datastore.find(cls).delete(new DeleteOptions().multi(true)).getDeletedCount();
    }

    @Override
    public boolean delete(T t) {
        return datastore.delete(t).getDeletedCount() == 1;
    }

    @Override
    public List<? extends T> findAll(Class<? extends T> cls) {
        try(MorphiaCursor<? extends T> iterator = datastore.find(cls).iterator()){
            return iterator.toList();
        }

        //        try(MongoCursor<T> iterator = datastore.getMapper().getCollection(cls).find().iterator()){
        //        }
    }
}

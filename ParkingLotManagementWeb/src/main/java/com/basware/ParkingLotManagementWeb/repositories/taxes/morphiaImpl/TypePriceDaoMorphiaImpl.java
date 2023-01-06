package com.basware.ParkingLotManagementWeb.repositories.taxes.morphiaImpl;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.taxes.TypePrice;
import com.basware.ParkingLotManagementWeb.repositories.taxes.TypePriceDao;
import com.basware.ParkingLotManagementWeb.utils.parsers.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoWriteException;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.morphia.query.experimental.filters.Filters.and;
import static dev.morphia.query.experimental.filters.Filters.eq;

@Service
@Primary
public class TypePriceDaoMorphiaImpl implements TypePriceDao {

    private final Datastore datastore;

    public TypePriceDaoMorphiaImpl(Datastore datastore){
        this.datastore = datastore;
    }

    @Override
    public Optional<Price> getPriceByTypeInfo(TypeInfo typeInfo) {
        Query<TypePrice> query = datastore.find(TypePrice.class);
        TypePrice typePrice = query
                .filter(and(eq(TypePrice.TYPE_INFO_FIELD + "." + TypeInfo.TYPE_NAME_FIELD, typeInfo.getTypeName()),
                            eq(TypePrice.TYPE_INFO_FIELD + "." + TypeInfo.TYPE_VALUE_FIELD, typeInfo.getTypeValue())))
                .first();

        return typePrice == null ? Optional.empty() : Optional.of(typePrice.getPrice());
    }

    @Override
    public boolean save(TypePrice typePrice) {
        try{
            return datastore.save(typePrice) != null;
        } catch (MongoWriteException e){
            return false;
        }
    }

    @Override
    public long deleteAll() {
        return datastore.find(TypePrice.class).delete(new DeleteOptions().multi(true)).getDeletedCount();
    }
}

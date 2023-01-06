package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.taxes.TypePrice;
import com.basware.ParkingLotManagementWeb.utils.helpers.MongoDbHelper;
import com.basware.ParkingLotManagementWeb.repositories.taxes.TypePriceDao;
import com.basware.ParkingLotManagementWeb.utils.parsers.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypePriceDaoImpl implements TypePriceDao {

    private final MongoDbHelper mongoDbHelper;
    private final Logger logger = LoggerFactory.getLogger(TypePriceDaoImpl.class);

    public TypePriceDaoImpl(final MongoDbHelper mongoDbHelper){
        this.mongoDbHelper = mongoDbHelper;
    }

    @Override
    public Optional<Price> getPriceByTypeInfo(TypeInfo typeInfo) {
        Document query = new Document();
        query.append(TypePrice.TYPE_INFO_FIELD + "." + TypeInfo.TYPE_NAME_FIELD, typeInfo.getTypeName());
        query.append(TypePrice.TYPE_INFO_FIELD + "." + TypeInfo.TYPE_VALUE_FIELD, typeInfo.getTypeValue());

        Document document = mongoDbHelper.getMongoCollection(MongoDbHelper.PRICES_COLLECTION)
                .find(query)
                .first();

        if(document == null) {
            logger.info("Document not found");
            return Optional.empty();
        }

        try {
            JsonParser<Price> jsonParser = new JsonParser<>();
            Price price = jsonParser.getObjectFromJson(document.toJson(), TypePrice.PRICE_FIELD, Price.class);
            return Optional.of(price);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean save(TypePrice typePrice) {
        try {
            Document newDocument = Document.parse(new ObjectMapper().writeValueAsString(typePrice));
            mongoDbHelper.getMongoCollection(MongoDbHelper.PRICES_COLLECTION)
                    .insertOne(newDocument);
            logger.info("Saved document: " + newDocument);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public long deleteAll() {
        return mongoDbHelper.getMongoCollection(MongoDbHelper.PRICES_COLLECTION).deleteMany(new Document()).getDeletedCount();
    }
}
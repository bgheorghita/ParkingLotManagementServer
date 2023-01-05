package com.basware.ParkingLotManagementWeb.utils.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser<T> {

    public T getObjectFromJson(String json, String fieldName, Class<T> object) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(json);
        return new ObjectMapper().readValue(jsonNode.get(fieldName).toString(), object);

    }
}

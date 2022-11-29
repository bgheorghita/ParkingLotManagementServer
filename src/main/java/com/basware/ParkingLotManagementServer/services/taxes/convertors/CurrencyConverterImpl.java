package com.basware.ParkingLotManagementServer.services.taxes.convertors;

import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class CurrencyConverterImpl implements CurrencyConverter{
    @Override
    public Optional<Price> convert(Currency fromCurrency, Currency toCurrency, double amount) {
        String baseURL = "https://api.apilayer.com/exchangerates_data/convert";

        URI uri = UriComponentsBuilder.fromUriString(baseURL)
                .queryParam("to", toCurrency.name())
                .queryParam("from", fromCurrency.name())
                .queryParam("amount", String.valueOf(amount))
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.add("apiKey", System.getenv("API_LAYER_KEY"));
        try{
            ResponseEntity<String> responseEntity = new RestTemplate()
                    .exchange(uri, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
            JsonNode jsonNode = new ObjectMapper().readTree(responseEntity.getBody());

            return Optional.of(new Price(Double.parseDouble(String.valueOf(jsonNode.get("result"))), toCurrency));
        } catch (RestClientException | JsonProcessingException e){
            return Optional.empty();
        }
    }
}

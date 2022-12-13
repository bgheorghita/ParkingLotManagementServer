package com.basware.services.taxes.convertors;

import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.exceptions.ServiceNotAvailable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CurrencyConverterImpl implements CurrencyConverter{
    @Override
    public Price convert(Currency fromCurrency, Currency toCurrency, double amount) throws ServiceNotAvailable {
        final String BASE_URL = "https://api.apilayer.com/exchangerates_data/convert";

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
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
            return new Price(Double.parseDouble(String.valueOf(jsonNode.get("result"))), toCurrency);
        } catch (RestClientException e){
            throw new ServiceNotAvailable("There was a problem in calling the external server for exchange rates.");
        } catch (JsonProcessingException e) {
            throw new ServiceNotAvailable("There was a problem in processing the exchange rates.");
        }
    }
}

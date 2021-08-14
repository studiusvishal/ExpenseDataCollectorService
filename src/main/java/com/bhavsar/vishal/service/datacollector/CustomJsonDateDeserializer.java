package com.bhavsar.vishal.service.datacollector;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String utilDate = jsonParser.getText();
        try {
            java.util.Date uDate = format.parse(utilDate);
            return new Date(uDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

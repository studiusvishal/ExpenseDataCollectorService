package com.bhavsar.vishal.service.datacollector;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.bhavsar.vishal.service.datacollector.Constants.DATE_FORMAT;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(final JsonParser jsonParser,
                            final DeserializationContext deserializationContext) throws IOException {
        final var format = new SimpleDateFormat(DATE_FORMAT);
        final var utilDate = jsonParser.getText();
        try {
            final var date = format.parse(utilDate);
            return new Date(date.getTime());
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

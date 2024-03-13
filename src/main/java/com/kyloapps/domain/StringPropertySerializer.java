package com.kyloapps.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/** Serialize StringProperties like String. */
public class StringPropertySerializer extends StdSerializer<StringProperty> {
    protected StringPropertySerializer(Class<StringProperty> t) {
        super(t);
    }

    public StringPropertySerializer() {
        this(null);
    }

    @Override
    public void serialize(StringProperty stringProperty, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(stringProperty.get());
    }
}
